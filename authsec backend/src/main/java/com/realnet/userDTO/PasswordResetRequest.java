package com.realnet.userDTO;

import lombok.Data;

@Data
public class PasswordResetRequest {
	private String oldPassword;
	private String newPassword;
}
