package com.realnet.users.entity1;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignUp {

	private String first_name;

	private String last_name;

	private Long mob_no;

//	  private Set<String> role;
	@NotBlank
	@Size(min = 6, max = 40)
	private String password;

	private String confirm_passwordS;

}
