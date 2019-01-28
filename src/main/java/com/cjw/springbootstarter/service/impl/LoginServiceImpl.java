/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: LoginServiceImpl
 * Author:   yao
 * Date:     2019/1/23 15:32
 * Description: 用户登录业务实现类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.service.impl;

import com.cjw.springbootstarter.base.JsonResultData;
import com.cjw.springbootstarter.base.PublicResultConstant;
import com.cjw.springbootstarter.domain.TSysLoginResult;
import com.cjw.springbootstarter.service.LoginService;
import com.cjw.springbootstarter.util.JwtToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;


/**
 * 〈用户登录业务实现类〉
 *
 * @author yao
 * @create 2019/1/23
 * @since 1.0.0
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Override
    public TSysLoginResult login(String userName, String password) {
        TSysLoginResult loginResult = new TSysLoginResult();
        if (userName == null || userName.isEmpty()) {
            loginResult.setLogin(false);
            loginResult.setMsg(PublicResultConstant.EMPTY_USERNAME);
            return loginResult;
        }
        String msg;
        // 1、获取Subject实例对象
        Subject currentUser = SecurityUtils.getSubject();

        // 2、将用户名和密码封装到UsernamePasswordToken
        JwtToken jwtToken = new JwtToken(userName, password, false);
        // 3、认证
        try {
            // 传到MyAuthorizingRealm类中的方法进行认证
            currentUser.login(jwtToken);
            loginResult.setLogin(true);
            return loginResult;
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            msg = PublicResultConstant.INVALID_USER;
        } catch (IncorrectCredentialsException eww) {
            msg = PublicResultConstant.INVALID_USERNAME_PASSWORD;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            msg = PublicResultConstant.ERROR_LOGIN_INFO;
        }

        loginResult.setLogin(false);
        loginResult.setMsg(msg);

        return loginResult;
    }

    @Override
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }
}
