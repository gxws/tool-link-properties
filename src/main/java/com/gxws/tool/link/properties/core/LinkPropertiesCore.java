package com.gxws.tool.link.properties.core;

import java.util.ArrayList;
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
import com.gxws.tool.link.properties.datamodel.Property;
import com.gxws.tool.link.properties.exception.LinkPropertiesBaseException;
import com.gxws.tool.link.properties.exception.LinkPropertiesReaderInitException;
import com.gxws.tool.link.properties.reader.FileReader;
import com.gxws.tool.link.properties.reader.Reader;
import com.gxws.tool.link.properties.reader.ZookeeperReader;

/**
 * 获取相应配置类，读取配置信息，并写入相应的变量
 * 
 * @author zhuwl120820@gxwsxx.com
 * @since 1.0
 */
public class LinkPropertiesCore implements IPropertiesCore {

	private Logger log = LoggerFactory.getLogger(getClass());

	private ClassTool ct = new ReflectClassTool();

	private List<Class<?>> constantClassList = new ArrayList<Class<?>>();

	private Reader reader;

	private Set<Property> propertySet;

	/**
	 * 指定静态类，参数以List方式指定
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 * @param classnames
	 *            要读取配置的静态类列表
	 *
	 * @since 1.1
	 */
	public LinkPropertiesCore(List<String> classnames) {
		constantClassList = ct.forClasses(classnames);
		initReader();
		readLinkProperties();
	}

	/**
	 * 初始化数据读取对象reader
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 *
	 * @since 1.1
	 */
	private void initReader() {
		if (ProjectConstant.onlineEnvSet
				.contains(ProjectConstant.NAME_PROJECT_ENV)) {
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
	}

	/**
	 * 读取link properties配置信息
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 *
	 * @since 1.1
	 */
	private void readLinkProperties() {
		propertySet = new HashSet<Property>();
		for (Class<?> cls : constantClassList) {
			for (Property p : ct.getProperty(cls)) {
				try {
					String value = reader.valueString(p.getPropertyKey());
					if (null != value) {
						p.setValue(value);
						propertySet.add(p);
						ct.setProperty(p);
					}
				} catch (LinkPropertiesBaseException e) {
					log.error(e.getMessage(), e);
					continue;
				}
			}
		}
	}

	/**
	 * 不指定静态类，只读取默认系统参数
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 *
	 * @since 1.1
	 */
	public LinkPropertiesCore() {
		this(new ArrayList<String>());
	}

	/**
	 * 指定静态类，参数以String方式指定
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 * @param classnames
	 *            要读取配置的静态类，以","符号进行分隔
	 *
	 * @since 1.1
	 */
	public LinkPropertiesCore(String classnames) {
		this(new ArrayList<String>(Arrays.asList(classnames.split(","))));
	}

	/**
	 * 添加静态读取类
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 * @param cls
	 *            要读取配置的静态类
	 *
	 * @since 1.1
	 */
	public void addConstantClass(Class<?> cls) {
		constantClassList.add(cls);
	}

	/**
	 * 添加静态读取类
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 * @param clsList
	 *            要读取配置的静态类列表
	 * 
	 * @since 1.1
	 */
	public void addAllConstantClass(List<Class<?>> clsList) {
		constantClassList.addAll(clsList);
	}

	/**
	 * 设置自定义变量到spring配置
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 * @param props
	 *            spring配置读取的Properties对象
	 *
	 * @since 1.1
	 */
	@Override
	public void springProperties(Properties props) {
		for (Property p : propertySet) {
			props.put(p.getPropertyKey(), p.getValue());
		}
	}

	/**
	 * 设置自定义变量到servlet context
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 * @param servletContext
	 *            ServletContext对象
	 *
	 * @since 1.1
	 */
	@Override
	public void servletContextProperties(ServletContext servletContext) {
		for (Property p : propertySet) {
			servletContext.setAttribute(p.getFieldName(), p.getValue());
		}
	}
}
