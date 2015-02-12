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

	private Reader fileGet;

	private Reader httpGet;

	private Reader zkGet;

	private Reader redisGet;

	public Reader get(ReaderType type)
			throws LinkPropertiesBaseException {
		if (null == fileGet) {
			fileGet = new FileReader();
		}
		switch (type.name()) {
		case "HTTP":
			if (null == httpGet) {
				httpGet = new HttpReader((FileReader) fileGet);
			}
			return httpGet;
		case "ZK":
			if (null == zkGet) {
				zkGet = new ZookeeperReader((FileReader) fileGet);
			}
			return zkGet;
		case "REDIS":
			if (null == redisGet) {
				redisGet = new RedisReader((FileReader) fileGet);
			}
			return redisGet;
		default:
			return fileGet;
		}
	}

}
