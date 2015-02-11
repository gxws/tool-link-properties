package com.gxws.tool.link.properties.read;

import com.gxws.tool.link.properties.annotation.Type;
import com.gxws.tool.link.properties.exception.LinkPropertiesBaseException;

/**
 * 获取配置读取接口
 * 
 * @author 朱伟亮
 * @create 2015年2月11日上午8:54:26
 *
 */
public class LinkPropertiesTypeFactory {

	private LinkPropertiesType fileGet;

	private LinkPropertiesType httpGet;

	private LinkPropertiesType zkGet;

	private LinkPropertiesType redisGet;

	public LinkPropertiesType type(Type type)
			throws LinkPropertiesBaseException {
		if (null == fileGet) {
			fileGet = new LinkPropertiesFile();
		}
		switch (type.name()) {
		case "HTTP":
			if (null == httpGet) {
				httpGet = new LinkPropertiesHttp((LinkPropertiesFile) fileGet);
			}
			return httpGet;
		case "ZK":
			if (null == zkGet) {
				zkGet = new LinkPropertiesZk((LinkPropertiesFile) fileGet);
			}
			return zkGet;
		case "REDIS":
			if (null == redisGet) {
				redisGet = new LinkPropertiesRedis((LinkPropertiesFile) fileGet);
			}
			return redisGet;
		default:
			return fileGet;
		}
	}

}
