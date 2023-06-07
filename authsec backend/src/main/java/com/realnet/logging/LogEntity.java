package com.realnet.logging;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name= "LOGS")
public class LogEntity {
	
	@Id
	@Column(name="USER_ID")
	private Long userId;
	
	@Column(name="DATED")
	private String dated;
	
	@Column(name="LOGGER")
	private String logger;
	
	@Column(name="LAVEL")
	private String lavel;
	
	@Column(name="MESSAGE")
	private String message;
	
}
