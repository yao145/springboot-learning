package com.cjw.springbootstarter.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "用户基本信息类")
@Data
public class TSysUser {

  @ApiModelProperty(value = "编号")
  private long id;
  @ApiModelProperty(value = "用户名称")
  private String username;
  @ApiModelProperty(value = "用户密码")
  private String password;
}
