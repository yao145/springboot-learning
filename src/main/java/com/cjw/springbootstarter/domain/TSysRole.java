package com.cjw.springbootstarter.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "角色基本信息表")
@Data
public class TSysRole {

  @ApiModelProperty(value = "编号")
  private long id;
  @ApiModelProperty(value = "角色名称")
  private String name;

}
