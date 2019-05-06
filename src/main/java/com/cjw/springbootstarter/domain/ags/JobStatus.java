/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: JobStatus
 * Author:   yao
 * Date:     2019/4/28 15:19
 * Description: 任务状态类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.domain.ags;

import java.util.HashMap;

/**
 * 〈任务状态类〉
 *
 * @author yao
 * @create 2019/4/28
 * @since 1.0.0
 */
public class JobStatus {

    /**
     * key存放任务状态标志位，value标识是否为结束状态
     */
    private static HashMap<String, Boolean> statusList = new HashMap<>();
    static
    {
        statusList.put("JobCancelled", true);
        statusList.put("JobCancelling", true);
        statusList.put("JobDeleted", true);
        statusList.put("JobDeleting", true);
        statusList.put("JobExecuting", false);
        statusList.put("JobFailed", true);
        statusList.put("JobNew", false);
        statusList.put("JobSubmitted", false);
        statusList.put("JobSucceeded", true);
        statusList.put("JobTimedOut", true);
        statusList.put("JobWaiting", false);
    }

    /**
     * 查看传入任务状态是否为结束状态
     */
    public static boolean isEndStatus(String jobStatus) {
        if (statusList.containsKey(jobStatus)) {
            return statusList.get(jobStatus);
        } else {
            return true;
        }
    }

}
