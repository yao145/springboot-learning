package com.cjw.springbootstarter.exception;


/**
 * 自定义异常类
 */
public class MyException extends RuntimeException {


    /**
     * 状态码
     */
    private Integer code;
    /**
     * 异常消息
     */
    private String msg;


    public MyException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
