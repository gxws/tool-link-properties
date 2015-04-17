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
	 * 参数名
	 * 
	 * @author zhuwl120820@gxwsxx.com 2015年2月10日上午11:29:51
	 * 
	 * @return 参数名
	 */
	public String value() default "";

}
