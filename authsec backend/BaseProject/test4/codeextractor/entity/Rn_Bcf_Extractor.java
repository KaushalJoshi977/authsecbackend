package com.realnet.codeextractor.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.realnet.fnd.entity.Rn_Who_Columns;

@Entity
@Table(name = "RN_BCF_EXTRACTOR_T")
public class Rn_Bcf_Extractor extends Rn_Who_Columns {
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

	@Column(name = "OBJECT_TYPE")
	private String object_type;

	@Column(name = "SUB_OBJECT_TYPE")
	private String sub_object_type;

	@Column(name = "FORM_TYPE_NAME")
	private String form_type_name;

	@Column(name = "STD_WF_NAME")
	private String std_wf_name;

	@Column(name = "ICON_FILE_NAME")
	private String icon_file_name;

	@Column(name = "SAMPLE_FILE_NAME")
	private String sample_file_name;

	@Column(name = "EXTRACTOR_STAGE")
	private String extractor_stage;

	@OneToMany(mappedBy = "rn_bcf_extractor", targetEntity = Rn_Bcf_Extractor_Params.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<Rn_Bcf_Extractor_Params> rn_bcf_extractor_Params;

	// SYSTEM ACCOUNT ID
	@Column(name = "ACCOUNT_ID")
	private long accountId;

	public Rn_Bcf_Extractor() {
		super();
	}

	public Rn_Bcf_Extractor(Integer id, String tech_stack, String tech_stack_key, String object_type,
			String sub_object_type, String form_type_name, String std_wf_name, String icon_file_name,
			String sample_file_name, String extractor_stage, List<Rn_Bcf_Extractor_Params> rn_bcf_extractor_Params,
			long accountId) {
		super();
		this.id = id;
		this.tech_stack = tech_stack;
		this.tech_stack_key = tech_stack_key;
		this.object_type = object_type;
		this.sub_object_type = sub_object_type;
		this.form_type_name = form_type_name;
		this.std_wf_name = std_wf_name;
		this.icon_file_name = icon_file_name;
		this.sample_file_name = sample_file_name;
		this.extractor_stage = extractor_stage;
		this.rn_bcf_extractor_Params = rn_bcf_extractor_Params;
		this.accountId = accountId;
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

	public String getForm_type_name() {
		return form_type_name;
	}

	public void setForm_type_name(String form_type_name) {
		this.form_type_name = form_type_name;
	}

	public String getStd_wf_name() {
		return std_wf_name;
	}

	public void setStd_wf_name(String std_wf_name) {
		this.std_wf_name = std_wf_name;
	}

	public String getIcon_file_name() {
		return icon_file_name;
	}

	public void setIcon_file_name(String icon_file_name) {
		this.icon_file_name = icon_file_name;
	}

	public String getSample_file_name() {
		return sample_file_name;
	}

	public void setSample_file_name(String sample_file_name) {
		this.sample_file_name = sample_file_name;
	}

	public String getExtractor_stage() {
		return extractor_stage;
	}

	public void setExtractor_stage(String extractor_stage) {
		this.extractor_stage = extractor_stage;
	}

	public List<Rn_Bcf_Extractor_Params> getRn_bcf_extractor_Params() {
		return rn_bcf_extractor_Params;
	}

	public void setRn_bcf_extractor_Params(List<Rn_Bcf_Extractor_Params> rn_bcf_extractor_Params) {
		this.rn_bcf_extractor_Params = rn_bcf_extractor_Params;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	@Override
	public String toString() {
		return "Rn_Bcf_Extractor [id=" + id + ", tech_stack=" + tech_stack + ", tech_stack_key=" + tech_stack_key
				+ ", object_type=" + object_type + ", sub_object_type=" + sub_object_type + ", form_type_name="
				+ form_type_name + ", std_wf_name=" + std_wf_name + ", icon_file_name=" + icon_file_name
				+ ", sample_file_name=" + sample_file_name + ", extractor_stage=" + extractor_stage
				+ ", rn_bcf_extractor_Params=" + rn_bcf_extractor_Params + ", accountId=" + accountId + "]";
	}
	
	

}
