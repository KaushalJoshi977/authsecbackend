package com.realnet.Connector.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.Connector.Entity.Connector;
@Repository
public interface Connectorrepository extends JpaRepository<Connector, Long>{

	
	@Query(value = "select * from connector where name=?1", nativeQuery = true)
	Connector findconnectorbyname(String name);

}
