package com.realnet.Connector.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Column;

import com.realnet.Dashboard1.Entity.dashbord_Who_collumn;

import lombok.Data;

@Entity
@Data
public class Connector extends dashbord_Who_collumn {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	@Column(length = 15000)
	private String get_str;
	@Lob
	private String connector_json;

}
