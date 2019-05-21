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

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
        if (attributes.stream().filter(item -> item.getName() == fieldItem.getName()).count() > 0) {
            return this;
        }
        attributes.add(fieldItem);
        return this;
    }

}
