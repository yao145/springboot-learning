/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: TSysLoginResult
 * Author:   yao
 * Date:     2019/1/23 15:50
 * Description: 用户登录返回对象
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.domain;

/**
 * 〈用户登录返回对象〉
 *
 * @author yao
 * @create 2019/1/23
 * @since 1.0.0
 */
public class TSysLoginResult {
    private boolean isLogin = false;
    private String msg;

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
