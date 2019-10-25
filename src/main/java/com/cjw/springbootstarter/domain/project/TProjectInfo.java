package com.cjw.springbootstarter.domain.project;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "高产农田项目信息-基本信息")
@Data
public class TProjectInfo {

  private long id;
  private long layerIndex;
  private String extent;
  private String code;
  private String projectName;
  private String projectYear;
  private String investType;
  private String projectType;
  private String reportType;
  private String projectProperty;
  private String xzqhName;
  private String projectLoc;
  private String projectOffice;
  private String approvalNumber;
  private String approvalName;
  private String approvalDate;
  private String projectSchedule;
  private String isDisaster;
  private String budgetNumber;
  private String budgetDate;
  private String projectPlanUnit;
  private String xzqhDetail;
  private String lonlatRegion;
  private String projectLocation;
  private String projectLandType;
  private String email;
  private String xNewsName;
  private String xNewsPhone;
  private String sNewsName;
  private String sNewsPhone;
  private String sysType;
}
