/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: PolygonVO
 * Author:   yao
 * Date:     2019/10/25 11:04
 * Description: 前端传入后端的空间数据
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.domain.cad;

import com.cjw.springbootstarter.base.JsonResultData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈前端传入后端的空间数据〉
 *
 * @author yao
 * @create 2019/10/25
 * @since 1.0.0
 */
@Data
public class PolygonVO implements Serializable {

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "工作任务guid,可以为空")
    private String jobGuid;

    @ApiModelProperty(value = "点集合，格式为：x1,y1;x2,y2...,如果有多个面，则使用&分开")
    private List<List<double[]>> rings = new ArrayList<>();

    @ApiModelProperty(value = "CAD文件类型：DWG、DXF")
    private String cadType;

    @ApiModelProperty(value = "CAD版本号：2000、2004、2005、2007、2010")
    private String cadVersion;

    /**
     * 检验参数传入的有效性
     */
    public JsonResultData checkParams() {
        //1、不为空检验 2、数字检验
        if (this.userName == null || this.userName.length() == 0) {
            return JsonResultData.buildError("userName不能为空");
        }
        if (this.jobGuid == null || this.jobGuid.length() == 0) {
            return JsonResultData.buildError("jobGuid不能为空");
        }

        return null;
    }
}
