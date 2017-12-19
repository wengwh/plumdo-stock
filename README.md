# plumdo-stock


## 系统介绍
使用前后分离做一个简单的关于股票的增删改查demo

## 安装
```
下载：https://github.com/wengwh/plumdo-stock/releases/tag/0.1.0

需要环境：JDK1.8,Mysql 5.16+
服务器默认端口：8088
mysql默认配置：
	端口：3306
	用户/密码：root/root

修改端口：--server.port=8088
修改mysql配置：
--spring.datasource.url=jdbc:mysql://localhost:3306?useUnicode=true&characterEncoding=UTF-8
--spring.datasource.username=root
--spring.datasource.password=root


第一次运行（初始化设置成true，会执行Sql脚本）：
java -jar plumdo-stock.jar --spring.datasource.initialize=true

后续执行：
java -jar plumdo-stock.jar 


执行成功访问：http://localhost:8088

```

## 相关技术

```
前端技术
构建：Yeoman Bower Gulp
框架：AngularJS
UI：Angular Material


后端技术
语言：Java
构建：Maven
框架：SpringBoot
数据库：Mysql
```

![Aaron Swartz](https://raw.githubusercontent.com/wengwh/plumdo-stock/master/docs/demo.png)