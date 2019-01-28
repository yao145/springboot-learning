/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: MyShiroRealm
 * Author:   yao
 * Date:     2019/1/23 15:58
 * Description: shiro用户认证与授权业务
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.base;

import com.cjw.springbootstarter.domain.TSysPremission;
import com.cjw.springbootstarter.domain.TSysUser;
import com.cjw.springbootstarter.service.UserService;
import com.cjw.springbootstarter.util.JwtToken;
import com.cjw.springbootstarter.util.Log4JUtils;
import com.cjw.springbootstarter.util.MD5Utils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 〈shiro用户认证与授权业务〉
 *
 * @author yao
 * @create 2019/1/23
 * @since 1.0.0
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    //权限信息，包括角色以及权限
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Log4JUtils.getLogger().info("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //如果身份认证的时候没有传入User对象，这里只能取到userName
        //也就是SimpleAuthenticationInfo构造的时候第一个参数传递需要User对象
        TSysUser user = (TSysUser) principals.getPrimaryPrincipal();

        List<TSysPremission> permissionList = userService.GetPermissionByUserId(user.getId());
        for (TSysPremission premission : permissionList) {
            String perms = premission.getPerms();
            if (StringUtils.hasLength(perms))
                authorizationInfo.addStringPermission(perms);
        }
        return authorizationInfo;
    }

    //主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        Log4JUtils.getLogger().info("用户身份认证-->MyShiroRealm.doGetAuthorizationInfo()");
        JwtToken jwtToken = (JwtToken) token;
        //获取用户的输入的账号.
        String userName = (String) jwtToken.getPrincipal();
        //通过username从数据库中查找 User对象.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        TSysUser user = userService.findByUserName(userName);
        Log4JUtils.getLogger().info("----->>user=" + user);
        if (user == null) {
            return null;
        }
        String passWd = user.getPassword();
        if (jwtToken.isNoPasswd() == true) {
            //进行jwt自登录操作，不进行密码校验，需要奖密码进行md5加密
            passWd = MD5Utils.getPwd(userName);
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                //这里传入的是user对象，比对的是用户名，直接传入用户名也没错，但是在授权部分就需要自己重新从数据库里取权限
                user,
                //密码
                passWd,
                //realm name
                getName()
        );
        return authenticationInfo;
    }

}
