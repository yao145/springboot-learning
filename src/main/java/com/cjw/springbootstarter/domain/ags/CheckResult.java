/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: CheckResult
 * Author:   yao
 * Date:     2019/5/28 19:15
 * Description: 一张图审查结果
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.domain.ags;

import com.cjw.springbootstarter.util.Log4JUtils;
import lombok.Data;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

/**
 * 〈一张图审查结果〉
 *
 * @author yao
 * @create 2019/5/28
 * @since 1.0.0
 */
@Data
public class CheckResult implements Serializable {


    /**
     * 结果代码
     */
    private String statusCode;

    /**
     * 结果展示名称
     */
    private String statusName;
    /**
     * 结果的综合性描述，根据模型不同进行区分
     */
    private String detail;

    public CheckResult() {
    }

    /**
     * 主动构建一个审查结果
     */
    public CheckResult(String statusCode) {
        this.statusCode = statusCode;
        this.statusName = JobStatus.getStatusName(statusCode);
    }

    /**
     * 根据模型类型，动态构建当前结果的描述信息
     */
    public void refreshResult(TArcGpserver gpserver, FeatureArray featureArray) {

        //根据模型类型进行结果描述语言的构建
        //featureArray中存放着统计信息
        String modelName = gpserver.getName();

        List<FeatureItem> features = featureArray.getFeatures();

        if ("dltb".equals(modelName)) {
            this.statusCode = JobStatus.RESULT_WARNING;
            this.detail = "请通过对项目范围内的用地现状构成进行分析，以确认报件出让方式、利用方向的合理性";
        } else if ("tdgh".equals(modelName)) {
            this.statusCode = JobStatus.RESULT_WARNING;
            this.detail = "请通过对项目范围内的用地规划构成进行分析，以确认报件中土地地类、权属、面积在规划内";
        } else if ("jbnt".equals(modelName) || "tdzz".equals(modelName) || "kcq".equals(modelName)) {
            String tarName = "";
            switch (modelName) {
                case "jbnt":
                    tarName = "基本农田数据";
                    break;
                case "tdzz":
                    tarName = "土地整治项目";
                    break;
                case "kcq":
                    tarName = "矿产资源数据";
                    break;
                default:
            }
            if (features.size() == 0) {
                this.statusCode = JobStatus.RESULT_SUCCESS;
            } else if (features.size() == 1) {
                Object area = features.get(0).getValueByName("AREA");
                String areaStr = convertM2HA(area);
                if ("".equals(areaStr)) {
                    this.statusCode = JobStatus.RESULT_SUCCESS;
                } else {
                    this.statusCode = JobStatus.RESULT_ERROR;
                    this.detail = "报件中的项目范围占用" + tarName + "，合计 " + areaStr;
                }
            } else {
                Log4JUtils.getLogger().error(tarName + "统计结果出现两条记录，代码存在逻辑错误");
            }
        } else if ("gzq".equals(modelName)) {
            if (features.size() == 0) {
                this.statusCode = JobStatus.RESULT_ERROR;
                this.detail = "未发现有效数据，请检验传入坐标的有效性";
            } else {
                //需要逻辑写死，通过GZQLXDM字段信息判断是否符合建设用地要求
                FeatureItem featureItem = null;
                int flag = 0;
                for (FeatureItem item : features) {
                    String gzqDM = item.getValueByName("GZQLXDM").toString();
                    if ("020".equals(gzqDM) && featureItem == null) {
                        //有条件
                        featureItem = item;
                        flag = 1;
                    } else if ("030".equals(gzqDM)) {
                        //限制区
                        featureItem = item;
                        flag = 2;
                    }
                }
                if (flag == 0) {
                    this.statusCode = JobStatus.RESULT_SUCCESS;
                } else if (flag == 1) {
                    this.statusCode = JobStatus.RESULT_WARNING;
                    this.detail = "报件中的项目范围含有条件建设区";
                } else if (flag == 2) {
                    this.statusCode = JobStatus.RESULT_ERROR;
                    this.detail = "报件中的项目范围含限制建设区";
                }
            }
        }
        this.statusName = JobStatus.getStatusName(statusCode);
    }

    /**
     * 平方米转公顷
     */
    private String convertM2HA(Object fieldValue) {
        try {
            double area = Double.parseDouble(fieldValue.toString());
            if (area <= 0.00001) {
                return "";
            } else if (area <= 100) {
                return area + " 平方米";
            } else {
                DecimalFormat df = new DecimalFormat(".00");
                String areaHA = df.format(area / 10000);
                return areaHA + " 公顷";
            }
        } catch (Exception ex) {
            return " -1 平方米";
        }
    }

}
