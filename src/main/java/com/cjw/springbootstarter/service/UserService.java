package com.cjw.springbootstarter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjw.springbootstarter.domain.sys.TSysPremission;
import com.cjw.springbootstarter.domain.sys.TSysUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 用户基本信息
 */
public interface UserService {
    TSysUser findByUserName(String userName);

    List<TSysPremission> GetPermissionByUserId(long userId);

    /**
     * 这是一个测试多表查询的接口
     */
    Page<Map<String,Object>> testMutiTableGetUserList(long userId, int pageNum, int pageSize);
}
