package com.realnet.template.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realnet.template.entity.Dynamic_template;

@Repository
public interface DynamicTempRepo extends JpaRepository<Dynamic_template, Long>{

	
	@Query(value= "select schema_name from information_schema.schemata",nativeQuery = true)
	List<Object> getdatabaseList();

	@Query(value= "SELECT table_name FROM information_schema.tables WHERE table_schema =?1",nativeQuery = true)
	List<String> getListOftables(String table_schema);

	@Query(value= "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE `TABLE_SCHEMA`=:'TABLE_SCHEMA' AND `TABLE_NAME`='TABLE_NAME';",nativeQuery = true)
	List<Object> getallcolumnlist(@Param("TABLE_SCHEMA") Object table_schema, @Param("TABLE_NAME") Object tABLE_NAME);
}
