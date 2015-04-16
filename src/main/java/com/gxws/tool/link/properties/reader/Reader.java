package com.gxws.tool.link.properties.reader;

import com.gxws.tool.link.properties.exception.LinkPropertiesBaseException;

/**
 * @author zhuwl120820
 *  2015年2月10日下午1:55:56
 *
 */
public interface Reader {
	/**
	 * 获取属性值
	 * 
	 * @author zhuwl120820
	 *  2015年2月10日上午11:59:42
	 * 
	 * @param key
	 *            属性名
	 * @return
	 * @throws LinkPropertiesBaseException
	 */
	public String valueString(String key) throws LinkPropertiesBaseException;

}
