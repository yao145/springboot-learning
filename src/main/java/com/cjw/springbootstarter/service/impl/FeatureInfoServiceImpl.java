/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: FeatureInfoServiceImpl
 * Author:   yao
 * Date:     2019/3/19 15:12
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjw.springbootstarter.base.TPageResult;
import com.cjw.springbootstarter.domain.esmodel.TFeatureInfo;
import com.cjw.springbootstarter.mapper.FeatureInfoMapper;
import com.cjw.springbootstarter.service.FeatureInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author yao
 * @create 2019/3/19
 * @since 1.0.0
 */
@Service
public class FeatureInfoServiceImpl implements FeatureInfoService {

    @Autowired
    private FeatureInfoMapper featureInfoMapper;

    /**
     * 查询指定url的结果列表
     */
    @Override
    public TPageResult listByUrl(String url, long pagenum, long pagesize) {
        Wrapper queryWrapper = new QueryWrapper<TFeatureInfo>().eq("url", url).and(i->i.eq("isdelete",0));;
        Page<TFeatureInfo> page = new Page(pagenum, pagesize);
        IPage resultPage = featureInfoMapper.selectPage(page, queryWrapper);
        TPageResult result = new TPageResult<TFeatureInfo>(pagenum, pagesize, resultPage.getTotal(), resultPage.getRecords());
        return result;
    }

    /**
     * 通过like方式查询满足要求的要素列表
     */
    @Override
    public TPageResult listByKeyWords(String keywords, long pagenum, long pagesize) {
        Wrapper queryWrapper = new QueryWrapper<TFeatureInfo>().like("text",keywords)
                .and(i->i.eq("isdelete",0));
        Page<TFeatureInfo> page = new Page(pagenum, pagesize);
        IPage resultPage = featureInfoMapper.selectPage(page, queryWrapper);
        int allCount = featureInfoMapper.selectCount(queryWrapper);
        TPageResult result = new TPageResult<TFeatureInfo>(pagenum, pagesize, allCount, resultPage.getRecords());
        return result;
    }
}
