package com.cjw.springbootstarter.service;

import com.cjw.springbootstarter.domain.sys.TSysPremission;
import com.cjw.springbootstarter.domain.sys.TSysUser;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户基本信息
 */
public interface UserService {
    TSysUser findByUserName(String userName);

    List<TSysPremission> GetPermissionByUserId(long userId);
}
