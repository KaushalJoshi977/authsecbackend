"package com.realnet.template.repository;" + "\r\n" + 
"" + "\r\n" + 
"import java.util.List;" + "\r\n" + 
"" + "\r\n" + 
"import org.springframework.data.jpa.repository.JpaRepository;" + "\r\n" + 
"import org.springframework.data.jpa.repository.Query;" + "\r\n" + 
"import org.springframework.data.repository.query.Param;" + "\r\n" + 
"import org.springframework.stereotype.Repository;" + "\r\n" + 
"" + "\r\n" + 
"import com.realnet.template.entity.Dynamic_template;" + "\r\n" + 
"" + "\r\n" + 
"@Repository" + "\r\n" + 
"public interface DynamicTempRepo extends JpaRepository<Dynamic_template, Long>{" + "\r\n" + 
"" + "\r\n" + 
"	" + "\r\n" + 
"	@Query(value= \"select schema_name from information_schema.schemata\",nativeQuery = true)" + "\r\n" + 
"	List<Object> getdatabaseList();" + "\r\n" + 
"" + "\r\n" + 
"	@Query(value= \"SELECT table_name FROM information_schema.tables WHERE table_schema =?1\",nativeQuery = true)" + "\r\n" + 
"	List<String> getListOftables(String table_schema);" + "\r\n" + 
"" + "\r\n" + 
"	@Query(value= \"SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE `TABLE_SCHEMA`=:'TABLE_SCHEMA' AND `TABLE_NAME`='TABLE_NAME';\",nativeQuery = true)" + "\r\n" + 
"	List<Object> getallcolumnlist(@Param(\"TABLE_SCHEMA\") Object table_schema, @Param(\"TABLE_NAME\") Object tABLE_NAME);" + "\r\n" + 
"}" 