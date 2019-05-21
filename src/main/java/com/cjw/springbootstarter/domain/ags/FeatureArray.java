/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: FeatureArray
 * Author:   yao
 * Date:     2019/5/21 8:38
 * Description: 要素信息实体类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.domain.ags;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈要素数组实体类〉
 *
 * @author yao
 * @create 2019/5/21
 * @since 1.0.0
 */
@Data
public class FeatureArray implements Serializable {

    /**
     * 要素集合
     */
    private List<FeatureItem> features = new ArrayList<>();

    /**
     * 要素字段映射表
     */
    private List<FieldItem> attMapping = new ArrayList<>();

    /**
     * 添加一条要素信息
     */
    public FeatureArray addFeature(FeatureItem featureItem) {

        if (featureItem != null) {
            features.add(featureItem);
        }
        return this;
    }

    /**
     * 添加一个属性映射信息
     */
    public FeatureArray addAttMapping(FieldItem fieldItem) {
        if ("".equals(fieldItem.getName())) {
            return this;
        }
        if (attMapping.stream().filter(item -> item.getName() == fieldItem.getName()).count() > 0) {
            return this;
        }
        attMapping.add(fieldItem);
        return this;
    }
}
