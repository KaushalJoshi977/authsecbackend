package com.realnet.users.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.realnet.users.entity1.AppUser;

import lombok.Data;
@Data
@Entity
public class PasswordResetToken {
	
	 private static final int EXPIRATION = 60 * 24;
	 
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;
	 
	    private String token;
	 
	    @OneToOne(targetEntity = AppUser.class, fetch = FetchType.EAGER)
//	    @JoinColumn(nullable = false, name = "user_id")
	    private AppUser user;
	 
	    private Date expiryDate;

		public PasswordResetToken(  AppUser user,String token) {
			super();
			this.token = token;
			this.user = user;
		}
		public PasswordResetToken(  String token) {
			super();
			this.token = token;
//			this.user = user;
		}

		public PasswordResetToken() {
			super();
			// TODO Auto-generated constructor stub
		}

		
	    

}
