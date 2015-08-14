package com.gxws.tool.link.properties.reader;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFrameworkFactory.Builder;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gxws.tool.common.constant.ProjectConstant;
import com.gxws.tool.link.properties.exception.LinkPropertiesReaderInitException;

/**
 * 通过zookeeper实现的配置源中获取配置信息
 * 
 * @author zhuwl120820@gxwsxx.com
 * @since 1.0
 */
public class ZookeeperReader implements RemoteReader {

	private Logger log = LoggerFactory.getLogger(getClass());

	public final static String DEFAULT_NAMESPACE = "link.properties";

	public final static String DEFAULT_ADDR_ZOOKEEPER = "zookeeper.gxwsxx.com:17000";

	private CuratorFramework cf;

	/**
	 * 创建ZookeeperReader实例
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 * @throws LinkPropertiesReaderInitException
	 *             配置读取对象初始化异常
	 * @since 1.0
	 */
	public ZookeeperReader() throws LinkPropertiesReaderInitException {
		try {
			Builder builder = CuratorFrameworkFactory.builder().connectString(DEFAULT_ADDR_ZOOKEEPER)
					.connectionTimeoutMs(5000).sessionTimeoutMs(5000).retryPolicy(new ExponentialBackoffRetry(1000, 3));
			builder.namespace(DEFAULT_NAMESPACE);
			cf = builder.build();
			cf.start();
			cf.newNamespaceAwareEnsurePath("/" + DEFAULT_NAMESPACE);
		} catch (Exception e1) {
			LinkPropertiesReaderInitException e = new LinkPropertiesReaderInitException();
			e.setStackTrace(e1.getStackTrace());
			e.appendMessage(e1.getMessage());
			throw e;
		}
	}

	/**
	 * @see com.gxws.tool.link.properties.reader.Reader#valueString(java.lang.String)
	 */
	@Override
	public String valueString(String propertyKey) {
		ProjectConstant pc = ProjectConstant.instance();
		String path = "/" + pc.getEnv() + "/" + pc.getName() + "/" + propertyKey;
		try {
			return new String(cf.getData().forPath(path), "utf-8");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

}
