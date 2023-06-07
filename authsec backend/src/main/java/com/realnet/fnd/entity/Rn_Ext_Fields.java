package com.realnet.fnd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "RN_EXT_FIELD_T")
public class Rn_Ext_Fields extends Rn_Who_Columns {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "FIELD_NAME")
	private String field_name;

	@Column(name = "MAPPING")
	private String mapping;

	@Column(name = "DATA_TYPE")
	private String data_type;

	@Column(name = "FORM_CODE")
	private String form_code;

	@Column(name = "IS_ACTIVE")
	private boolean isActive;
	
	@Column(name = "ACCOUNT_ID")
	private long account_id;
	
	

	

	
	

//	@Column(name = "SP_NAME_FOR_AUTOCOMPLETE")
//	private String sp_name_for_autocomplete;
//
//	@Column(name = "SP_FOR_AUTOCOMPLETE")
//	private String sp_for_autocomplete;
//
//	@Column(name = "EXT_AUTO_ID")
//	private String ext_auto_id;
//
//	@Column(name = "EXT_DEPENDENT_ID")
//	private String ext_dependent_id;
//
//	@Column(name = "EXT_DD_ID")
//	private String ext_dd_id;
//
//	@Column(name = "SP_NAME")
//	private String sp_name;
//
//	@Column(name = "DROP_VALUE")
//	private String drop_value;
//
//	@Column(name = "DROPDOWN")
//	private String dropdown;
//
//	@Column(name = "MANDATORY")
//	private String mandatory;
//
//	@Column(name = "HIDDEN")
//	private String hidden;
//
//	@Column(name = "READONLY")
//	private String readonly;
//
//	@Column(name = "DEPENDENT")
//	private String dependent;
//	@Column(name = "SEQ_NAME")
//	private String seq_name;
//
//	@Column(name = "DEPENDENT_SP")
//	private String dependent_sp;
//
//	@Column(name = "DEPENDENT_SP_PARAM")
//	private String dependent_sp_param;
//
//	@Column(name = "VALIDATION_1")
//	private String validation_1;
//
//	@Column(name = "VAL_TYPE")
//	private String val_type;
//
//	@Column(name = "VAL_SP")
//	private String val_sp;
//
//	@Column(name = "VAL_SP_PARAM")
//	private String val_sp_param;
//
//	@Column(name = "SEQUENCE")
//	private String sequence;
//
//	@Column(name = "SEQ_SP")
//	private String seq_sp;
//
//	@Column(name = "SEQ_SP_PARAM")
//	private String seq_sp_param;
//
//	@Column(name = "DEFAULT_1")
//	private String default_1;
//
//	@Column(name = "DEFAULT_TYPE")
//	private String default_type;
//
//	@Column(name = "DEFAULT_VALUE")
//	private String default_value;
//
//	@Column(name = "DEFAULT_SP")
//	private String default_sp;
//
//	@Column(name = "DEFAULT_SP_PARAM")
//	private String default_sp_param;
//
//	@Column(name = "CALCULATED_FIELD")
//	private String calculated_field;
//
//	@Column(name = "CAL_SP")
//	private String cal_sp;
//
//	@Column(name = "CAL_SP_PARAM")
//	private String cal_sp_param;
//
//	@Column(name = "ADD_TO_GRID")
//	private String add_to_grid;
//
//	@Column(name = "DEPENDENT_ON")
//	private String dependent_on;
//	@Column(name = "RADIO")
//	private String radio;
//	@Column(name = "RADIO_OPTION")
//	private String radio_option;





}
