/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: UserServiceImpl
 * Author:   yao
 * Date:     2019/1/23 15:29
 * Description: 用户操作实现类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cjw.springbootstarter.base.GlobeVarData;
import com.cjw.springbootstarter.domain.sys.TSysPermissionRole;
import com.cjw.springbootstarter.domain.sys.TSysPremission;
import com.cjw.springbootstarter.domain.sys.TSysUser;
import com.cjw.springbootstarter.exception.MyException;
import com.cjw.springbootstarter.mapper.sys.RoleUserMapper;
import com.cjw.springbootstarter.mapper.sys.UserMapper;
import com.cjw.springbootstarter.service.UserService;
import com.cjw.springbootstarter.util.Log4JUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 〈一句话功能简述〉<br>
 * 〈用户操作实现类〉
 *
 * @author yao
 * @create 2019/1/23
 * @since 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleUserMapper roleUserMapper;

    @Override
    public TSysUser findByUserName(String userName) {
        List<TSysUser> userList = userMapper.selectList(new QueryWrapper<TSysUser>().eq("username", userName));
        if (userList.size() > 1) {
            Log4JUtils.getLogger().error("用户名重复，无法正常使用，用户名为-->【" + userName + "】");
            throw new MyException("用户名存在重复，无法正常使用", -1);
        } else if (userList.size() == 0) {
            return null;
        } else {
            return userList.get(0);
        }
    }

    @Override
    public List<TSysPremission> GetPermissionByUserId(long userId) {
        //通过用户id获取权限列表
        //查询所有的role id，并去重复
        List<Long> roleIdList = roleUserMapper.findRoleIdsByUserId(userId);
        //查询全局变量获取权限id列表
        List<Long> permissionIdList = GlobeVarData.premissionRoleList.stream().
                filter(a -> roleIdList.contains(a.getRoleId())).
                map(TSysPermissionRole::getPermissionId).collect(Collectors.toList());
        //查询全局变量获取权限列表
        List<TSysPremission> premissionList = GlobeVarData.premissionList.stream().
                filter(a -> permissionIdList.contains(a.getId())).collect(Collectors.toList());
        Log4JUtils.getLogger().info("用户【 id=" + userId + "】权限列表获取完成，共计：" + premissionList.size());
        return premissionList;
    }
}
