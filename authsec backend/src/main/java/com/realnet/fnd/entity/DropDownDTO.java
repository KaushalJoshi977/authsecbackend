package com.realnet.fnd.entity;

public class DropDownDTO {
	private int id;
	private String name;
	
	public DropDownDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DropDownDTO(int id, String name) {
		super();
		this.id = id;
		this.name = name;
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

	@Override
	public String toString() {
		return "DropDownDTO [id=" + id + ", name=" + name + "]";
	}
}
