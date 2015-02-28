package com.gxws.tool.link.properties.reader;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.gxws.tool.link.properties.exception.LinkPropertiesBaseException;

/**
 * @author 朱伟亮
 * @create 2015年2月10日下午4:51:37
 *
 */
public class RedisReader implements RemoteReader {

	private final String PROPERTY_KEY_ADDR_REDIS = "global.remote.addr.redis";

	/**
	 * @author 朱伟亮
	 * @create 2015年2月10日下午4:52:04
	 * 
	 * @param linkFile
	 * @throws LinkPropertiesBaseException
	 */
	public RedisReader() {
		// try {
		// String redisValue = linkFile.valueString(GLOBAL_REMOTE_ADDR_REDIS);
		// ReaderFactory.GLOBAL_PROPERTY_MAP.put(GLOBAL_REMOTE_ADDR_REDIS,
		// redisAddr);
		// } catch (LinkPropertiesBaseException e1) {
		// LinkPropertiesRequestMissingException e = new
		// LinkPropertiesRequestMissingException();
		// e.setMessage(GLOBAL_REMOTE_ADDR_REDIS);
		// e.setStackTrace(e1.getStackTrace());
		// throw e;
		// }
	}

	@Override
	public String valueString(String propertyKey) {
		return "";
	}

	@Override
	public Set<String> localPropertyKeySet() {
		return new HashSet<>(
				Arrays.asList(new String[] { PROPERTY_KEY_ADDR_REDIS }));
	}

}
