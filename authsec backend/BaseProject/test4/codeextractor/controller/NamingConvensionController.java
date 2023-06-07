package com.realnet.codeextractor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.codeextractor.entity.NamingConvension;
import com.realnet.codeextractor.repository.NamingConvension_Repository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { "Naming Convension" })
public class NamingConvensionController {

	@Autowired
	private NamingConvension_Repository namingConvensionRepository;

//	@Autowired
//	private TokenUtil tokenUtil;

	// GET ALL
	@ApiOperation(value = "List of Naming", response = NamingConvension.class)
	@GetMapping("/naming-convension")
	public ResponseEntity<?> getAll() {
		List<NamingConvension> namingConvension = namingConvensionRepository.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(namingConvension);
	}

//	// GET BY ID
//	@ApiOperation(value = "Get a Form", response = NamingConvension.class)
//	@GetMapping("/form_setup/{id}")
//	public ResponseEntity<NamingConvension> getFormsById(@PathVariable(value = "id") int id) {
//		NamingConvension NamingConvension = NamingConvension_service.getById(id);
//		return ResponseEntity.ok().body(NamingConvension);
//	}

//	// SAVE
//	@ApiOperation(value = "naming-convension", response = NamingConvension.class)
//	@PostMapping("/naming-convension")
//	public ResponseEntity<NamingConvension> create(
//			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
//			@Valid @RequestBody NamingConvension namingConvension) {
//		NamingConvension savedNamingConvension = namingConvensionRepository.save(namingConvension);
//		return ResponseEntity.status(HttpStatus.CREATED).body(savedNamingConvension);
//	}

//	// UPDATE
//	@ApiOperation(value = "Update A Form", response = NamingConvension.class)
//	@PutMapping("/form_setup/{id}")
//	public ResponseEntity<NamingConvension> updateNamingConvension(
//			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
//			@PathVariable(value = "id") Integer id, @Valid @RequestBody List<NamingConvension> NamingConvensionRequest) {
//		
//		NamingConvension namingConvension = namingConvensionRepository.findById(id).get();
//		
//	
//		NamingConvension updatedNamingConvension = namingConvensionRepository.update(id, NamingConvension);
//		return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedNamingConvension);
//	}
}
