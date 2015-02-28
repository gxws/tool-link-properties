package com.gxws.tool.link.properties.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 朱伟亮
 * @create 2015年2月10日上午11:27:25
 *
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface LinkProperties {

	/**
	 * 参数名
	 * 
	 * @author 朱伟亮
	 * @create 2015年2月10日上午11:29:51
	 * 
	 * @return
	 */
	public String value() default "";

	/**
	 * 是否需要添加到 servlet context
	 * 
	 * @author 朱伟亮
	 * @create 2015年2月10日上午11:51:42
	 * 
	 * @return
	 */
	public boolean contextScope() default false;
}
