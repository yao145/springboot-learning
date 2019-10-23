/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: TestSchedule
 * Author:   yao
 * Date:     2019/6/13 17:45
 * Description: 定时器测试类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.Schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 〈定时器测试类〉
 *
 * @author yao
 * @create 2019/6/13
 * @since 1.0.0
 */
@Service
public class TestSchedule {

    //规律：秒分时日月年
    @Scheduled(cron="0/5 * * * * *")
    public void testPrint() {
//        System.out.println("测试定时器---->" + System.currentTimeMillis());
    }
}
