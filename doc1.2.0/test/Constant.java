package com.gxws.tool.link.properties.core.test;

import com.gxws.tool.link.properties.annotation.LinkProperties;

/**
 * 测试常量类
 * 
 * @author zhuwl120820@gxwsxx.com
 * @since 1.2
 */
public class Constant {

	@LinkProperties("p1")
	public static String PROPERTY_1;

	@LinkProperties(value = "p2", servletContextAttrNames = { "p2", "property2" })
	public static String PROPERTY_2;

	@LinkProperties("p3")
	public static String PROPERTY_3;
}
