package com.realnet.entitybuilder.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.entitybuilder.Services.BuilderService;
import com.realnet.entitybuilder.response.EntityResponse;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping(value = "/entityBuilder", produces = MediaType.APPLICATION_JSON_VALUE)
public class BuilderpullController {

	@Autowired
	private BuilderService builderService;
	
	@GetMapping(value = "/builder/{proj_id}/{java_class}")
	public ResponseEntity<?> Pull_Script(@PathVariable Integer proj_id,@PathVariable String java_class) throws IOException {

		//for git pull and push for builder class
		String workflow_id = "61";

		builderService.CreateFiles(proj_id, workflow_id,java_class);


			return new ResponseEntity<>(new EntityResponse("script created"), HttpStatus.OK);
		}

	


}
