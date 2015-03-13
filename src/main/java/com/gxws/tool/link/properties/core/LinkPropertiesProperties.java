package com.gxws.tool.link.properties.core;

import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gxws.tool.link.properties.classtool.ReflectClassTool;
import com.gxws.tool.link.properties.classtool.ClassTool;
import com.gxws.tool.link.properties.exception.LinkPropertiesBaseException;
import com.gxws.tool.link.properties.exception.LinkPropertiesReaderInitException;
import com.gxws.tool.link.properties.info.Property;
import com.gxws.tool.link.properties.reader.FileReader;
import com.gxws.tool.link.properties.reader.Reader;
import com.gxws.tool.link.properties.reader.ZookeeperReader;

/**
 * 获取相应配置类，读取配置信息，并写入相应的变量
 * 
 * @author 朱伟亮
 * @create 2015年2月10日上午11:57:19
 *
 */
public class LinkPropertiesProperties {

	private Logger log = LoggerFactory.getLogger(getClass());

	private ClassTool ct = new ReflectClassTool();

	private ProjectPropertiesCore ppc = new ProjectPropertiesCore();

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
		ppc.handle(servletContext, props);
		List<Class<?>> classList = ct.forClasses(classnames);
		Reader reader;
		try {
			reader = new FileReader();
		} catch (LinkPropertiesReaderInitException e) {
			try {
				reader = new ZookeeperReader();
			} catch (LinkPropertiesReaderInitException e1) {
				log.error("can not find 'link properties' resource");
				return;
			}
		}
		for (Class<?> cls : classList) {
			for (Property p : ct.getProperty(cls)) {
				try {
					String value = reader.valueString(p.getPropertyKey());
					if (null != value) {
						props.put(p.getPropertyKey(), value);
						ct.setProperty(cls, p.getFieldName(), value);
					}
					servletContext.setAttribute(p.getFieldName(), value);
				} catch (LinkPropertiesBaseException e) {
					log.error(e.getMessage(), e);
					continue;
				}
			}
		}
	}
}