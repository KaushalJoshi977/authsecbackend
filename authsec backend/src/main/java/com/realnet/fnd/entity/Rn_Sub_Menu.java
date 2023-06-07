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

@Entity
@Table(name = "RN_SUB_MENU")
public class Rn_Sub_Menu extends Rn_Who_Columns {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "SUB_MENU_NAME")
	private String sub_menu_name;
	
	@Column(name = "SUB_MENU_ICON")
	private String sub_menu_icon;

	@Column(name = "SUB_MENU_ACTION_LINK")
	private String sub_menu_action_link;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "MAIN_MENU_ID")
	@JsonBackReference
	private Rn_Main_Menu rn_main_menu;

	public Rn_Sub_Menu() {
		super();
	}

	

	public Rn_Sub_Menu(Integer id, String sub_menu_name, String sub_menu_icon, String sub_menu_action_link,
			Rn_Main_Menu rn_main_menu) {
		super();
		this.id = id;
		this.sub_menu_name = sub_menu_name;
		this.sub_menu_icon = sub_menu_icon;
		this.sub_menu_action_link = sub_menu_action_link;
		this.rn_main_menu = rn_main_menu;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSub_menu_name() {
		return sub_menu_name;
	}

	public void setSub_menu_name(String sub_menu_name) {
		this.sub_menu_name = sub_menu_name;
	}

	public String getSub_menu_icon() {
		return sub_menu_icon;
	}

	public void setSub_menu_icon(String sub_menu_icon) {
		this.sub_menu_icon = sub_menu_icon;
	}

	public String getSub_menu_action_link() {
		return sub_menu_action_link;
	}

	public void setSub_menu_action_link(String sub_menu_action_link) {
		this.sub_menu_action_link = sub_menu_action_link;
	}

	public Rn_Main_Menu getRn_main_menu() {
		return rn_main_menu;
	}

	public void setRn_main_menu(Rn_Main_Menu rn_main_menu) {
		this.rn_main_menu = rn_main_menu;
	}
	

}
