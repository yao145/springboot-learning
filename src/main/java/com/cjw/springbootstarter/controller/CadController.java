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
import com.cjw.springbootstarter.domain.cad.PolygonVO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 〈cad文件处理服务〉<br>
 * 〈〉
 *
 * @author yao
 * @create 2019/4/15
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "/cad")
@Api(tags = "/cad", description = "cad文件处理服务", protocols = "http")
public class CadController {

    @ApiOperation(value = "获取cad面要素的json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", dataType = "String", required = true, value = "用户名称")
            , @ApiImplicitParam(paramType = "query", name = "cadrealname", dataType = "String", required = true, value = "文件名称")})
    @ApiResponses({@ApiResponse(code = 400, message = "参数错误"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"), @ApiResponse(code = 404, message = "请求路径不对")})
    @RequestMapping(value = "/getJson", method = {RequestMethod.POST}, headers = "content-type=multipart/form-data")
    public JsonResultData getJsonByCad(@RequestParam("username") String username, @RequestParam("cadrealname") String cadrealname,
                                       @RequestParam(value = "uploadfile", required = true) MultipartFile uploadfile) {
        if (username == null || username.length() == 0) {
            return JsonResultData.buildError("username不能为空");
        }
        if (uploadfile == null || uploadfile.isEmpty()) {
            return JsonResultData.buildError("上传文件不能为空");
        }

        return JsonResultData.buildSuccess("ok");
    }

    @ApiOperation(value = "通过传入点集合，获取对应cad")
    @ApiResponses({@ApiResponse(code = 400, message = "参数错误"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"), @ApiResponse(code = 404, message = "请求路径不对")})
    @RequestMapping(value = "/getCad", method = {RequestMethod.POST}, consumes = "application/json")
    public JsonResultData getCadByJson(@RequestBody PolygonVO polygonVO) {
        JsonResultData result = polygonVO.checkParams();
        if (result != null) {
            return result;
        }

        return JsonResultData.buildSuccess("ok");
    }
}
