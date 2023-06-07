package com.realnet.template.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.template.repository.DynamicTempRepo;
import com.realnet.template.service.DatabaseQueryService;

@RestController
@RequestMapping("/sureserve/template/databasequery")
public class DatabaseQueryController {

	@Autowired
	private DatabaseQueryService rn_table_service;
	@Autowired
	private DynamicTempRepo tempRepo;

	@Value("${spring.datasource.username}")
	private String userName;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.datasource.url}")
	private String url;

// get all databse list available
	@GetMapping("/Table_list")
	@ResponseBody
	public List<Object> getdatabase() {
		List<Object> list = tempRepo.getdatabaseList();
		return list;
	}

	// get all table list available
	@GetMapping("/Table_list_all")
	@ResponseBody
	public List<String> gettableList()

//			@PathVariable String table_schema) 
	{
//		List<String> list = tempRepo.getListOftables(table_schema);
		List<String> list = rn_table_service.getListOftable("sureserve");
		return list;
	}

	// get all column list available
	@GetMapping("/Table_list/{table_schema}/{TABLE_NAME}")
	@ResponseBody
	public List<String> getallcolumnlist(@PathVariable String table_schema, @PathVariable String TABLE_NAME) {
		List<String> list = rn_table_service.getColumnAliasList1(table_schema, TABLE_NAME);
		return list;
	}

	// create database
	@GetMapping("/createdatabase/{table_schema}")
	@ResponseBody
	public List<Integer> createdatabase(@PathVariable String table_schema) {
		List<Integer> list = rn_table_service.createdatabase(table_schema);
		return list;
	}

}