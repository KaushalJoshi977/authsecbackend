package com.realnet.codeextractor.controller;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.SerializationFeature;



//@JsonIgnoreProperties(ignoreUnknown=true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JacksonFeatures(serializationDisable = {SerializationFeature.FAIL_ON_EMPTY_BEANS})
@JsonSerialize
@RestController
public class FileUploadController {

	@PostMapping("/upload-gb")
	public ResponseEntity<Void> uploadFileGb(@RequestParam("file") MultipartFile file[]){
		
		for(MultipartFile files:file) {
			System.out.println("File name::"+files.getOriginalFilename());
			
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
		
	}
	
	@PostMapping("/upload-string")
	public ResponseEntity<String> uploadFileString(){
		return ResponseEntity.ok("working");
	}
	
}
