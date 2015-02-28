package com.gxws.tool.link.properties.reader;

import java.util.Set;

import com.gxws.tool.link.properties.exception.LinkPropertiesBaseException;

/**
 * @author 朱伟亮
 * @create 2015年2月10日下午1:55:56
 *
 */
public interface Reader {
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
	public String valueString(String key) throws LinkPropertiesBaseException;

	/**
	 * 依赖的本地参数，参数名集合
	 * 
	 * @author 朱伟亮
	 * @create 2015年2月28日上午9:59:54
	 * 
	 * @return
	 */
	public Set<String> localPropertyKeySet();

}
