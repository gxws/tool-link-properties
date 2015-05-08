package com.gxws.tool.link.properties.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zhuwl120820@gxwsxx.com
 *
 */
public class LinkPropertiesCoreTest {

	LinkPropertiesCore lpc = new LinkPropertiesCore();

	List<String> classNameList = new ArrayList<>(
			Arrays.asList(new String[] { "com.gxws.tool.link.properties.constant.LinkPropertiesConstant" }));

	@Test
	public void handleTest() {
		MockHttpServletRequest req = new MockHttpServletRequest();
		ServletContext sc = req.getServletContext();
		Properties props = new Properties();
		lpc.handle(classNameList, props, sc);
	}

}
