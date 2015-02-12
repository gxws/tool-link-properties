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
public class ZookeeperReader extends RemoteReader {

	private Logger log = Logger.getLogger(getClass());

	private String globalRemoteAddrZookeeperKey = "global.remote.addr.zookeeper";

	private String globalRemoteAddrZookeeperValue;

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
		super(linkFile);
		globalRemoteAddrZookeeperValue = linkFile
				.get(globalRemoteAddrZookeeperKey);
		LinkPropertiesRequestMissingException e = new LinkPropertiesRequestMissingException();
		if (null == globalRemoteAddrZookeeperValue
				|| "".equals(globalRemoteAddrZookeeperValue)) {
			e.setMessage(globalRemoteAddrZookeeperKey);
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
		Builder builder = CuratorFrameworkFactory.builder()
				.connectString(globalRemoteAddrZookeeperValue)
				.connectionTimeoutMs(5000).sessionTimeoutMs(5000)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3));
		builder.namespace(globalEnvValue);
		cf = builder.build();
		cf.start();
		cf.newNamespaceAwareEnsurePath("/" + globalEnvValue);
	}

	@Override
	public String get(String key) {
		String path = "/" + globalNameVlaue + "/" + key.replace(".", "/");
		try {
			return new String(cf.getData().forPath(path), "utf-8");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

}
