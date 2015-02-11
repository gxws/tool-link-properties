package com.gxws.tool.link.properties.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.gxws.tool.link.properties.core.LinkPropertiesCore;

/**
 * 以servlet listener方式读取link properties配置参数
 * 
 * @author 朱伟亮
 * @create 2015年2月10日下午1:37:03
 *
 */
public class LinkPropertiesListener implements ServletContextListener {

	private String LINK_CONFIG_CLASS_NAME = "linkPropertiesClassnames";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String className = sce.getServletContext().getInitParameter(
				LINK_CONFIG_CLASS_NAME);
		ServletContext servletContext = sce.getServletContext();
		LinkPropertiesCore lpc = new LinkPropertiesCore();
		lpc.handle(className, servletContext);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
