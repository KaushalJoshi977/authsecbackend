package com.realnet.users.entity1;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Registration {
	
	private String first_name;

	private String last_name;

	private Long mob_no;
	
	private String email;

	@NotBlank
	@Size(min = 6, max = 40)
	private String new_password;
	
	private Long account_id;
	
	private Long usrGrpId;

	private String confirm_passwordS;
	private String accesstype;
}
