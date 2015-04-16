package com.gxws.tool.link.properties.reader;

import java.util.ResourceBundle;

import com.gxws.tool.link.properties.exception.LinkPropertiesBaseException;
import com.gxws.tool.link.properties.exception.LinkPropertiesKeyException;
import com.gxws.tool.link.properties.exception.LinkPropertiesReaderInitException;
import com.gxws.tool.link.properties.exception.LinkPropertiesValueException;

/**
 * 从link.properties获取配置信息
 * 
 * @author zhuwl120820
 *  2015年2月10日上午11:59:16
 *
 */
public class FileReader implements Reader {

	private ResourceBundle linkFile = null;

	private final String LINK_FILE_NAME = "link";

	/**
	 * @author zhuwl120820
	 * @throws LinkPropertiesReaderInitException
	 *  2015年3月12日上午11:55:37
	 * 
	 */
	public FileReader() throws LinkPropertiesReaderInitException {
		try {
			linkFile = ResourceBundle.getBundle(LINK_FILE_NAME);
		} catch (Exception e1) {
			LinkPropertiesReaderInitException e = new LinkPropertiesReaderInitException();
			e.setStackTrace(e1.getStackTrace());
			e.setMessage(":");
			throw e;
		}
	}

	@Override
	public String valueString(String propertyKey)
			throws LinkPropertiesBaseException {
		if (propertyKey == null) {
			LinkPropertiesKeyException e = new LinkPropertiesKeyException();
			e.setMessage("property key is null");
			throw e;
		} else {
			String k = propertyKey.trim();
			if ("".equals(k)) {
				LinkPropertiesKeyException e = new LinkPropertiesKeyException();
				e.setMessage("property key is not null but empty");
				throw e;
			} else {
				try {
					return linkFile.getString(k);
				} catch (Exception e1) {
					LinkPropertiesValueException e = new LinkPropertiesValueException();
					e.setStackTrace(e1.getStackTrace());
					e.setMessage("property key '" + k
							+ "' value reading exception , probable not exist");
					throw e;
				}
			}
		}
	}
}
