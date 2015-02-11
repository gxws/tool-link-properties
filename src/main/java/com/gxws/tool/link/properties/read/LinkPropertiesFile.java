package com.gxws.tool.link.properties.read;

import java.util.ResourceBundle;

import com.gxws.tool.link.properties.exception.LinkPropertiesBaseException;
import com.gxws.tool.link.properties.exception.LinkPropertiesNameException;
import com.gxws.tool.link.properties.exception.LinkPropertiesValueException;

/**
 * 从link.properties获取配置信息
 * 
 * @author 朱伟亮
 * @create 2015年2月10日上午11:59:16
 *
 */
public class LinkPropertiesFile implements LinkPropertiesType {

	private ResourceBundle linkFile = null;

	private String linkFileName = "link";

	@Override
	public String get(String key) throws LinkPropertiesBaseException {
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
		if (key == null) {
			LinkPropertiesNameException e = new LinkPropertiesNameException();
			e.setMessage("key is null");
			throw e;
		} else {
			String name = key.trim();
			if ("".equals(name)) {
				LinkPropertiesNameException e = new LinkPropertiesNameException();
				e.setMessage("key is not null but empty");
				throw e;
			} else {
				try {
					return linkFile.getString(name);
				} catch (Exception e1) {
					LinkPropertiesValueException e = new LinkPropertiesValueException();
					e.setStackTrace(e1.getStackTrace());
					e.setMessage("key '"+name+"' value reading exception , probable not exist");
					throw e;
				}
			}
		}
	}

}
