package com.cjw.springbootstarter.domain.layertree;


import lombok.Data;

@Data
public class LayerMenuFolder extends LayerMenuRoot {
	private int parent;
}
