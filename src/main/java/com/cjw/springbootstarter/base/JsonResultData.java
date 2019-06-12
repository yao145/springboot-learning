package com.cjw.springbootstarter.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 功能描述：工具类
 *
 * <p> 创建时间：May 14, 2018 7:58:06 PM </p>
 */
@Data
public class JsonResultData implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    // 状态码 0 表示成功，1表示处理中，-1表示失败
    private Integer code;
    // 数据
    private Object data;
    // 描述
    private String msg;

    public JsonResultData() {
    }

    public JsonResultData(Integer code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    // 成功，传入数据
    public static JsonResultData buildSuccess() {
        return new JsonResultData(0, null, null);
    }

    // 成功，传入数据
    public static JsonResultData buildSuccess(Object data) {
        return new JsonResultData(0, data, JsonResultData.SUCCESS);
    }

    // 失败，传入描述信息
    public static JsonResultData buildError(Object data) {
        return new JsonResultData(-1, data, JsonResultData.ERROR);
    }

    // 失败，传入描述信息,状态码
    public static JsonResultData buildError(Object data, Integer code) {
        return new JsonResultData(code, data, JsonResultData.ERROR);
    }

    // 成功，传入数据,及描述信息
    public static JsonResultData buildSuccess(Object data, String msg) {
        return new JsonResultData(0, data, msg);
    }

    // 成功，传入数据,及状态码
    public static JsonResultData buildSuccess(Object data, int code) {
        return new JsonResultData(code, data, JsonResultData.SUCCESS);
    }

    @Override
    public String toString() {
        return "JsonResultData [code=" + code + ", data=" + data + ", msg=" + msg
                + "]";
    }
}
