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

import com.gxws.tool.link.properties.classtool.ReflectClassTool;
import com.gxws.tool.link.properties.classtool.ClassTool;
import com.gxws.tool.link.properties.constant.LinkPropertiesConstant;
import com.gxws.tool.link.properties.exception.LinkPropertiesBaseException;
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
public class LinkPropertiesCore {

	private Logger log = LoggerFactory.getLogger(getClass());

	private ClassTool ct = new ReflectClassTool();

	private final Set<String> ENV_SET = new HashSet<>(
			Arrays.asList(new String[] { "dev", "test", "real" }));

	private final Reader DEFAULT_REMOTE_READER = new ZookeeperReader();
	private final Reader DEFAULT_LOCAL_READER = new FileReader();

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
		List<Class<?>> classList = new ArrayList<>();
		classList.add(LinkPropertiesConstant.class);
		classList.addAll(ct.forClasses(classnames));
		Reader reader;
		for (Class<?> cls : classList) {
			for (Property p : ct.getProperty(cls)) {
				if (ENV_SET.contains(LinkPropertiesConstant.GLOBAL_PROJECT_ENV)
						&& !LinkPropertiesConstant.GLOBAL_PROPERTY_MAP()
								.keySet().contains(p.getPropertyKey())) {
					reader = DEFAULT_REMOTE_READER;
				} else {
					reader = DEFAULT_LOCAL_READER;
				}
				try {
					String value = reader.valueString(p.getPropertyKey());
					if (null != value) {
						props.put(p.getPropertyKey(), value);
						ct.setProperty(cls, p.getFieldName(), value);
					}
					if (p.isContextScope()) {
						servletContext.setAttribute(p.getFieldName(), value);
					}
				} catch (LinkPropertiesBaseException e) {
					log.error(e.getMessage(), e);
					continue;
				}
			}
		}
	}
}
