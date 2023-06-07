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
@Table(name = "RN_BCF_RULES")
public class Rn_Bcf_Rules extends Rn_Who_AccId_Column {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "GROUP_ID")
	private String group_id;

	@Column(name = "RULE_NAME")
	private String rule_name;

	@Column(name = "TECH_STACK")
	private String tech_stack;

	@Column(name = "OBJECT_TYPE")
	private String object_type;

	@Column(name = "SUB_OBJECT_TYPE")
	private String sub_object_type;

	@Column(name = "FILE_CODE")
	private String file_code;

	@Column(name = "RULE_TYPE")
	private String rule_type;

	// Big text
	@Column(name = "IDENTIFIER_START_STRING")
	private String identifier_start_string;

	// Big text
	@Column(name = "IDENTIFIER_END_STRING")
	private String identifier_end_string;

	// Big text
	@Column(name = "REPLACEMENT_STRING",length = 8000)
	private String replacement_string;

	public Rn_Bcf_Rules() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Rn_Bcf_Rules(Integer id, String group_id, String rule_name, String tech_stack, String object_type,
			String sub_object_type, String file_code, String rule_type, String identifier_start_string,
			String identifier_end_string, String replacement_string) {
		super();
		this.id = id;
		this.group_id = group_id;
		this.rule_name = rule_name;
		this.tech_stack = tech_stack;
		this.object_type = object_type;
		this.sub_object_type = sub_object_type;
		this.file_code = file_code;
		this.rule_type = rule_type;
		this.identifier_start_string = identifier_start_string;
		this.identifier_end_string = identifier_end_string;
		this.replacement_string = replacement_string;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public String getRule_name() {
		return rule_name;
	}

	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
	}

	public String getTech_stack() {
		return tech_stack;
	}

	public void setTech_stack(String tech_stack) {
		this.tech_stack = tech_stack;
	}

	public String getObject_type() {
		return object_type;
	}

	public void setObject_type(String object_type) {
		this.object_type = object_type;
	}

	public String getSub_object_type() {
		return sub_object_type;
	}

	public void setSub_object_type(String sub_object_type) {
		this.sub_object_type = sub_object_type;
	}

	public String getFile_code() {
		return file_code;
	}

	public void setFile_code(String file_code) {
		this.file_code = file_code;
	}

	public String getRule_type() {
		return rule_type;
	}

	public void setRule_type(String rule_type) {
		this.rule_type = rule_type;
	}

	public String getIdentifier_start_string() {
		return identifier_start_string;
	}

	public void setIdentifier_start_string(String identifier_start_string) {
		this.identifier_start_string = identifier_start_string;
	}

	public String getIdentifier_end_string() {
		return identifier_end_string;
	}

	public void setIdentifier_end_string(String identifier_end_string) {
		this.identifier_end_string = identifier_end_string;
	}

	public String getReplacement_string() {
		return replacement_string;
	}

	public void setReplacement_string(String replacement_string) {
		this.replacement_string = replacement_string;
	}
	
	
//	// WHO COLUMNS
//	@Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "CREATED_AT", nullable = false, updatable = false)
//    @CreatedDate
//    private Date createdAt;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "UPDATED_AT", nullable = false)
//    @LastModifiedDate
//    private Date updatedAt;
//    
//    @Column(name = "CREATED_BY", updatable = false)
//	private Long createdBy;
//    
//	@Column(name = "UPDATED_BY")
//	private Long updatedBy;
//	
//    @Column(name = "ACCOUNT_ID")
//	private Long accountId;
	
	
}
