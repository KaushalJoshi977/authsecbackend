package com.realnet.fnd.entity;

import lombok.Data;

@Data
public class WireFrameCopyDTO {
	private int from_projectId;
	private int from_moduleId;
	private int from_WireFrameId;
	private String to_uiName;

	public WireFrameCopyDTO() {
		super();
	}

	

}
