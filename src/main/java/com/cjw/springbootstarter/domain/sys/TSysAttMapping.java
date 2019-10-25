package com.cjw.springbootstarter.domain.sys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "业务表中的属性名描述表")
@Data
public class TSysAttMapping {

    @JsonIgnore
    @ApiModelProperty(value = "编号")
    private long id;

    @JsonIgnore
    @ApiModelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "属性名称")
    private String attName;

    @ApiModelProperty(value = "显示名称")
    private String showName;

    @ApiModelProperty(value = "描述")
    private String detail;

    @ApiModelProperty(value = "显示类型,显示show、隐藏hide、高亮highlight等效果")
    private String showType;

    @JsonIgnore
    @ApiModelProperty(value = "排序")
    private long orderId;

    @ApiModelProperty(value = "表中内部类型")
    private String sysType;
}
