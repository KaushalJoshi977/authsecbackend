package com.realnet.fnd.entity;

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

//@Data
//@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "RN_FORMS_COMPONENT_SETUP_T")
public class Rn_Forms_Component_Setup extends Rn_Who_Columns {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COMPONENT_ID")
	private int component_id;

	@Column(name = "LABEL")
	private String label;

	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "MAPPING")
	private String mapping;

	@Column(name = "MANDATORY")
	private String mandatory;

	@Column(name = "READONLY")
	private String readonly;

	@Column(name = "DROP_VALUES")
	private String drop_values;

	@Column(name = "SP")
	private String sp;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "form_id", nullable = false)
	@JsonBackReference
	private Rn_Forms_Setup rn_forms_setup;

	public int getComponent_id() {
		return component_id;
	}

	public void setComponent_id(int component_id) {
		this.component_id = component_id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMapping() {
		return mapping;
	}

	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

	public String getMandatory() {
		return mandatory;
	}

	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public String getDrop_values() {
		return drop_values;
	}

	public void setDrop_values(String drop_values) {
		this.drop_values = drop_values;
	}

	public String getSp() {
		return sp;
	}

	public void setSp(String sp) {
		this.sp = sp;
	}

	public Rn_Forms_Setup getRn_forms_setup() {
		return rn_forms_setup;
	}

	public void setRn_forms_setup(Rn_Forms_Setup rn_forms_setup) {
		this.rn_forms_setup = rn_forms_setup;
	}

}