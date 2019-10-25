package com.cjw.springbootstarter.domain.project;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "高产农田项目信息-建设信息")
@Data
public class TProjectBuildInfo {

  private long id;
  private String code;
  private String zgm;
  private String zlgm;
  private String jbntgm;
  private String fkgm;
  private String kfgm;
  private String jhgm;
  private String jstj;
  private String xzgd;
  private String ssnd;
  private String zldb;
  private String stmj;
  private String sjdmj;
  private String hdmj;
  private String xzcnzb;
  private String gzmj;
  private String pjzldb;
  private String gzstmj;
  private String gzzldb;
  private String gzsjdmj;
  private String tscnzb;
  private String zhgdmj;
  private String zbpjmj;
  private String xzgdl;
  private String gdtzbz;
}
