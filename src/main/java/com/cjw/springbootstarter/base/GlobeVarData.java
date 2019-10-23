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

import com.cjw.springbootstarter.domain.onemap.*;
import com.cjw.springbootstarter.domain.sys.TSysAttMapping;
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
     * 业务表中的属性名描述表
     */
    public static List<TSysAttMapping> attMappingList = new ArrayList<>();

    /**
     * 土地规划地类编码表
     */
    public static List<TCodeTdgh> tdghCodeList = new ArrayList<>();

    /**
     * 土地利用现状地类编码表：新版
     */
    //public static List<TCodeTdly> tdlyCodeList = new ArrayList<>();

    /**
     * 土地利用现状地类编码表：旧版
     */
    public static List<TCodeTdly2007> tdlyCode2007List = new ArrayList<>();

    /**
     * 管制区用途分类及编码表
     */
    public static List<TCodeGzq> tCodeGzqList =new ArrayList<>();

    /**
     * 文件上传下载存储位置
     */
    public static String FileUploadFolderPath = "";

    /**
     * 土地利用统计表格名称
     */
    public static String excelTempTdly="";
}
