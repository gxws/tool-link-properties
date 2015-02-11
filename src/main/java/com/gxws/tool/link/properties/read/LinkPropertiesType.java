package com.gxws.tool.link.properties.read;

import com.gxws.tool.link.properties.exception.LinkPropertiesBaseException;

/**
 * @author 朱伟亮
 * @create 2015年2月10日下午1:55:56
 *
 */
public interface LinkPropertiesType {
	/**
	 * 获取属性值
	 * 
	 * @author 朱伟亮
	 * @create 2015年2月10日上午11:59:42
	 * 
	 * @param key
	 *            属性名
	 * @return
	 * @throws LinkPropertiesBaseException
	 */
	public String get(String key) throws LinkPropertiesBaseException;
}
