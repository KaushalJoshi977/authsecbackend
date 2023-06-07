package com.realnet.codeextractor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.realnet.fnd.entity.Rn_Who_AccId_Column;

import lombok.ToString;

@ToString
@Entity
@Table(name = "RN_BCF_TECHNOLOGY_STACK")
public class Rn_Bcf_Technology_Stack1 extends Rn_Who_AccId_Column {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "TECH_STACK")
	private String tech_stack;

	@Column(name = "TECH_STACK_KEY")
	private String tech_stack_key;

	// Big text
	@Column(name = "TAGS")
	private String tags;

	@Column(name = "BASE_PRJ_FILE_NAME")
	private String base_prj_file_name;

	@Column(name = "IS_ACTIVE")
	private boolean active;

	public Rn_Bcf_Technology_Stack1() {
		super();
	}

	public Rn_Bcf_Technology_Stack1(Integer id, String tech_stack, String tech_stack_key, String tags,
			String base_prj_file_name, boolean active) {
		super();
		this.id = id;
		this.tech_stack = tech_stack;
		this.tech_stack_key = tech_stack_key;
		this.tags = tags;
		this.base_prj_file_name = base_prj_file_name;
		this.active = active;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTech_stack() {
		return tech_stack;
	}

	public void setTech_stack(String tech_stack) {
		this.tech_stack = tech_stack;
	}

	public String getTech_stack_key() {
		return tech_stack_key;
	}

	public void setTech_stack_key(String tech_stack_key) {
		this.tech_stack_key = tech_stack_key;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getBase_prj_file_name() {
		return base_prj_file_name;
	}

	public void setBase_prj_file_name(String base_prj_file_name) {
		this.base_prj_file_name = base_prj_file_name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
