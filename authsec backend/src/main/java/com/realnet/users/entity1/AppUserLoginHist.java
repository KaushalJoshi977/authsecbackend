package com.realnet.users.entity1;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="USERLOGINHIST")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppUserLoginHist implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@OneToOne
	@JoinColumn(name="user_id")
	private AppUser userId;
	private Date lastLoginDate;
	private Date lastPasswordChgDate;
	private Long lastPasswordFailNo;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;
	private Long expiryReminder;
}
