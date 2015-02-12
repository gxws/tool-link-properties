package com.gxws.tool.link.properties.reader;

import java.util.ResourceBundle;

import com.gxws.tool.link.properties.exception.LinkPropertiesBaseException;
import com.gxws.tool.link.properties.exception.LinkPropertiesKeyException;
import com.gxws.tool.link.properties.exception.LinkPropertiesValueException;

/**
 * 从link.properties获取配置信息
 * 
 * @author 朱伟亮
 * @create 2015年2月10日上午11:59:16
 *
 */
public class FileReader implements Reader {

	private ResourceBundle linkFile = null;

	private String linkFileName = "link";

	@Override
	public String valueString(String propertyKey)
			throws LinkPropertiesBaseException {
		if (null == linkFile) {
			try {
				linkFile = ResourceBundle.getBundle(linkFileName);
			} catch (Exception e1) {
				LinkPropertiesBaseException e = new LinkPropertiesBaseException();
				e.setMessage(linkFileName + ".properties loading");
				e.setStackTrace(e1.getStackTrace());
				throw e;
			}
		}
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
