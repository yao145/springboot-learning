/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Polygon
 * Author:   yao
 * Date:     2019/5/21 15:26
 * Description: 空间对象，面
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.domain.ags.geo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈空间对象，面〉
 *
 * @author yao
 * @create 2019/5/21
 * @since 1.0.0
 */
@Data
public class Polygon implements IGeometry, Serializable {

    /**
     * 空间对象类型
     */
    private String type = "polygon";

    /**
     * 空间坐标
     */
    private List<List<double[]>> rings = new ArrayList<>();


    public Polygon() {
    }
}
