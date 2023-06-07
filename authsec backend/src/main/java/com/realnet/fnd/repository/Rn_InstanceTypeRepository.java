package com.realnet.fnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realnet.fnd.entity.Rn_Instance_Type;

@Repository
public interface Rn_InstanceTypeRepository extends JpaRepository<Rn_Instance_Type, Integer> {
	
}
