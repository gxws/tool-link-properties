package com.gxws.tool.link.properties.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.gxws.tool.link.properties.classtool.ClassReflect;
import com.gxws.tool.link.properties.classtool.ClassTool;
import com.gxws.tool.link.properties.exception.LinkPropertiesBaseException;
import com.gxws.tool.link.properties.info.Property;
import com.gxws.tool.link.properties.reader.Reader;
import com.gxws.tool.link.properties.reader.ReaderFactory;

/**
 * 获取相应配置类，读取配置信息，并写入相应的变量
 * 
 * @author 朱伟亮
 * @create 2015年2月10日上午11:57:19
 *
 */
public class LinkPropertiesCore {

	private Logger log = Logger.getLogger(getClass());

	private ClassTool ct;

	private ReaderFactory factory;

	public LinkPropertiesCore() {
		ct = new ClassReflect();
		try {
			factory = new ReaderFactory();
		} catch (LinkPropertiesBaseException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 处理静态变量
	 * 
	 * @author 朱伟亮
	 * @create 2015年2月12日上午10:22:08
	 * 
	 * @param classnames
	 * @param servletContext
	 */
	public void handle(List<String> classnames, Properties props,
			ServletContext servletContext) {
		Set<Class<?>> classSet = ct.forClasses(classnames);
		Set<Property> propertySet = ct.getProperty(classSet);
		Map<String, String> valueMap = new HashMap<>();
		Map<String, String> contextMap = new HashMap<>();
		for (Property p : propertySet) {
			try {
				Reader reader = factory.getReader(p);
				if (null == reader) {
					continue;
				}
				String value = reader.valueString(p.getPropertyKey());
				if (null != value) {
					props.put(p.getPropertyKey(), value);
				}
				valueMap.put(p.getFullName(), value);
				if (p.isContextScope()) {
					contextMap.put(p.getFieldName(), value);
				}
			} catch (LinkPropertiesBaseException e) {
				log.error(e.getMessage(), e);
				continue;
			}
		}
		ct.setProperty(classSet, valueMap);
		servletContextAttribute(servletContext, contextMap);
	}

	/**
	 * 将变量放置到servletContext
	 * 
	 * @author 朱伟亮
	 * @create 2015年2月11日上午9:36:39
	 * 
	 * @param servletContext
	 * @param contextMap
	 */
	private void servletContextAttribute(ServletContext servletContext,
			Map<String, String> contextMap) {
		if (null != contextMap && 0 != contextMap.size()) {
			for (String key : contextMap.keySet()) {
				servletContext.setAttribute(key, contextMap.get(key));
			}
		}
	}

}
