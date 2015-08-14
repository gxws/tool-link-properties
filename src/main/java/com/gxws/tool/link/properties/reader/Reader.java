package com.gxws.tool.link.properties.reader;

import com.gxws.tool.link.properties.exception.LinkPropertiesBaseException;

/**
 * 配置源接口，提供给配置读取核心业务对象调用，获取从配置源中读取配置信息的方法。<br>
 * 配置读取核心业务对象，为IPropertiesCore的实现类。<br>
 * 
 * @author zhuwl120820@gxwsxx.com
 * @since 1.0
 */
public interface Reader {
	/**
	 * 获取配置信息值
	 * 
	 * @author zhuwl120820@gxwsxx.com 2015年2月10日上午11:59:42
	 * 
	 * @param key
	 *            配置信息属性名，配置信息键
	 * @return 配置信息值
	 * @throws LinkPropertiesBaseException
	 *             读取配置信息属性值异常
	 */
	public String valueString(String key) throws LinkPropertiesBaseException;

}
