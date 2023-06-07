package com.realnet.fnd.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realnet.fnd.entity.Rn_Main_Menu;

@Repository
public interface Rn_Main_Menu_Repository extends JpaRepository<Rn_Main_Menu, Integer> {
	// for pagination
	Page<Rn_Main_Menu> findAll(Pageable p);
	
	@Query(value = "SELECT * FROM RN_MAIN_MENU WHERE ACCOUNT_ID =:acc_id" , nativeQuery = true)
	List<Rn_Main_Menu> findByAccountId(@Param("acc_id") String account_id); // accountId
}
