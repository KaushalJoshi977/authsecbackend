package com.realnet.fnd.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "RN_MAIN_MENU")
public class Rn_Main_Menu extends Rn_Who_Columns {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "MENU_NAME")
	private String menu_name;

	@Column(name = "MENU_ACTION_LINK")
	private String menu_action_link;

	@Column(name = "MENU_ICON")
	private String menu_icon;

	@Column(name = "MENU_TYPE")
	private String menu_type;

	@OneToMany(mappedBy = "rn_main_menu", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Rn_Sub_Menu> sub_menus;

	public Rn_Main_Menu() {
		super();
	}

	public Rn_Main_Menu(Integer id, String menu_name, String menu_action_link, String menu_icon, String menu_type,
			List<Rn_Sub_Menu> sub_menus) {
		super();
		this.id = id;
		this.menu_name = menu_name;
		this.menu_action_link = menu_action_link;
		this.menu_icon = menu_icon;
		this.menu_type = menu_type;
		this.sub_menus = sub_menus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMenu_name() {
		return menu_name;
	}

	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}

	public String getMenu_action_link() {
		return menu_action_link;
	}

	public void setMenu_action_link(String menu_action_link) {
		this.menu_action_link = menu_action_link;
	}

	public String getMenu_icon() {
		return menu_icon;
	}

	public void setMenu_icon(String menu_icon) {
		this.menu_icon = menu_icon;
	}

	public String getMenu_type() {
		return menu_type;
	}

	public void setMenu_type(String menu_type) {
		this.menu_type = menu_type;
	}

	public List<Rn_Sub_Menu> getSub_menus() {
		return sub_menus;
	}

	public void setSub_menus(List<Rn_Sub_Menu> sub_menus) {
		this.sub_menus = sub_menus;
	}
	
}
