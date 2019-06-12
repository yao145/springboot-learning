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
     * 检验完成
     */
    public static final String SUCCESS = "JobSucceeded";
    /**
     * 检测通过
     */
    public static final String RESULT_SUCCESS="RESULT_SUCCESS";
    /**
     * 有待核查
     */
    public static final String RESULT_WARNING="RESULT_WARNING";
    /**
     * 检测不通过
     */
    public static final String RESULT_ERROR ="RESULT_ERROR";

    /**
     * key存放任务状态标志位，value标识是否为结束状态
     */
    private static HashMap<String, Boolean> statusList = new HashMap<>();
    /**
     * key存放状态标志位，value存放前端展示信息
     */
    private static HashMap<String, String> statusNameList = new HashMap<>();

    static {
        statusList.put("JobCancelled", true);
        statusNameList.put("JobCancelled", "检测取消");
        statusList.put("JobCancelling", true);
        statusNameList.put("JobCancelling", "检测取消中");
        statusList.put("JobDeleted", true);
        statusNameList.put("JobDeleted", "任务被删除");
        statusList.put("JobDeleting", true);
        statusNameList.put("JobDeleting", "任务删除中");
        statusList.put("JobExecuting", false);
        statusNameList.put("JobExecuting", "检测执行中");
        statusList.put("JobFailed", true);
        statusNameList.put("JobFailed", "检测失败");
        statusList.put("JobNew", false);
        statusNameList.put("JobNew", "任务新建");
        statusList.put("JobSubmitted", false);
        statusNameList.put("JobSubmitted", "任务已提交");
        statusList.put("JobSucceeded", true);
        statusNameList.put("JobSucceeded", "检测成功");
        statusList.put("JobTimedOut", true);
        statusNameList.put("JobTimedOut", "检测超时");
        statusList.put("JobWaiting", false);
        statusNameList.put("JobWaiting", "任务等待中");

        //检验结果信息
        statusNameList.put("RESULT_SUCCESS", "通过检验");
        statusNameList.put("RESULT_WARNING", "有待核查");
        statusNameList.put("RESULT_ERROR", "未通过检验");
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

    /**
     * 获取指定状态的描述名称
     */
    public static String getStatusName(String jobStatus) {
        return statusNameList.get(jobStatus);
    }

}
