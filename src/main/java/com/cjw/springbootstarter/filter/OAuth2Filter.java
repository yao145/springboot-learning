/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: OAuth2Filter
 * Author:   yao
 * Date:     2019/1/24 11:18
 * Description: oauth2拦截器，现在改为 JWT 认证
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.filter;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.cjw.springbootstarter.base.ApplicationConstant;
import com.cjw.springbootstarter.base.JsonResultData;
import com.cjw.springbootstarter.base.PublicResultConstant;
import com.cjw.springbootstarter.util.JwtToken;
import com.cjw.springbootstarter.util.JwtUtils;
import com.cjw.springbootstarter.util.Log4JUtils;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 〈oauth2拦截器，现在改为 JWT 认证〉
 *
 * @author yao
 * @create 2019/1/24
 * @since 1.0.0
 */
@Component
public class OAuth2Filter extends BasicHttpAuthenticationFilter {

    private static final Gson gson = new Gson();

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws UnauthorizedException {
        String token = getRequestToken((HttpServletRequest) request);
        //判断请求的请求头是否带上 "Token"
        if (!StringUtils.isEmpty(token)) {
            //如果存在，则进入 executeLogin 方法执行登入，检查 token 是否正确
                try {
                executeLogin(request, response);
                return true;
            } catch (Exception e) {
                //token 无效
                sendJsonMessage(response, JsonResultData.buildError(PublicResultConstant.TOKEN_IS_INVALID));
            }
        }
        //如果请求头不存在 Token，则可能是执行登陆操作或者是游客状态访问，无需检查 token，直接返回 true
        return true;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        String token = getRequestToken((HttpServletRequest) request);
        Claims claims = JwtUtils.checkJWT(token);
        if (claims != null) {
            String userName = claims.get("name").toString();
            // 如果没有抛出异常则代表token有效,这里需要模拟登录
            JwtToken jwtToken = new JwtToken(userName, userName, true);
            // 提交给realm进行登入，如果错误他会抛出异常并被捕获
            SecurityUtils.getSubject().login(jwtToken);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest) {
        //从header中获取token
        String token = httpRequest.getHeader(ApplicationConstant.TOKEN_NAME);

        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = httpRequest.getParameter(ApplicationConstant.TOKEN_NAME);
        }
        //最后尝试从cookies中获取
        if (StringUtils.isBlank(token)) {
            // 从 cookie 获取 token
            Cookie[] cookies = httpRequest.getCookies();
            if (null == cookies || cookies.length == 0) {
                return null;
            }
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(ApplicationConstant.TOKEN_NAME)) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        return token;
    }

    /**
     * 响应数据给前端
     */
    public static void sendJsonMessage(ServletResponse response, Object result) {

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        PrintWriter writer = null;

        try {
            writer = httpResponse.getWriter();
            httpResponse.setContentType("application/json; charset=utf-8");
            writer.println(gson.toJson(result));
            httpResponse.flushBuffer();
        } catch (Exception ex) {
            Log4JUtils.getLogger().error("写入ServletResponse失败");
        } finally {
            writer.close();
        }
    }
}
