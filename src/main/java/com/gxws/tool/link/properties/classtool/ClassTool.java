package com.gxws.tool.link.properties.classtool;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
	public Set<Class<?>> forClasses(List<String> classnames);

	/**
	 * 获取所有配置项名称
	 * 
	 * @author 朱伟亮
	 * @create 2015年2月10日下午5:00:54
	 * 
	 * @param classes
	 *            配置项类
	 * @return
	 */
	public Set<Property> getProperty(Set<Class<?>> classes);

	/**
	 * 给配置类的属性设置值
	 * 
	 * @author 朱伟亮
	 * @create 2015年2月11日上午9:01:55
	 * 
	 * @param classes
	 *            配置项类
	 * @param map
	 *            配置项和对应的值
	 */
	public void setProperty(Set<Class<?>> classes, Map<String, String> map);
}
