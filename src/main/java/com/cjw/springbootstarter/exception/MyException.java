package com.cjw.springbootstarter.exception;


import lombok.Data;

/**
 * 自定义异常类
 */
@Data
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
}
