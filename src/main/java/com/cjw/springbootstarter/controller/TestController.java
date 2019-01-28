/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: TestController
 * Author:   yao
 * Date:     2019/1/23 16:36
 * Description: 测试服务接口
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.controller;

import com.cjw.springbootstarter.base.JsonResultData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉<br>
 * 〈测试服务接口〉
 *
 * @author yao
 * @create 2019/1/23
 * @since 1.0.0
 */
@RestController
@RequestMapping("/user")
public class TestController {
    /**
     * 用户查询
     */
    @RequestMapping("/userList")
    @RequiresPermissions("system:user:view")
    public JsonResultData userInfo() {
        return JsonResultData.buildSuccess("成功");
    }
}
