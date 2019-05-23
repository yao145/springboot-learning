/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: FieldItem
 * Author:   yao
 * Date:     2019/5/21 8:37
 * Description: 属性描述实体类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.domain.ags;

import lombok.Data;

import java.io.Serializable;

/**
 * 〈属性描述实体类〉
 *
 * @author yao
 * @create 2019/5/21
 * @since 1.0.0
 */
@Data
public class FieldItem implements Serializable {

    private String name;
    private String value;

    public FieldItem(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public FieldItem() {
    }
}
