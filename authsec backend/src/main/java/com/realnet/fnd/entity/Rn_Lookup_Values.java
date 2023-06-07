package com.realnet.fnd.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RN_LOOKUP_VALUES_T")

public class Rn_Lookup_Values extends Rn_Who_Columns {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "LOOKUP_CODE")
	private String lookupCode;

	@Column(name = "MEANING")
	private String meaning;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "LOOKUP_TYPE")
	private String lookupType;
	
	@Column(name = "ACTIVE_START_DATE")
	private Date active_start_date;

	@Column(name = "ACTIVE_END_DATE")
	private Date active_end_date;
	
	@Column(name = "DROP_VALUE")
	private String dropValue;
	
	@Column(name = "ENABLED_FLAG")
	private boolean enabled_flag;

	//
//	@Column(name = "PATIENT_COUNTRY")
//	private String patientCountry;
//
//	
//
//	@Column(name = "STATE_NAME")
//	private String stateName;
//
//	@Column(name = "CITY_NAME")
//	private String cityName;
//
//	@Column(name = "ZIP_CODE")
//	private String zipCode;
//
//	@Column(name = "COUNTRY_ID")
//	private int country_id;

	public Rn_Lookup_Values() {
		super();
	}

	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLookupCode() {
		return lookupCode;
	}

	public void setLookupCode(String lookupCode) {
		this.lookupCode = lookupCode;
	}

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLookupType() {
		return lookupType;
	}

	public void setLookupType(String lookupType) {
		this.lookupType = lookupType;
	}

	

	public String getDropValue() {
		return dropValue;
	}

	public void setDropValue(String dropValue) {
		this.dropValue = dropValue;
	}

	public Date getActive_start_date() {
		return active_start_date;
	}

	public void setActive_start_date(Date active_start_date) {
		this.active_start_date = active_start_date;
	}

	public Date getActive_end_date() {
		return active_end_date;
	}

	public void setActive_end_date(Date active_end_date) {
		this.active_end_date = active_end_date;
	}

	public boolean isEnabled_flag() {
		return enabled_flag;
	}

	public void setEnabled_flag(boolean enabled_flag) {
		this.enabled_flag = enabled_flag;
	}

//	@Column(name = "CREATED_BY")
//	private int created_by;
//
//	@DateTimeFormat(pattern = "dd/mm/yyyy")
//	@Column(name = "CREATION_DATE")
//	private Date creation_date = new java.sql.Date(new java.util.Date().getTime());
//
//	@Column(name = "LAST_UPDATED_BY")
//	private int last_updated_by;
//
//	@DateTimeFormat(pattern = "dd/mm/yyyy")
//	@Column(name = "LAST_UPDATE_DATE")
//	private Date last_update_date = new java.sql.Date(new java.util.Date().getTime());
	
//	public String getPatientCountry() {
//		return patientCountry;
//	}
//
//	public void setPatientCountry(String patientCountry) {
//		this.patientCountry = patientCountry;
//	}
//	public String getStateName() {
//		return stateName;
//	}
//
//	public void setStateName(String stateName) {
//		this.stateName = stateName;
//	}
//
//	public String getCityName() {
//		return cityName;
//	}
//
//	public void setCityName(String cityName) {
//		this.cityName = cityName;
//	}
//
//	public String getZipCode() {
//		return zipCode;
//	}
//
//	public void setZipCode(String zipCode) {
//		this.zipCode = zipCode;
//	}
//
//	public int getCountry_id() {
//		return country_id;
//	}
//
//	public void setCountry_id(int country_id) {
//		this.country_id = country_id;
//	}

}
