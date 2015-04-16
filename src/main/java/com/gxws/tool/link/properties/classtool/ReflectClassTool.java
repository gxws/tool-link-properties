package com.gxws.tool.link.properties.classtool;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gxws.tool.link.properties.annotation.LinkProperties;
import com.gxws.tool.link.properties.info.Property;

/**
 * 类工具反射
 * 
 * @author zhuwl120820@gxwsxx.com
 *  2015年2月10日下午1:50:39
 *
 */
public class ReflectClassTool implements ClassTool {
	private Logger log = LoggerFactory.getLogger(getClass());

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

	@Override
	public List<Property> getProperty(Class<?> cls) {
		List<Property> list = new ArrayList<>();
		Field[] fields = null;
		LinkProperties lp = null;
		Property p = null;
		fields = cls.getFields();
		for (Field field : fields) {
			lp = field.getAnnotation(LinkProperties.class);
			if (null != lp) {
				p = new Property();
				if ("".equals(lp.value())) {
					p.setPropertyKey(field.getName());
				} else {
					p.setPropertyKey(lp.value());
				}
				p.setFieldName(field.getName());
				p.setFullName(cls.getName() + "." + field.getName());
				list.add(p);
			}
		}
		return list;
	}

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
		} catch (NoSuchFieldException | SecurityException
				| IllegalArgumentException | IllegalAccessException e) {
			log.error("setting value '" + value + "' exception at class '"
					+ cls.getName() + "' field name '" + fieldName + "'", e);
		}
	}
}
