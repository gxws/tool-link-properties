package com.gxws.tool.link.properties.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.web.context.ServletContextAware;

import com.gxws.tool.link.properties.core.IPropertiesCore;
import com.gxws.tool.link.properties.core.LinkPropertiesCore;
import com.gxws.tool.link.properties.core.ProjectPropertiesCore;

/**
 * 以spring bean 方式读取link properties配置参数<br>
 * 对配置的constant class 赋值<br>
 * 将读取的配置信息加入servlet context<br>
 * 将读取的配置信息加入spring application context<br>
 * 
 * @author zhuwl120820@gxwsxx.com
 * @since 1.0
 */
public class LinkPropertiesBean extends PropertyPlaceholderConfigurer implements ServletContextAware {

	private List<String> constantClassnames;

	private ServletContext servletContext;

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		List<IPropertiesCore> corelist = new ArrayList<>();
		// 处理项目全局变量
		corelist.add(new ProjectPropertiesCore(servletContext));
		// 处理项目自定义变量
		corelist.add(new LinkPropertiesCore(constantClassnames));
		for (IPropertiesCore core : corelist) {
			core.servletContextProperties(servletContext);
			core.springProperties(props);
		}
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
