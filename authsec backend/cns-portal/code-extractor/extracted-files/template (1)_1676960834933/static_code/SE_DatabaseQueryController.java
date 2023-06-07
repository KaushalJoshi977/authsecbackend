"package com.realnet.template.controller;" + "\r\n" + 
"" + "\r\n" + 
"import java.sql.Connection;" + "\r\n" + 
"import java.sql.DriverManager;" + "\r\n" + 
"import java.sql.SQLException;" + "\r\n" + 
"import java.sql.Statement;" + "\r\n" + 
"import java.util.ArrayList;" + "\r\n" + 
"import java.util.List;" + "\r\n" + 
"" + "\r\n" + 
"import org.springframework.beans.factory.annotation.Autowired;" + "\r\n" + 
"import org.springframework.beans.factory.annotation.Value;" + "\r\n" + 
"import org.springframework.web.bind.annotation.GetMapping;" + "\r\n" + 
"import org.springframework.web.bind.annotation.PathVariable;" + "\r\n" + 
"import org.springframework.web.bind.annotation.RequestMapping;" + "\r\n" + 
"import org.springframework.web.bind.annotation.ResponseBody;" + "\r\n" + 
"import org.springframework.web.bind.annotation.RestController;" + "\r\n" + 
"" + "\r\n" + 
"import com.realnet.template.repository.DynamicTempRepo;" + "\r\n" + 
"import com.realnet.template.service.DatabaseQueryService;" + "\r\n" + 
"" + "\r\n" + 
"@RestController" + "\r\n" + 
"@RequestMapping(\"/sureserve/template/databasequery\")" + "\r\n" + 
"public class DatabaseQueryController {" + "\r\n" + 
"" + "\r\n" + 
"	@Autowired" + "\r\n" + 
"	private DatabaseQueryService rn_table_service;" + "\r\n" + 
"	@Autowired" + "\r\n" + 
"	private DynamicTempRepo tempRepo;" + "\r\n" + 
"" + "\r\n" + 
"	@Value(\"${spring.datasource.username}\")" + "\r\n" + 
"	private String userName;" + "\r\n" + 
"" + "\r\n" + 
"	@Value(\"${spring.datasource.password}\")" + "\r\n" + 
"	private String password;" + "\r\n" + 
"" + "\r\n" + 
"	@Value(\"${spring.datasource.url}\")" + "\r\n" + 
"	private String url;" + "\r\n" + 
"" + "\r\n" + 
"// get all databse list available" + "\r\n" + 
"	@GetMapping(\"/Table_list\")" + "\r\n" + 
"	@ResponseBody" + "\r\n" + 
"	public List<Object> getdatabase() {" + "\r\n" + 
"		List<Object> list = tempRepo.getdatabaseList();" + "\r\n" + 
"		return list;" + "\r\n" + 
"	}" + "\r\n" + 
"" + "\r\n" + 
"	// get all table list available" + "\r\n" + 
"	@GetMapping(\"/Table_list_all\")" + "\r\n" + 
"	@ResponseBody" + "\r\n" + 
"	public List<String> gettableList()" + "\r\n" + 
"" + "\r\n" + 
"//			@PathVariable String table_schema) " + "\r\n" + 
"	{" + "\r\n" + 
"//		List<String> list = tempRepo.getListOftables(table_schema);" + "\r\n" + 
"		List<String> list = rn_table_service.getListOftable(\"sureserve\");" + "\r\n" + 
"		return list;" + "\r\n" + 
"	}" + "\r\n" + 
"" + "\r\n" + 
"	// get all column list available" + "\r\n" + 
"	@GetMapping(\"/Table_list/{table_schema}/{TABLE_NAME}\")" + "\r\n" + 
"	@ResponseBody" + "\r\n" + 
"	public List<String> getallcolumnlist(@PathVariable String table_schema, @PathVariable String TABLE_NAME) {" + "\r\n" + 
"		List<String> list = rn_table_service.getColumnAliasList1(table_schema, TABLE_NAME);" + "\r\n" + 
"		return list;" + "\r\n" + 
"	}" + "\r\n" + 
"" + "\r\n" + 
"	// create database" + "\r\n" + 
"	@GetMapping(\"/createdatabase/{table_schema}\")" + "\r\n" + 
"	@ResponseBody" + "\r\n" + 
"	public List<Integer> createdatabase(@PathVariable String table_schema) {" + "\r\n" + 
"		List<Integer> list = rn_table_service.createdatabase(table_schema);" + "\r\n" + 
"		return list;" + "\r\n" + 
"	}" + "\r\n" + 
"" + "\r\n" + 
"}" 