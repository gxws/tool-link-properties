package com.gxws.tool.link.properties.datamodel;

import java.util.Set;

/**
 * 配置项的相关信息
 * 
 * @author zhuwl120820@gxwsxx.com 2015年2月10日下午5:11:57
 * 
 * @since 1.0
 */
public class Property {
	// 读取的配置名
	private String propertyKey;

	// 配置类字段名
	private String fieldName;

	// 配置字段全名（包名+类名+字段名）
	private String fullName;

	// 配置值
	private String value;

	// 对应的class对象
	private Class<?> clazz;

	// 配置项在servlet context中的属性名集合
	private Set<String> servletContextAttrNames;

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Set<String> getServletContextAttrNames() {
		return servletContextAttrNames;
	}

	public void setServletContextAttrNames(Set<String> servletContextAttrNames) {
		this.servletContextAttrNames = servletContextAttrNames;
	}

}
