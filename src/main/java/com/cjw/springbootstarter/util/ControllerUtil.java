/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ControllerUtil
 * Author:   yao
 * Date:     2019/4/10 9:38
 * Description: Controller工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.util;

import com.cjw.springbootstarter.base.ApplicationConstant;

import java.util.HashMap;
import java.util.Iterator;

/**
 * 〈Controller工具类〉
 *
 * @author yao
 * @create 2019/4/10
 * @since 1.0.0
 */
public class ControllerUtil {
    /**
     * 初始化传入参数,如果存在错误，直接返回错误提示
     */
    public static String initParams(HashMap<String,Object> params){
        Iterator iterator = params.keySet().iterator();
        while(iterator.hasNext()){
            String key = iterator.next().toString();
            Object value = params.get(key);
            if("pagenum".equals(key)){
                if("".equals(value)||value==null){
                    params.put(key, ApplicationConstant.HTTP_PARAM_PAGENUM);
                }
            }
            if("pagesize".equals(key)){
                if("".equals(value)||value==null){
                    params.put(key,ApplicationConstant.HTTP_PARAM_PAGESIZE);
                }
            }
            if("keywords".equals(key)){
                if("".equals(value) || value == null){
                    return "keywords不能为空";
                }
            }
        }
        return "";
    }
}
