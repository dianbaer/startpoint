# Identity

[![Build Status](https://travis-ci.org/dianbaer/Identity.svg?branch=master)](https://travis-ci.org/dianbaer/Identity)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/e14f986cdb3a42e7b4865fbc77863479)](https://www.codacy.com/app/232365732/Identity?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=dianbaer/Identity&amp;utm_campaign=Badge_Grade)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

# Identity是一个身份系统。


## 打版本：在项目根目录下，执行

	ant


## 配置：

	dist/IdentityConfig/configext.json----访问身份系统及其他配置

	dist/IdentityConfig/mybatis-config.xml---访问身份系统数据库

	dist/IdentityServer.properties----IdentityConfig在服务器路径


## 推荐环境：

>快捷部署 https://github.com/dianbaer/deployment-server

	jdk-8u121

	apache-tomcat-8.5.12

	MariaDB-10.1.22

	CentOS-7-1611


## 发布项目：

>1、安装数据库
	
	create database identity
	
	source ****/identity.sql

>2、将IdentityConfig放入服务器某个路径，例如
	
	/home/IdentityConfig

>3、将IdentityServer.properties放入tomcat根目录下，例如
	
	/home/tomcat/IdentityServer.properties
	
	并修改config_dir对应的IdentityConfig路径

>4、将IdentityServer.war放入tomcat/webapps，例如
	
	/home/tomcat/webapps/IdentityServer.war

	
## 依赖Identity开发的项目：

>1、文件系统：https://github.com/dianbaer/FileSystem
	

>2、聊天：https://github.com/dianbaer/Chat
	

>3、支付：https://github.com/dianbaer/Pay
	

## Identity提供的API功能：

>1、用户组API（树形结构）：
	
	创建、修改、获取、删除用户组。
	获取用户组列表。

>2、用户API：
	
	创建、修改、获取用户。
	获取用户列表。
	根据邮箱获取用户、获取用户头像。
	检查用户名、手机是否存在。

>3、tokenAPI：
	
	登录通过用户名密码获取token
	更新token
	删除token



