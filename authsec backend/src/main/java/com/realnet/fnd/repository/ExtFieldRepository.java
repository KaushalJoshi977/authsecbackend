package com.realnet.fnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realnet.fnd.entity.Rn_Ext_Fields;

@Repository
public interface ExtFieldRepository extends JpaRepository<Rn_Ext_Fields, Integer> {
	
	@Query(value= "SELECT * FROM rn_ext_field_t WHERE ACCOUNT_ID=:acc_id AND FORM_CODE=:f_code" , nativeQuery = true)
	List<Rn_Ext_Fields> getExtensionFieldByFormCode(@Param("acc_id")String accountId, @Param("f_code") String form_code);
}
