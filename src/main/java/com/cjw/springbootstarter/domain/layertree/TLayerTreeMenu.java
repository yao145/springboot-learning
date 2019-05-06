package com.cjw.springbootstarter.domain.layertree;

import lombok.Data;

@Data
public class TLayerTreeMenu {
	private int id;
	private int parent;
	private String layerName;
	private String icon;
}
