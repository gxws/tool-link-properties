package com.gxws.tool.link.properties.exception;

/**
 * 必要属性缺失异常
 * 
 * @author zhuwl120820@gxwsxx.com
 *  2015年2月10日下午3:37:30
 *
 */
@Deprecated
public class LinkPropertiesRequireMissingException extends
		LinkPropertiesBaseException {

	private static final long serialVersionUID = 8536271162856438688L;

	public LinkPropertiesRequireMissingException(String message) {
		super.setMessage("attribute '" + message
				+ "' missing in file link.properties");
	}

}
