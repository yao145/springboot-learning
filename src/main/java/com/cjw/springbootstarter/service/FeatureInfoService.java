package com.cjw.springbootstarter.service;

import com.cjw.springbootstarter.base.TPageResult;


/**
 * 要素图层要素级别搜索，对应数据库表:t_feature_info
 */
public interface FeatureInfoService {

    TPageResult listByUrl(String url, long pagenum, long pagesize);

    TPageResult listByKeyWords(String keywords, long pagenum, long pagesize);
}
