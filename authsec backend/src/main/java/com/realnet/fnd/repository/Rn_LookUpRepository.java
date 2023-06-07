package com.realnet.fnd.repository;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.fnd.entity.Rn_Lookup_Values;

@Repository
public interface Rn_LookUpRepository extends JpaRepository<Rn_Lookup_Values, Integer> {
	// for pagination
		Page<Rn_Lookup_Values> findAll(Pageable p);
		
	@Query(value = "SELECT LOOKUP_CODE FROM RN_LOOKUP_VALUES_T WHERE LOOKUP_TYPE ='Form_ext'" , nativeQuery = true)
	List<String> findLookupValues();
	
	@Query(value = "SELECT LOOKUP_CODE FROM RN_LOOKUP_VALUES_T WHERE LOOKUP_TYPE ='DataType'" , nativeQuery = true)
	List<String> findDataTypeValues();
	
	
	@Query(value = "SELECT * FROM RN_LOOKUP_VALUES_T WHERE LOOKUP_TYPE ='Form_ext'" , nativeQuery = true)
	List<Rn_Lookup_Values> findExtensions();
	
	 
}
