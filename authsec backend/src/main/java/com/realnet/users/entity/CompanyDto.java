package com.realnet.users.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class CompanyDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userEmail; // for load the user who is responsible
	private String companyName;
	private String workspace;
	private String gstNumber;

}
