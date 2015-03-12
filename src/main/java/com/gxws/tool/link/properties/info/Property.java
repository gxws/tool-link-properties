package com.gxws.tool.link.properties.info;

/**
 * 配置项的相关信息
 * 
 * @author 朱伟亮
 * @create 2015年2月10日下午5:11:57
 *
 */
public class Property {

	private String propertyKey;// 读取的配置名

	private String fieldName;// 配置类字段名

	private String fullName;// 配置字段全名（包名+类名+字段名）

//	private boolean contextScope;// 变量是否需要添加到web application context

//	public boolean isContextScope() {
//		return contextScope;
//	}
//
//	public void setContextScope(boolean contextScope) {
//		this.contextScope = contextScope;
//	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getPropertyKey() {
		return propertyKey;
	}

	public void setPropertyKey(String propertyKey) {
		this.propertyKey = propertyKey;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
