package com.gxws.tool.link.properties.reader;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFrameworkFactory.Builder;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gxws.tool.link.properties.constant.LinkPropertiesConstant;

/**
 * 通过zookeeper获取配置信息
 * 
 * @author 朱伟亮
 * @create 2015年2月10日下午2:37:19
 *
 */
public class ZookeeperReader implements RemoteReader {

	private Logger log = LoggerFactory.getLogger(getClass());

	private final String NAMESPACE = "link.properties";

	private final String DEFAULT_ADDR_ZOOKEEPER = "zookeeper.gxwsxx.com:17000";

	private final String PROPERTY_KEY_ADDR_ZOOKEEPER = "global.remote.addr.zookeeper";

	private CuratorFramework cf;

	/**
	 * 
	 * @author 朱伟亮
	 * @create 2015年2月28日上午9:33:45
	 *
	 */
	public void init() {
		String zkAddr = zookeeperAddr();
		Builder builder = CuratorFrameworkFactory.builder()
				.connectString(zkAddr).connectionTimeoutMs(5000)
				.sessionTimeoutMs(5000)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3));
		builder.namespace(NAMESPACE);
		cf = builder.build();
		cf.start();
		cf.newNamespaceAwareEnsurePath("/" + NAMESPACE);
	}

	@Override
	public String valueString(String propertyKey) {
		if (null == cf) {
			init();
		}
		String path = "/" + LinkPropertiesConstant.GLOBAL_PROJECT_ENV + "/"
				+ LinkPropertiesConstant.GLOBAL_PROJECT_NAME + "/"
				+ propertyKey;
		try {
			return new String(cf.getData().forPath(path), "utf-8");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	private String zookeeperAddr() {
		String addr;
		addr = LinkPropertiesConstant.GLOBAL_PROPERTY_MAP
				.get(PROPERTY_KEY_ADDR_ZOOKEEPER);
		if (null == addr || "".equals(addr)) {
			addr = DEFAULT_ADDR_ZOOKEEPER;
		}
		return addr;
	}

	@Override
	public Set<String> localPropertyKeySet() {
		return new HashSet<>(
				Arrays.asList(new String[] { PROPERTY_KEY_ADDR_ZOOKEEPER }));
	}
}
