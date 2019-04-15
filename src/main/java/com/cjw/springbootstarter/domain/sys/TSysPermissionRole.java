package com.cjw.springbootstarter.domain.sys;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "权限--角色对应表")
@Data
public class TSysPermissionRole {

  @ApiModelProperty(value = "编号")
  private long id;
  @ApiModelProperty(value = "角色编号")
  private long roleId;
  @ApiModelProperty(value = "权限编号")
  private long permissionId;

}
