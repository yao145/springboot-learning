/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: LayerTreeServiceImpl
 * Author:   yao
 * Date:     2019/4/1 11:28
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cjw.springbootstarter.domain.layertree.*;
import com.cjw.springbootstarter.mapper.LayerTreeMenuMapper;
import com.cjw.springbootstarter.mapper.LayerTreeServiceMapper;
import com.cjw.springbootstarter.service.LayerTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 图层树结构的目录服务，对应数据库表:t_layer_tree_service和t_layer_tree_menu
 *
 * @author yao
 * @create 2019/4/1
 * @since 1.0.0
 */
@Service
public class LayerTreeServiceImpl implements LayerTreeService {

    @Autowired
    private LayerTreeMenuMapper layerTreeMenuMapper;

    @Autowired
    private LayerTreeServiceMapper layerTreeServiceMapper;

    @Override
    public List<LayerMenuRoot> listAllLayerMenu() {
        List<TLayerTreeMenu> menuList = layerTreeMenuMapper.selectList(null);
        List<TLayerTreeService> serviceList = layerTreeServiceMapper.selectList(null);

        //解析目录结构
        List<LayerMenuRoot> menuResult = new ArrayList<LayerMenuRoot>();
        for (TLayerTreeMenu menuEntity : menuList) {
            LayerMenuRoot menuItem = null;
            if (menuEntity.getParent() == 0) {
                menuItem = new LayerMenuRoot();
            } else {
                menuItem = new LayerMenuFolder();
                ((LayerMenuFolder) menuItem).setParent(menuEntity.getParent());
            }
            Config config = new Config();
            if (menuEntity.getIcon() == null || "".equals(menuEntity.getIcon())) {
                // 如果为空则使用id
                config.setIcon(menuEntity.getId() + "");
            } else {
                config.setIcon(menuEntity.getIcon());
            }
            menuItem.setConfig(config);
            menuItem.setId(menuEntity.getId());
            menuItem.setLayer_name(menuEntity.getLayer_name());

            menuResult.add(menuItem);
        }
        // 解析地图服务部分
        for (TLayerTreeService serviceEntity : serviceList) {
            LayerService serviceItem = new LayerService();
            serviceItem.setId(serviceEntity.getId());
            serviceItem.setLayer_name(serviceEntity.getLayerName());
            serviceItem.setParent(serviceEntity.getParent());
            String folder = serviceEntity.getServerFolder();
            if (folder == null || "".equals(folder)) {
                serviceItem.setUrl("${gsMapUrl}/arcgis/rest/services/" + serviceEntity.getServerName() + "/MapServer");
            }
            else {
                serviceItem.setUrl("${gsMapUrl}/arcgis/rest/services/" + folder + "/" + serviceEntity.getServerName()
                        + "/MapServer");
            }
            if (serviceEntity.getRealName() == null || "".equals(serviceEntity.getRealName())) {
                serviceItem.setReal_name(serviceEntity.getServerName());
            }
            serviceItem.setVisible("true".equals(serviceEntity.getVisible()));
            String layerIds = serviceEntity.getLayerIds();
            if (layerIds != null && "".equals(layerIds) == false) {
                String[] idsStr = layerIds.split(",");
                ArrayList idsInt = new ArrayList();
                for (String idStr : idsStr) {
                    try {
                        int a = Integer.parseInt(idStr);
                        idsInt.add(a);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                serviceItem.setLayerIds(idsInt);
            }
            serviceItem.setCan_query("true".equals(serviceEntity.getCanQuery()));
            Config config = new Config();
            if (serviceEntity.getIcon() == null || "".equals(serviceEntity.getIcon())) {
                // 如果为空则使用id
                config.setIcon(serviceEntity.getId()+"");
            } else {
                config.setIcon(serviceEntity.getIcon());
            }
            String relation = serviceEntity.getRelation();
            if (relation != null && "".equals(relation) == false) {
                config.setRelation(relation.split(","));
            }
            serviceItem.setConfig(config);
            LayerConfigs layerConfig = new LayerConfigs();

            if (folder == null || "".equals(folder)) {
                layerConfig.setLayer(serviceEntity.getServerName());
            }
            else {
                layerConfig.setLayer(serviceEntity.getServerFolder() + "_" + serviceEntity.getServerName());
            }

            if (folder == null || "".equals(folder)) {
                layerConfig.setUrl("${gsMapUrl}/arcgis/rest/services/" + serviceEntity.getServerName() + "/MapServer/WMTS");
            }
            else {
                layerConfig.setUrl("${gsMapUrl}/arcgis/rest/services/" + folder + "/" + serviceEntity.getServerName()
                        + "/MapServer/WMTS");
            }

            serviceItem.setLayerConfigs(layerConfig);

            String server_zj_name = serviceEntity.getServerZjName();
            if (server_zj_name != null && "".equals(server_zj_name) == false) {
                LayerConfigs zjLayerConfig = new LayerConfigs();
                zjLayerConfig.setLayer(server_zj_name);
                zjLayerConfig.setUrl("${hgMapUrl}/arcgis/rest/services/" + serviceEntity.getServerFolder() + "/"
                        + server_zj_name + "/MapServer/WMTS");
                serviceItem.setZjLayerConfig(zjLayerConfig);
            }
            serviceItem.setGeometryType(serviceEntity.getGeometryType());

            menuResult.add(serviceItem);
        }

        return menuResult;
    }

}
