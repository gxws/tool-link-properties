package com.gxws.tool.link.properties.core;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.gxws.tool.link.properties.core.test.SpringPropertyTestBean;

@WebAppConfiguration
@ContextConfiguration(locations = { "/test-spring.xml" })
public class LinkPropertiesCoreTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private SpringPropertyTestBean sptb;

	@Test
	public void servletContextProperties() {
		ServletContext sc = wac.getServletContext();
		Assert.assertEquals(sc.getAttribute("PROPERTY_1"), "env_default.p1value");
		Assert.assertEquals(sc.getAttribute("PROPERTY_2"), "env_default.p2value");
		Assert.assertEquals(sc.getAttribute("p2"), "env_default.p2value");
		Assert.assertEquals(sc.getAttribute("property2"), "env_default.p2value");
	}

	@Test
	public void springProperties() {

	}
}
