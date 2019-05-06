/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ProjectSimpleInfo
 * Author:   yao
 * Date:     2019/4/17 17:21
 * Description: 项目基本信息，部分字段，供列表显示
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.domain.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 〈项目基本信息，部分字段，供列表显示〉
 *
 * @author yao
 * @create 2019/4/17
 * @since 1.0.0
 */
@ApiModel(value = "高产农田项目信息")
@Data
public class ProjectSimpleInfo {

    @ApiModelProperty(value = "编号")
    private String code;
    @ApiModelProperty(value = "服务中图层索引")
    private long layerIndex;
    @ApiModelProperty(value = "数据空间范围，格式：xmin,ymin,xmax,ymax")
    private String extent;
    @ApiModelProperty(value = "项目名称")
    private String name;
    @ApiModelProperty(value = "投资类型")
    private String invest;
    @ApiModelProperty(value = "立项批复文号")
    private String approval;

    @ApiModelProperty(value = "项目类型，不进行外部展示")
    private String sysType;

    public ProjectSimpleInfo(long layerIndex, String extent, String code, String name, String invest, String approval, String sysType) {
        this.layerIndex = layerIndex;
        this.extent = extent;
        this.code = code;
        this.name = name;
        this.invest = invest;
        this.approval = approval;
        this.sysType = sysType;
    }

    public ProjectSimpleInfo() {
    }
}
