package com.realnet.codeextractor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "NAMING_CONVENSION")
public class NamingConvension {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "VARIABLE_NAME")
	private String variableName;
	
	@Column(name = "OBJECT_NAME_DYNAMIC_STRING")
	private String objectNameDynamicString;
}
