package com.realnet.fnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realnet.fnd.entity.Rn_Menu_Group_Header;

@Repository
public interface Rn_Menu_Group_Repository extends JpaRepository<Rn_Menu_Group_Header, Long> {

	// JPQL: @Query("SELECT e from RN_FORMS_SETUP_T e where e.FORM_ID =:form_id")
	// NATIVE : @Query(value = "SELECT * FROM RN_FORMS_SETUP_T WHERE FORM_ID=:form_id" , nativeQuery = true)

//	@Query(value = "SELECT * FROM RN_FORMS_SETUP_T WHERE FORM_ID=:form_id", nativeQuery = true)
//	List<Rn_Forms_Setup> findByFormId(@Param("form_id") int form_id);

}
