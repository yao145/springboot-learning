/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: EShitsUtil
 * Author:   yao
 * Date:     2019/4/10 9:19
 * Description: 全文检索结果转换工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.util;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 〈全文检索结果转换工具类〉
 *
 * @author yao
 * @create 2019/4/10
 * @since 1.0.0
 */
public class EShitsUtil {

    public static <T> List convertHits2Object(SearchResponse response, T tIn) throws Exception {
        List resultList = new ArrayList<T>();

        SearchHits searchHits = response.getHits();
        Iterator<SearchHit> iterator = searchHits.iterator();
        while (iterator.hasNext()) {
            SearchHit searchHit = iterator.next();

            T t = (T) tIn.getClass().newInstance();
            Class tClazz = t.getClass();
            Field[] fields = tClazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                // 取消属性的访问权限控制，即使private属性也可以进行访问。
                fields[i].setAccessible(true);
                try {
                    fields[i].set(t, searchHit.getSourceAsMap().get(fields[i].getName()));
                } catch (Exception ex) {
                    Log4JUtils.getLogger().error("属性转换失败");
                }
            }
            resultList.add(t);
        }
        return resultList;
    }

}
