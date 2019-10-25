package com.cjw.springbootstarter.domain.project;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "高产农田项目信息-投资信息")
@Data
public class TProjectInvestInfo {

  private long id;
  private String code;
  private String tzgm;
  private String dfzj;
  private String xzzjZy;
  private String xzzjDf;
  private String gdkk;
  private String zxbcgd;
  private String tdcrsr;
  private String tdfg;
  private String tdfgtz;
  private String qtzj;

}
