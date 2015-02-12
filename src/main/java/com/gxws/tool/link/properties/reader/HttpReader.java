package com.gxws.tool.link.properties.reader;

import com.gxws.tool.link.properties.exception.LinkPropertiesBaseException;

/**
 * 通过http方式获取远程配置信息
 * 
 * @author 朱伟亮
 * @create 2015年2月10日下午3:15:09
 *
 */
public class HttpReader extends RemoteReader {

	private String globalRemoteAddrHttpKey = "global.remote.addr.http";

	private String globalRemoteAddrHttpValue;

	/**
	 * @author 朱伟亮
	 * @create 2015年2月10日下午3:17:49
	 * 
	 * @param linkFile
	 * @throws LinkPropertiesBaseException
	 */
	public HttpReader(FileReader linkFile) throws LinkPropertiesBaseException {
		super(linkFile);
		globalRemoteAddrHttpValue = linkFile
				.valueString(globalRemoteAddrHttpKey);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gxws.tool.link.properties.reader.RemoteReader#valueString(java.lang
	 * .String)
	 */
	@Override
	public String valueString(String propertyKey) {
		// TODO Auto-generated method stub
		return null;
	}

}
