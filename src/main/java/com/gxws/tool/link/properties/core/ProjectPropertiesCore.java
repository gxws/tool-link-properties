package com.gxws.tool.link.properties.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
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
public class ProjectPropertiesCore implements IPropertiesCore {

	private Logger log = LoggerFactory.getLogger(getClass());

	private ProjectConstant pc = ProjectConstant.instance();

	private ServletContext sc;

	/**
	 * 以servlet方式启动项目<br>
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 * @since 1.1
	 */
	public ProjectPropertiesCore(ServletContext servletContext) {
		this.sc = servletContext;
		readPropertiesProperties();
	}

	/**
	 * env:从系统环境变量读取，需要在容器（tomcat）启动命令加上java -Dproject.env=dev。<br>
	 * name:项目目录/WEB-INF/web.xml文件，display-name标签。<br>
	 * version:项目目录/WEB-INF/web.xml文件，context-param标签。<br>
	 * ip:项目运行服务器的所有网络接口的IP地址，除127.0.0.1以外，多个ip以","分隔。<br>
	 * port:项目运行web容器(tomcat)启动参数-Dproject.port。<br>
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 * @since 1.1
	 */
	private void readPropertiesProperties() {
		pc.setEnv(System.getProperty(ProjectConstant.NAME_PROJECT_ENV));
		if (null != sc) {
			Properties p = new Properties();
			InputStream is;
			try {
				is = new FileInputStream(mavenPropertiesPath());
				p.load(is);
				pc.setName(p.getProperty("artifactId"));
				pc.setVersion(p.getProperty("version"));
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
			pc.setContextPath(sc.getContextPath());
		}
		pc.setIp(ips());
		pc.setPort(System.getProperty(ProjectConstant.NAME_PROJECT_PORT));
		for (Entry<String, String> en : pc.getAll().entrySet()) {
			log.debug("项目全局变量加载 " + en.getKey() + " = " + en.getValue());
		}
	}

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
	@Override
	public void springProperties(Properties props) {
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
	@Override
	public void servletContextProperties(ServletContext servletContext) {
		if (null != servletContext) {
			pc.getAll()
					.entrySet()
					.forEach(
							en -> servletContext.setAttribute(en.getKey(),
									en.getValue()));
			servletContext.setAttribute("project", pc);
			servletContext.setAttribute("ctx", pc.getContextPath());
		}
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
			log.error("读取不到当前服务器IP", e);
			return "";
		}
		return sb.substring(1);
	}

	/**
	 * 获取maven信息文件
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 * @return maven信息文件
	 * @since 1.1
	 */
	private File mavenPropertiesPath() {
		File f = new File(sc.getRealPath("/") + "META-INF/maven");
		f = f.listFiles()[0].listFiles()[0];
		f = f.listFiles((dir, name) -> ("pom.properties".equals(name) ? true
				: false))[0];
		log.debug("读取项目maven信息的路径 " + f.getAbsolutePath());
		return f;
	}
}
