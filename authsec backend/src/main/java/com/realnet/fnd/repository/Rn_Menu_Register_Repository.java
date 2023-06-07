package com.realnet.fnd.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realnet.fnd.entity.Rn_Menu_Register;

@Repository
public interface Rn_Menu_Register_Repository extends JpaRepository<Rn_Menu_Register, Integer> {
	// for pagination
	Page<Rn_Menu_Register> findAll(Pageable p);
	
	@Query(value = "SELECT * FROM RN_MENU_REGISTER WHERE ACCOUNT_ID =:acc_id" , nativeQuery = true)
	List<Rn_Menu_Register> findByAccountId(@Param("acc_id") String account_id); // accountId
}
