/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: FeatureItem
 * Author:   yao
 * Date:     2019/5/21 8:41
 * Description: 要素信息实体类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.domain.ags;

import com.cjw.springbootstarter.domain.ags.geo.IGeometry;
import com.google.gson.InstanceCreator;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 〈要素信息实体类〉
 *
 * @author yao
 * @create 2019/5/21
 * @since 1.0.0
 */
@Data
public class FeatureItem implements Serializable {

    /**
     * 属性集合
     */
    private List<FieldItem> attributes = new ArrayList<>();

    public FeatureItem addFiledItem(FieldItem fieldItem) {
        if ("".equals(fieldItem.getName())) {
            return this;
        }
        if (attributes.stream().filter(item -> item.getName().equals(fieldItem.getName())).count() > 0) {
            return this;
        }
        attributes.add(fieldItem);
        return this;
    }

    /**
     * 通过字段名获取值
     */
    public Object getValueByName(String fieldName) {
        List<FieldItem> itemList = attributes.stream().filter(item -> item.getName().equals(fieldName)).collect(Collectors.toList());
        if (itemList != null && itemList.size() > 0) {
            return itemList.get(0).getValue();
        } else {
            return null;
        }
    }

    /**
     * 空间对象
     */
    private IGeometry geometry;
}
