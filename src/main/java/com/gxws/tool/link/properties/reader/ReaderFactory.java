package com.gxws.tool.link.properties.reader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.gxws.tool.link.properties.exception.LinkPropertiesBaseException;
import com.gxws.tool.link.properties.exception.LinkPropertiesRequestMissingException;
import com.gxws.tool.link.properties.info.Property;

/**
 * 获取配置读取接口
 * 
 * @author 朱伟亮
 * @create 2015年2月11日上午8:54:26
 *
 */
public class ReaderFactory {

	private Logger log = Logger.getLogger(getClass());

	public static Map<String, String> GLOBAL_REMOTE_MAP = new HashMap<>();

	public final static String GLOBAL_PROJECT_ENV = "global.project.env";

	public final static String GLOBAL_PROJECT_NAME = "global.project.name";

	private Set<String> localPropertyKeySet = new HashSet<>(
			Arrays.asList(new String[] { GLOBAL_PROJECT_ENV,
					GLOBAL_PROJECT_NAME }));

	private Set<String> specifiedEnvSet = new HashSet<>(
			Arrays.asList(new String[] { "dev", "test", "real" }));

	private FileReader fileReader;

	private HttpReader httpReader;

	private ZookeeperReader zookeeperReader;

	private RedisReader redisReader;

	public ReaderFactory() throws LinkPropertiesBaseException {
		fileReader = new FileReader();
		String envValue = fileReader.valueString(GLOBAL_PROJECT_ENV);
		String nameVlaue = fileReader.valueString(GLOBAL_PROJECT_NAME);
		LinkPropertiesRequestMissingException e = new LinkPropertiesRequestMissingException();
		if (null == envValue || "".equals(envValue)) {
			e.setMessage(GLOBAL_PROJECT_ENV);
			throw e;
		} else if (null == nameVlaue || "".equals(nameVlaue)) {
			e.setMessage(GLOBAL_PROJECT_NAME);
			throw e;
		} else {
			GLOBAL_REMOTE_MAP.put(GLOBAL_PROJECT_ENV, envValue);
			GLOBAL_REMOTE_MAP.put(GLOBAL_PROJECT_NAME, nameVlaue);
		}
		try {
			httpReader = new HttpReader(fileReader);
			localPropertyKeySet.addAll(httpReader.ignoreSet());
		} catch (LinkPropertiesRequestMissingException e2) {
			log.warn(e2.getMessage());
			httpReader = null;
		}
		try {
			zookeeperReader = new ZookeeperReader(fileReader);
			localPropertyKeySet.addAll(zookeeperReader.ignoreSet());
		} catch (LinkPropertiesRequestMissingException e2) {
			log.warn(e2.getMessage());
			zookeeperReader = null;
		}
		try {
			redisReader = new RedisReader(fileReader);
			localPropertyKeySet.addAll(redisReader.ignoreSet());
		} catch (LinkPropertiesRequestMissingException e2) {
			log.warn(e2.getMessage());
			redisReader = null;
		}
	}

	public Reader getReader(Property p) throws LinkPropertiesBaseException {
		String env = GLOBAL_REMOTE_MAP.get(GLOBAL_PROJECT_ENV);
		if (specifiedEnvSet.contains(env)) {
			if (localPropertyKeySet.contains(p.getPropertyKey())) {
				return fileReader;
			} else {
				return specified();
			}
		} else {
			return other(p.getType());
		}
	}

	private Reader specified() {
		if (null != zookeeperReader) {
			return zookeeperReader;
		} else if (null != redisReader) {
			return redisReader;
		} else if (null != httpReader) {
			return httpReader;
		} else {
			return null;
		}
	}

	private Reader other(ReaderType type) throws LinkPropertiesBaseException {
		switch (type.name()) {
		case "ZOOKEEPER":
			return zookeeperReader;
		case "HTTP":
			return httpReader;
		case "REDIS":
			return redisReader;
		default:
			return fileReader;
		}
	}

	public Set<String> getLocalPropertyKeySet() {
		return localPropertyKeySet;
	}
}
