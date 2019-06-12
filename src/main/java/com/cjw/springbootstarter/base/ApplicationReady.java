/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ApplicationReady
 * Author:   yao
 * Date:     2019/1/22 17:26
 * Description: 系统启动后运行
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.base;


import com.cjw.springbootstarter.domain.sys.TSysAttMapping;
import com.cjw.springbootstarter.mapper.onemap.CodeTdghMapper;
import com.cjw.springbootstarter.mapper.onemap.CodeTdly2007Mapper;
import com.cjw.springbootstarter.mapper.onemap.CodeTdlyMapper;
import com.cjw.springbootstarter.mapper.sys.AttMappingMapper;
import com.cjw.springbootstarter.mapper.sys.PermissionRoleMapper;
import com.cjw.springbootstarter.mapper.sys.PremissionMapper;
import com.cjw.springbootstarter.mapper.sys.RoleMapper;
import com.cjw.springbootstarter.util.DbUtil;
import com.cjw.springbootstarter.util.Log4JUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 〈系统启动后运行〉
 *
 * @author yao
 * @create 2019/1/22
 * @since 1.0.0
 */
@Component
@Configuration
public class ApplicationReady implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        Log4JUtils.getLogger().info("数据库部分内容读入到全局缓存...");
        //数据库部分内容读入到全局缓存
        refreshDbToData();
        Log4JUtils.getLogger().info("数据库部分内容读入到全局缓存完成！");
    }

    @Autowired
    private PermissionRoleMapper permissionRoleMapper;
    @Autowired
    private PremissionMapper premissionMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AttMappingMapper attMappingMapper;

    @Autowired
    private CodeTdghMapper codeTdghMapper;

    @Autowired
    private CodeTdlyMapper codeTdlyMapper;

    @Autowired
    private CodeTdly2007Mapper codeTdly2007Mapper;

    @Value("${fileupload.folder.path}")
    private String FileUploadFolderPath;

    /**
     * 将数据库中的部分数据同步到全局变量
     */
    private void refreshDbToData() {
        GlobeVarData.premissionRoleList = permissionRoleMapper.selectList(null);
        GlobeVarData.premissionList = premissionMapper.selectList(null);
        GlobeVarData.roleList = roleMapper.selectList(null);

        //业务表中的属性名描述表
        GlobeVarData.attMappingList = attMappingMapper.selectList(null);
        //进行字段下划线转驼峰处理
        for (TSysAttMapping attMapping : GlobeVarData.attMappingList) {
            String attName = attMapping.getAttName();
            attMapping.setAttName(DbUtil.underlineToCamel(attName));
        }
        //存储全局编码数据
        GlobeVarData.tdghCodeList = codeTdghMapper.selectList(null);
        //GlobeVarData.tdlyCodeList = codeTdlyMapper.selectList(null);
        GlobeVarData.tdlyCode2007List = codeTdly2007Mapper.selectList(null);

        //设置文件上传下载存放路径
        GlobeVarData.FileUploadFolderPath = FileUploadFolderPath;
        File directory = new File(FileUploadFolderPath);
        if (directory.exists() == false) {
            directory.mkdir();
        }
    }
}
