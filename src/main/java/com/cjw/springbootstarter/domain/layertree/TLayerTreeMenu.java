package com.cjw.springbootstarter.domain.layertree;

import lombok.Data;

@Data
public class TLayerTreeMenu {
	private int id;
	private int parent;
	private String layer_name;
	private String icon;
}
