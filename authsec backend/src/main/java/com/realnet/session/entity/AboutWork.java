package com.realnet.session.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Accounts")
public class AboutWork {
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id ;
	private String name;
	
	private String password;
	private Long mobile;
	private String email;
	private String companyname;
	private String pancard;
	private String working;
	private String managing_work;
//	@OneToOne(mappedBy = "user",fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL)
//	private User user;
}
