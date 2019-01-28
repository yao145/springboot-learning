package com.cjw.springbootstarter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjw.springbootstarter.base.GlobeVarData;
import com.cjw.springbootstarter.domain.TSysPermissionRole;
import com.cjw.springbootstarter.domain.TSysPremission;
import com.cjw.springbootstarter.domain.TSysRoleUser;
import com.cjw.springbootstarter.domain.TSysUser;
import com.cjw.springbootstarter.mapper.RoleUserMapper;
import com.cjw.springbootstarter.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.awt.geom.AreaOp;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMyBatisPlus {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleUserMapper roleUserMapper;

    @Test
    public void testUserMapper() {
        IPage<TSysUser> userList = userMapper.selectPage(
                new Page<TSysUser>(1, 10),
                new QueryWrapper<TSysUser>().like("id", "1")
        );
        List<TSysUser> userListResutl = userList.getRecords();
        System.out.println(userListResutl.get(0).getUsername());
    }

    @Test
    public void testGetPermissionByUserId() {
        int id = 1;
        //查询所有的role id，并去重复
        List<Long> roleIdList = roleUserMapper.findRoleIdsByUserId(id);
        //查询全局变量获取权限id列表
        List<Long> permissionIdList = GlobeVarData.premissionRoleList.stream().
                filter(a -> roleIdList.contains(a.getRoleId())).
                map(TSysPermissionRole::getPermissionId).collect(Collectors.toList());
        //查询全局变量获取权限列表
        List<TSysPremission> premissionList = GlobeVarData.premissionList.stream().
                filter(a -> permissionIdList.contains(a.getId())).collect(Collectors.toList());
        System.out.println("权限列表获取完成，共计：" + premissionList.size());
    }
}

