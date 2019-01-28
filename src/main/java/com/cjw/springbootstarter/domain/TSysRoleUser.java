package com.cjw.springbootstarter.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "角色--用户对应表")
public class TSysRoleUser {

  @ApiModelProperty(value = "编号")
  private long id;
  @ApiModelProperty(value = "用户编号")
  private long sysUserId;
  @ApiModelProperty(value = "角色编号")
  private long sysRoleId;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getSysUserId() {
    return sysUserId;
  }

  public void setSysUserId(long sysUserId) {
    this.sysUserId = sysUserId;
  }


  public long getSysRoleId() {
    return sysRoleId;
  }

  public void setSysRoleId(long sysRoleId) {
    this.sysRoleId = sysRoleId;
  }

}
