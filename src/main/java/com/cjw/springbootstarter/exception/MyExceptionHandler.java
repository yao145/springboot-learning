package com.cjw.springbootstarter.exception;


import com.cjw.springbootstarter.base.JsonResultData;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
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
        } else if (e instanceof UnauthenticatedException) {
            return JsonResultData.buildError("用户未授权", -1);
        }else if(e instanceof UnauthorizedException){
            return JsonResultData.buildError("用户缺少服务调用权限", -1);
        }
        else {
            return JsonResultData.buildError("全局异常，未知错误");
        }
    }


}
