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

import com.cjw.springbootstarter.base.ApplicationConstant;
import com.cjw.springbootstarter.base.JsonResultData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
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
