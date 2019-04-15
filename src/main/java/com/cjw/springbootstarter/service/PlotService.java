/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: PlotService
 * Author:   yao
 * Date:     2019/4/10 16:35
 * Description: 标绘功能
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.service;

import com.cjw.springbootstarter.domain.plot.TPlotFile;
import com.cjw.springbootstarter.domain.plot.TPlotIcon;
import com.cjw.springbootstarter.base.TPageResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 〈标绘功能〉
 *
 * @author yao
 * @create 2019/4/10
 * @since 1.0.0
 */
public interface PlotService {

    /**
     * 添加一个标绘
     */
    int addNewPlot(TPlotIcon icon);

    /**
     * 获取标绘列表
     */
    TPageResult getPlots(long pagenum, long pagesize);

    /**
     * 获取指定id的标绘信息
     */
    TPlotIcon getPlotById(long iconid);

    /**
     * 通过共享id获取共享标绘
     */
    List<TPlotIcon> getSharePlot(String shareid);

    /**
     * 更新标绘信息
     */
    int updatePlot(TPlotIcon icon);

    /**
     * 通过标绘id删除标绘，需要删除对应附件文件
     */
    int deletePlot(long iconid);

    /**
     * 添加标绘对应文件附件
     */
    TPlotFile addPlotFiles(long iconid,String type,MultipartFile uploadfile);

    /**
     * 通过标绘id获取其文件附件
     */
    List<TPlotFile> getPlotFile(long iconid);

    /**
     * 通过文件id删除标绘附件
     */
    int deletePlotFile(long fileid);
}
