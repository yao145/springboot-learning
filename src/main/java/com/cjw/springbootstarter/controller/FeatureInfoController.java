/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: FeatureInfoController
 * Author:   yao
 * Date:     2019/3/19 15:27
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.controller;

import com.cjw.springbootstarter.base.ApplicationConstant;
import com.cjw.springbootstarter.base.JsonResultData;
import com.cjw.springbootstarter.base.TPageResult;
import com.cjw.springbootstarter.domain.esmodel.TFeatureInfo;
import com.cjw.springbootstarter.service.FeatureInfoService;
import com.cjw.springbootstarter.util.ControllerUtil;
import com.cjw.springbootstarter.util.EShitsUtil;
import io.swagger.annotations.*;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;

/**
 * 〈地图要素级别的检索服务〉
 *
 * @author yao
 * @create 2019/3/19
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "/featureinfo")
@Api(tags = "/featureinfo", description = "要素信息查询接口", protocols = "http")
public class FeatureInfoController {

    @Autowired
    private FeatureInfoService featureInfoService;

    @Autowired
    private RestHighLevelClient rhlClient;

    @ApiOperation(value = "属性查询服务")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "url", dataType = "String", required = true, value = "关键字"),
            @ApiImplicitParam(paramType = "query", name = "pagenum", dataType = "long", required = false, defaultValue = "1", value = "页码(默认1)"),
            @ApiImplicitParam(paramType = "query", name = "pagesize", dataType = "long", required = false, defaultValue = "15", value = "每页数量(默认15)")})
    @ApiResponses({@ApiResponse(code = 400, message = "参数错误"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"), @ApiResponse(code = 404, message = "请求路径不对")})
    @RequestMapping(value = "/geturl", method = {RequestMethod.GET})
    public JsonResultData geturl(@RequestParam("url") String url, @PathParam("pagenum") long pagenum,
                                 @PathParam("pagesize") long pagesize) {
        try {
            // 解析参数
            HashMap<String,Object> params = new HashMap<>();
            params.put("pagenum",pagenum);
            params.put("pagesize",pagesize);
            String msg = ControllerUtil.initParams(params);
            if(!"".equals(msg)){
                return JsonResultData.buildError(msg);
            }
            pagenum = Long.parseLong(params.get("pagenum").toString());
            pagesize = Long.parseLong(params.get("pagesize").toString());

            TPageResult resultPage = featureInfoService.listByUrl(url, pagenum, pagesize);
            return JsonResultData.buildSuccess(resultPage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonResultData.buildError(ApplicationConstant.HTTP_RETURN_ERROR);
    }

    @ApiOperation(value = "全文检索服务")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "keywords", dataType = "String", required = true, value = "关键字"),
            @ApiImplicitParam(paramType = "query", name = "pagenum", dataType = "long", required = false, defaultValue = "1", value = "页码(默认1)"),
            @ApiImplicitParam(paramType = "query", name = "pagesize", dataType = "long", required = false, defaultValue = "15", value = "每页数量(默认15)")})
    @ApiResponses({@ApiResponse(code = 400, message = "参数错误"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"), @ApiResponse(code = 404, message = "请求路径不对")})
    @RequestMapping(value = "/searchES", method = {RequestMethod.GET})
    public JsonResultData search(@RequestParam("keywords") String keywords, @PathParam("pagenum") long pagenum,
                                 @PathParam("pagesize") long pagesize) {
        try {
            // 解析参数
            HashMap<String, Object> params = new HashMap<>();
            params.put("keywords", keywords);
            params.put("pagenum", pagenum);
            params.put("pagesize", pagesize);
            String msg = ControllerUtil.initParams(params);
            if (!"".equals(msg)) {
                return JsonResultData.buildError(msg);
            }
            keywords = params.get("keywords").toString();
            pagenum = Long.parseLong(params.get("pagenum").toString());
            pagesize = Long.parseLong(params.get("pagesize").toString());

            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.from((int) ((pagenum - 1) * pagesize));
            sourceBuilder.size((int) pagesize);

            QueryBuilder queryBuilder =QueryBuilders.boolQuery().must(QueryBuilders.termQuery("isdelete",0))
                                                                .must(QueryBuilders.matchQuery("text", keywords));
            sourceBuilder.query(queryBuilder);

            SearchRequest searchRequest = new SearchRequest("gsdb_info");
            searchRequest.types("service_info");
            searchRequest.source(sourceBuilder);

            SearchResponse response = rhlClient.search(searchRequest, RequestOptions.DEFAULT);
            List<TFeatureInfo> reusltList = EShitsUtil.convertHits2Object(response, new TFeatureInfo());

            TPageResult resultPage = new TPageResult(pagenum, pagesize, response.getHits().totalHits, reusltList);

            return JsonResultData.buildSuccess(resultPage);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResultData.buildError(ApplicationConstant.HTTP_RETURN_ERROR);
        }
    }


    @ApiOperation(value = "like方式检索服务")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "keywords", dataType = "String", required = true, value = "关键字"),
            @ApiImplicitParam(paramType = "query", name = "pagenum", dataType = "long", required = false, defaultValue = "1", value = "页码(默认1)"),
            @ApiImplicitParam(paramType = "query", name = "pagesize", dataType = "long", required = false, defaultValue = "15", value = "每页数量(默认15)")})
    @ApiResponses({@ApiResponse(code = 400, message = "参数错误"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"), @ApiResponse(code = 404, message = "请求路径不对")})
    @RequestMapping(value = "/search", method = {RequestMethod.GET})
    public JsonResultData search2(@RequestParam("keywords") String keywords, @PathParam("pagenum") long pagenum,
                                  @PathParam("pagesize") long pagesize) {
        try {
            // 解析参数
            HashMap<String, Object> params = new HashMap<>();
            params.put("keywords", keywords);
            params.put("pagenum", pagenum);
            params.put("pagesize", pagesize);
            String msg = ControllerUtil.initParams(params);
            if (!"".equals(msg)) {
                return JsonResultData.buildError(msg);
            }
            keywords = params.get("keywords").toString();
            pagenum = Long.parseLong(params.get("pagenum").toString());
            pagesize = Long.parseLong(params.get("pagesize").toString());


            TPageResult resultPage = featureInfoService.listByKeyWords(keywords,pagenum,pagesize);
            return JsonResultData.buildSuccess(resultPage);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResultData.buildError(ApplicationConstant.HTTP_RETURN_ERROR);
        }
    }
}
