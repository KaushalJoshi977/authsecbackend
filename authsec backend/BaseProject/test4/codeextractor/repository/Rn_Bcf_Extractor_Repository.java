package com.realnet.codeextractor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realnet.codeextractor.entity.Rn_Bcf_Extractor;

@Repository
public interface Rn_Bcf_Extractor_Repository extends JpaRepository<Rn_Bcf_Extractor, Integer> {
	// for pagination
	Page<Rn_Bcf_Extractor> findAll(Pageable p);
}
