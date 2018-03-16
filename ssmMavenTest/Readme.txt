一个ssm(spring+springmvc+mybatis)框架demo
spring:管理bean的容器,黏合其他模块组件,控制反转（IoC）和面向切面（AOP）

spring-mvc:mvc框架，与struts差不多。http请求一到，由容器（如：tomact）解析http搞成一个request，通过映射关系（路径，方法，参数啊）被spring mvc一个分发器去找到可以处理这个请求的bean，那tomcat里面就由spring管理bean的一个池子（bean容器）里面找到，处理完了就把响应返回回去。

mybatis:持定制化 SQL、存储过程以及高级映射
1.maven创建项目
2.填写依赖文件pom.xml
3.搭建spring+mybatis配置文件，即spring-mybatis.xml和applicationContext.xml
4.连接数据库测试
5.配置spring-mvc.xml
6.配置web.xml
7.测试
