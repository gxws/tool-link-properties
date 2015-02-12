package com.gxws.tool.link.properties.reader;

import com.gxws.tool.link.properties.exception.LinkPropertiesBaseException;

/**
 * @author 朱伟亮
 * @create 2015年2月10日下午4:51:37
 *
 */
public class RedisReader extends RemoteReader {

	/**
	 * @author 朱伟亮
	 * @create 2015年2月10日下午4:52:04
	 * 
	 * @param linkFile
	 * @throws LinkPropertiesBaseException
	 */
	public RedisReader(FileReader linkFile)
			throws LinkPropertiesBaseException {
		super(linkFile);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String valueString(String propertyKey) {
		return null;
	}

}
