package com.realnet.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realnet.users.entity.Sys_Accounts;

@Repository
public interface SysAccountRepo extends JpaRepository<Sys_Accounts,Long > {
	
	Sys_Accounts findByEmail(String email);

}
