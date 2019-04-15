package com.cjw.springbootstarter.service;

import com.cjw.springbootstarter.domain.sys.TSysLoginResult;
import org.springframework.stereotype.Service;

/**
 * 用户登录相关
 */
public interface LoginService {
    TSysLoginResult login(String userName, String password);

    void logout();
}
