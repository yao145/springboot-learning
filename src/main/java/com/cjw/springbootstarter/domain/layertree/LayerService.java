package com.cjw.springbootstarter.domain.layertree;

import lombok.Data;

import java.util.ArrayList;

@Data
public class LayerService extends LayerMenuFolder {
	private String real_name;
	private boolean visible;
	private String url;
	private ArrayList layerIds;
	private boolean can_query;
	private LayerConfigs layerConfigs;
	private LayerConfigs zjLayerConfig;
	private String geometryType;
}
