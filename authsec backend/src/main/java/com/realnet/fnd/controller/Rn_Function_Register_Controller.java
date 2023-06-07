package com.realnet.fnd.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.fnd.entity.Rn_Function_Register;
import com.realnet.fnd.response.Rn_Function_Register_Response;
import com.realnet.fnd.service.Rn_Function_Register_Service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { "Function Register" })
public class Rn_Function_Register_Controller {

	@Autowired
	private Rn_Function_Register_Service rn_menu_register_service;

//	@Autowired
//	private TokenUtil tokenUtil;

	// GET ALL
	@ApiOperation(value = "List of Functions", response = Rn_Function_Register_Response.class)
	@GetMapping("/function-register")
	public Rn_Function_Register_Response getFunctions(
			@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
			@RequestParam(value = "size", defaultValue = "20", required = false) Integer size) {
		
		// SORTED DATA
		//Pageable paging = PageRequest.of(page, size, Sort.by("createdAt").descending());
		
		Pageable paging = PageRequest.of(page, size);
		Page<Rn_Function_Register> result = rn_menu_register_service.getAll(paging);
		Rn_Function_Register_Response resp = new Rn_Function_Register_Response();
		resp.setPageStats(result, true);
		resp.setItems(result.getContent());
		return resp;
	}

	// GET BY ID
	@ApiOperation(value = "Get a Function", response = Rn_Function_Register.class)
	@GetMapping("/function-register/{id}")
	public ResponseEntity<Rn_Function_Register> getFunctionById(@PathVariable(value = "id") Integer id) {
		Rn_Function_Register rn_function_register = rn_menu_register_service.getById(id);
		return ResponseEntity.ok().body(rn_function_register);
	}

	// SAVE
	@ApiOperation(value = "Add A New Function", response = Rn_Function_Register.class)
	@PostMapping("/function-register")
	public ResponseEntity<Rn_Function_Register> createFunction(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
			@Valid @RequestBody Rn_Function_Register rn_function_register) {
//		String userId = tokenUtil.getUserId(authToken);
//		rn_function_register.setCreatedBy(userId);
//		rn_function_register.setAccountId(userId);
		Rn_Function_Register savedFunction = rn_menu_register_service.save(rn_function_register);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedFunction);
	}

	// UPDATE
	@ApiOperation(value = "Update A Function", response = Rn_Function_Register.class)
	@PutMapping("/function-register/{id}")
	public ResponseEntity<Rn_Function_Register> updateFunction(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
			@PathVariable(value = "id") Integer id, @Valid @RequestBody Rn_Function_Register rn_function_register) {
//		String userId = tokenUtil.getUserId(authToken);
//		rn_function_register.setUpdatedBy(userId);
		Rn_Function_Register updatedFunction = rn_menu_register_service.updateById(id, rn_function_register);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedFunction);
	}

	// DELETE
	@DeleteMapping("/function-register/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteFunction(@PathVariable(value = "id") Integer id) {
		boolean deleted = rn_menu_register_service.deleteById(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		// response.put("deleted", deleted);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
