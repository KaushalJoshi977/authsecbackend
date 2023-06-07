package com.realnet.entitybuilder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.entitybuilder.entity.frontendtable;
import com.realnet.entitybuilder.repo.FrontendRepo;
@RestController
@RequestMapping("/entityBuilder/frontendtable")
public class getalltable {
	@Autowired
	private FrontendRepo front;
	
	@GetMapping("/getall")
	public List<frontendtable> getdetails() {
		 List<frontendtable> get = front.findAll();		
		return get;
}
	
	@PostMapping("/save")
	
	  public frontendtable Savedata(@RequestBody frontendtable data) {
		frontendtable save = front.save(data)	;
		 return save;
	  }
}
