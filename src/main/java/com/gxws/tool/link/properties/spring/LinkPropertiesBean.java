package com.gxws.tool.link.properties.spring;

import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.web.context.ServletContextAware;

import com.gxws.tool.link.properties.core.LinkPropertiesCore;
import com.gxws.tool.link.properties.core.ProjectPropertiesCore;

/**
 * 以spring bean 方式读取link properties配置参数<br>
 * 对配置的constant class 赋值<br>
 * 将读取的配置信息加入servlet context<br>
 * 将读取的配置信息加入spring application context<br>
 * 
 * @author zhuwl120820@gxwsxx.com 2015年2月10日下午1:40:59
 * 
 * @since 1.0
 *
 */
public class LinkPropertiesBean extends PropertyPlaceholderConfigurer implements
		ServletContextAware {

	private List<String> constantClassnames;

	private ServletContext servletContext;

	// LinkPropertiesCore lpc = new LinkPropertiesCore();

	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		// lpc.handle(constantClassnames, props, servletContext);
		// 处理项目全局变量
		ProjectPropertiesCore ppc = new ProjectPropertiesCore(servletContext);
		ppc.servletContextPrpjectProperties(servletContext);
		ppc.springProjectProperties(props);

		// 处理项目自定义变量
		LinkPropertiesCore lpc = new LinkPropertiesCore(constantClassnames);
		lpc.servletContextPrpjectProperties(servletContext);
		lpc.springProjectProperties(props);
		super.processProperties(beanFactoryToProcess, props);
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public void setConstantClassnames(List<String> constantClassnames) {
		this.constantClassnames = constantClassnames;
	}

}
