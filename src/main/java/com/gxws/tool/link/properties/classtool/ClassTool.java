package com.gxws.tool.link.properties.classtool;

import java.util.List;

import com.gxws.tool.link.properties.datamodel.Property;

/**
 * 读取和设置类属性等信息
 * 
 * @author zhuwl120820@gxwsxx.com 2015年2月10日下午1:51:50
 *
 */
public interface ClassTool {

	/**
	 * 获取所有配置类
	 * 
	 * @author zhuwl120820@gxwsxx.com 2015年2月10日下午5:00:41
	 * 
	 * @param classnames
	 *            要加载的类全名
	 * @return 要加载的类对象
	 * @since 1.0
	 */
	public List<Class<?>> forClasses(List<String> classnames);

	/**
	 * 获取所有配置项的配置相关信息
	 * 
	 * @author zhuwl120820@gxwsxx.com 2015年2月28日上午11:35:34
	 * 
	 * @param cls
	 *            Constants类对象
	 * @return Constants类相关信息
	 * @since 1.0
	 */
	public List<Property> getProperty(Class<?> cls);

	/**
	 * 给配置类的属性设置值
	 * 
	 * @author zhuwl120820@gxwsxx.com 2015年2月28日下午12:11:48
	 * 
	 * @param cls
	 *            Constants类对象
	 * @param fieldName
	 *            配置项名称
	 * @param value
	 *            配置项值
	 * @since 1.0
	 */
	public void setProperty(Class<?> cls, String fieldName, String value);

	/**
	 * 给配置类的属性设置值
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 * @param pro
	 *            配置项的对象
	 *
	 * @since 1.1
	 */
	public void setProperty(Property pro);

}
