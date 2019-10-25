/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: CadServiceImpl
 * Author:   yao
 * Date:     2019/10/25 11:27
 * Description: cad操作实现类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.service.impl;

import com.cjw.springbootstarter.base.JsonResultData;
import com.cjw.springbootstarter.domain.cad.PolygonVO;
import com.cjw.springbootstarter.mapper.cad.CadMapper;
import com.cjw.springbootstarter.service.CadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 〈cad操作实现类〉
 *
 * @author yao
 * @create 2019/10/25
 * @since 1.0.0
 */
@Service
public class CadServiceImpl implements CadService {

    private CadMapper cadMapper;

    @Override
    public JsonResultData getJsonByCad(String username, MultipartFile uploadfile) {
        //1 初始化一条数据录记录
        //2 保存cad文件到本地指定文件夹
        //3 调用本地的gp服务：cad转json
        //4 更新数据库记录，并返回记录到调用者
        return null;
    }

    @Override
    public JsonResultData getCadByJson(PolygonVO polygonVO) {
        //1 在原始json的基础上拷贝一份结果json，如若没有，则跳过此步骤
        //2 通过点集合构建一个标准的ags可识别的json
        //3 调用本地的gp服务：json转cad
        //4 更新数据库记录，并返回记录到调用者
        return null;
    }
}
