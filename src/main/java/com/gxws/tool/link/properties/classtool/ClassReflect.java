package com.gxws.tool.link.properties.classtool;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.gxws.tool.link.properties.annotation.LinkProperties;
import com.gxws.tool.link.properties.info.Property;

/**
 * 类工具反射
 * 
 * @author 朱伟亮
 * @create 2015年2月10日下午1:50:39
 *
 */
public class ClassReflect implements ClassTool {
	private Logger log = Logger.getLogger(getClass());

	@Override
	public Set<Class<?>> forClasses(List<String> classnames) {
		Set<Class<?>> set = new HashSet<>();
		if (null != classnames && 0 != classnames.size()) {
			Class<?> cls = null;
			ClassLoader cl = this.getClass().getClassLoader();
			for (String className : classnames) {
				try {
					cls = Class.forName(className, false, cl);
					set.add(cls);
				} catch (ClassNotFoundException e) {
					log.error("class not found '" + className + "'");
					continue;
				}
			}
			return set;
		}
		return null;
	}

	@Override
	public Set<Property> getProperty(Set<Class<?>> classes) {
		Set<Property> set = new HashSet<>();
		Field[] fields = null;
		LinkProperties lp = null;
		Property p = null;
		for (Class<?> clz : classes) {
			fields = clz.getFields();
			for (Field field : fields) {
				lp = field.getAnnotation(LinkProperties.class);
				if (null != lp) {
					p = new Property();
					if ("".equals(lp.value())) {
						p.setPropertyKey(field.getName());
					} else {
						p.setPropertyKey(lp.value());
					}
					p.setType(lp.type());
					p.setContextScope(lp.contextScope());
					p.setFieldName(field.getName());
					p.setFullName(clz.getName() + "." + field.getName());
					set.add(p);
				}
			}
		}
		return set;
	}

	@Override
	public void setProperty(Set<Class<?>> classes, Map<String, String> map) {
		Field[] fields = null;
		LinkProperties lp = null;
		for (Class<?> clz : classes) {
			fields = clz.getFields();
			for (Field field : fields) {
				lp = field.getAnnotation(LinkProperties.class);
				if (null != lp && Modifier.isStatic(field.getModifiers())) {
					try {
						field.set(null,
								map.get(clz.getName() + "." + field.getName()));
					} catch (IllegalArgumentException | IllegalAccessException e) {
						log.error("setting value '" + map.get(field.getName())
								+ "' exception at class '" + clz.getName()
								+ "' field name '" + field.getName() + "'", e);
					}
				}
			}
		}
	}
}
