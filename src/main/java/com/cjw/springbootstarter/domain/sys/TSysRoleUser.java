package com.cjw.springbootstarter.domain.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "角色--用户对应表")
@Data
public class TSysRoleUser {

  @ApiModelProperty(value = "编号")
  private long id;
  @ApiModelProperty(value = "用户编号")
  private long sysUserId;
  @ApiModelProperty(value = "角色编号")
  private long sysRoleId;

}
