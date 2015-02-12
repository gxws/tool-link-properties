package com.gxws.tool.link.properties.reader;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFrameworkFactory.Builder;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.log4j.Logger;

import com.gxws.tool.link.properties.exception.LinkPropertiesBaseException;
import com.gxws.tool.link.properties.exception.LinkPropertiesRequestMissingException;

/**
 * 通过zookeeper获取配置信息
 * 
 * @author 朱伟亮
 * @create 2015年2月10日下午2:37:19
 *
 */
public class ZookeeperReader implements Reader {

	private Logger log = Logger.getLogger(getClass());

	private final String GLOBAL_REMOTE_ADDR_ZOOKEEPER = "global.remote.addr.zookeeper";

	private CuratorFramework cf;

	/**
	 * @author 朱伟亮
	 * @create 2015年2月10日下午3:17:03
	 * 
	 * @param linkFile
	 * @throws LinkPropertiesBaseException
	 */
	public ZookeeperReader(FileReader linkFile)
			throws LinkPropertiesBaseException {
		String zkValue = linkFile.valueString(GLOBAL_REMOTE_ADDR_ZOOKEEPER);
		if (null == zkValue || "".equals(zkValue)) {
			LinkPropertiesRequestMissingException e = new LinkPropertiesRequestMissingException();
			e.setMessage(GLOBAL_REMOTE_ADDR_ZOOKEEPER);
			throw e;
		} else {
			ReaderFactory.GLOBAL_REMOTE_MAP.put(GLOBAL_REMOTE_ADDR_ZOOKEEPER,
					zkValue);
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
		builder.namespace(ReaderFactory.GLOBAL_REMOTE_MAP
				.get(ReaderFactory.GLOBAL_PROJECT_ENV));
		cf = builder.build();
		cf.start();
		cf.newNamespaceAwareEnsurePath("/"
				+ ReaderFactory.GLOBAL_REMOTE_MAP
						.get(ReaderFactory.GLOBAL_PROJECT_ENV));
	}

	@Override
	public String valueString(String propertyKey) {
		String path = "/"
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

}
