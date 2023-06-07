package com.realnet.users.entity1;

import lombok.Data;

@Data
public class AppUserDto {
	private Long userId;
	private String username;
	private String userPassw;
	private String title;
	private String shortName;
	private String fullName;
	private String status;
	
	private String positionCodeId;
	private String departmentCodeId;
	private Long usrGrpId;
	private Long customerId;
	private String email;
	private String notification;
	private Long mob_no;
	private boolean active;


}
