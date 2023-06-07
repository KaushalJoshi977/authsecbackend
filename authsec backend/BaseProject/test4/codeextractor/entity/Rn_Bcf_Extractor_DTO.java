package com.realnet.codeextractor.entity;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize
public class Rn_Bcf_Extractor_DTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String tech_stack;

	private String tech_stack_key;

	private String object_type;

	private String sub_object_type;

	private String form_type_name;

	private String std_wf_name;

	private String icon_file_name;

	private String sample_file_name;

	// private String extractor_stage;

	// private long accountId;

}
