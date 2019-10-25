package com.cjw.springbootstarter.domain.cad;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class TCadInfo {

    /**
     * 自增id
     */
    /**
     * 唯一标识
     */
    @TableId(value = "id")
    private long id;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 工作任务guid，唯一标识文件夹
     */
    private String jobGuid;
    /**
     * 原始cad名称，带后缀
     */
    private String cadRealName;
    /**
     * 自己保存的cad名称，带后缀
     */
    private String cadFileName;
    /**
     * cad原始文件对应json名称，无后缀
     */
    private String cadJsonName;
    /**
     * 用户提交后的json文件名，无后缀
     */
    private String jsonResName;
    /**
     * 用户提交json转换后的cad文件名，带后缀
     */
    private String cadResName;
}
