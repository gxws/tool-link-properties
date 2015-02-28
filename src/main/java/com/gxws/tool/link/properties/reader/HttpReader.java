package com.gxws.tool.link.properties.reader;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 通过http方式获取远程配置信息
 * 
 * @author 朱伟亮
 * @create 2015年2月10日下午3:15:09
 *
 */
public class HttpReader implements RemoteReader {

	private final String PROPERTY_KEY_ADDR_HTTP = "global.remote.addr.http";

	@Override
	public String valueString(String propertyKey) {
		return "";
	}

	@Override
	public Set<String> localPropertyKeySet() {
		return new HashSet<>(
				Arrays.asList(new String[] { PROPERTY_KEY_ADDR_HTTP }));
	}

}
