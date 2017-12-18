# plumdo-stock


## 系统介绍
使用前后分离做一个简单的关于股票的增删改查demo

## 安装
```
需要环境：JDK1.8,Mysql 5.16+
服务器默认端口：8088
mysql默认配置：3306,root/root

修改端口：执行后续加入 --server.port=8088
修改mysql配置：--spring.datasource.username=root，--spring.datasource.password=root


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
<img src="https://wengwh.github.io/plumdo-stock/demo.png" alt="image" data-canonical-src="" style="max-width:100%;">