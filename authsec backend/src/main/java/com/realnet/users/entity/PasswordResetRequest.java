package com.realnet.users.entity;

import lombok.Data;

@Data
public class PasswordResetRequest {
	
	private Long userId;
	private String email;

	private String oldPassword;
	private String newPassword;
	private  String token;
	private String hash;
	private String confirmPassword;
}
