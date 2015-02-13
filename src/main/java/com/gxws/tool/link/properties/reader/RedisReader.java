package com.gxws.tool.link.properties.reader;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.gxws.tool.link.properties.exception.LinkPropertiesBaseException;
import com.gxws.tool.link.properties.exception.LinkPropertiesRequestMissingException;

/**
 * @author 朱伟亮
 * @create 2015年2月10日下午4:51:37
 *
 */
public class RedisReader implements RemoteReader {

	private final String GLOBAL_REMOTE_ADDR_REDIS = "global.remote.addr.redis";

	/**
	 * @author 朱伟亮
	 * @create 2015年2月10日下午4:52:04
	 * 
	 * @param linkFile
	 * @throws LinkPropertiesBaseException
	 */
	public RedisReader(FileReader linkFile)
			throws LinkPropertiesRequestMissingException {
		try {
			String redisValue = linkFile.valueString(GLOBAL_REMOTE_ADDR_REDIS);
			ReaderFactory.GLOBAL_REMOTE_MAP.put(GLOBAL_REMOTE_ADDR_REDIS,
					redisValue);
		} catch (LinkPropertiesBaseException e1) {
			LinkPropertiesRequestMissingException e = new LinkPropertiesRequestMissingException();
			e.setMessage(GLOBAL_REMOTE_ADDR_REDIS);
			e.setStackTrace(e1.getStackTrace());
			throw e;
		}
	}

	@Override
	public String valueString(String propertyKey) {
		return null;
	}

	@Override
	public Set<String> ignoreSet() {
		return new HashSet<>(
				Arrays.asList(new String[] { GLOBAL_REMOTE_ADDR_REDIS }));
	}

}
