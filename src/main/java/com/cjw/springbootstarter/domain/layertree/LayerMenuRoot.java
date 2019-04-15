package com.cjw.springbootstarter.domain.layertree;


import lombok.Data;

@Data
public class LayerMenuRoot {
	private int id;
	private String layer_name;
	private Config config;
}
