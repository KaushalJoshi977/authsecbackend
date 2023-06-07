package com.realnet.codeextractor.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realnet.codeextractor.entity.Rn_Bcf_Extractor_Params;

@Repository
public interface Rn_Bcf_Extractor_Params_Repository extends JpaRepository<Rn_Bcf_Extractor_Params, Integer> {
	// for pagination
	Page<Rn_Bcf_Extractor_Params> findAll(Pageable p);

	// need modification 
	@Query(value = "SELECT * FROM rn_bcf_extractor_params_t WHERE ACCOUNT_ID =:accId", nativeQuery = true)
	List<Rn_Bcf_Extractor_Params> findByAccountId(@Param("accId") Integer id, Pageable pageable);
	
	@Query(value = "SELECT * FROM rn_bcf_extractor_params_t WHERE HEADER_ID =:hId", nativeQuery = true)
	List<Rn_Bcf_Extractor_Params> findByHeaderId(@Param("hId") Integer headerId);
	
	//@Query(value = "SELECT * FROM RN_BCF_EXTRACTOR_PARAMS_T WHERE HEADER_ID =:hId ORDER BY created_at DESC", nativeQuery = true)
	@Query(value = "SELECT * FROM rn_bcf_extractor_params_t WHERE HEADER_ID =:hId ORDER BY created_at DESC", nativeQuery = true)
	List<Rn_Bcf_Extractor_Params> findByHeaderIdOrderByDate(@Param("hId") Integer headerId);
}

