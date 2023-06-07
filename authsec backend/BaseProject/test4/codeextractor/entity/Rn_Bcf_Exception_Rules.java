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
@Table(name = "RN_BCF_EXCEPTION_RULES")
public class Rn_Bcf_Exception_Rules extends Rn_Who_AccId_Column {
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

	@Column(name = "OBJECT_TYPE")
	private String object_type;

	@Column(name = "SUB_OBJECT_TYPE")
	private String sub_object_type;

	@Column(name = "OBJECT_NAME_VARIABLE")
	private String object_name_variable;

	@Column(name = "OBJECT_NAME_DYNAMIC_STRING")
	private String object_name_dynamic_string;

	public Rn_Bcf_Exception_Rules() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Rn_Bcf_Exception_Rules(Integer id, String tech_stack, String object_type, String sub_object_type,
			String object_name_variable, String object_name_dynamic_string) {
		super();
		this.id = id;
		this.tech_stack = tech_stack;
		this.object_type = object_type;
		this.sub_object_type = sub_object_type;
		this.object_name_variable = object_name_variable;
		this.object_name_dynamic_string = object_name_dynamic_string;
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

	public String getObject_name_variable() {
		return object_name_variable;
	}

	public void setObject_name_variable(String object_name_variable) {
		this.object_name_variable = object_name_variable;
	}

	public String getObject_name_dynamic_string() {
		return object_name_dynamic_string;
	}

	public void setObject_name_dynamic_string(String object_name_dynamic_string) {
		this.object_name_dynamic_string = object_name_dynamic_string;
	}
	
	

//	// WHO COLUMNS
//	@Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "CREATED_AT", nullable = false, updatable = false)
//    @CreatedDate
//    private Date createdAt;
//	
//	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") private LocalDateTime createdAt;
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
