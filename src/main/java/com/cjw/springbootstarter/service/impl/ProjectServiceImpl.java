/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ProjectServiceImpl
 * Author:   yao
 * Date:     2019/4/17 15:42
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
import com.cjw.springbootstarter.base.GlobeVarData;
import com.cjw.springbootstarter.base.TPageResult;
import com.cjw.springbootstarter.domain.project.*;
import com.cjw.springbootstarter.domain.sys.TSysAttMapping;
import com.cjw.springbootstarter.mapper.project.*;
import com.cjw.springbootstarter.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 〈高产农田项目信息操作实现〉
 *
 * @author yao
 * @create 2019/4/17
 * @since 1.0.0
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectInfoMapper projectInfoMapper;

    @Autowired
    private ProjectBuildInfoMapper projectBuildInfoMapper;

    @Autowired
    private ProjectInvestInfoMapper projectInvestInfoMapper;

    @Autowired
    private ProjectFileInfoMapper projectFileInfoMapper;

    @Autowired
    private ProjectFileTreeMapper projectFileTreeMapper;

    @Override
    public TPageResult getProjectList(String keyword, long pagenum, long pagesize) {
        Wrapper query = null;
        if (keyword != null && keyword.length() > 0) {
            query = new QueryWrapper<TProjectInfo>().like("project_name", keyword);
        }
        IPage page = new Page(pagenum, pagesize);
        IPage<TProjectInfo> resultPage = projectInfoMapper.selectPage(page, query);
        int allCount = projectInfoMapper.selectCount(query);
        List<TProjectInfo> infoList = resultPage.getRecords();
        List<ProjectSimpleInfo> simpleList = new ArrayList<>();
        for (TProjectInfo info : infoList) {
            simpleList.add(new ProjectSimpleInfo(info.getLayerIndex(),info.getExtent(), info.getCode(), info.getProjectName(),
                    info.getInvestType(), info.getApprovalNumber(), info.getSysType()));
        }
        //设置分页相关信息
        TPageResult result = new TPageResult<ProjectSimpleInfo>(pagenum, pagesize, allCount, simpleList);
        return result;
    }

    @Override
    public TPageResult getBaseInfoByCode(String code) {
        Wrapper query = new QueryWrapper<TProjectInfo>().eq("code", code);
        TProjectInfo info = projectInfoMapper.selectOne(query);
        List<TProjectInfo> infoList = new ArrayList<>();
        infoList.add(info);
        //设置分页相关信息
        TPageResult result = new TPageResult<TProjectInfo>(1, 10, 1, infoList);
        List<TSysAttMapping> attMappingList = null;
        if (info != null) {
            if (info.getSysType() == null) {
                info.setSysType("");
            }
            final String sysType = info.getSysType();
            //获取属性映射表
            attMappingList = GlobeVarData.attMappingList.stream().
                    filter(a -> a.getTableName().equals("t_project_info") && a.getSysType().equals(sysType))
                    .collect(Collectors.toList());
        }
        result.setAttMappingList(attMappingList);
        return result;
    }

    @Override
    public TPageResult getInvestInfoByCode(String code) {
        Wrapper query = new QueryWrapper<TProjectInvestInfo>().eq("code", code);
        TProjectInvestInfo info = projectInvestInfoMapper.selectOne(query);
        List<TProjectInvestInfo> infoList = new ArrayList<>();
        infoList.add(info);
        //设置分页相关信息
        TPageResult result = new TPageResult<TProjectInvestInfo>(1, 10, 1, infoList);
        //获取字段映射表
        List<TSysAttMapping> attMappingList = GlobeVarData.attMappingList.stream().
                filter(a -> a.getTableName().equals("t_project_invest_info")).collect(Collectors.toList());
        result.setAttMappingList(attMappingList);
        return result;
    }

    @Override
    public TPageResult getBuildInfoByCode(String code) {
        Wrapper query = new QueryWrapper<TProjectBuildInfo>().eq("code", code);
        TProjectBuildInfo info = projectBuildInfoMapper.selectOne(query);
        List<TProjectBuildInfo> infoList = new ArrayList<>();
        infoList.add(info);
        //设置分页相关信息
        TPageResult result = new TPageResult<TProjectBuildInfo>(1, 10, 1, infoList);
        //获取字段映射表
        List<TSysAttMapping> attMappingList = GlobeVarData.attMappingList.stream().
                filter(a -> a.getTableName().equals("t_project_build_info")).collect(Collectors.toList());
        result.setAttMappingList(attMappingList);
        return result;
    }

    @Override
    public List<TProjectFileTree> getFileTree() {
        return projectFileTreeMapper.selectList(null);
    }

    @Override
    public List<TProjectFileInfo> getFileInfoList(String code) {
        Wrapper query = new QueryWrapper<TProjectFileInfo>().eq("code", code);
        return projectFileInfoMapper.selectList(query);
    }
}
