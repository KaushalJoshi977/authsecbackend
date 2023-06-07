package com.realnet.codeextractor.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.codeextractor.entity.Rn_Bcf_Technology_Stack1;

@Repository
public interface Rn_Bcf_TechnologyStack_Repository1 extends JpaRepository<Rn_Bcf_Technology_Stack1, Integer> {
	// for pagination
	Page<Rn_Bcf_Technology_Stack1> findAll(Pageable p);

	@Query(value = "SELECT * FROM rn_bcf_technology_stack WHERE IS_ACTIVE=1", nativeQuery = true)
	List<Rn_Bcf_Technology_Stack1> activeTechStacks();
	// List<Rn_Bcf_Technology_Stack> findByActive(@Param("status")boolean status);

//	@Procedure("active_technology")
//	List<Rn_Bcf_Technology_Stack> activeTechList();

//	@Query(value = "call active_technology", nativeQuery = true) // call store procedure
//	List<Rn_Bcf_Technology_Stack> activeTechList();

}
