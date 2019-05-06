/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: HttpAPIService
 * Author:   yao
 * Date:     2019/4/25 11:14
 * Description: HttpClient工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.service.impl;

import com.cjw.springbootstarter.base.JsonResultData;
import com.cjw.springbootstarter.util.Log4JUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 〈HttpClient工具类〉
 *
 * @author yao
 * @create 2019/4/25
 * @since 1.0.0
 */
@Service
public class HttpAPIService {


    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private RequestConfig config;


    /**
     * 不带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     *
     * @param url
     * @return
     * @throws Exception
     */
    public JsonResultData doGet(String url) {
        // 声明 http get 请求
        HttpGet httpGet = new HttpGet(url);

        // 装载配置信息
        httpGet.setConfig(config);
        CloseableHttpResponse response = null;

        try {
            // 发起请求
            response = this.httpClient.execute(httpGet);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log4JUtils.getLogger().error(url + "-->" + ex.getMessage());
            return JsonResultData.buildError("服务调用失败", -1);
        }

        return getResult(response);
    }

    /**
     * 带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     *
     * @param url
     * @return
     * @throws Exception
     */
    public JsonResultData doGet(String url, Map<String, Object> map) {
        try {
            URIBuilder uriBuilder = new URIBuilder(url);

            if (map != null) {
                // 遍历map,拼接请求参数
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
                }
            }
            String httpFullUrl = uriBuilder.build().toString();
            // 调用不带参数的get请求
            return this.doGet(httpFullUrl);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log4JUtils.getLogger().error(url + "-->" + ex.getMessage());
            return JsonResultData.buildError("服务调用失败", -1);
        }
    }

    /**
     * 带参数的post请求
     *
     * @param url
     * @param map
     * @return
     * @throws Exception
     */
    public JsonResultData doPost(String url, Map<String, Object> map) {
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(config);
        CloseableHttpResponse response = null;
        try {

            // 判断map是否为空，不为空则进行遍历，封装from表单对象
            if (map != null) {
                List<NameValuePair> list = new ArrayList<NameValuePair>();
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                }
                // 构造from表单对象
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");

                // 把表单放到post里
                httpPost.setEntity(urlEncodedFormEntity);
            }
            // 发起请求
            response = this.httpClient.execute(httpPost);
            return getResult(response);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log4JUtils.getLogger().error(url + "-->" + ex.getMessage());
            return JsonResultData.buildError("服务调用失败", -1);
        }
    }

    /**
     * 不带参数post请求
     *
     * @param url
     * @return
     * @throws Exception
     */
    public JsonResultData doPost(String url) {
        return this.doPost(url, null);
    }

    /**
     * 将调用结果解析为统一格式
     */
    private JsonResultData getResult(CloseableHttpResponse response) {
        try {

            String resStr = EntityUtils.toString(response.getEntity(), "UTF-8");
            //将字符串转换成JsonObject
            JsonObject resJson = new JsonParser().parse(resStr).getAsJsonObject();
            int statusCode = response.getStatusLine().getStatusCode();
            // 判断状态码是否为200
            if (statusCode == 200) {
                // 返回响应体的内容
                return JsonResultData.buildSuccess(resJson, statusCode);
            } else {
                return JsonResultData.buildError(resJson.toString());
            }
        } catch (IOException ex) {
            return JsonResultData.buildError(ex.getMessage());
        }
    }
}
