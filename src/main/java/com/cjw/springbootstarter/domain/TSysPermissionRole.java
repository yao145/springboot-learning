package com.cjw.springbootstarter.domain;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "权限--角色对应表")
public class TSysPermissionRole {

  @ApiModelProperty(value = "编号")
  private long id;
  @ApiModelProperty(value = "角色编号")
  private long roleId;
  @ApiModelProperty(value = "权限编号")
  private long permissionId;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getRoleId() {
    return roleId;
  }

  public void setRoleId(long roleId) {
    this.roleId = roleId;
  }


  public long getPermissionId() {
    return permissionId;
  }

  public void setPermissionId(long permissionId) {
    this.permissionId = permissionId;
  }

}
