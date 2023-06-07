"package com.realnet.entityevents.Repository;" + "\r\n" + 
"" + "\r\n" + 
"import java.util.List;" + "\r\n" + 
"" + "\r\n" + 
"import org.springframework.data.jpa.repository.JpaRepository;" + "\r\n" + 
"import org.springframework.data.jpa.repository.Query;" + "\r\n" + 
"import org.springframework.data.repository.query.Param;" + "\r\n" + 
"import org.springframework.stereotype.Repository;" + "\r\n" + 
"" + "\r\n" + 
"import com.realnet.entityevents.Entity.Entity_events_t;" + "\r\n" + 
"" + "\r\n" + 
"@Repository" + "\r\n" + 
"public interface Entity_eventsRepository extends JpaRepository<Entity_events_t, Long> {" + "\r\n" + 
"" + "\r\n" + 
"	@Query(value = \"SELECT * FROM realnet_CNSBE.entity_events_t WHERE entity_name = :entityName\", nativeQuery = true)" + "\r\n" + 
"    List<Entity_events_t> findByEntityName(@Param(\"entityName\") String entityName);" + "\r\n" + 
"" + "\r\n" + 
"	" + "\r\n" + 
"	" + "\r\n" + 
"" + "\r\n" + 
"	" + "\r\n" + 
"}" + "\r\n" + 
"" 