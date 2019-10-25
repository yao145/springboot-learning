package com.cjw.springbootstarter.domain.ags;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * ags server发布的gp工具描述类
 */
@Data
public class TArcGpserver {

    @TableId
    private long id;
    /**
     * 标识名称
     */
    private String name;
    /**
     * 工具详细描述
     */
    private String detail;
    /**
     * ags server ip地址
     */
    private String serverHost;
    /**
     * ags server 端口，一般为6080
     */
    private String serverPort;
    /**
     * 发布后，所处gp服务名称
     */
    private String toolName;
    /**
     * 发布后，gp工具名称
     */
    private String modelName;
    /**
     * 发布后，获取结果名称
     */
    private String resultName;
    /**
     * 用于进行统计的分组字段，例如：DLMC,DLBM
     */
    private String statisticsFields;
    /**
     * 统计分组字段对应的描述
     */
    private String statisticsFieldsDetail;

    /**
     * 用于查询展示字段，例如：DLMC,DLBM
     */
    private String queryFields;
    /**
     * 查询展示字段对应的描述
     */
    private String queryFieldsDetail;

    /**
     * 获取gp任务提交基地址
     */
    public String getSubmitJobUrl() {
        return "http://" + serverHost + ":" + serverPort + "/arcgis/rest/services/"
                + toolName + "/GPServer/" + modelName + "/submitJob";
    }

    /**
     * 获取指定任务的状态
     */
    public String getJobStatusUrl(String jobId) {
        return "http://" + serverHost + ":" + serverPort + "/arcgis/rest/services/"
                + toolName + "/GPServer/" + modelName + "/jobs/" + jobId;
    }

    /**
     * 获取指定任务的状态
     */
    public String getResultServerUrl(String jobId) {
        return "http://" + serverHost + ":" + serverPort + "/arcgis/rest/services/"
                + toolName + "/MapServer/jobs/" + jobId;
    }
}
