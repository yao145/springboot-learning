package com.cjw.springbootstarter.domain.onemap;

import lombok.Data;

/**
 * 管制区用途分类及编码表:t_code_gzq
 */
@Data
public class TCodeGzq implements TCode {

    private long id;
    private String code;
    private String name;
    private long pid;
    private long level;

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
