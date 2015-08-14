package com.gxws.tool.link.properties.reader;

import com.gxws.tool.link.properties.exception.LinkPropertiesReaderInitException;

/**
 * 获取配置源对象的工厂对象
 * 
 * @author zhuwl120820@gxwsxx.com
 * @since 1.2
 */
public class ReaderFactory {

	public static Reader newReaderInstance(String name) throws LinkPropertiesReaderInitException {
		String key = name.toLowerCase();
		switch (key) {
		case "zookeeper":
			return new ZookeeperReader();
		case "redis":
			return new RedisReader();
		case "http":
			return new HttpReader();
		default:
			return new FileReader();
		}
	}

}
