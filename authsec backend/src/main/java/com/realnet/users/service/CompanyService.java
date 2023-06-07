package com.realnet.users.service;

import java.util.List;

import com.realnet.users.entity.CompanyDto;
import com.realnet.users.entity.Sys_Accounts;

public interface CompanyService {
	// creating new company
	Sys_Accounts companyResister(CompanyDto company);

	public boolean insertOrSaveCompany(Sys_Accounts company);
	// company registration

	List<Sys_Accounts> getAll();

	public Sys_Accounts getById(Long id);

	void delete(long id);

}
