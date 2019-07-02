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

import com.alibaba.druid.sql.visitor.functions.Isnull;
import com.cjw.springbootstarter.base.ApplicationConstant;
import com.cjw.springbootstarter.base.JsonResultData;
import io.swagger.annotations.*;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.concurrent.TimeUnit;

/**
 * 〈测试服务接口〉
 *
 * @author yao
 * @create 2019/1/23
 * @since 1.0.0
 */
@RestController
@RequestMapping("/test")
@Api(tags = "/test", description = "测试接口入口", protocols = "http")
public class TestController {


    @ApiOperation(value = "这是一个规范的接口示例")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "name", required = true, dataType = "String", defaultValue = "长江", value = "名称"),
            @ApiImplicitParam(paramType = "query", name = "age", dataType = "Integer", defaultValue = "18", value = "年龄")})
    @ApiResponses({@ApiResponse(code = 400, message = "参数错误"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"), @ApiResponse(code = 404, message = "请求路径不对")})
    @RequestMapping(value = "/getname", method = {RequestMethod.GET})
    public JsonResultData getname(@RequestParam("name") String name, @PathParam("age") Integer age) {
        if (age == null) {
            age = -1;
        }

        return JsonResultData.buildSuccess("我叫 " + name + " ,年龄【" + age + "】");
    }


    /**
     * 该接口进行分布式锁的测试，实现方式为redisson
     */
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redisson;

    @RequestMapping("redisson")
    public Object testConcurrent1() {

        //1、单服务器部署使用简单的同步锁
        // synchronized (TestController.class)

        //2、多服务器部署时，尝试使用redis分布式锁:Redisson方式
        String key = ApplicationConstant.REDIS_LOCK_KEY;

        //获取锁
        RLock rLock = null;
        try {
            rLock = redisson.getLock(key);
            rLock.lock(ApplicationConstant.REDIS_LOCK_KEY_EXPIRE_TIME, TimeUnit.SECONDS);

            //业务逻辑,redis中存一个数，作为计数器
            Object allCount = redisTemplate.opsForValue().get("allCount");
            if (allCount == null) {
                redisTemplate.opsForValue().set("allCount", 1);
            } else {
                redisTemplate.opsForValue().set("allCount", (int) allCount + 1);
            }
            System.out.println("allCount-->>> " + redisTemplate.opsForValue().get("allCount"));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (rLock.isExists()) {
                rLock.unlock();
            }
        }
        return "执行完成";
    }
}
