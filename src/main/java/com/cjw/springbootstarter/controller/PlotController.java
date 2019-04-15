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

import com.cjw.springbootstarter.base.ApplicationConstant;
import com.cjw.springbootstarter.base.JsonResultData;
import com.cjw.springbootstarter.base.TPageResult;
import com.cjw.springbootstarter.domain.plot.TPlotFile;
import com.cjw.springbootstarter.domain.plot.TPlotIcon;
import com.cjw.springbootstarter.service.PlotService;
import com.cjw.springbootstarter.util.ControllerUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
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
@RequestMapping(value = "/plot")
@Api(tags = "/plot", description = "标绘功能服务接口", protocols = "http")
public class PlotController {

    @Autowired
    private PlotService plotService;

    @ApiOperation(value = "添加一个标绘")
    @ApiResponses({@ApiResponse(code = 400, message = "参数错误"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"), @ApiResponse(code = 404, message = "请求路径不对")})
    @RequestMapping(value = "/addicon", method = {RequestMethod.POST}, consumes = "application/json")
    public JsonResultData addNewPlot(@RequestBody TPlotIcon inputIcon) {
        JsonResultData result = checkParams(inputIcon.getName(), inputIcon.getType(), inputIcon.getUsers());
        if (result != null) {
            return result;
        }
        TPlotIcon icon = new TPlotIcon(inputIcon.getUsers(), inputIcon.getType(), inputIcon.getName(),
                inputIcon.getDescstr(), "", inputIcon.getData(), "");
        int count = plotService.addNewPlot(icon);
        if (count > 0) {
            return JsonResultData.buildSuccess(icon);
        } else {
            return JsonResultData.buildError(ApplicationConstant.DB_INSERT_ERROR);
        }
    }

    @ApiOperation(value = "获取标绘列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pagenum", dataType = "long", required = false, defaultValue = "1", value = "页码(默认1)"),
            @ApiImplicitParam(paramType = "query", name = "pagesize", dataType = "long", required = false, defaultValue = "15", value = "每页数量(默认15)")})
    @ApiResponses({@ApiResponse(code = 400, message = "参数错误"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"), @ApiResponse(code = 404, message = "请求路径不对")})
    @RequestMapping(value = "/geticons", method = {RequestMethod.GET})
    public JsonResultData getPlots(@PathParam("pagenum") long pagenum, @PathParam("pagesize") long pagesize) {
        // 解析参数
        HashMap<String, Object> params = new HashMap<>();
        params.put("pagenum", pagenum);
        params.put("pagesize", pagesize);
        String msg = ControllerUtil.initParams(params);
        if (!"".equals(msg)) {
            return JsonResultData.buildError(msg);
        }
        TPageResult resultPage = plotService.getPlots(pagenum, pagesize);
        return JsonResultData.buildSuccess(resultPage);
    }

    @ApiOperation(value = "获取指定id的标绘信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "iconid", dataType = "Long", required = true, value = "标绘id")})
    @ApiResponses({@ApiResponse(code = 400, message = "参数错误"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"), @ApiResponse(code = 404, message = "请求路径不对")})
    @RequestMapping(value = "/geticonbyid", method = {RequestMethod.GET})
    public JsonResultData getPlotById(@RequestParam("iconid") Long iconid) {
        if (iconid == null) {
            return JsonResultData.buildError("iconid不能为空");
        }
        TPlotIcon result = plotService.getPlotById(iconid);
        return JsonResultData.buildSuccess(result);
    }

    @ApiOperation(value = "通过共享id获取共享标绘")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "share", dataType = "String", required = true, value = "标绘id"),})
    @ApiResponses({@ApiResponse(code = 400, message = "参数错误"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"), @ApiResponse(code = 404, message = "请求路径不对")})
    @RequestMapping(value = "/geticonbyshare", method = {RequestMethod.GET})
    public JsonResultData getSharePlot(@RequestParam("share") String share) {
        if ("".equals(share) || share.length() == 0) {
            return JsonResultData.buildError("share不能为空");
        }
        List<TPlotIcon> resultList = plotService.getSharePlot(share);
        return JsonResultData.buildSuccess(resultList);
    }

    @ApiOperation(value = "更新标绘信息")
    @ApiResponses({@ApiResponse(code = 400, message = "参数错误"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"), @ApiResponse(code = 404, message = "请求路径不对")})
    @RequestMapping(value = "/updateicon", method = {RequestMethod.POST}, consumes = "application/json")
    public JsonResultData updatePlot(@RequestBody TPlotIcon inputIcon) {
        JsonResultData result = checkParams(inputIcon.getName(), inputIcon.getType(), inputIcon.getUsers());
        if (result != null) {
            return result;
        }
        //判断待更新icon是否存在
        TPlotIcon tPlotIcon = plotService.getPlotById(inputIcon.getIconid());
        if (tPlotIcon == null) {
            return JsonResultData.buildError("未发现iconid【" + inputIcon.getIconid() + "】对应的对象");
        }
        //更新操作
        tPlotIcon.refreshAtt(inputIcon);
        int count = plotService.updatePlot(tPlotIcon);
        if (count > 0) {
            return JsonResultData.buildSuccess(tPlotIcon);
        } else {
            return JsonResultData.buildError(ApplicationConstant.DB_UPDATE_ERROR);
        }
    }

    @ApiOperation(value = "通过标绘id删除标绘，需要删除对应附件文件")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "iconid", dataType = "Long", required = true, value = "标绘id")})
    @ApiResponses({@ApiResponse(code = 400, message = "参数错误"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"), @ApiResponse(code = 404, message = "请求路径不对")})
    @RequestMapping(value = "/deleteiconbyid", method = {RequestMethod.GET})
    public JsonResultData deletePlot(@RequestParam("iconid") Long iconid) {
        if (iconid == null) {
            return JsonResultData.buildError("iconid不能为空");
        }
        int count = plotService.deletePlot(iconid);
        if (count > 0) {
            return JsonResultData.buildSuccess("iconid为" + iconid);
        } else {
            return JsonResultData.buildError(ApplicationConstant.DB_DELETE_ERROR);
        }
    }

    @ApiOperation(value = "添加标绘对应文件附件")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "iconid", dataType = "Long", required = true, value = "标绘id"),
            @ApiImplicitParam(paramType = "query", name = "type", dataType = "String", required = false, value = "类型")})
    @ApiResponses({@ApiResponse(code = 400, message = "参数错误"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"), @ApiResponse(code = 404, message = "请求路径不对")})
    @RequestMapping(value = "/addfile", method = {RequestMethod.POST}, headers = "content-type=multipart/form-data")
    public JsonResultData addPlotFiles(@RequestParam("iconid") Long iconid, @RequestParam("type") String type
            , @RequestParam(value = "uploadfile", required = true) MultipartFile uploadfile) {
        if (iconid == null) {
            return JsonResultData.buildError("iconid不能为空");
        }
        if (uploadfile == null || uploadfile.isEmpty()) {
            return JsonResultData.buildError("上传文件不能为空");
        }
        //判断待更新icon是否存在
        TPlotIcon tPlotIcon = plotService.getPlotById(iconid);
        if (tPlotIcon == null) {
            return JsonResultData.buildError("未发现iconid【" + iconid + "】对应的对象");
        }
        TPlotFile tPlotFile = plotService.addPlotFiles(iconid, type, uploadfile);
        if (tPlotFile == null) {
            return JsonResultData.buildError(ApplicationConstant.HTTP_FILEUPLOAD_ERROR);
        } else {
            return JsonResultData.buildSuccess(tPlotFile);
        }
    }

    @ApiOperation(value = "通过标绘id获取其文件附件")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "iconid", dataType = "Long", required = true, value = "标绘id")})
    @ApiResponses({@ApiResponse(code = 400, message = "参数错误"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"), @ApiResponse(code = 404, message = "请求路径不对")})
    @RequestMapping(value = "/getfilesbyiconid", method = {RequestMethod.GET})
    public JsonResultData getPlotFile(@RequestParam("iconid") Long iconid) {
        if (iconid == null) {
            return JsonResultData.buildError("iconid不能为空");
        }
        List<TPlotFile> fileList = plotService.getPlotFile(iconid);
        return JsonResultData.buildSuccess(fileList);
    }

    @ApiOperation(value = "通过文件id删除标绘附件")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "fileid", dataType = "Long", required = true, value = "文件id")})
    @ApiResponses({@ApiResponse(code = 400, message = "参数错误"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"), @ApiResponse(code = 404, message = "请求路径不对")})
    @RequestMapping(value = "/deletefilebyfileid", method = {RequestMethod.GET})
    public JsonResultData deletePlotFile(@RequestParam("fileid") Long fileid) {
        if (fileid == null) {
            return JsonResultData.buildError("fileid不能为空");
        }
        int count = plotService.deletePlotFile(fileid);
        if (count > 0) {
            return JsonResultData.buildSuccess("fileid为" + fileid);
        } else {
            return JsonResultData.buildError(ApplicationConstant.DB_DELETE_ERROR);
        }
    }

    /**
     * 公共方法，用于进行参数传入的验证
     */
    private JsonResultData checkParams(String name, String type, String users) {
        if (name == null || name.length() == 0) {
            return JsonResultData.buildError("name不能为空");
        }
        if (type == null || type.length() == 0) {
            return JsonResultData.buildError("type不能为空");
        }
        if (users == null || users.length() == 0) {
            return JsonResultData.buildError("users不能为空");
        }
        return null;
    }
}
