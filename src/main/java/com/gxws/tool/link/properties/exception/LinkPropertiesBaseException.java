package com.gxws.tool.link.properties.exception;

import com.gxws.tool.common.exception.BaseException;

/**
 * 配置信息异常
 * 
 * @author zhuwl120820@gxwsxx.com 2015年2月10日下午2:46:46
 *
 */
public class LinkPropertiesBaseException extends BaseException {

	private static final long serialVersionUID = 7375318176113330850L;

	/**
	 * 配置信息异常
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 * @since 1.0
	 */
	public LinkPropertiesBaseException() {
		this.setMsg("配置信息异常。");
	}

	@Override
	public String getMessage() {
		return this.getMsg();
	}

	public void setMessage(String message) {
		this.setMsg(message);
	}

	public void appendMessage(String message) {
		this.setMsg(this.getMessage() + message);
	}

}
