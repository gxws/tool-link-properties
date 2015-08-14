package com.gxws.tool.link.properties.reader;

import java.util.ResourceBundle;

import com.gxws.tool.common.constant.ProjectConstant;
import com.gxws.tool.link.properties.exception.LinkPropertiesBaseException;
import com.gxws.tool.link.properties.exception.LinkPropertiesKeyException;
import com.gxws.tool.link.properties.exception.LinkPropertiesReaderInitException;
import com.gxws.tool.link.properties.exception.LinkPropertiesValueException;

/**
 * 从本地文件配置源中获取配置信息
 * 
 * @author zhuwl120820@gxwsxx.com
 * @since 1.0
 */
public class FileReader implements Reader {

	private ResourceBundle linkFile;

	// 文件名为link.properties
	private static final String LINK_FILE_NAME = "link";

	/**
	 * 从本地文件配置源中获取配置信息
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 * @throws LinkPropertiesReaderInitException
	 *             配置读取对象初始化异常
	 * @since 1.0
	 */
	public FileReader() throws LinkPropertiesReaderInitException {
		try {
			linkFile = ResourceBundle.getBundle(LINK_FILE_NAME);
		} catch (Exception e1) {
			LinkPropertiesReaderInitException e = new LinkPropertiesReaderInitException();
			e.appendMessage(e1.getMessage());
			e.setStackTrace(e1.getStackTrace());
			throw e;
		}
	}

	/**
	 * @see com.gxws.tool.link.properties.reader.Reader#valueString(java.lang.String)
	 */
	@Override
	public String valueString(String propertyKey) throws LinkPropertiesBaseException {
		ProjectConstant pc = ProjectConstant.instance();
		if (propertyKey == null) {
			LinkPropertiesKeyException e = new LinkPropertiesKeyException();
			// e.setMessage(propertyKey + "属性名为null");
			e.appendMessage("属性名为null");
			throw e;
		} else {
			String k = propertyKey.trim();
			if (k.isEmpty()) {
				LinkPropertiesKeyException e = new LinkPropertiesKeyException();
				// e.setMessage(k + "属性名不是null，但是值为空");
				e.appendMessage("属性名不是null，但是值为空");
				throw e;
			} else {
				k = pc.getEnv() + "." + k;
				try {
					return linkFile.getString(k);
				} catch (Exception e1) {
					LinkPropertiesValueException e = new LinkPropertiesValueException();
					e.setStackTrace(e1.getStackTrace());
					// e.setMessage("属性名 '" + k + "' 的值读取异常 , 检查值是否正确配置");
					e.appendMessage("属性名：" + k);
					throw e;
				}
			}
		}
	}
}
