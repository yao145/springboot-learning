/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ApplicationConstant
 * Author:   yao
 * Date:     2019/1/22 10:17
 * Description: 项目常量类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.base;


/**
 * 〈一句话功能简述〉<br>
 * 〈项目常量类〉
 *
 * @author yao
 * @create 2019/1/22
 * @since 1.0.0
 */
public class ApplicationConstant {

    //项目基本信息
    public static final String PROJECT_NAME = "SpringBoot脚手架工程";
    public static final String PROJECT_DES = "后台框架性工程，基于这个搭建后台服务";
    public static final String PROJECT_VERSION = "1.0";
    public static final String PROJECT_GROUPNAME = "demo-yao-api";

    //token标志位
    public static final String TOKEN_NAME = "token";
    public static final String TOKEN_SUBJECT = "cjw";
    //过期时间，毫秒，设置为一小时
    public static final int TOKEN_EXPIRE = 1000 * 60 * 60;
    //密钥
    public static final String TOKEN_APPSECRET = "yaozhiwu";

    //redis实现分布式锁时的临时key
    public static final String REDIS_LOCK_KEY = "this_is_a_key";
    //分布式锁时，key的最长时间
    public static final int REDIS_LOCK_KEY_EXPIRE_TIME = 60;

}
