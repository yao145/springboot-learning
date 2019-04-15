/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: GlobeVarData
 * Author:   yao
 * Date:     2019/1/22 17:13
 * Description: 系统全局变量
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.base;

import com.cjw.springbootstarter.domain.sys.TSysPermissionRole;
import com.cjw.springbootstarter.domain.sys.TSysPremission;
import com.cjw.springbootstarter.domain.sys.TSysRole;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈系统全局变量〉
 *
 * @author yao
 * @create 2019/1/22
 * @since 1.0.0
 */
public class GlobeVarData {

    /**
     * 权限列表
     */
    public static List<TSysPremission> premissionList = new ArrayList<>();
    /**
     * 权限-角色对应列表
     */
    public static List<TSysPermissionRole> premissionRoleList = new ArrayList<>();
    /**
     * 角色列表
     */
    public static List<TSysRole> roleList = new ArrayList<>();

    /**
     * 文件上传下载存储位置
     */
    public static String FileUploadFolderPath="";
}
