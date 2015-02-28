package com.gxws.tool.link.properties.exception;

/**
 * 必要属性缺失异常
 * 
 * @author 朱伟亮
 * @create 2015年2月10日下午3:37:30
 *
 */
public class LinkPropertiesRequireMissingException extends
		LinkPropertiesBaseException {

	private static final long serialVersionUID = 8536271162856438688L;

	public LinkPropertiesRequireMissingException(String message) {
		super.setMessage("attribute '" + message
				+ "' missing in file link.properties");
	}

}
