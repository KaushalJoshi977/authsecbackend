package com.realnet.fnd.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "RN_FUNC_REGISTER")
public class Rn_Function_Register extends Rn_Who_Columns {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "MENU_ID")
	private int menu_id;

	@Column(name = "FUNCTION_NAME")
	private String function_name;

	@Column(name = "FUNCTION_ACTION_NAME")
	private String function_action_name;

	@Column(name = "FUNCTION_ICON")
	private String function_icon;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	@Column(name = "END_DATE")
	private LocalDate end_date;

	@Column(name = "ENABLE_FLAG")
	private String enable_flag;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMenu_id() {
		return menu_id;
	}

	public void setMenu_id(int menu_id) {
		this.menu_id = menu_id;
	}

	public String getFunction_name() {
		return function_name;
	}

	public void setFunction_name(String function_name) {
		this.function_name = function_name;
	}

	public String getFunction_action_name() {
		return function_action_name;
	}

	public void setFunction_action_name(String function_action_name) {
		this.function_action_name = function_action_name;
	}

	public String getFunction_icon() {
		return function_icon;
	}

	public void setFunction_icon(String function_icon) {
		this.function_icon = function_icon;
	}

	public LocalDate getEnd_date() {
		return end_date;
	}

	public void setEnd_date(LocalDate end_date) {
		this.end_date = end_date;
	}

	public String getEnable_flag() {
		return enable_flag;
	}

	public void setEnable_flag(String enable_flag) {
		this.enable_flag = enable_flag;
	}

//	@Column(name = "MENU_NAME")
//	private String menu_name;
//	public String getMenu_name() {
//		return menu_name;
//	}
//	public void setMenu_name(String menu_name) {
//		this.menu_name = menu_name;
//	}

}
