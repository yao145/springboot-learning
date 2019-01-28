package com.cjw.springbootstarter.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "权限基本信息表")
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


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescripion() {
        return descripion;
    }

    public void setDescripion(String descripion) {
        this.descripion = descripion;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }


    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }


    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    public long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(long orderNum) {
        this.orderNum = orderNum;
    }

}
