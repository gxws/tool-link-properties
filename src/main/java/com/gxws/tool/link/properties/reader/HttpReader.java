package com.gxws.tool.link.properties.reader;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.gxws.tool.link.properties.exception.LinkPropertiesBaseException;
import com.gxws.tool.link.properties.exception.LinkPropertiesRequestMissingException;

/**
 * 通过http方式获取远程配置信息
 * 
 * @author 朱伟亮
 * @create 2015年2月10日下午3:15:09
 *
 */
public class HttpReader implements RemoteReader {

	private final String GLOBAL_REMOTE_ADDR_HTTP = "global.remote.addr.http";

	/**
	 * @author 朱伟亮
	 * @create 2015年2月10日下午3:17:49
	 * 
	 * @param linkFile
	 * @throws LinkPropertiesRequestMissingException
	 */
	public HttpReader(FileReader linkFile)
			throws LinkPropertiesRequestMissingException {
		try {
			String httpValue = linkFile.valueString(GLOBAL_REMOTE_ADDR_HTTP);
			ReaderFactory.GLOBAL_REMOTE_MAP.put(GLOBAL_REMOTE_ADDR_HTTP,
					httpValue);
		} catch (LinkPropertiesBaseException e1) {
			LinkPropertiesRequestMissingException e = new LinkPropertiesRequestMissingException();
			e.setMessage(GLOBAL_REMOTE_ADDR_HTTP);
			e.setStackTrace(e1.getStackTrace());
			throw e;
		}
	}

	@Override
	public String valueString(String propertyKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> ignoreSet() {
		return new HashSet<>(
				Arrays.asList(new String[] { GLOBAL_REMOTE_ADDR_HTTP }));
	}

}
