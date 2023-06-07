package com.realnet.entitybuilder.response;

import lombok.Data;

@Data
public class EntityResponse {
	
	private String msg;

	public EntityResponse(String msg) {
		super();
		this.msg = msg;
	}

}
