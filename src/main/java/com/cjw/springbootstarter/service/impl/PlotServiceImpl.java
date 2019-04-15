/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: PlotServiceImpl
 * Author:   yao
 * Date:     2019/4/11 8:15
 * Description: 标绘功能服务类实现
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjw.springbootstarter.base.ApplicationConstant;
import com.cjw.springbootstarter.base.GlobeVarData;
import com.cjw.springbootstarter.domain.plot.TPlotFile;
import com.cjw.springbootstarter.domain.plot.TPlotIcon;
import com.cjw.springbootstarter.base.TPageResult;
import com.cjw.springbootstarter.mapper.PlotFileMapper;
import com.cjw.springbootstarter.mapper.PlotIconMapper;
import com.cjw.springbootstarter.service.PlotService;
import com.cjw.springbootstarter.util.Log4JUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 〈标绘功能服务类实现〉
 *
 * @author yao
 * @create 2019/4/11
 * @since 1.0.0
 */
@Service
public class PlotServiceImpl implements PlotService {

    @Autowired
    private PlotIconMapper plotIconMapper;

    @Autowired
    private PlotFileMapper plotFileMapper;


    @Override
    public int addNewPlot(TPlotIcon icon) {
        int count = plotIconMapper.insert(icon);
        if (count > 0) {
            Log4JUtils.getLogger().info(ApplicationConstant.DB_INSERT_SUCCESS);
            return count;
        } else {
            Log4JUtils.getLogger().error(ApplicationConstant.DB_INSERT_ERROR);
            return count;
        }
    }

    @Override
    public TPageResult getPlots(long pagenum, long pagesize) {
        Page<TPlotIcon> page = new Page(pagenum, pagesize);
        IPage resultPage = plotIconMapper.selectPage(page, null);
        TPageResult result = new TPageResult<TPlotIcon>(pagenum, pagesize, resultPage.getTotal(), resultPage.getRecords());
        return result;
    }

    @Override
    public TPlotIcon getPlotById(long iconid) {
        TPlotIcon result = plotIconMapper.selectById(iconid);
        return result;
    }

    @Override
    public List<TPlotIcon> getSharePlot(String shareid) {
        Wrapper wrapper = new QueryWrapper<TPlotIcon>().eq("share", shareid);
        List<TPlotIcon> resultList = plotIconMapper.selectList(wrapper);
        return resultList;
    }

    @Override
    public int updatePlot(TPlotIcon icon) {
        int count = plotIconMapper.updateById(icon);
        if (count > 0) {
            Log4JUtils.getLogger().info(ApplicationConstant.DB_UPDATE_SUCCESS);
            return count;
        } else {
            Log4JUtils.getLogger().error(ApplicationConstant.DB_UPDATE_ERROR);
            return count;
        }
    }

    @Override
    public int deletePlot(long iconid) {
        int count = plotIconMapper.deleteById(iconid);
        //删除标绘对应的文件内容
        deletePlotFileByIconid(iconid);
        return deleteObject(count, iconid);
    }

    @Override
    public TPlotFile addPlotFiles(long iconid, String type, MultipartFile uploadfile) {
        String fileOriginName = uploadfile.getOriginalFilename();
        String fileExe = fileOriginName.substring(fileOriginName.lastIndexOf("."));
        String fileSaveName = UUID.randomUUID().toString() + fileExe;
        String plotDirPath = GlobeVarData.FileUploadFolderPath + "plot";
        File plotDir = new File(plotDirPath);
        if (plotDir.exists() == false) {
            plotDir.mkdir();
        }
        String fileSaveFullName = plotDirPath + "/" + fileSaveName;
        Log4JUtils.getLogger().info("待存放文件全路径为--->" + fileSaveFullName);
        //进行文件存储操作
        try {
            File newFile = new File(fileSaveFullName);
            if (newFile.exists()) {
                newFile.delete();
            }
            uploadfile.transferTo(newFile);
            //进行数据库写入操作
            TPlotFile plotFile = new TPlotFile(fileOriginName, type, fileSaveName, iconid);
            int count = plotFileMapper.insert(plotFile);
            if (count > 0) {
                Log4JUtils.getLogger().info(ApplicationConstant.DB_INSERT_SUCCESS);
                return plotFile;
            } else {
                Log4JUtils.getLogger().error(ApplicationConstant.DB_INSERT_ERROR);
                //删除文件
                deleteFileOnDisk(plotFile);
                return null;
            }
        } catch (IOException e) {
            Log4JUtils.getLogger().error("文件保存失败--->" + fileSaveFullName);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<TPlotFile> getPlotFile(long iconid) {
        Wrapper wrapper = new QueryWrapper<TPlotFile>().eq("iconid", iconid);
        List<TPlotFile> resultList = plotFileMapper.selectList(wrapper);
        return resultList;
    }

    @Override
    public int deletePlotFile(long fileid) {
        TPlotFile plotFile = plotFileMapper.selectById(fileid);
        //删除文件
        deleteFileOnDisk(plotFile);

        int count = plotFileMapper.deleteById(fileid);
        return deleteObject(count, fileid);
    }

    private void deletePlotFileByIconid(long iconid) {
        Wrapper wrapper = new QueryWrapper<TPlotFile>().eq("iconid", iconid);
        //查询获取标绘id对应的文件名，然后删除对应文件
        List<TPlotFile> fileList = plotFileMapper.selectList(wrapper);
        //进行文件删除操作，yaozhiwu
        for (TPlotFile plotFile : fileList) {
            //删除文件
            deleteFileOnDisk(plotFile);
        }
        int count = plotFileMapper.delete(wrapper);
        deleteObject(count, iconid);
    }

    /**
     * 删除服务器上指定的plot file文件
     */
    private void deleteFileOnDisk(TPlotFile plotFile) {
        if (plotFile != null && plotFile.getRealfilename() != null) {
            String fileSaveFullName = GlobeVarData.FileUploadFolderPath + "plot/" + plotFile.getRealfilename();
            File file = new File(fileSaveFullName);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * 公共方法，删除后的提示内容
     *
     * @param count    删除后的影响数
     * @param objectId 删除使用的id
     * @return 删除后的影响数
     */
    private int deleteObject(int count, long objectId) {
        if (count > 0) {
            Log4JUtils.getLogger().info(ApplicationConstant.DB_DELETE_SUCCESS + "-->【" + objectId + "】");
            return count;
        } else {
            Log4JUtils.getLogger().error(ApplicationConstant.DB_DELETE_ERROR);
            return 0;
        }
    }
}
