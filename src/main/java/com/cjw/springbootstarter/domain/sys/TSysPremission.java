package com.cjw.springbootstarter.domain.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "权限基本信息表")
@Data
public class TSysPremission {

    @ApiModelProperty(value = "编号")
    private long id;
    @ApiModelProperty(value = "权限名称")
    private String name;
    @ApiModelProperty(value = "描述")
    private String descripion;
    @ApiModelProperty(value = "后台服务入口")
    private String url;
    @ApiModelProperty(value = "父节点id")
    private long pid;
    @ApiModelProperty(value = "perms")
    private String perms;
    @ApiModelProperty(value = "类型，0--页面；1--功能列表；2--按钮")
    private long type;
    @ApiModelProperty(value = "前端显示按钮")
    private String icon;
    @ApiModelProperty(value = "内部排序")
    private long orderNum;
}
