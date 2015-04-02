package com.gxws.tool.link.properties.core;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gxws.tool.common.constant.ProjectConstant;

/**
 * 处理项目全局变量
 * 
 * @author 朱伟亮
 * @create 2015年3月12日下午3:11:05
 *
 */
public class ProjectPropertiesCore {

	private Logger log = LoggerFactory.getLogger(getClass());

	public static final String ENV_DEFAULT_VALUE = "env_default";

	public static final String PORT_DEFAULT_VALUE = "port_default";

	/**
	 * 设置项目全局变量
	 * 
	 * @author 朱伟亮
	 * @create 2015年3月12日下午3:12:04
	 * 
	 * @param servletContext
	 * @param props
	 */
	public void handle(ServletContext servletContext, Properties props) {
		/**
		 * 获取值
		 */
		String env = System.getProperty(ProjectConstant.NAME_PROJECT_ENV);
		if (null == env || "".equals(env)) {
			env = ENV_DEFAULT_VALUE;
		}
		ProjectConstant.VALUE_PROJECT_ENV = env;
		ProjectConstant.put(ProjectConstant.NAME_PROJECT_ENV,
				ProjectConstant.VALUE_PROJECT_ENV);

		ProjectConstant.VALUE_PROJECT_NAME = servletContext
				.getServletContextName();
		ProjectConstant.put(ProjectConstant.NAME_PROJECT_NAME,
				ProjectConstant.VALUE_PROJECT_NAME);

		ProjectConstant.VALUE_PROJECT_VERSION = servletContext
				.getInitParameter(ProjectConstant.NAME_PROJECT_VERSION);
		ProjectConstant.put(ProjectConstant.NAME_PROJECT_VERSION,
				ProjectConstant.VALUE_PROJECT_VERSION);

		ProjectConstant.VALUE_PROJECT_IP = ips();
		ProjectConstant.put(ProjectConstant.NAME_PROJECT_IP,
				ProjectConstant.VALUE_PROJECT_IP);

		String port = System.getProperty(ProjectConstant.NAME_PROJECT_PORT);
		if (null == port || "".equals(port)) {
			port = PORT_DEFAULT_VALUE;
		}
		ProjectConstant.VALUE_PROJECT_PORT = port;
		ProjectConstant.put(ProjectConstant.NAME_PROJECT_PORT,
				ProjectConstant.VALUE_PROJECT_PORT);
		/**
		 * 将值放入servlet context
		 */
		for (String k : ProjectConstant.getAll().keySet()) {
			servletContext.setAttribute(k, ProjectConstant.get(k));
		}
		/**
		 * 将值放入spring properties
		 */
		props.putAll(ProjectConstant.getAll());
	}

	/**
	 * 获取网卡IP地址
	 * 
	 * @author 朱伟亮
	 * @create 2015年3月12日下午2:30:52
	 * 
	 * @return
	 */
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
