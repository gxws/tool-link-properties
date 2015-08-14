package com.gxws.tool.link.properties.core;

import java.util.Properties;

import javax.servlet.ServletContext;

/**
 * 设置配置接口
 * 
 * @author zhuwl120820@gxwsxx.com
 * @since 1.1
 */
public interface IPropertiesCore {
	/**
	 * 设置spring配置
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 * @param props
	 *            spring的Properties参数对象
	 * @since 1.1
	 */
	public void springProperties(Properties props);

	/**
	 * 设置servlet context配置
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 * @param servletContext
	 *            web应用的ServletContext对象
	 * @since 1.1
	 */
	public void servletContextProperties(ServletContext servletContext);
}
