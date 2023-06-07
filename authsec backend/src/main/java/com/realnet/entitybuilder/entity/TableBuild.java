package com.realnet.entitybuilder.entity;

import java.util.List;

import javax.persistence.OneToMany;

import lombok.Data;

@Data
public class TableBuild {
	
	private String table_name;
	
	private String json;
	
	@OneToMany
	private List<EntityBuild> entity_name;

}
