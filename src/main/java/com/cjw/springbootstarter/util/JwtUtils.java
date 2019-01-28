/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: JwtUtils
 * Author:   yao
 * Date:     2018/12/18 10:42
 * Description: jwt登录授权工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.util;

import com.cjw.springbootstarter.base.ApplicationConstant;
import com.cjw.springbootstarter.base.PublicResultConstant;
import com.cjw.springbootstarter.domain.TSysUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * 〈jwt登录授权工具类〉
 *
 * @author yao
 * @create 2018/12/18
 * @since 1.0.0
 */
public class JwtUtils {

    /**
     * 生成一个jwt
     *
     * @param user
     * @return
     */
    public static String getJsonWebToken(TSysUser user) {
        //对用户信息进行简单校验
        if (user == null || user.getUsername() == null) {
            return null;
        }

        String token = Jwts.builder().setSubject(ApplicationConstant.TOKEN_SUBJECT).claim("name", user.getUsername())
                .claim("id", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ApplicationConstant.TOKEN_EXPIRE))
                .signWith(SignatureAlgorithm.HS256, ApplicationConstant.TOKEN_APPSECRET)
                .compact();
        return token;
    }

    /**
     * 解密一个token
     *
     * @param token
     * @return
     */
    public static Claims checkJWT(String token) {
        try {
            final Claims claims = Jwts.parser().setSigningKey(ApplicationConstant.TOKEN_APPSECRET).parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception ex) {
            Log4JUtils.getLogger().info(PublicResultConstant.TOKEN_IS_INVALID + "-->【" + token + "】");
        }
        return null;
    }
}
