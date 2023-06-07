package com.realnet.fnd.entity;

import lombok.Data;

@Data
public class ModuleCopyDTO {
	private int from_projectId;
	private int from_moduleId;
	private String to_moduleName;

	
}
