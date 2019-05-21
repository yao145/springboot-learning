/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ArcGpServiceImpl
 * Author:   yao
 * Date:     2019/4/25 11:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cjw.springbootstarter.base.ApplicationConstant;
import com.cjw.springbootstarter.base.JsonResultData;
import com.cjw.springbootstarter.domain.ags.*;
import com.cjw.springbootstarter.mapper.ags.ArcGpserverMapper;
import com.cjw.springbootstarter.service.ArcGpService;
import com.cjw.springbootstarter.util.Log4JUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 〈ags gp服务调用〉
 *
 * @author yao
 * @create 2019/4/25
 * @since 1.0.0
 */
@Service
public class ArcGpServiceImpl implements ArcGpService {

    @Autowired
    private HttpAPIService httpAPIService;

    @Autowired
    private ArcGpserverMapper gpserverMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public JsonResultData submitJob(int gpId, HashMap<String, Object> params, boolean isPost) {
        JsonResultData resultData;
        TArcGpserver gpserver = getGpserverById(gpId);
        if (gpserver == null) {
            return JsonResultData.buildError("无效的gpid");
        }
        if (isPost) {
            resultData = httpAPIService.doGet(gpserver.getSubmitJobUrl(), params);
        } else {
            resultData = httpAPIService.doPost(gpserver.getSubmitJobUrl(), params);
        }
        if (resultData.getCode() != 200) {
            return resultData;
        } else {
            //调用成功,获取结果内容
            JsonObject jsonObject = (JsonObject) resultData.getData();
            if (jsonObject == null || jsonObject.get("jobId") == null || jsonObject.get("jobStatus") == null) {
                return JsonResultData.buildError("结果获取失败");
            }
            String jobId = jsonObject.get("jobId").getAsString();
            String jobStatus = jsonObject.get("jobStatus").getAsString();
            //将任务id相关信息写入到redis，供状态查询操作
            String key = String.format(ApplicationConstant.AGS_GP_STATUS_KEY, gpserver.getName(), jobId);
            redisTemplate.opsForValue().set(key, jobStatus.substring(4),
                    ApplicationConstant.AGS_GP_KEEP_RESULT_TOTAL_TIME, ApplicationConstant.AGS_GP_KEEP_RESULT_TOTAL_TIMEUNIT);
            return JsonResultData.buildSuccess(jobId);
        }
    }

    @Override
    public JsonResultData checkJobStatus(int gpId, String jobId) {
        JsonResultData resultData;
        TArcGpserver gpserver = getGpserverById(gpId);
        if (gpserver == null) {
            return JsonResultData.buildError("无效的gpid");
        }
        String key = String.format(ApplicationConstant.AGS_GP_STATUS_KEY, gpserver.getName(), jobId);
        String jobStatus = redisTemplate.opsForValue().get(key).toString();
        if (jobStatus == null) {
            return JsonResultData.buildError("给定任务编号不存在");
        } else {
            //如果状态为结束状态，则不通过ags服务器获取状态信息
            if (JobStatus.isEndStatus(jobStatus) == false) {
                //通过查询ags，获取任务状态，更新到redis，并返回
                HashMap<String, Object> params = new HashMap<>();
                params.put("f", "pjson");
                resultData = httpAPIService.doGet(gpserver.getJobStatusUrl(jobId), params);
                JsonObject jsonObject = (JsonObject) resultData.getData();
                jobStatus = jsonObject.get("jobStatus").getAsString().substring(4);
                redisTemplate.opsForValue().set(key, jobStatus
                        , ApplicationConstant.AGS_GP_KEEP_RESULT_TOTAL_TIME, ApplicationConstant.AGS_GP_KEEP_RESULT_TOTAL_TIMEUNIT);
            }
            return JsonResultData.buildSuccess(jobStatus);
        }
    }

