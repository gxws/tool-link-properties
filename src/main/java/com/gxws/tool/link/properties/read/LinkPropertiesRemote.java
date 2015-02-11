package com.gxws.tool.link.properties.read;

import com.gxws.tool.link.properties.exception.LinkPropertiesBaseException;
import com.gxws.tool.link.properties.exception.LinkPropertiesRequestMissingException;

/**
 * 获取远程配置信息
 * 
 * @author 朱伟亮
 * @create 2015年2月10日下午12:00:24
 *
 */
public abstract class LinkPropertiesRemote implements LinkPropertiesType {

	protected String globalEnvKey = "global.env";

	protected String globalEnvValue;

	protected String globalNameKey = "global.name";

	protected String globalNameVlaue;

	public LinkPropertiesRemote(LinkPropertiesFile linkFile)
			throws LinkPropertiesBaseException {
		globalEnvValue = linkFile.get(globalEnvKey);
		globalNameVlaue = linkFile.get(globalNameKey);
		LinkPropertiesRequestMissingException e = new LinkPropertiesRequestMissingException();
		if (null == globalEnvValue || "".equals(globalEnvValue)) {
			e.setMessage(globalEnvKey);
			throw e;
		} else if (null == globalNameVlaue || "".equals(globalNameVlaue)) {
			e.setMessage(globalNameKey);
			throw e;
		}
	}

	@Override
	public abstract String get(String key);
	
}
