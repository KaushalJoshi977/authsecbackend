package com.realnet.entitybuilder.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class frontendtable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	public frontendtable(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public frontendtable() {
		super();
		// TODO Auto-generated constructor stub
	}

}
