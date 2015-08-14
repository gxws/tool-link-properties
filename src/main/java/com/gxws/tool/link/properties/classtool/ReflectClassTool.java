package com.gxws.tool.link.properties.classtool;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gxws.tool.link.properties.annotation.LinkProperties;
import com.gxws.tool.link.properties.datamodel.Property;

/**
 * 类工具反射
 * 
 * @author zhuwl120820@gxwsxx.com 2015年2月10日下午1:50:39
 * 
 * @since 1.0
 *
 */
public class ReflectClassTool implements ClassTool {
	private Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * @see com.gxws.tool.link.properties.classtool.ClassTool#forClasses(List)
	 */
	@Override
	public List<Class<?>> forClasses(List<String> classnames) {
		List<Class<?>> list = new ArrayList<>();
		if (null != classnames && 0 != classnames.size()) {
			Class<?> cls = null;
			ClassLoader cl = this.getClass().getClassLoader();
			for (String className : classnames) {
				try {
					cls = Class.forName(className, false, cl);
					list.add(cls);
				} catch (ClassNotFoundException e) {
					log.error("class not found '" + className + "'");
					continue;
				}
			}
			return list;
		}
		return null;
	}

	/**
	 * @see com.gxws.tool.link.properties.classtool.ClassTool#getProperty(Class)
	 */
	@Override
	public List<Property> getProperty(Class<?> cls) {
		List<Property> list = new ArrayList<>();
		Field[] fields = null;
		LinkProperties lp = null;
		Property p = null;
		Set<String> set = null;
		fields = cls.getFields();
		for (Field field : fields) {
			lp = field.getAnnotation(LinkProperties.class);
			if (null != lp && Modifier.isStatic(field.getModifiers())) {
				p = new Property();
				if (lp.value().isEmpty()) {
					p.setPropertyKey(field.getName());
				} else {
					p.setPropertyKey(lp.value());
				}
				p.setFieldName(field.getName());
				p.setFullName(cls.getName() + "." + field.getName());
				p.setClazz(cls);
				set = new HashSet<>(Arrays.asList(lp.servletContextAttrNames()));
				p.setServletContextAttrNames(set);
				try {
					p.setValue(String.valueOf(field.get(null)));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					log.error("获取值错误：在类：'" + cls.getName() + "' 字段名：'" + field.getName() + "'", e);
				}
				list.add(p);
			}
		}
		return list;
	}

	/**
	 * @see com.gxws.tool.link.properties.classtool.ClassTool#setProperty(Class,
	 *      String, String)
	 */
	@Override
	public void setProperty(Class<?> cls, String fieldName, String value) {
		LinkProperties lp;
		Field field;
		try {
			field = cls.getField(fieldName);
			lp = field.getAnnotation(LinkProperties.class);
			if (null != lp && Modifier.isStatic(field.getModifiers())) {
				field.set(null, value);
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			log.error("设置值错误：'" + value + "' 在类：'" + cls.getName() + "' 字段名：'" + fieldName + "'", e);
		}
	}

	/**
	 * @see com.gxws.tool.link.properties.classtool.ClassTool#setProperty(Property)
	 */
	@Override
	public void setProperty(Property pro) {
		setProperty(pro.getClazz(), pro.getFieldName(), pro.getValue());
	}

}
