package com.gxws.tool.link.properties.spring;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

import com.gxws.tool.link.properties.core.LinkPropertiesCore;


/**
 * 以spring bean 方式读取link properties配置参数
 * 
 * @author 朱伟亮
 * @create 2015年2月10日下午1:40:59
 *
 */
public class LinkPropertiesBean implements ServletContextAware {

	private List<String> linkPropertiesClassnames;

	private ServletContext servletContext;

	public void initialized() {
		// WebApplicationContext webApplicationContext = ContextLoader
		// .getCurrentWebApplicationContext();
		// ServletContext servletContext = webApplicationContext
		// .getServletContext();
		LinkPropertiesCore lpc = new LinkPropertiesCore();
		lpc.handle(linkPropertiesClassnames, servletContext);
	}

	public void destroyed() {

	}

	public void setLinkPropertiesClassnames(
			List<String> linkPropertiesClassnames) {
		this.linkPropertiesClassnames = linkPropertiesClassnames;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}
