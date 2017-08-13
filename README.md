# threecss-identity

threecss-identity是基于ThreeCSS分布式框架开发的一款身份系统。


打版本：在项目根目录下，配置好build-custom.properties每个项目的路径及发布路径，执行

	ant


配置：

	dist/ThreeCSSIdentityConfigData/configext.json----访问身份系统及其他配置

	dist/ThreeCSSIdentityConfigData/mybatis-config.xml---访问身份系统数据库

	dist/ThreeCSSIdentity.properties----ThreeCSSIdentityConfigData在服务器路径


推荐环境：

	jdk-8u121

	apache-tomcat-8.5.12

	MariaDB-10.1.22

	CentOS-7-1611


发布项目：

1、安装数据库
	
	create database threecssidentity
	
	source ****/threecssidentity.sql

2、将ThreeCSSIdentityConfigData放入服务器某个路径，例如
	
	/home/ThreeCSSIdentityConfigData

3、将ThreeCSSIdentity.properties放入tomcat根目录下，例如
	
	/home/tomcat/ThreeCSSIdentity.properties
	
	并修改config_dir对应的ThreeCSSIdentityConfigData路径

4、将ThreeCSSIdentity.war放入tomcat/webapps，例如
	
	/home/tomcat/webapps/ThreeCSSIdentity.war

	
依赖threecss-identity开发的项目：

1、网盘：https://github.com/dianbaer/threecss-box
	
	体验地址：http://box.threecss.com

2、嵌入式聊天：https://github.com/dianbaer/threecss-embed-chat
	
	体验地址：http://embedchat.threecss.com

3、支付平台：https://github.com/dianbaer/threecss-pay
	
	体验地址：http://pay.threecss.com
	

threecss-identity提供的API功能：

1、用户组API：
	
	创建、修改、获取、删除用户组。
	获取用户组列表。

2、用户API：
	
	创建、修改、获取用户。
	获取用户列表。
	根据邮箱获取用户、获取用户头像。
	检查用户名、手机是否存在。

3、tokenAPI：
	
	登录通过用户名密码获取token
	更新token
	删除token




