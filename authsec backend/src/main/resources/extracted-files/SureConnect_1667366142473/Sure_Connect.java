package com.realnet.SureConnect.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Sure_Connect {
	
	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private int id;
	
	private String connection_name;
	
	@Column(length = 10000)
	private String description;
	
	private String type;
	private String access_token;
	private int client_id;
	private String username;
	private String password;

}
