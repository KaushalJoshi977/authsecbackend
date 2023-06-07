package com.realnet.userDTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserProfileDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String fullName;
	private String pronounce;
	private String role;
	private String department;
	private String about;
	

}
