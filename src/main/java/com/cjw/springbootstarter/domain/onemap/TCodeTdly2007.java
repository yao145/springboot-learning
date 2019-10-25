package com.cjw.springbootstarter.domain.onemap;

import lombok.Data;

/**
 * 土地利用现状分类及三大地类对应表:t_code_tdly_2007
 */
@Data
public class TCodeTdly2007 implements TCode {

    private long id;
    private String code;
    private String name;
    private long pid;
    private long level;
    private String type;

    @Override
    public String getMyCode() {
        return code;
    }

    @Override
    public String getMyName() {
        return name;
    }

    @Override
    public long getMyLevel() {
        return level;
    }
}
