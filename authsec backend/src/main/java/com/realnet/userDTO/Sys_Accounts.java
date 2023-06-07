package com.realnet.userDTO;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(exclude="users")
@EqualsAndHashCode(callSuper=false)
public class Sys_Accounts {
	private Long id;

	private String companyName;

	private String workspace;

	private String gstNumber;
	
	private List<User> users;

}
