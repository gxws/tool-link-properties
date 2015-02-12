package com.gxws.tool.link.properties.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.gxws.tool.link.properties.reader.ReaderType;

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
	 * 读取配置数据类型
	 * 
	 * 只有global.env的值不为real test dev才有效
	 * 
	 * @author 朱伟亮
	 * @create 2015年2月10日上午11:43:20
	 * 
	 * @return
	 */
	public ReaderType type() default ReaderType.FILE;

	/**
	 * 是否需要添加到application context
	 * 
	 * @author 朱伟亮
	 * @create 2015年2月10日上午11:51:42
	 * 
	 * @return
	 */
	public boolean contextScope() default false;
}
