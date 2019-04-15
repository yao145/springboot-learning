/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: TFeatureInfo
 * Author:   yao
 * Date:     2019/3/19 14:45
 * Description: es方式下搜索结果
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.domain.esmodel;

import lombok.Data;

import java.io.Serializable;

/**
 * 〈图层搜索结果后的信息，对应数据库中的service表〉<br>
 * 〈es方式下搜索结果〉
 *
 * @author yao
 * @create 2019/3/19
 * @since 1.0.0
 */
@Data
public class TFeatureInfo implements Serializable {
    private Integer id;

    private Integer objectid;

    private String text;

    private Integer priority;

    private String url;

    private String idname;

    private String name;

    private Integer layerid;

    private String category;
}
