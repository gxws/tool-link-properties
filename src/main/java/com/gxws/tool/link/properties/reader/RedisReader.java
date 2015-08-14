package com.gxws.tool.link.properties.reader;

/**
 * 从redis配置源中读取配置信息。<br>
 * 已定义，未实现。<br>
 * 
 * @author zhuwl120820@gxwsxx.com
 * @since
 */
public class RedisReader implements RemoteReader {

	/**
	 * @see com.gxws.tool.link.properties.reader.Reader#valueString(java.lang.String)
	 */
	@Override
	public String valueString(String propertyKey) {
		return "";
	}
}
