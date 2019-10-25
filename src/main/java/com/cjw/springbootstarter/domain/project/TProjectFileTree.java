package com.cjw.springbootstarter.domain.project;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "高产农田项目信息-附件树结构")
@Data
public class TProjectFileTree {

  private long id;
  private String name;
  private String detail;
  private long orderId;
  private long pId;
}
