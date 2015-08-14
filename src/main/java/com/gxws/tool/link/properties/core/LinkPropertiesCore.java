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
import com.gxws.tool.link.properties.reader.ReaderFactory;
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

	private static final String READER_SYSTEM_PROPERTY_KEY = "link.properties.reader";

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
		// initReader();
		initReaderSpecific();
		readLinkProperties();
	}

	/**
	 * 初始化配置源读取Reader对象。
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 * @Deprecated 1.2以后废弃，修改配置读取策略，将从系统变量读取环境和读取策略。不再从环境变量判断读取策略。
	 * @since 1.1
	 */
	@Deprecated
	private void initReader() {
		if (ProjectConstant.onlineEnvSet.contains(ProjectConstant.NAME_PROJECT_ENV)) {
			try {
				reader = new ZookeeperReader();
			} catch (LinkPropertiesReaderInitException e) {
				log.error("找不到 'link properties' 资源", e);
				return;
			}
		} else {
			try {
				reader = new FileReader();
			} catch (LinkPropertiesReaderInitException e) {
				log.error("找不到 'link properties' 资源", e);
				return;
			}
		}
	}

	/**
	 * 使用系统变量指定配置源读取对象Reader。<br>
	 * 系统变量分为操作系统环境变量和jvm环境变量。<br>
	 * 操作系统环境变量通过System.getenv(key)获取，linux通过profile设置，windows通过高级系统设置->高级->
	 * 环境变量进行设置。<br>
	 * jvm环境变量通过，System.getProperty(key)获取，通过启动java进程进行设置。<br>
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 * @since 1.2
	 */
	private void initReaderSpecific() {
		String name = System.getProperty(READER_SYSTEM_PROPERTY_KEY);
		if (null == name) {
			name = "file";
		}
		try {
			this.reader = ReaderFactory.newReaderInstance(name);
		} catch (LinkPropertiesReaderInitException e) {
			log.error(e.getMessage(), e);
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
		for (Property p : propertySet) {
			log.debug("项目自定义变量加载 " + p.getPropertyKey() + " = " + p.getValue());
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
		if (null != servletContext) {
			for (Property p : propertySet) {
				if (!p.getServletContextAttrNames().isEmpty()) {
					Set<String> attrNames = p.getServletContextAttrNames();
					for (String an : attrNames) {
						if (an.isEmpty()) {
							continue;
						} else {
							servletContext.setAttribute(an, p.getValue());
						}
					}
				}
				servletContext.setAttribute(p.getFieldName(), p.getValue());
			}
		}
	}
}
