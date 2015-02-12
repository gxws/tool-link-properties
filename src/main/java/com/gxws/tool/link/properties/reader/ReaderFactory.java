package com.gxws.tool.link.properties.reader;

import com.gxws.tool.link.properties.exception.LinkPropertiesBaseException;

/**
 * 获取配置读取接口
 * 
 * @author 朱伟亮
 * @create 2015年2月11日上午8:54:26
 *
 */
public class ReaderFactory {

	private Reader fileReader;

	private Reader httpReader;

	private Reader zookeeperReader;

	private Reader redisReader;

	public Reader getReader(ReaderType type)
			throws LinkPropertiesBaseException {
		if (null == fileReader) {
			fileReader = new FileReader();
		}
		switch (type.name()) {
		case "HTTP":
			if (null == httpReader) {
				httpReader = new HttpReader((FileReader) fileReader);
			}
			return httpReader;
		case "ZK":
			if (null == zookeeperReader) {
				zookeeperReader = new ZookeeperReader((FileReader) fileReader);
			}
			return zookeeperReader;
		case "REDIS":
			if (null == redisReader) {
				redisReader = new RedisReader((FileReader) fileReader);
			}
			return redisReader;
		default:
			return fileReader;
		}
	}

}
