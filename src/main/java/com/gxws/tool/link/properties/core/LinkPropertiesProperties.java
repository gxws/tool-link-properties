package com.gxws.tool.link.properties.core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gxws.tool.common.constant.ProjectConstant;
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
 * @author zhuwl120820@gxwsxx.com 2015年2月10日上午11:57:19
 *
 */
public class LinkPropertiesProperties {

	private Logger log = LoggerFactory.getLogger(getClass());

	private ClassTool ct = new ReflectClassTool();

	private ProjectPropertiesCore ppc = new ProjectPropertiesCore();

	private final Set<String> envRemoteSet = new HashSet<String>(
			Arrays.asList(new String[] { "dev", "test", "real" }));

	/**
	 * 处理自定义变量
	 * 
	 * @param classnames
	 *            所有类名
	 * @param props
	 *            从spring bean factory获取的Properties对象
	 * @param servletContext
	 *            servletContext 从interceptor获取的servlet上下文
	 */
	public void handle(List<String> classnames, Properties props,
			ServletContext servletContext) {
		ppc.handle(servletContext, props);
		List<Class<?>> classList = ct.forClasses(classnames);
		Reader reader;
		// if (ProjectPropertiesCore.ENV_DEFAULT_VALUE
		// .equals(ProjectConstant.NAME_PROJECT_ENV)) {
		// try {
		// reader = new FileReader();
		// } catch (LinkPropertiesReaderInitException e) {
		// log.error("can not find 'link properties' resource", e);
		// return;
		// }
		// } else {
		// try {
		// reader = new ZookeeperReader();
		// } catch (LinkPropertiesReaderInitException e) {
		// log.error("can not find 'link properties' resource", e);
		// return;
		// }
		// }
		if (envRemoteSet.contains(ProjectConstant.NAME_PROJECT_ENV)) {
			try {
				reader = new ZookeeperReader();
			} catch (LinkPropertiesReaderInitException e) {
				log.error("can not find 'link properties' resource", e);
				return;
			}
		} else {
			try {
				reader = new FileReader();
			} catch (LinkPropertiesReaderInitException e) {
				log.error("can not find 'link properties' resource", e);
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
