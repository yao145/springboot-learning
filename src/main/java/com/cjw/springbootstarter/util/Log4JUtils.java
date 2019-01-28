/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Log4JUtils
 * Author:   yao
 * Date:     2018/12/20 15:33
 * Description: 日志输出公共类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.Reflection;

/**
 * 〈一句话功能简述〉<br>
 * 〈日志输出公共类〉
 *
 * @author yao
 * @create 2018/12/20
 * @since 1.0.0
 */
public class Log4JUtils {

    private static Logger logger = null;

    public static Logger getLogger() {
        //Java8 废弃了Reflection.getCallerClass(),出现报错
        //0：返回sun.reflect.Reflection
        //1:返回当前类的Class对象
        //2：返回调用该方法的Class对象
        logger = LoggerFactory.getLogger(Reflection.getCallerClass(2).getName());
        return logger;
    }
}
