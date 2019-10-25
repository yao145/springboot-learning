package com.cjw.springbootstarter.domain.project;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "高产农田项目信息-附件文件信息")
@Data
public class TProjectFileInfo {

  private long id;
  private String code;
  private String fileName;
  private long fileId;
}
