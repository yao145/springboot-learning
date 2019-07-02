package com.cjw.springbootstarter.exception;


import com.cjw.springbootstarter.base.JsonResultData;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常处理控制器
 */
@ControllerAdvice
public class MyExceptionHandler {


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResultData Handler(Exception e) {

        if (e instanceof MyException) {
            MyException xdException = (MyException) e;
            return JsonResultData.buildError(xdException.getMsg(), xdException.getCode());
        } else if(e instanceof MissingServletRequestParameterException){
            return JsonResultData.buildError("参数传入不全");
        }else {
            return JsonResultData.buildError("全局异常，未知错误");
        }
    }


}
