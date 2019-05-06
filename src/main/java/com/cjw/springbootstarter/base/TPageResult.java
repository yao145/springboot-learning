/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: TPageResult
 * Author:   yao
 * Date:     2019/3/19 15:33
 * Description: 服务返回结果对象
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.base;

import com.cjw.springbootstarter.domain.sys.TSysAttMapping;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 〈服务返回结果对象〉
 *
 * @author yao
 * @create 2019/3/19
 * @since 1.0.0
 */
@Data
public class TPageResult<T> implements Serializable {

    /**
     * 当前页码
     */
    private long pagenum;
    /**
     * 单页数量
     */
    private long pagesize;
    /**
     * 总数
     */
    private long total;

    /**
     * 结果列表
     */
    private List<T> result;

    public TPageResult(long pagenum, long pagesize, long total, List<T> result) {
        this.pagenum = pagenum;
        this.pagesize = pagesize;
        this.total = total;
        this.result = result;
    }

    /**
     * 查询结果字段映射表
     */
    private List<TSysAttMapping> attMappingList;

    public TPageResult() {
    }
}
