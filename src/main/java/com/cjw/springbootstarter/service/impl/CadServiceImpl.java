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

import com.cjw.springbootstarter.base.GlobeVarData;
import com.cjw.springbootstarter.base.JsonResultData;
import com.cjw.springbootstarter.domain.cad.PolygonVO;
import com.cjw.springbootstarter.domain.cad.TCadInfo;
import com.cjw.springbootstarter.mapper.cad.CadMapper;
import com.cjw.springbootstarter.service.CadService;
import com.cjw.springbootstarter.util.Log4JUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

/**
 * 〈cad操作实现类〉
 *
 * @author yao
 * @create 2019/10/25
 * @since 1.0.0
 */
@Service
public class CadServiceImpl implements CadService {

    @Autowired
    private HttpAPIService httpAPIService;

    @Autowired
    private CadMapper cadMapper;

    @Override
    public JsonResultData getJsonByCad(String username, MultipartFile uploadfile) {
        JsonResultData resultData = null;

        String fileOriginName = uploadfile.getOriginalFilename();
        String fileExe = fileOriginName.substring(fileOriginName.lastIndexOf("."));
        //1 初始化一条数据录记录
        TCadInfo cadInfo = new TCadInfo();
        cadInfo.setUserName(username);
        cadInfo.setCadRealName(fileOriginName);
        //2 保存cad文件到本地指定文件夹
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        cadInfo.setJobGuid(uuid);
        String job_folder = GlobeVarData.FileUploadFolderPath + username + File.separator + uuid;
        File job_folder_file = new File(job_folder);
        if (job_folder_file.exists() == false) {
            job_folder_file.mkdirs();
        }
        cadInfo.setCadFileName(uuid + fileExe);
        //cad文件的保存操作
        String fileSaveFullName = job_folder + File.separator + cadInfo.getCadFileName();
        Log4JUtils.getLogger().info("待存放CAD文件全路径为--->" + fileSaveFullName);
        //进行文件存储操作
        try {
            File newFile = new File(fileSaveFullName);
            if (newFile.exists()) {
                newFile.delete();
            }
            uploadfile.transferTo(newFile);
            cadInfo.setCadJsonName(uuid);
            //3 调用本地的gp服务：cad转json
            HashMap<String, Object> params = new HashMap<>();
            params.put("user_name", cadInfo.getUserName());
            params.put("job_guid", cadInfo.getJobGuid());
            params.put("cad_file_name", cadInfo.getCadFileName());
            params.put("out_json_name", cadInfo.getCadJsonName());
            params.put("f", "pjson");
            resultData = httpAPIService.doPost("http://localhost:6080/arcgis/rest/services/cad2json/GPServer/cad2json/execute", params);
            if (resultData.getCode() != 200) {
                return JsonResultData.buildError("cad转换服务调用失败");
            } else {
                //4 更新数据库记录，并返回记录到调用者
                cadMapper.insert(cadInfo);
                return JsonResultData.buildSuccess(cadInfo);
            }
        } catch (IOException e) {
            Log4JUtils.getLogger().error("文件保存失败--->" + fileSaveFullName);
            e.printStackTrace();
            return JsonResultData.buildError(e.getMessage());
        }
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
