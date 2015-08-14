package com.gxws.tool.link.properties.exception;

/**
 * 配置读取对象初始化异常
 * 
 * @author zhuwl120820@gxwsxx.com
 *
 */
public class LinkPropertiesReaderInitException extends LinkPropertiesBaseException {
	private static final long serialVersionUID = -4918054219335019375L;

	public LinkPropertiesReaderInitException() {
		this.setMsg("配置读取对象初始化异常。");
	}
}
