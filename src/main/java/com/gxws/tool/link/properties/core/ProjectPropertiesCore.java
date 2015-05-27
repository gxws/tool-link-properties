package com.gxws.tool.link.properties.core;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;
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
 * @author zhuwl120820@gxwsxx.com
 * @since 1.0
 */
public class ProjectPropertiesCore {

	private Logger log = LoggerFactory.getLogger(getClass());

	// public static final String ENV_DEFAULT_VALUE = "env_default";

	// public static final String PORT_DEFAULT_VALUE = "port_default";

	private ProjectConstant pc = ProjectConstant.instance();

	/**
	 * 以servlet方式启动项目<br>
	 * env:从系统环境变量读取，需要在容器（tomcat）启动命令加上java -Dproject.env=dev。<br>
	 * name:项目目录/WEB-INF/web.xml文件，display-name标签。<br>
	 * version:项目目录/WEB-INF/web.xml文件，context-param标签。<br>
	 * ip:项目运行服务器的所有网络接口的IP地址，除127.0.0.1以外，多个ip以","分隔。<br>
	 * port:项目运行web容器(tomcat)启动参数-Dproject.port。<br>
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 * @since 1.1
	 */
	public ProjectPropertiesCore(ServletContext servletContext) {
		pc.setEnv(System.getProperty(ProjectConstant.NAME_PROJECT_ENV));
		pc.setName(servletContext.getServletContextName());
		pc.setVersion(servletContext
				.getInitParameter(ProjectConstant.NAME_PROJECT_VERSION));
		pc.setIp(ips());
		pc.setPort(System.getProperty(ProjectConstant.NAME_PROJECT_PORT));
		pc.setContextPath(servletContext.getContextPath());
	}

	/**
	 * 以非servlet方式启动项目，读取配置文件路径为classpath:project.properties<br>
	 * env:读取配置文件中的项project.env。<br>
	 * name:读取配置文件中的项project.name。<br>
	 * version:读取配置文件中的项project.version。<br>
	 * ip:项目运行服务器的所有网络接口的IP地址，除127.0.0.1以外，多个ip以","分隔。<br>
	 * port:读取配置文件中的项project.port。<br>
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 * @since 1.1
	 */
	public ProjectPropertiesCore() {
		ResourceBundle pro = ResourceBundle.getBundle("project");
		pc.setEnv(pro.getString(ProjectConstant.NAME_PROJECT_ENV));
		pc.setName(pro.getString(ProjectConstant.NAME_PROJECT_NAME));
		pc.setVersion(pro.getString(ProjectConstant.NAME_PROJECT_VERSION));
		pc.setIp(ips());
		pc.setPort(pro.getString(ProjectConstant.NAME_PROJECT_PORT));
		pc.setContextPath(pro
				.getString(ProjectConstant.NAME_PROJECT_CONTEXT_PATH));
	}

	// /**
	// * 设置项目全局变量
	// *
	// * @author zhuwl120820@gxwsxx.com 2015年3月12日下午3:12:04
	// *
	// * @deprecated 已废弃，
	// * 使用springProjectProperties和servletContextPrpjectProperties
	// * ，分别获取相应的项目全局变量 。
	// *
	// * @param servletContext
	// * 从interceptor获取的servlet context对象
	// * @param props
	// * 从spring bean factory获取的Properties对象
	// *
	// * @since 1.1
	// */
	// @Deprecated
	// public void handle(ServletContext servletContext, Properties props) {
	// // /**
	// // * 获取值
	// // */
	// // String env = System.getProperty(ProjectConstant.NAME_PROJECT_ENV);
	// // if (null == env || "".equals(env)) {
	// // env = ENV_DEFAULT_VALUE;
	// // }
	// // ProjectConstant.VALUE_PROJECT_ENV = env;
	// // ProjectConstant.put(ProjectConstant.NAME_PROJECT_ENV,
	// // ProjectConstant.VALUE_PROJECT_ENV);
	// //
	// // ProjectConstant.VALUE_PROJECT_NAME = servletContext
	// // .getServletContextName();
	// // ProjectConstant.put(ProjectConstant.NAME_PROJECT_NAME,
	// // ProjectConstant.VALUE_PROJECT_NAME);
	// //
	// // ProjectConstant.VALUE_PROJECT_VERSION = servletContext
	// // .getInitParameter(ProjectConstant.NAME_PROJECT_VERSION);
	// // ProjectConstant.put(ProjectConstant.NAME_PROJECT_VERSION,
	// // ProjectConstant.VALUE_PROJECT_VERSION);
	// //
	// // ProjectConstant.VALUE_PROJECT_IP = ips();
	// // ProjectConstant.put(ProjectConstant.NAME_PROJECT_IP,
	// // ProjectConstant.VALUE_PROJECT_IP);
	// //
	// // String port = System.getProperty(ProjectConstant.NAME_PROJECT_PORT);
	// // if (null == port || "".equals(port)) {
	// // port = PORT_DEFAULT_VALUE;
	// // }
	// // ProjectConstant.VALUE_PROJECT_PORT = port;
	// // ProjectConstant.put(ProjectConstant.NAME_PROJECT_PORT,
	// // ProjectConstant.VALUE_PROJECT_PORT);
	// /**
	// * 获取值
	// */
	// ProjectConstant pc = ProjectConstant.instance();
	// pc.setEnv(System.getProperty(ProjectConstant.NAME_PROJECT_ENV));
	// pc.setName(servletContext.getServletContextName());
	// pc.setVersion(servletContext
	// .getInitParameter(ProjectConstant.NAME_PROJECT_VERSION));
	// pc.setIp(ips());
	// pc.setPort(System.getProperty(ProjectConstant.NAME_PROJECT_PORT));
	// /**
	// * 将值放入servlet context
	// */
	// for (String k : pc.getAll().keySet()) {
	// servletContext.setAttribute(k, ProjectConstant.get(k));
	// }
	// servletContext.setAttribute("project", pc);
	//
	// /**
	// * 将值放入spring properties
	// */
	// if (null == pc.getAll()) {
	// System.out.println("pc.getAll() is null");
	// }
	// if (null == props) {
	// System.out.println("props is null");
	// }
	// props.putAll(pc.getAll());
	// }

	/**
	 * 获取项目全局变量参数
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 * @return 项目全局变量
	 *
	 * @since 1.1
	 */
	public ProjectConstant getProjectConstant() {
		return pc;
	}

	/**
	 * 设置全局变量到spring配置
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 * @param props
	 *            spring配置读取的Properties对象
	 * @since 1.1
	 */
	public void springProjectProperties(Properties props) {
		props.putAll(pc.getAll());
	}

	/**
	 * 设置全局变量到servlet context
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 * @param servletContext
	 *            ServletContext对象
	 * @since 1.1
	 */
	public void servletContextPrpjectProperties(ServletContext servletContext) {
		Map<String, String> map = pc.getAll();
		for (Entry<String, String> en : map.entrySet()) {
			servletContext.setAttribute(en.getKey(), en.getValue());
		}
		servletContext.setAttribute("project", pc);
	}

	/**
	 * 获取网卡IP地址
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 * @return ip地址，以","分隔
	 * @since 1.0
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
