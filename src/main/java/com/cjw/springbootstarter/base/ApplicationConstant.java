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


import java.util.concurrent.TimeUnit;

/**
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

    //服务调用相关信息
    public static final String HTTP_RETURN_ERROR = "服务调用失败";
    public static final String HTTP_FILEUPLOAD_ERROR = "文件上传失败";
    //服务调用时，分页参数，默认页码
    public static final long HTTP_PARAM_PAGENUM = 1L;
    //服务调用时，分页参数，每页默认数量
    public static final long HTTP_PARAM_PAGESIZE = 15L;

    //数据库操作后的相关提示文本
    public static final String DB_INSERT_ERROR = "数据库插入失败";
    public static final String DB_INSERT_SUCCESS = "数据库插入成功";
    public static final String DB_DELETE_ERROR = "数据内容删除失败";
    public static final String DB_DELETE_SUCCESS = "数据内容删除成功";
    public static final String DB_UPDATE_ERROR = "数据内容更新失败";
    public static final String DB_UPDATE_SUCCESS = "数据内容更新成功";

    //ags gp服务调用相关常量
    public static final String AGS_GP_STATUS_KEY = "ags_gpstatus_%s_%s";
    public static final String AGS_GP_RESULT_FOR_STATICS_KEY = "ags_gpresult_statics_%s_%s";
    public static final int AGS_GP_KEEP_RESULT_TOTAL_TIME = 3;
    public static final TimeUnit AGS_GP_KEEP_RESULT_TOTAL_TIMEUNIT = TimeUnit.HOURS;
}
