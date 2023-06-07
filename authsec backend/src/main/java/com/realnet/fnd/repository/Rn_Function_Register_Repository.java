package com.realnet.fnd.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realnet.fnd.entity.Rn_Function_Register;

@Repository
public interface Rn_Function_Register_Repository extends JpaRepository<Rn_Function_Register, Integer> {
	// for pagination
	Page<Rn_Function_Register> findAll(Pageable p);
	
	@Query(value = "SELECT * FROM RN_FUNC_REGISTER WHERE MENU_ID =:menu_id" , nativeQuery = true)
	List<Rn_Function_Register> findByMenuId(@Param("menu_id") int menu_id);
	
}
