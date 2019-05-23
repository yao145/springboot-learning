/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Point
 * Author:   yao
 * Date:     2019/5/21 15:37
 * Description: 空间对象，点
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.domain.ags.geo;

import lombok.Data;

import java.io.Serializable;

/**
 * 〈空间对象，点〉
 *
 * @author yao
 * @create 2019/5/21
 * @since 1.0.0
 */
@Data
public class Point implements IGeometry, Serializable {
    /**
     * 空间对象类型
     */
    private String type = "point";

    /**
     * 空间坐标:经度
     */
    private double x;
    /**
     * 空间坐标：纬度
     */
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
    }
}
