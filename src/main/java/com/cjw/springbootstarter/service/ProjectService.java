package com.cjw.springbootstarter.service;

import com.cjw.springbootstarter.base.TPageResult;
import com.cjw.springbootstarter.domain.project.*;

import java.util.List;

/**
 * 高产农田项目信息操作接口
 */
public interface ProjectService {

    /**
     * 获取农田项目列表,keyword为空时，查询所有
     */
    TPageResult getProjectList(String keyword, long pagenum, long pagesize);

    /**
     * 通过编号获取项目基本信息
     */
    TPageResult getBaseInfoByCode(String code);

    /**
     * 通过编号获取项目投资信息
     */
    TPageResult getInvestInfoByCode(String code);

    /**
     * 通过编号获取项目建设信息
     */
    TPageResult getBuildInfoByCode(String code);

    /**
     * 获取项目文件附件的树结构
     */
    List<TProjectFileTree> getFileTree();

    /**
     * 获取指定项目的所有附件信息
     */
    List<TProjectFileInfo> getFileInfoList(String code);
}

