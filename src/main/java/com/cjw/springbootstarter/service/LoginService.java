/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: UserService
 * Author:   yao
 * Date:     2019/1/23 15:25
 * Description: 用户操作业务类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.service;

import com.cjw.springbootstarter.base.JsonResultData;
import com.cjw.springbootstarter.domain.TSysLoginResult;

/**
 * 〈用户登录业务接口〉
 *
 * @author yao
 * @create 2019/1/23
 * @since 1.0.0
 */
public interface LoginService {

    TSysLoginResult login(String userName, String password);

    void logout();
}
