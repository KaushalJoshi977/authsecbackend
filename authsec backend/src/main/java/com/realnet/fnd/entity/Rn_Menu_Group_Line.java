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
@Table(name = "RN_MENU_GROUP_LINE")
// @Table(name = "rn_menu_group_line_1")
public class Rn_Menu_Group_Line extends Rn_Who_Columns {
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
	
	private String menu_name;
	


	@Column(name = "NAME")
	private String name;

	@Column(name = "ACTIVE")
	private boolean active;


	@Column(name = "SEQ")
	private int seq;

	@Column(name = "TYPE")
	private String type;

	// line
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "menu_group_header_id", nullable = false)
	@JsonBackReference
	private Rn_Menu_Group_Header menu_group_header;

	public Rn_Menu_Group_Line() {
		super();
	}

	public Rn_Menu_Group_Line(int id, String name, boolean active, int menu_id,
			int seq, String type, Rn_Menu_Group_Header menu_group_header) {
		super();
		this.id = id;
		this.name = name;
		this.active = active;
		this.menu_id = menu_id;
		this.seq = seq;
		this.type = type;
		this.menu_group_header = menu_group_header;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}


	public int getMenu_id() {
		return menu_id;
	}

	public void setMenu_id(int menu_id) {
		this.menu_id = menu_id;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Rn_Menu_Group_Header getMenu_group_header() {
		return menu_group_header;
	}

	public void setMenu_group_header(Rn_Menu_Group_Header menu_group_header) {
		this.menu_group_header = menu_group_header;
	}

	public String getMenu_name() {
		return menu_name;
	}

	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	
	
}
