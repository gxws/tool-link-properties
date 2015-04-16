package com.gxws.tool.link.properties.constant;

import java.util.HashMap;
import java.util.Map;

import com.gxws.tool.link.properties.annotation.LinkProperties;

/**
 * 读取必要属性的值
 * 
 * @author zhuwl120820
 *  2015年2月27日下午5:33:56
 *
 */
@Deprecated
public class LinkPropertiesConstant {

	@LinkProperties(value = "global.project.name")
	public static String GLOBAL_PROJECT_NAME = "";

	@LinkProperties(value = "global.project.env")
	public static String GLOBAL_PROJECT_ENV = "";

	@LinkProperties(value = "global.project.version")
	public static String GLOBAL_PROJECT_VERSION = "";

	private static Map<String, String> map;

	public static Map<String, String> GLOBAL_PROPERTY_MAP() {
		if (null == map) {
			map = new HashMap<>();
			map.put("global.project.name", "");
			map.put("global.project.env", "");
			map.put("global.project.version", "");
		}
		return map;
	}

}
