package com.cjw.springbootstarter.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "角色基本信息表")
public class TSysRole {

  @ApiModelProperty(value = "编号")
  private long id;
  @ApiModelProperty(value = "角色名称")
  private String name;


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

}
