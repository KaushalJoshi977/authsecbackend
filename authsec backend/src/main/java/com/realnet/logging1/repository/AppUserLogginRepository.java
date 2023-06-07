package com.realnet.logging1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.logging1.entity.AppUserLog;

@Repository
public interface AppUserLogginRepository extends JpaRepository<AppUserLog,Long>{
	List<AppUserLog> findByUserName(String userName);
//	@Query(value="select Max(*) from app")
//	long getMax();
}
