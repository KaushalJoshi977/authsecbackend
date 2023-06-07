package com.realnet.users.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.realnet.users.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
	// for pagination
	Page<Role> findAll(Pageable p);
	Role findByName(String name);
	

}
