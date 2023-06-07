package com.realnet.SureConnect.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.SureConnect.Entities.Sure_Connect;

@Repository
public interface SureRepository extends JpaRepository<Sure_Connect, Integer>{

	Sure_Connect findById(int id);

	@Query(value = "select * from sure_connect where connection_name=?1 ", nativeQuery = true)
	Sure_Connect findByConnection_name(String connection_name);
}
