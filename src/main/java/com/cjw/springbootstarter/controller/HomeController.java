package com.cjw.springbootstarter.controller;

import com.cjw.springbootstarter.base.ApplicationConstant;
import com.cjw.springbootstarter.base.JsonResultData;
import com.cjw.springbootstarter.base.PublicResultConstant;
import com.cjw.springbootstarter.domain.sys.TSysLoginResult;
import com.cjw.springbootstarter.domain.sys.TSysUser;
import com.cjw.springbootstarter.service.LoginService;
import com.cjw.springbootstarter.service.UserService;
import com.cjw.springbootstarter.util.CookieUtils;
import com.cjw.springbootstarter.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class HomeController {
    @ApiIgnore
    @RequestMapping(value = "/")
    public void index(HttpServletResponse response) throws Exception {

        response.sendRedirect("swagger-ui.html");
    }

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public JsonResultData login(HttpServletRequest request, HttpServletResponse response) {
        String userName = request.getParameter("username");
        String password = request.getParameter("passwd");

        TSysLoginResult loginResult = loginService.login(userName, password);
        if (loginResult.isLogin()) {
            //登录成功则构建jwt放入cookies
            TSysUser user = userService.findByUserName(userName);
            CookieUtils.setCookie(request, response, ApplicationConstant.TOKEN_NAME,
                    JwtUtils.getJsonWebToken(user), ApplicationConstant.TOKEN_EXPIRE / 1000);
            return JsonResultData.buildSuccess(PublicResultConstant.SUCCEED, 0);
        } else {
            return JsonResultData.buildError(loginResult.getMsg(), -1);
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public JsonResultData logOut(HttpServletRequest request, HttpServletResponse response) {
        //涉及到jwt的注销问题，这里将cookies中的jwt删除，即完成了退出操作
        //不过并不表示该token不可用
        CookieUtils.deleteCookie(request, response, ApplicationConstant.TOKEN_NAME);
        loginService.logout();
        return JsonResultData.buildSuccess("退出成功", 0);
    }

}
