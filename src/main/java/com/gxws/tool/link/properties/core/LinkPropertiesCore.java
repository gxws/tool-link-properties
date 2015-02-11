package com.gxws.tool.link.properties.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.gxws.tool.link.properties.classtool.ClassReflect;
import com.gxws.tool.link.properties.classtool.ClassTool;
import com.gxws.tool.link.properties.exception.LinkPropertiesBaseException;
import com.gxws.tool.link.properties.info.Property;
import com.gxws.tool.link.properties.read.LinkPropertiesType;
import com.gxws.tool.link.properties.read.LinkPropertiesTypeFactory;

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

	private LinkPropertiesTypeFactory factory;

	public LinkPropertiesCore() {
		ct = new ClassReflect();
		factory = new LinkPropertiesTypeFactory();
	}

	public void handle(String[] classnames, ServletContext servletContext) {
		handle(Arrays.asList(classnames), servletContext);
	}

	public void handle(String classname, ServletContext servletContext) {
		handle(new String[] { classname }, servletContext);
	}

	public void handle(List<String> classnames, ServletContext servletContext) {
		Set<Class<?>> classSet = ct.forClasses(classnames);
		Set<Property> propertySet = ct.getProperty(classSet);
		Map<String, String> valueMap = new HashMap<>();
		Map<String, String> contextMap = new HashMap<>();
		for (Property p : propertySet) {
			try {
				LinkPropertiesType link = factory.type(p.getType());
				String value = link.get(p.getPropertyKey());
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
