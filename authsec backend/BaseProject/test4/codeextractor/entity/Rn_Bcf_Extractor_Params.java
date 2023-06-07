package com.realnet.codeextractor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.realnet.fnd.entity.Rn_Who_Columns;

@Entity
@Table(name = "RN_BCF_EXTRACTOR_PARAMS_T")
public class Rn_Bcf_Extractor_Params extends Rn_Who_Columns {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

//	@Column(name = "HEADER_ID")
//	private int header_id;

	@Column(name = "TECH_STACK")
	private String tech_stack;

	@Column(name = "OBJECT_TYPE")
	private String object_type;

	@Column(name = "SUB_OBJECT_TYPE")
	private String sub_object_type;

	@Column(name = "FILE_CODE")
	private String file_code;

	@Column(name = "NAME_STRING")
	private String name_string;

	@Column(name = "ADDRESS_STRING")
	private String address_string;

	@Column(name = "MOVED_ADDRESS_STRING")
	private String moved_address_string;

	@Column(name = "REFERENCE_ADDRESS_STRING")
	private String reference_address_string;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "FILE_NAME_VAR")
	private String file_name_var;

	@Column(name = "FILE_NAME_DYNAMIC_STRING")
	private String file_name_dynamic_string;

	@Column(name = "IS_EXTRACTION_ENABLED")
	private boolean is_extraction_enabled;

	@Column(name = "IS_CREATION_ENABLED")
	private boolean is_creation_enabled;

	@Column(name = "Total_Project_Path_Dynamic_String")
	private String total_project_path_dynamic_string;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "HEADER_ID")
	@JsonBackReference
	private Rn_Bcf_Extractor rn_bcf_extractor;
	
//	@JsonIgnore
//	private int headerId;
//
//	public int getHeaderId() {
//		return headerId;
//	}
//
//	public void setHeaderId(int headerId) {
//		this.headerId = headerId;
//	}

	// SYSTEM ACCOUNT ID
	@Column(name = "ACCOUNT_ID")
	private long accountId;

	public Rn_Bcf_Extractor_Params() {
		super();
	}

	public Rn_Bcf_Extractor_Params(Integer id, String tech_stack, String object_type, String sub_object_type,
			String file_code, String name_string, String address_string, String moved_address_string,
			String reference_address_string, String description, String file_name_var, String file_name_dynamic_string,
			boolean is_extraction_enabled, boolean is_creation_enabled, Rn_Bcf_Extractor rn_bcf_extractor,
			long accountId,String total_project_path_dynamic_string) {
		super();
		this.id = id;
		this.tech_stack = tech_stack;
		this.object_type = object_type;
		this.sub_object_type = sub_object_type;
		this.file_code = file_code;
		this.name_string = name_string;
		this.address_string = address_string;
		this.moved_address_string = moved_address_string;
		this.reference_address_string = reference_address_string;
		this.description = description;
		this.file_name_var = file_name_var;
		this.file_name_dynamic_string = file_name_dynamic_string;
		this.is_extraction_enabled = is_extraction_enabled;
		this.is_creation_enabled = is_creation_enabled;
		this.rn_bcf_extractor = rn_bcf_extractor;
		this.accountId = accountId;
		this.total_project_path_dynamic_string=total_project_path_dynamic_string;
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

	public String getFile_code() {
		return file_code;
	}

	public void setFile_code(String file_code) {
		this.file_code = file_code;
	}

	public String getName_string() {
		return name_string;
	}

	public void setName_string(String name_string) {
		this.name_string = name_string;
	}

	public String getAddress_string() {
		return address_string;
	}

	public void setAddress_string(String address_string) {
		this.address_string = address_string;
	}

	public String getMoved_address_string() {
		return moved_address_string;
	}

	public void setMoved_address_string(String moved_address_string) {
		this.moved_address_string = moved_address_string;
	}

	public String getReference_address_string() {
		return reference_address_string;
	}

	public void setReference_address_string(String reference_address_string) {
		this.reference_address_string = reference_address_string;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFile_name_var() {
		return file_name_var;
	}

	public void setFile_name_var(String file_name_var) {
		this.file_name_var = file_name_var;
	}

	public String getFile_name_dynamic_string() {
		return file_name_dynamic_string;
	}

	public void setFile_name_dynamic_string(String file_name_dynamic_string) {
		this.file_name_dynamic_string = file_name_dynamic_string;
	}

	public boolean isIs_extraction_enabled() {
		return is_extraction_enabled;
	}

	public void setIs_extraction_enabled(boolean is_extraction_enabled) {
		this.is_extraction_enabled = is_extraction_enabled;
	}

	public boolean isIs_creation_enabled() {
		return is_creation_enabled;
	}

	public void setIs_creation_enabled(boolean is_creation_enabled) {
		this.is_creation_enabled = is_creation_enabled;
	}

	public Rn_Bcf_Extractor getRn_bcf_extractor() {
		return rn_bcf_extractor;
	}

	public void setRn_bcf_extractor(Rn_Bcf_Extractor rn_bcf_extractor) {
		this.rn_bcf_extractor = rn_bcf_extractor;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getTotal_project_path_dynamic_string() {
		return total_project_path_dynamic_string;
	}

	public void setTotal_project_path_dynamic_string(String total_project_path_dynamic_string) {
		this.total_project_path_dynamic_string = total_project_path_dynamic_string;
	}

	@Override
	public String toString() {
		return "Rn_Bcf_Extractor_Params [id=" + id + ", tech_stack=" + tech_stack + ", object_type=" + object_type
				+ ", sub_object_type=" + sub_object_type + ", file_code=" + file_code + ", name_string=" + name_string
				+ ", address_string=" + address_string + ", moved_address_string=" + moved_address_string
				+ ", reference_address_string=" + reference_address_string + ", description=" + description
				+ ", file_name_var=" + file_name_var + ", file_name_dynamic_string=" + file_name_dynamic_string
				+ ", is_extraction_enabled=" + is_extraction_enabled + ", is_creation_enabled=" + is_creation_enabled
				+ ", accountId=" + accountId + "]";
	}

}
