/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ArcGpserverController
 * Author:   yao
 * Date:     2019/4/28 9:14
 * Description: arcgis gp服务调用类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.controller;

import com.cjw.springbootstarter.base.ApplicationConstant;
import com.cjw.springbootstarter.base.JsonResultData;
import com.cjw.springbootstarter.domain.ags.GpModelForClib;
import com.cjw.springbootstarter.service.ArcGpService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.HashMap;

/**
 * 〈arcgis gp服务调用类〉
 *
 * @author yao
 * @create 2019/4/28
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "/arc")
@Api(tags = "/arc", description = "gp服务接口", protocols = "http")
public class ArcGpserverController {

    @Autowired
    private ArcGpService arcGpService;

    @ApiOperation(value = "提交一个裁剪任务")
    @ApiResponses({@ApiResponse(code = 400, message = "参数错误"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"), @ApiResponse(code = 404, message = "请求路径不对")})
    @RequestMapping(value = "/submit/clip", method = {RequestMethod.POST}, consumes = "application/json")
    public JsonResultData submitJob(@RequestBody GpModelForClib gpModelForClib) {
        JsonResultData result = gpModelForClib.checkParams();
        if (result != null) {
            return result;
        }
        HashMap<String, Object> params = new HashMap<>();
        params.put("clip_area", gpModelForClib.toAgsJsonStr());
        params.put("f", "pjson");
        return arcGpService.submitJob(Integer.parseInt(gpModelForClib.getGpid()), params, true);
    }


    @ApiOperation(value = "获取gp任务的运行状态")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "gpid", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "query", name = "jobid", dataType = "String", required = true)})
    @ApiResponses({@ApiResponse(code = 400, message = "参数错误"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"), @ApiResponse(code = 404, message = "请求路径不对")})
    @RequestMapping(value = "/getjobstatus", method = {RequestMethod.GET})
    public JsonResultData getJobStatus(@RequestParam("gpid") String gpid, @RequestParam("jobid") String jobid) {
        JsonResultData result = checkParams(gpid, jobid);
        if (result != null) {
            return result;
        }
        result = arcGpService.checkJobStatus(Integer.parseInt(gpid), jobid);
        return result;
    }

    @ApiOperation(value = "获取运行结果的统计信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "gpid", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "query", name = "jobid", dataType = "String", required = true)})
    @ApiResponses({@ApiResponse(code = 400, message = "参数错误"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"), @ApiResponse(code = 404, message = "请求路径不对")})
    @RequestMapping(value = "/result/statistics", method = {RequestMethod.GET})
    public JsonResultData getResultForStatistics(@RequestParam("gpid") String gpid, @RequestParam("jobid") String jobid) {
        JsonResultData result = checkParams(gpid, jobid);
        if (result != null) {
            return result;
        }
        result = arcGpService.resultForStatistics(Integer.parseInt(gpid), jobid);
        return result;
    }

    @ApiOperation(value = "获取运行结果的要素集合信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "gpid", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "query", name = "jobid", dataType = "String", required = true)})
    @ApiResponses({@ApiResponse(code = 400, message = "参数错误"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"), @ApiResponse(code = 404, message = "请求路径不对")})
    @RequestMapping(value = "/result/query", method = {RequestMethod.GET})
    public JsonResultData getResultForQuery(@RequestParam("gpid") String gpid, @RequestParam("jobid") String jobid) {
        JsonResultData result = checkParams(gpid, jobid);
        if (result != null) {
            return result;
        }
        result = arcGpService.resultForQueryFeatures(Integer.parseInt(gpid), jobid);
        return result;
    }

    /**
     * 公共方法，用于进行参数传入的验证
     */
    private JsonResultData checkParams(String gpid, String jobid) {
        if (gpid == null || gpid.length() == 0) {
            return JsonResultData.buildError("gpid不能为空");
        }
        if (jobid == null || jobid.length() == 0) {
            return JsonResultData.buildError("jobid不能为空");
        }
        try {
            Integer.parseInt(gpid);
        } catch (Exception ex) {
            return JsonResultData.buildError("gpid格式错误");
        }
        return null;
    }

}
