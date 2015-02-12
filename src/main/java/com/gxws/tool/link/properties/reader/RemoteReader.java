package com.gxws.tool.link.properties.reader;

import java.util.HashMap;
import java.util.Map;

import com.gxws.tool.link.properties.exception.LinkPropertiesBaseException;
import com.gxws.tool.link.properties.exception.LinkPropertiesRequestMissingException;

/**
 * 获取远程配置信息
 * 
 * @author 朱伟亮
 * @create 2015年2月10日下午12:00:24
 *
 */
public abstract class RemoteReader implements Reader {

	protected Map<String, String> globalMap = new HashMap<>();

	protected String GLOBAL_PROJECT_ENV = "global.project.env";

	protected String GLOBAL_PROJECT_NAME = "global.project.name";

	public RemoteReader(FileReader linkFile) throws LinkPropertiesBaseException {
		String envValue = linkFile.valueString(GLOBAL_PROJECT_ENV);
		String nameVlaue = linkFile.valueString(GLOBAL_PROJECT_NAME);
		LinkPropertiesRequestMissingException e = new LinkPropertiesRequestMissingException();
		if (null == envValue || "".equals(envValue)) {
			e.setMessage(GLOBAL_PROJECT_ENV);
			throw e;
		} else if (null == nameVlaue || "".equals(nameVlaue)) {
			e.setMessage(GLOBAL_PROJECT_NAME);
			throw e;
		} else {
			globalMap.put(GLOBAL_PROJECT_ENV, envValue);
			globalMap.put(GLOBAL_PROJECT_NAME, nameVlaue);
		}
	}

	@Override
	public abstract String valueString(String propertyKey);

}
