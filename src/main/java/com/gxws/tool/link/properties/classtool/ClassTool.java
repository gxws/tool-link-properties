package com.gxws.tool.link.properties.classtool;

import java.util.List;

import com.gxws.tool.link.properties.info.Property;

/**
 * 读取和设置类属性等信息
 * 
 * @author 朱伟亮
 * @create 2015年2月10日下午1:51:50
 *
 */
public interface ClassTool {

	/**
	 * 获取所有配置类
	 * 
	 * @author 朱伟亮
	 * @create 2015年2月10日下午5:00:41
	 * 
	 * @param classnames
	 * @return
	 */
	public List<Class<?>> forClasses(List<String> classnames);

	/**
	 * 获取所有配置项名称
	 * 
	 * @author 朱伟亮
	 * @create 2015年2月28日上午11:35:34
	 * 
	 * @param cls
	 * @return
	 */
	public List<Property> getProperty(Class<?> cls);

	/**
	 * 给配置类的属性设置值
	 * 
	 * @author 朱伟亮
	 * @create 2015年2月28日下午12:11:48
	 * 
	 * @param cls
	 * @param fieldName
	 * @param value
	 */
	public void setProperty(Class<?> cls, String fieldName, String value);
}
