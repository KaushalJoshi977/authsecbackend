package com.realnet.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.realnet.users.entity.Sys_Accounts;

public interface CompanyRepo extends JpaRepository<Sys_Accounts, Long> {
	Sys_Accounts findByCompanyName(String company_name);

}
