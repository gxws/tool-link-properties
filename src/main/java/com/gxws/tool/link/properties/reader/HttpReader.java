package com.gxws.tool.link.properties.reader;

import com.gxws.tool.link.properties.exception.LinkPropertiesBaseException;
import com.gxws.tool.link.properties.exception.LinkPropertiesRequestMissingException;

/**
 * 通过http方式获取远程配置信息
 * 
 * @author 朱伟亮
 * @create 2015年2月10日下午3:15:09
 *
 */
public class HttpReader extends RemoteReader {

	private String GLOBAL_REMOTE_ADDR_HTTP = "global.remote.addr.http";

	/**
	 * @author 朱伟亮
	 * @create 2015年2月10日下午3:17:49
	 * 
	 * @param linkFile
	 * @throws LinkPropertiesBaseException
	 */
	public HttpReader(FileReader linkFile) throws LinkPropertiesBaseException {
		super(linkFile);
		String httpValue = linkFile.valueString(GLOBAL_REMOTE_ADDR_HTTP);
		if (null == httpValue || "".equals(httpValue)) {
			LinkPropertiesRequestMissingException e = new LinkPropertiesRequestMissingException();
			e.setMessage(GLOBAL_REMOTE_ADDR_HTTP);
			throw e;
		} else {
			globalMap.put(GLOBAL_REMOTE_ADDR_HTTP, httpValue);
		}
	}

	@Override
	public String valueString(String propertyKey) {
		// TODO Auto-generated method stub
		return null;
	}

}
