package com.realnet.fnd.entity1;

import javax.persistence.Column;

import lombok.Data;

@Data
public class ExtensionModel {

	
	
	private String ext1;
	private String ext2;
	private String ext3;
	private String ext4;
	private String ext5;
	private String ext6;
	private String ext7;
	private String ext8;
	private String ext9;
	private String ext10;
	
	@Column(length = 4000)
	private String bigext1;
	
	@Column(length = 4000)
	private String bigext2;
	
	@Column(length = 4000)
	private String bigext3;
	
	@Column(length = 4000)
	private String bigext4;
	
	@Column(length = 4000)
	private String bigext5;
	
	private String flex1;
	private String flex2;

	private String flex3;
	private String flex4;
	private String flex5;



}
