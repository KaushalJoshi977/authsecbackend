package com.realnet.users.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.realnet.fnd.entity.Rn_Who_Columns;

import lombok.Data;


@Entity
public class Email  extends Rn_Who_Columns {
	
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private String email1;
	private String email2;
	private String email3;
	private String email4;

	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
//	private String password;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "ACCOUNT_ID")
//	@JsonBackReference
//	private Sys_Accounts sys_account;
	
//public String getPassword() {
//		return password;
//	}
//	public void setPassword(String password) {
//		this.password = password;
//	}
	//	public Sys_Accounts getSys_account() {
//		return sys_account;
//	}
//	public void setSys_account(Sys_Accounts sys_account) {
//		this.sys_account = sys_account;
//	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getEmail1() {
		return email1;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	public String getEmail3() {
		return email3;
	}
	public void setEmail3(String email3) {
		this.email3 = email3;
	}
	public String getEmail4() {
		return email4;
	}
	public void setEmail4(String email4) {
		this.email4 = email4;
	}


}
