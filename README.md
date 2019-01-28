这里是整个项目的简要说明

1、构建项目的基础目录结构-ok

2、添加swagger-ui，以便支持api规范-ok

3、添加mysql支持，其中使用mybatis-plus插件-ok

4、基于RBAC进行用户权限的控制，使用shiro中间件-ok

5、添加jwt登录验证，token验证+用户权限检验-ok
   登录成功后将jwt放入cookies，退出时清空cookies；
   根据实际需求可以加入token黑名单或者redis
   进行（1）退出操作和（2）不操作踢出

6、日志输出集成-ok

7、配置跨域支持，CorsFilter.java-为侧式

8、添加分布式锁