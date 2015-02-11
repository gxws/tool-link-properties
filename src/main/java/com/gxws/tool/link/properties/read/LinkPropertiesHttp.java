package com.gxws.tool.link.properties.read;

import com.gxws.tool.link.properties.exception.LinkPropertiesBaseException;

/**
 * 通过http方式获取远程配置信息
 * 
 * @author 朱伟亮
 * @create 2015年2月10日下午3:15:09
 *
 */
public class LinkPropertiesHttp extends LinkPropertiesRemote {
	
	private String globalRemoteAddrHttpKey = "global.remote.addr.http";

	private String globalRemoteAddrHttpValue;

	/**
	 * @author 朱伟亮
	 * @create 2015年2月10日下午3:17:49
	 * 
	 * @param linkFile
	 * @throws LinkPropertiesBaseException
	 */
	public LinkPropertiesHttp(LinkPropertiesFile linkFile)
			throws LinkPropertiesBaseException {
		super(linkFile);
		globalRemoteAddrHttpValue = linkFile.get(globalRemoteAddrHttpKey);
	}

	@Override
	public String get(String key) {
		return null;
	}

}
