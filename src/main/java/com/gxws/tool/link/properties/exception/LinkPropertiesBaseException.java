package com.gxws.tool.link.properties.exception;

/**
 * 配置信息异常
 * 
 * @author zhuwl120820@gxwsxx.com
 *  2015年2月10日下午2:46:46
 *
 */
public class LinkPropertiesBaseException extends Exception {

	private static final long serialVersionUID = 7375318176113330850L;
	private String message;

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
