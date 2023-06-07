package com.realnet.codeextractor.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realnet.codeextractor.entity.Rn_Bcf_Rules;

@Repository
public interface Rn_Bcf_Rule_Library_Repository extends JpaRepository<Rn_Bcf_Rules, Integer> {
	// for pagination
	Page<Rn_Bcf_Rules> findAll(Pageable p);

//	@Query(value = "SELECT * FROM RN_BCF_EXTRACTOR_PARAMS_T WHERE HEADER_ID =:hId ORDER BY created_at DESC", nativeQuery = true)
//	List<Rn_Bcf_Extractor_Params> findByHeaderIdOrderByDate(@Param("hId") Integer headerId);

//	List<Rn_Bcf_Rules> getByRuleType(String tech_stack, String object_type, String sub_object_type);
//
	@Query(value = "SELECT ID, GROUP_ID, RULE_NAME, ':to_tech_stack', ':to_object_type', ':to_sub_object_type', FILE_CODE, RULE_TYPE, IDENTIFIER_START_STRING, IDENTIFIER_END_STRING, REPLACEMENT_STRING FROM rn_bcf_rules WHERE TECH_STACK =:from_tech_stack AND OBJECT_TYPE =:from_object_type AND SUB_OBJECT_TYPE =:from_sub_object_type", nativeQuery = true)
	public List<Rn_Bcf_Rules> copyRules(
			@Param("to_tech_stack") String to_tech_stack,
			@Param("to_object_type") String to_object_type, 
			@Param("to_sub_object_type") String to_sub_object_type,
			@Param("from_tech_stack") String from_tech_stack, 
			@Param("from_object_type") String from_object_type,
			@Param("from_sub_object_type") String from_sub_object_type);

	@Query(value = "SELECT * FROM rn_bcf_rules WHERE TECH_STACK =:from_tech_stack AND OBJECT_TYPE =:from_object_type AND SUB_OBJECT_TYPE =:from_sub_object_type", nativeQuery = true)
	public List<Rn_Bcf_Rules> copyRules2(@Param("from_tech_stack") String from_tech_stack,
			@Param("from_object_type") String from_object_type,
			@Param("from_sub_object_type") String from_sub_object_type);

}
