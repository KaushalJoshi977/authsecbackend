package com.realnet.fnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realnet.fnd.entity.Rn_Forms_Setup;

@Repository
public interface Rn_Forms_Setup_Repository extends JpaRepository<Rn_Forms_Setup, Integer> {

	// JPQL: @Query("SELECT e from RN_FORMS_SETUP_T e where e.FORM_ID =:form_id")
	// NATIVE : @Query(value = "SELECT * FROM RN_FORMS_SETUP_T WHERE FORM_ID=:form_id" , nativeQuery = true)

	@Query(value = "SELECT * FROM RN_FORMS_SETUP_T WHERE FORM_ID=:form_id", nativeQuery = true)
	List<Rn_Forms_Setup> findByFormId(@Param("form_id") int form_id);

	// @Query(value = "SELECT * FROM RN_DYNAMIC_TRANSACTION WHERE FORM_ID=:form_id",
	// nativeQuery = true)
	// Optional<Rn_Forms_Setup> findByFormId(@Param("form_id") int form_id);
}
