/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ArcGpService
 * Author:   yao
 * Date:     2019/4/25 10:43
 * Description: ags gp服务调用
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.service;

import com.cjw.springbootstarter.base.JsonResultData;

import java.util.HashMap;

/**
 * 〈ags gp服务调用〉
 *
 * @author yao
 * @create 2019/4/25
 * @since 1.0.0
 */
public interface ArcGpService {
    /**
     * 提交一个gp任务
     */
    JsonResultData submitJob(int gpId, HashMap<String, Object> params, boolean isPost);

    /**
     * 通过gp的任务 id获取运行状态
     */
    JsonResultData checkJobStatus(int gpId, String jobId);

    /**
     * 获取gp结果，进行结果内容统计
     */
    JsonResultData resultForStatistics(int gpId,String jobId);
}
