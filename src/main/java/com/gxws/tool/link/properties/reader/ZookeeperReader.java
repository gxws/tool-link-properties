package com.gxws.tool.link.properties.reader;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFrameworkFactory.Builder;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.log4j.Logger;

import com.gxws.tool.link.properties.exception.LinkPropertiesRequestMissingException;

/**
 * 通过zookeeper获取配置信息
 * 
 * @author 朱伟亮
 * @create 2015年2月10日下午2:37:19
 *
 */
public class ZookeeperReader implements RemoteReader {

	private Logger log = Logger.getLogger(getClass());

	private final String NAMESPACE = "link.properties";

	private final String GLOBAL_REMOTE_ADDR_ZOOKEEPER = "global.remote.addr.zookeeper";

	private CuratorFramework cf;

	/**
	 * @author 朱伟亮
	 * @create 2015年2月10日下午3:17:03
	 * 
	 * @param linkFile
	 * @throws LinkPropertiesRequestMissingException
	 */
	public ZookeeperReader(FileReader linkFile)
			throws LinkPropertiesRequestMissingException {
		try {
			String zkValue = linkFile.valueString(GLOBAL_REMOTE_ADDR_ZOOKEEPER);
			ReaderFactory.GLOBAL_REMOTE_MAP.put(GLOBAL_REMOTE_ADDR_ZOOKEEPER,
					zkValue);
		} catch (Exception e1) {
			LinkPropertiesRequestMissingException e = new LinkPropertiesRequestMissingException();
			e.setMessage(GLOBAL_REMOTE_ADDR_ZOOKEEPER);
			e.setStackTrace(e.getStackTrace());
			throw e;
		}
		init();
	}

	/**
	 * 初始化zk客户端
	 * 
	 * @author 朱伟亮
	 * @create 2015年2月10日下午3:56:48
	 *
	 */
	private void init() {
		Builder builder = CuratorFrameworkFactory
				.builder()
				.connectString(
						ReaderFactory.GLOBAL_REMOTE_MAP
								.get(GLOBAL_REMOTE_ADDR_ZOOKEEPER))
				.connectionTimeoutMs(5000).sessionTimeoutMs(5000)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3));
		builder.namespace(NAMESPACE);
		cf = builder.build();
		cf.start();
		cf.newNamespaceAwareEnsurePath("/" + NAMESPACE);
	}

	@Override
	public String valueString(String propertyKey) {
		String path = "/"
				+ ReaderFactory.GLOBAL_REMOTE_MAP
						.get(ReaderFactory.GLOBAL_PROJECT_ENV)
				+ "/"
				+ ReaderFactory.GLOBAL_REMOTE_MAP
						.get(ReaderFactory.GLOBAL_PROJECT_NAME) + "/"
				+ propertyKey;
		try {
			return new String(cf.getData().forPath(path), "utf-8");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public Set<String> ignoreSet() {
		return new HashSet<>(
				Arrays.asList(new String[] { GLOBAL_REMOTE_ADDR_ZOOKEEPER }));
	}

}
