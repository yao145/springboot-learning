package com.cjw.springbootstarter.domain.plot;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class TPlotIcon implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "iconid")
    private long iconid;
    /**
     * 用户名称
     */
    private String users;
    /**
     * 标注类型
     */
    private String type;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述信息
     */
    private String descstr;
    /**
     * 是否共享标注
     */
    private String share;
    /**
     * 存放gml
     */
    private String data;
    /**
     * 二进制描述,未被用到
     */
    private String geo;

    public TPlotIcon(String users, String type, String name, String descstr, String share, String data, String geo) {
        this.users = users;
        this.type = type;
        this.name = name;
        this.descstr = descstr;
        this.share = share;
        this.data = data;
        this.geo = geo;
    }

    public TPlotIcon(){}

    /**
     * 根据一个传入的对象对当前对象进行更新
     */
    public void refreshAtt(TPlotIcon inputIcon){
        if(inputIcon.getUsers()!=null){
            this.users = inputIcon.getUsers();
        }
        if(inputIcon.getType()!=null){
            this.type = inputIcon.getType();
        }
        if(inputIcon.getName()!=null){
            this.name = inputIcon.getName();
        }
        if(inputIcon.getDescstr()!=null){
            this.descstr = inputIcon.getDescstr();
        }
        if(inputIcon.getShare()!=null){
            this.share = inputIcon.getShare();
        }
        if(inputIcon.getData()!=null){
            this.data = inputIcon.getData();
        }
        if(inputIcon.getGeo()!=null){
            this.geo = inputIcon.getGeo();
        }
    }
}
