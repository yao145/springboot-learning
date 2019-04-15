package com.cjw.springbootstarter.service;

import com.cjw.springbootstarter.domain.layertree.LayerMenuRoot;

import java.util.List;

/**
 * 图层树结构的目录服务，对应数据库表:t_layer_tree_service和t_layer_tree_menu
 */
public interface LayerTreeService {

    /**
     * 获取数据库表中的图层树结构信息，双表组合
     * @return
     */
    List<LayerMenuRoot> listAllLayerMenu();
}
