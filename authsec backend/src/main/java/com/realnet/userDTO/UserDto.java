package com.realnet.userDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class UserDto implements Serializable {
	private static final long serialVersionUID = 1L;
//	private String username;
//	private String password;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private String fullName;
	private String department;
	private int menuGroupId;
	@JsonIgnore
	private String phone;
	@JsonIgnore
	private Set<Role> roles;
	
}