    @Override
    public JsonResultData resultForStatistics(int gpId, String jobId) {
        JsonResultData resultData;
        TArcGpserver gpserver = getGpserverById(gpId);
        if (gpserver == null) {
            return JsonResultData.buildError("无效的gpid");
        }
        String statisticsFields = gpserver.getStatisticsFields();
        String statisticsFieldsSetail = gpserver.getStatisticsFieldsDetail();
        if (statisticsFields == null || statisticsFields.length() == 0) {
            return JsonResultData.buildError("【" + gpserver.getDetail() + "】模型服务不支持统计分析");
        }
        String key = String.format(ApplicationConstant.AGS_GP_RESULT_FOR_STATICS_KEY, gpserver.getName(), jobId);
        Object jobResult = redisTemplate.opsForValue().get(key);
        if (jobResult == null || jobResult.toString().length() == 0) {
            //调用ags
            HashMap<String, Object> params = new HashMap<>();
            params.put("f", "pjson");
            params.put("where", "1=1");
            params.put("groupByFieldsForStatistics", statisticsFields);
            params.put("outStatistics",
                    "[{\"statisticType\":\"sum\",\"onStatisticField\":\"Shape_Area\",\"outStatisticFieldName\":\"AREA\"}]");
            resultData = httpAPIService.doGet(gpserver.getResultServerUrl(jobId) + "/0/query", params);
            if (resultData.getCode() < 0) {
                return resultData;
            }
            JsonObject jsonObject = (JsonObject) resultData.getData();
            JsonArray featuresAgs = jsonObject.get("features").getAsJsonArray();
            if (featuresAgs == null || featuresAgs.size() == 0) {
                redisTemplate.opsForValue().set(key, "");
                return JsonResultData.buildError("分析结果为空");
            }
            FeatureArray features = new FeatureArray();
            //1  构建features数组
            try {
                for (int iii = 0; iii < featuresAgs.size(); iii++) {
                    JsonObject featureJson = (JsonObject) featuresAgs.get(iii);
                    JsonObject attributesJson = (JsonObject) featureJson.get("attributes");
                    //构建一个要素
                    FeatureItem feature = new FeatureItem();
                    Set<Map.Entry<String, JsonElement>> entrySet = attributesJson.entrySet();
                    for (Map.Entry<String, JsonElement> entry : entrySet) {
                        feature.addFiledItem(new FieldItem(entry.getKey(), entry.getValue().getAsString()));
                    }
                    features.addFeature(feature);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Log4JUtils.getLogger().error("结果解析成json失败");
                return JsonResultData.buildError("结果解析成json失败");
            }
            //2  构建属性名-描述的JsonObject
            JsonObject fieldDetail = new JsonObject();
            String[] fieldArr = statisticsFields.split(",");
            String[] fieldDetailArr = statisticsFieldsSetail.split(",");
            for (int i = 0; i < fieldArr.length; i++) {
                features.addAttMapping(new FieldItem(fieldArr[i], fieldDetailArr[i]));
            }
            features.addAttMapping(new FieldItem("AREA", "面积"));
            //3  将对象存储到redis
            redisTemplate.opsForValue().set(key, new Gson().toJson(features)
                    , ApplicationConstant.AGS_GP_KEEP_RESULT_TOTAL_TIME, ApplicationConstant.AGS_GP_KEEP_RESULT_TOTAL_TIMEUNIT);
            return JsonResultData.buildSuccess(features);
        } else {
            Log4JUtils.getLogger().info("从redis获取分析结果");
            FeatureArray featureArray = new Gson().fromJson(jobResult.toString(), FeatureArray.class);
            return JsonResultData.buildSuccess(featureArray);
        }
    }

    /**
     * 获取gpid对应的model类
     */
    private TArcGpserver getGpserverById(int gpId) {
        Wrapper wrapper = new QueryWrapper<TArcGpserver>().eq("id", gpId);
        return gpserverMapper.selectOne(wrapper);
    }


}
