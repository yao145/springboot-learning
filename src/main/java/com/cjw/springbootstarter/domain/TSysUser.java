package com.cjw.springbootstarter.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户基本信息类")
public class TSysUser {

  @ApiModelProperty(value = "编号")
  private long id;
  @ApiModelProperty(value = "用户名称")
  private String username;
  @ApiModelProperty(value = "用户密码")
  private String password;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
