/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: LayerMenuController
 * Author:   yao
 * Date:     2019/4/10 8:40
 * Description: 图层结构树服务类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.controller;

import com.cjw.springbootstarter.base.JsonResultData;
import com.cjw.springbootstarter.domain.layertree.LayerMenuRoot;
import com.cjw.springbootstarter.service.LayerTreeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 〈图层结构树服务类〉
 *
 * @author yao
 * @create 2019/4/10
 * @since 1.0.0
 */

@RestController
@RequestMapping(value = "/layermenu")
@Api(tags = "/layermenu", description = "图层结构查询接口", protocols = "http")
public class LayerMenuController {

    @Autowired
    private LayerTreeService layerTreeService;

    @ApiOperation(value = "获取图层结构")
    @ApiResponses({@ApiResponse(code = 400, message = "参数错误"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"), @ApiResponse(code = 404, message = "请求路径不对")})
    @RequestMapping(value = "/getall", method = {RequestMethod.GET})
    public JsonResultData getLayerTree() {
        try {
            List<LayerMenuRoot> menuResult = layerTreeService.listAllLayerMenu();
            return JsonResultData.buildSuccess(menuResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonResultData.buildError("检索失败");
    }
}
