/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: JwtToken
 * Author:   yao
 * Date:     2019/1/25 11:00
 * Description: 自定义AuthenticationToken
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.util;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 〈自定义AuthenticationToken〉
 * 基于该类进行jwt方式的自登录操作
 *
 * @author yao
 * @create 2019/1/25
 * @since 1.0.0
 */
public class JwtToken implements AuthenticationToken {

    private String userName;

    private String passWd;

    public String getPassWd() {
        return passWd;
    }

    public void setPassWd(String passWd) {
        this.passWd = passWd;
    }

    /**
     * 标识位，是否使用密码进行登录
     */
    private boolean isNoPasswd;

    public boolean isNoPasswd() {
        return isNoPasswd;
    }

    public void setNoPasswd(boolean isNoPasswd) {
        isNoPasswd = isNoPasswd;
    }

    public JwtToken(String userName, String passWd, boolean isNoPasswd) {
        this.userName = userName;
        this.passWd = passWd;
        this.isNoPasswd = isNoPasswd;
    }

    @Override
    public Object getPrincipal() {
        return userName;
    }

    @Override
    public Object getCredentials() {
        if (isNoPasswd) {
            return userName;
        } else {
            return passWd;
        }
    }
}
