package com.gxws.tool.link.properties.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 读取配置项的注解<br>
 * 需要添加在定义为public statis String 的field上
 * 
 * @author zhuwl120820@gxwsxx.com 2015年2月10日上午11:27:25
 *
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface LinkProperties {

	/**
	 * 指定link-properties参数配置源要读取值的参数名。<br>
	 * 同时该参数名在spring properties中使用。<br>
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 * @return 参数名
	 * @since 1.0
	 */
	public String value() default "";

	/**
	 * 在ServletContext对象中读取的键，即servletContext.getAttribute(attrName)中的attrName值。
	 * <br>
	 * 同时该键在jsp页面中使用el表达式获取ServletContext中的值，即${attrName}<br>
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 * @return ServletContext对象中读取的键
	 * @since 1.2
	 */
	public String[]servletContextAttrNames() default "";
}
