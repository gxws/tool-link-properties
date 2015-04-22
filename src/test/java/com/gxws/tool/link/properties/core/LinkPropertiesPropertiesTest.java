package com.gxws.tool.link.properties.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gxws.test.constant.Constant;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class LinkPropertiesPropertiesTest extends
		AbstractJUnit4SpringContextTests {

	@Test
	public void handleTest() {
		System.out.println(Constant.abc);
	}

}
