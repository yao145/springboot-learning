/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: GpModelForClib
 * Author:   yao
 * Date:     2019/4/28 9:17
 * Description: 裁剪分析类输入参数
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.domain.ags;

import com.cjw.springbootstarter.base.JsonResultData;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 〈裁剪分析类输入参数〉
 *
 * @author yao
 * @create 2019/4/28
 * @since 1.0.0
 */
@Data
public class GpModelForClib {

    @ApiModelProperty(value = "gp编号")
    private String gpid;
    @ApiModelProperty(value = "坐标系wkid,与点集合匹配,一般设置为4490")
    private String wkid;
    @ApiModelProperty(value = "点集合，格式为：x1,y1;x2,y2...,如果有多个面，则使用&分开")
    private String points;

    /**
     * 检验参数传入的有效性
     */
    public JsonResultData checkParams() {
        //1、不为空检验 2、数字检验
        if (this.gpid == null || this.gpid.length() == 0) {
            return JsonResultData.buildError("gpid不能为空");
        }
        if (this.wkid == null || this.wkid.length() == 0) {
            return JsonResultData.buildError("wkid不能为空");
        } else {
            try {
                Integer.parseInt(this.wkid);
            } catch (Exception ex) {
                return JsonResultData.buildError("wkid必须为数字,例如4490");
            }
        }
        if (this.points == null || this.points.length() == 0) {
            return JsonResultData.buildError("points不能为空");
        } else {
            try {
                String[] ringsStr = this.points.split("&");
                for (String ringStr : ringsStr) {
                    String[] xyList = ringStr.split(";");
                    for (String xy : xyList) {
                        String[] xyxy = xy.split(",");
                        if (xyxy.length != 2) {
                            return JsonResultData.buildError("points存在无效的xy点");
                        }
                        double x = Double.parseDouble(xyxy[0]);
                        double y = Double.parseDouble(xyxy[1]);
                        if (x < -180 || x > 180) {
                            return JsonResultData.buildError("points存在超出范围的x");
                        }
                        if (y < -90 || y > 90) {
                            return JsonResultData.buildError("points存在超出范围的y");
                        }
                    }
                    if (xyList.length < 3) {
                        return JsonResultData.buildError("传入坐标点无法构建面");
                    }
                }
            } catch (Exception ex) {
                return JsonResultData.buildError("points格式错误");
            }
        }
        return null;
    }

    /**
     * 将对象转换为ags支持的json字符串
     */
    public String toAgsJsonStr() {
        JsonObject root = new JsonObject();
        root.addProperty("displayFieldName", "");
        root.addProperty("geometryType", "esriGeometryPolygon");
        root.addProperty("exceededTransferLimit", false);
        //添加坐标系
        JsonObject sRef = new JsonObject();
        sRef.addProperty("wkid", this.wkid);
        sRef.addProperty("latestWkid", this.wkid);
        root.add("spatialReference", sRef);
        //添加固定字段
        JsonArray fields = new JsonArray();
        JsonObject field = new JsonObject();
        field.addProperty("name", "Id");
        field.addProperty("type", "esriFieldTypeInteger");
        field.addProperty("alias", "Id");
        fields.add(field);
        root.add("fields", fields);
        //添加features
        JsonArray features = new JsonArray();
        JsonObject feature = new JsonObject();

        JsonObject attributes = new JsonObject();
        attributes.addProperty("Id", 0);
        feature.add("attributes", attributes);

        JsonObject geometry = new JsonObject();

        JsonArray rings = new JsonArray();

        //解析点集合
        String[] ringsStr = this.points.split("&");
        for (String ringStr : ringsStr) {
            JsonArray ring = new JsonArray();
            String[] xyList = ringStr.split(";");
            for (String xyStr : xyList) {
                String[] xyxy = xyStr.split(",");
                double x = Double.parseDouble(xyxy[0]);
                double y = Double.parseDouble(xyxy[1]);
                JsonArray xyJson = new JsonArray();
                xyJson.add(x);
                xyJson.add(y);
                ring.add(xyJson);
            }
            rings.add(ring);
        }

        geometry.add("rings", rings);
        feature.add("geometry", geometry);

        features.add(feature);
        root.add("features", features);

        //返回json字符串
        String resJson = root.toString();
        return resJson;
    }

}
