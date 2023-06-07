package com.realnet.fnd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realnet.fnd.entity.Rn_Dynamic_Transaction;

@Repository
public interface Rn_DynamicTransactionRepository extends JpaRepository<Rn_Dynamic_Transaction, Integer> {

	// JPQL
	// @Query("SELECT e from RN_DYNAMIC_TRANSACTION e where e.FORM_ID =:form_id")
	// @Query("SELECT e from RN_DYNAMIC_TRANSACTION e where e.FORM_ID =:form_id AND e.ID =:id")

	// NATIVE
	// @Query(value = "SELECT * FROM RN_DYNAMIC_TRANSACTION WHERE FORM_ID =:form_id", nativeQuery = true)
	// @Query(value = "SELECT * FROM RN_DYNAMIC_TRANSACTION WHERE ID =:id ANDFORM_ID =:form_id" , nativeQuery = true)

	@Query(value = "SELECT * FROM rn_dynamic_transaction WHERE FORM_ID =:form_id", nativeQuery = true)
	List<Rn_Dynamic_Transaction> findByFormId(@Param("form_id") int form_id);

	@Query(value = "SELECT * FROM rn_dynamic_transaction WHERE ID =:id AND FORM_ID =:form_id", nativeQuery = true)
	Optional<Rn_Dynamic_Transaction> findByIdAndFormId(@Param("id") int id, @Param("form_id") int form_id);

}
