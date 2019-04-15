package com.cjw.springbootstarter.domain.layertree;


import lombok.Data;

@Data
public class TLayerTreeService {
	private int id;
	private int parent;
	private String layerName;
	private String realName;
	private String visible;
	private String serverName;
	
	private String serverZjName;
	private String serverFolder;
	private String layerIds;
	private String canQuery;
	private String icon;
	private String relation;
	private String geometryType;
}
