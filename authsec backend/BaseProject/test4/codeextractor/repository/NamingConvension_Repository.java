package com.realnet.codeextractor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realnet.codeextractor.entity.NamingConvension;

@Repository
public interface NamingConvension_Repository extends JpaRepository<NamingConvension, Integer> {
	// for pagination
	Page<NamingConvension> findAll(Pageable p);
}
