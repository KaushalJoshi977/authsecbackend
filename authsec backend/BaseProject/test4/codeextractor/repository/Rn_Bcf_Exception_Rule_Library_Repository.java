package com.realnet.codeextractor.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realnet.codeextractor.entity.Rn_Bcf_Exception_Rules;

@Repository
public interface Rn_Bcf_Exception_Rule_Library_Repository extends JpaRepository<Rn_Bcf_Exception_Rules, Integer> {
	// for pagination
	Page<Rn_Bcf_Exception_Rules> findAll(Pageable p);
	
	@Query(value = "SELECT * FROM rn_bcf_exception_rules WHERE TECH_STACK =:tech_stack AND OBJECT_TYPE =:object_type AND SUB_OBJECT_TYPE =:sub_object_type", nativeQuery = true)
	List<Rn_Bcf_Exception_Rules> findByType(@Param("tech_stack")String tech_stack, @Param("object_type")String object_type, @Param("sub_object_type")String sub_object_type);
	
	// testing
	//List<Rn_Bcf_Exception_Rules> findByTechStackAndObjectTypeAndSubObjectType(String tech_stack, String object_type, String sub_object_type);
	
//	@Query(value = "SELECT * FROM RN_BCF_EXTRACTOR_PARAMS_T WHERE HEADER_ID =:hId ORDER BY created_at DESC", nativeQuery = true)
//	List<Rn_Bcf_Extractor_Params> findByHeaderIdOrderByDate(@Param("hId") Integer headerId);
}

