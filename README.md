tool-link-properties
====================

mail list:朱伟亮 \<zhuwl120820@gxwsxx.com>

初始化项目“全局变量”。<br>
从配置文件或远程读取项目“自定义变量”。<br>
将“全局变量”和“自定义变量”的配置信息放入spring启动配置和application context。<br>


版本变更说明
---
### 1.0.3
完善了说明文档README.md。<br>
将配置文件log4j2.xml和spring-logging.xml从项目目录转移至tool-logging的包目录。<br>
更改“自定义变量”使用“远程配置读取”方式的规则，["dev","test","real"]远程读取，其他则配置文件读取。<br>
更改“全局变量”jsp获取方式。<br>
读取tool-common中的ProjectConstant.onlineEnvSet用于区别线上环境。<br>
 
### 1.1.0
增加servlet context path为系统默认变量。调用方式为${project.contextPath}<br>
调整LinkPropertiesCore类的配置读取方式。<br>
调整maven依赖配置，去除parent配置，改为使用原始配置。<br>
加入了输出一些logger debug信息。<br>
调整了project.name和project.version的读取方式，改为读取maven信息。<br>
spring framework版本修改为4.1.7.RELEASE。<br>

### 1.1.1
增加了一个servlet context path系统变量别名。调用方式为${ctx}。<br>
修复了项目启动时没有servlet context的空指针异常。<br>

### 1.2.0
修改注释信息，优化代码。<br>
修改异常类的结构，代码优化。<br>
修改配置读取策略，将从系统变量读取环境和读取策略。不再从环境变量判断读取策略。<br>
修改maven依赖，tool-common版本修改为1.2.0-RELEASE。<br>
<br>
添加servlet context属性名称，可以通过注解中指定的属性名称，读取相应的信息。<br>
添加依赖testng测试框架，版本为6.9.6<br>
添加依赖spring-test测试框架。<br>


功能点
---
### 1、项目启动时初始化“全局变量”配置
项目全局变量包括：<br>

#### 项目名：
读取来源：项目目录/WEB-INF/web.xml文件，display-name标签。<br>
	
	<web-app>
		<display-name>service-demo</display-name>
	</web-app>
	
默认值：无<br>
键(key)：project.name<br>

#### 项目版本：
读取来源：项目目录/WEB-INF/web.xml文件，context-param标签。<br>

	<web-app>
		<param-name>project.version</param-name>
		<param-value>0.0.1-SNAPSHOT</param-value>
	</web-app>
	
默认值：无<br>
键(key)：project.version<br>

#### 项目运行环境：
读取来源：项目运行web容器(tomcat)启动参数-Dproject.env。<br>

	java -Dproject.env=dev
	
默认值：env_default<br>
键(key)：project.env<br>

#### 项目运行服务器ip
读取来源：项目运行服务器的所有网络接口的IP地址，除127.0.0.1以外，多个ip以","分隔。<br>

默认值：无<br>
键(key)：project.ip<br>

#### 项目运行端口号
读取来源：项目运行web容器(tomcat)启动参数-Dproject.port。<br>

	java -Dproject.port=10000
	
默认值：port_default<br>
键(key)：project.port<br>

### 2、项目启动时读取“自定义变量”配置
#### 配置读取方式
读取方式分为“配置文件读取”和“远程配置读取”两种。<br>
配置文件读取：配置项会从配置文件classpath:link.properties中读取。<br>
远程配置读取：配置项会从远程配置服务中读取相应项。<br>

#### 配置读取规则
根据读取键值project.env，如果值在["dev","test","real"]中的，都会使用“远程配置读取”的方式；如果不在，会使用“配置文件读取”的方式。

#### 远程配置读取方式
##### zookeeper
读取路径:/link.properties/${project.env}/${project.name}/${key}<br>
以test环境web-tv-demo项目的静态地址stc的值为示例：/link.properties/test/web-tv-demo/stc
以dev环境service-demo项目的数据库连接地址db.url的值为示例：/link.properties/dev/service-demo/db.url

##### redis
计划扩展，暂不支持

依赖关系
---
### 1、服务依赖
#### zookeeper
“远程配置读取”方式中需要依赖zookeeper服务。<br>
读取地址为zookeeper.gxwsxx.com:17000。<br>
暂不支持通过配置方式修改zookeeper的连接地址。

### 2、组件依赖
org.springframework spring-web 4.1<br>
com.gxws tool-common 1.0.1<br>
org.apache.curator curator-framework 2.7<br>

使用方式
---

## 1、引入maven配置
在pom.xml文件中加入

	<dependency>
		<groupId>com.gxws</groupId>
		<artifactId>tool-link-properties</artifactId>
		<version>最新版本号</version>
	</dependency>
	
## 2、读取
定义java静态类、静态字段。<br>
给静态字段添加注解@LinkProperties，value的值为读取配置的key值。<br>
规则一般为静态字段字母由小写改为大写，"."改为"_"。
	
	package com.gxws.web.tv.demo.constant;

	import com.gxws.tool.link.properties.annotation.LinkProperties;
	
	public class Constant {
	
		@LinkProperties
		public static String stc;
		
		@LinkProperties(value = "db.url")
		public static String DB_URL;
	
		@LinkProperties(value = "db.username")
		public static String DB_USERNAME;
	
		@LinkProperties(value = "db.password")
		public static String DB_PASSWORD;
	}
	
在spring配置文件中添加

	<beans>
		<!-- 读取配置 -->
		<bean class="com.gxws.tool.link.properties.spring.LinkPropertiesBean">
			<property name="constantClassnames">
				<list>
					<value>com.gxws.web.tv.demo.constant.Constant</value>
				</list>
			</property>
		</bean>
	</beans>
	
value为上一步定义的静态类。

## 3、使用
### 使用“全局变量”，以项目名的使用为例：
#### 1、spring配置使用“全局变量”的值：

	<beans>
		<dubbo:application name="${project.name}" />
	</beans>
	
#### 2、jsp页面EL表达式使用“全局变量”的值:

	<html>
		<body>
			${project.name}
		</body>
	</html>

#### 3、java代码变量使用“全局变量”的值：

	import com.gxws.tool.common.constant.ProjectConstant;
	
	public class DemoClass {
		public void DemoMethod(){
			system.out.println(ProjectConstant.instance().getName());
			system.out.println(ProjectConstant.get("project.name"));
			system.out.println(ProjectConstant.get(ProjectConstant.NAME_PROJECT_NAME));
		}
	}

### 使用“自定义变量”，以stc为例
#### 1、spring配置使用“自定义变量”的值：

	<beans>
		<bean id="dataSourceDruid">
			<property name="url" value="${db.url}" />
			<property name="username" value="${db.username}" />
			<property name="password" value="${db.password}" />
		</bean>
	</beans>
	
#### 2、jsp页面EL表达式使用“自定义变量”的值:

	<html>
		<body>
			${stc}
			${Constant.stc}
			${DB_URL}
			${Constant.DB_URL}
		</body>
	</html>
	
#### 3、java代码变量使用“自定义变量”的值：

	import com.gxws.web.tv.demo.constant.Constant;
	
	public class DemoClass {
		public void DemoMethod(){
			system.out.println(Constant.stc);
			system.out.println(Constant.DB_URL);
		}
	}
