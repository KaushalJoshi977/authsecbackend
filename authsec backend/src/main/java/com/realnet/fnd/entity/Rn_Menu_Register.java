package com.realnet.fnd.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "RN_MENU_REGISTER")
public class Rn_Menu_Register extends Rn_Who_Columns {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "MAIN_MENU_NAME")
	private String main_menu_name;

	@Column(name = "MAIN_MENU_ACTION_NAME")
	private String main_menu_action_name;

	@Column(name = "MAIN_MENU_ICON")
	private String main_menu_icon;

	@Column(name = "ENABLE_FLAG")
	private boolean enable_flag;

	// @DateTimeFormat(pattern = "dd/mm/yyyy")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	@Column(name = "END_DATE")
	private LocalDate end_date;
	
	private String end_date_1;
	
	public Rn_Menu_Register() {}

	public boolean getEnable_flag() {
		return enable_flag;
	}

	public void setEnable_flag(boolean enable_flag) {
		this.enable_flag = enable_flag;
	}

	public LocalDate getEnd_date() {
		return end_date;
	}

	public void setEnd_date(LocalDate end_date) {
		this.end_date = end_date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMain_menu_name() {
		return main_menu_name;
	}

	public void setMain_menu_name(String main_menu_name) {
		this.main_menu_name = main_menu_name;
	}

	public String getMain_menu_action_name() {
		return main_menu_action_name;
	}

	public void setMain_menu_action_name(String main_menu_action_name) {
		this.main_menu_action_name = main_menu_action_name;
	}

	public String getMain_menu_icon() {
		return main_menu_icon;
	}

	public void setMain_menu_icon(String main_menu_icon) {
		this.main_menu_icon = main_menu_icon;
	}

	public String getEnd_date_1() {
		return end_date_1;
	}

	public void setEnd_date_1(String end_date_1) {
		this.end_date_1 = end_date_1;
	}
	
	
}
