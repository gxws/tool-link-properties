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
	 * @since
	 */
	public void springProperties(Properties props);

	/**
	 * 设置servlet context配置
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 * @param servletContext
	 * @since
	 */
	public void servletContextProperties(ServletContext servletContext);
}
