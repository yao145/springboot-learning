/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: PlotController
 * Author:   yao
 * Date:     2019/4/15 9:22
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.controller;

import com.cjw.springbootstarter.base.JsonResultData;
import com.cjw.springbootstarter.base.TPageResult;
import com.cjw.springbootstarter.domain.project.TProjectFileInfo;
import com.cjw.springbootstarter.domain.project.TProjectFileTree;
import com.cjw.springbootstarter.service.ProjectService;
import com.cjw.springbootstarter.util.ControllerUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * 〈标绘功能服务接口〉<br>
 * 〈〉
 *
 * @author yao
 * @create 2019/4/15
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "/project")
@Api(tags = "/project", description = "高产农田服务接口", protocols = "http")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @ApiOperation(value = "获取农田项目列表:部分字段")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "keyword", dataType = "String", value = "关键字"),
            @ApiImplicitParam(paramType = "query", name = "pagenum", dataType = "long", defaultValue = "1", value = "页码(默认1)"),
            @ApiImplicitParam(paramType = "query", name = "pagesize", dataType = "long", defaultValue = "15", value = "每页数量(默认15)")})
    @ApiResponses({@ApiResponse(code = 400, message = "参数错误"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"), @ApiResponse(code = 404, message = "请求路径不对")})
    @RequestMapping(value = "/getprojectlist", method = {RequestMethod.GET})
    public JsonResultData getProjectList(@RequestParam(value = "keyword", required = false) String keyword,
                                         @RequestParam(value = "pagenum", required = false) long pagenum
                                         , @RequestParam(value = "pagesize", required = false) long pagesize) {
        // 解析参数
        HashMap<String, Object> params = new HashMap<>();
        params.put("pagenum", pagenum);
        params.put("pagesize", pagesize);
        String msg = ControllerUtil.initParams(params);
        if (!"".equals(msg)) {
            return JsonResultData.buildError(msg);
        }
        TPageResult resultPage = projectService.getProjectList(keyword, pagenum, pagesize);
        return JsonResultData.buildSuccess(resultPage);
    }

    @ApiOperation(value = "通过编号获取项目基本信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "code", dataType = "String", required = true, value = "编码")})
    @ApiResponses({@ApiResponse(code = 400, message = "参数错误"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"), @ApiResponse(code = 404, message = "请求路径不对")})
    @RequestMapping(value = "/getbaseinfo", method = {RequestMethod.GET})
    public JsonResultData getBaseInfoByCode(@RequestParam("code") String code) {
        JsonResultData result = checkParams(code);
        if (result != null) {
            return result;
        }
        TPageResult resultPage = projectService.getBaseInfoByCode(code);
        return JsonResultData.buildSuccess(resultPage);
    }

    @ApiOperation(value = "通过编号获取项目投资信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "code", dataType = "String", required = true, value = "编码")})
    @ApiResponses({@ApiResponse(code = 400, message = "参数错误"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"), @ApiResponse(code = 404, message = "请求路径不对")})
    @RequestMapping(value = "/getinvestinfo", method = {RequestMethod.GET})
    public JsonResultData getInvestInfoByCode(@RequestParam("code") String code) {
        JsonResultData result = checkParams(code);
        if (result != null) {
            return result;
        }

        TPageResult resultPage = projectService.getInvestInfoByCode(code);
        return JsonResultData.buildSuccess(resultPage);
    }

    @ApiOperation(value = "通过编号获取项目建设信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "code", dataType = "String", required = true, value = "编码")})
    @ApiResponses({@ApiResponse(code = 400, message = "参数错误"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"), @ApiResponse(code = 404, message = "请求路径不对")})
    @RequestMapping(value = "/getbuildinfo", method = {RequestMethod.GET})
    public JsonResultData getBuildInfoByCode(@RequestParam("code") String code) {
        JsonResultData result = checkParams(code);
        if (result != null) {
            return result;
        }

        TPageResult resultPage = projectService.getBuildInfoByCode(code);
        return JsonResultData.buildSuccess(resultPage);
    }

    @ApiOperation(value = "获取项目文件附件的树结构")
    @ApiResponses({@ApiResponse(code = 400, message = "参数错误"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"), @ApiResponse(code = 404, message = "请求路径不对")})
    @RequestMapping(value = "/getfiletree", method = {RequestMethod.GET})
    public JsonResultData getFileTree() {
        List<TProjectFileTree> fileTreeList = projectService.getFileTree();
        return JsonResultData.buildSuccess(fileTreeList);
    }

    @ApiOperation(value = "获取指定项目的所有附件信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "code", dataType = "String", required = true, value = "编码")})
    @ApiResponses({@ApiResponse(code = 400, message = "参数错误"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"), @ApiResponse(code = 404, message = "请求路径不对")})
    @RequestMapping(value = "/getfileinfolist", method = {RequestMethod.GET})
    public JsonResultData getFileInfoList(@RequestParam("code") String code) {
        JsonResultData result = checkParams(code);
        if (result != null) {
            return result;
        }

        List<TProjectFileInfo> fileInfoList = projectService.getFileInfoList(code);
        return JsonResultData.buildSuccess(fileInfoList);
    }

    /**
     * 公共方法，用于进行参数传入的验证
     */
    private JsonResultData checkParams(String code) {
        if (code == null || code.length() == 0) {
            return JsonResultData.buildError("name不能为空");
        }
        return null;
    }
}
