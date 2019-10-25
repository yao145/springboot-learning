package com.cjw.springbootstarter.service;

import com.cjw.springbootstarter.base.JsonResultData;
import com.cjw.springbootstarter.domain.cad.PolygonVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * cad操作接口
 */
public interface CadService {

    JsonResultData getJsonByCad(String username, MultipartFile uploadfile);

    JsonResultData getCadByJson(PolygonVO polygonVO);
}
