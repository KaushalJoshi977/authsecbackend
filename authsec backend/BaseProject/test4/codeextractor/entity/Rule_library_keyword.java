package com.realnet.codeextractor.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Rule_library_keyword {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String tech_stack;

	private String object_type;

	private String sub_object_type;

	private String version;

	private String replcement_string;
	private boolean isactive;

	
	
	

}
