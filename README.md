该项目作为《广水国土资源一张图》后台

整个项目的简要说明

1、构建项目的基础目录结构-ok

2、添加swagger-ui，以便支持api规范-ok

3、添加mysql支持，其中使用mybatis-plus插件-ok

4、基于RBAC进行用户权限的控制，使用shiro中间件-ok
   改进点：MyShiroRealm中可以将用户权限进行缓存操作,
   本项目将权限树读入了全局变量

5、添加jwt登录验证，token验证+用户权限检验-ok
   登录成功后将jwt放入cookies，退出时清空cookies；
   根据实际需求可以加入token黑名单或者redis
   进行（1）退出操作和（2）不操作踢出

6、日志输出集成-ok

7、配置跨域支持，CorsFilter.java-未侧式

8、增加Lombok插件，减少很多重复代码的书写。  -ok
   比如说getter/setter/toString等方法的编写

9、添加redis支持   基本操作使用jedis    -ok
   添加分布式锁  使用redisson方式    两种redis客户端互补  问题：打包成war报错,但不影响程序运行
   测试：http://localhost:8080/user/redisson  jmeter进行测试
   
10、实现分布式session机制,只需添加spring-session-data-redis依赖    -ok
      
11、结合redis，添加10分钟内5次登录操作的限制

12、添加图片验证码的支持，参考：https://blog.csdn.net/larger5/article/details/79522105
