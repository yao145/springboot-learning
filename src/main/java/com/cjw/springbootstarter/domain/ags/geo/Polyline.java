/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Polyline
 * Author:   yao
 * Date:     2019/5/21 15:38
 * Description: 空间对象，线
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.domain.ags.geo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈空间对象，线〉
 *
 * @author yao
 * @create 2019/5/21
 * @since 1.0.0
 */
public class Polyline implements IGeometry, Serializable {

    /**
     * 空间对象类型
     */
    private String type = "line";

    /**
     * 空间坐标
     */
    private List<List<double[]>> lines = new ArrayList<>();

}
