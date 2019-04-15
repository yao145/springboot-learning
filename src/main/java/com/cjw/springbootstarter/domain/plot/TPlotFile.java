package com.cjw.springbootstarter.domain.plot;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class TPlotFile implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 唯一标识符
     */
    @TableId(value = "fileid")
    private long fileid;
    /**
     * 上传时的文件名称，带后缀名
     */
    private String filename;
    /**
     * 类型,图片对应pic
     */
    private String type;
    /**
     * 后端存放的真实名称，使用uuid
     */
    private String realfilename;
    /**
     * 标注标识id
     */
    private long iconid;

    public TPlotFile(String filename, String type, String realfilename, long iconid) {
        this.filename = filename;
        this.type = type;
        this.realfilename = realfilename;
        this.iconid = iconid;
    }

    public TPlotFile(){}
}
