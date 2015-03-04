package com.gxws.tool.link.properties.core;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gxws.tool.common.constant.LocalConstant;
import com.gxws.tool.common.constant.ProjectConstant;
import com.gxws.tool.link.properties.classtool.ReflectClassTool;
import com.gxws.tool.link.properties.classtool.ClassTool;
import com.gxws.tool.link.properties.constant.LinkPropertiesConstant;
import com.gxws.tool.link.properties.exception.LinkPropertiesBaseException;
import com.gxws.tool.link.properties.info.Property;
import com.gxws.tool.link.properties.reader.FileReader;
import com.gxws.tool.link.properties.reader.Reader;
import com.gxws.tool.link.properties.reader.ZookeeperReader;

/**
 * 获取相应配置类，读取配置信息，并写入相应的变量
 * 
 * @author 朱伟亮
 * @create 2015年2月10日上午11:57:19
 *
 */
public class LinkPropertiesCore {

	private Logger log = LoggerFactory.getLogger(getClass());

	private ClassTool ct = new ReflectClassTool();

	private final Set<String> ENV_SET = new HashSet<>(
			Arrays.asList(new String[] { "dev", "test", "real" }));

	private final Reader DEFAULT_REMOTE_READER = new ZookeeperReader();
	private final Reader DEFAULT_LOCAL_READER = new FileReader();

	/**
	 * 处理静态变量
	 * 
	 * @author 朱伟亮
	 * @create 2015年2月12日上午10:22:08
	 * 
	 * @param classnames
	 * @param servletContext
	 */
	public void handle(List<String> classnames, Properties props,
			ServletContext servletContext) {
		List<Class<?>> classList = new ArrayList<>();
		classList.add(LinkPropertiesConstant.class);
		classList.addAll(ct.forClasses(classnames));
		Reader reader;
		for (Class<?> cls : classList) {
			for (Property p : ct.getProperty(cls)) {
				if (ENV_SET.contains(LinkPropertiesConstant.GLOBAL_PROJECT_ENV)
						&& !LinkPropertiesConstant.GLOBAL_PROPERTY_MAP()
								.keySet().contains(p.getPropertyKey())) {
					reader = DEFAULT_REMOTE_READER;
				} else {
					reader = DEFAULT_LOCAL_READER;
				}
				try {
					String value = reader.valueString(p.getPropertyKey());
					if (null != value) {
						props.put(p.getPropertyKey(), value);
						ct.setProperty(cls, p.getFieldName(), value);
					}
					if (p.isContextScope()) {
						servletContext.setAttribute(p.getFieldName(), value);
					}
				} catch (LinkPropertiesBaseException e) {
					log.error(e.getMessage(), e);
					continue;
				}
			}
		}
		ProjectConstant.NAME = LinkPropertiesConstant.GLOBAL_PROJECT_NAME;
		ProjectConstant.ENV = LinkPropertiesConstant.GLOBAL_PROJECT_ENV;
		ProjectConstant.VERSION = LinkPropertiesConstant.GLOBAL_PROJECT_VERSION;
		LocalConstant.IP = ips();
	}

	private String ips() {
		Enumeration<NetworkInterface> netInterfaces;
		Pattern p = Pattern
				.compile("^((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)$");
		Matcher m = null;
		String ip = null;
		StringBuffer sb = new StringBuffer();
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface nif = netInterfaces.nextElement();
				Enumeration<InetAddress> iparray = nif.getInetAddresses();
				while (iparray.hasMoreElements()) {
					ip = iparray.nextElement().getHostAddress();
					m = p.matcher(ip);
					if (m.matches() && !ip.startsWith("127")) {
						sb.append("," + ip);
					}
				}
			}
			if (0 == sb.length()) {
				throw new Exception();
			}
		} catch (Exception e) {
			log.error("can't read local ip address", e);
			return "";
		}
		return sb.substring(1);
	}
}
