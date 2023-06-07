package com.realnet.entityevents.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realnet.entityevents.Entity.Entity_events_t;

@Repository
public interface Entity_eventsRepository extends JpaRepository<Entity_events_t, Long> {

	@Query(value = "SELECT * FROM realnet_CNSBE.entity_events_t WHERE entity_name = :entityName", nativeQuery = true)
    List<Entity_events_t> findByEntityName(@Param("entityName") String entityName);

	
	

	
}

