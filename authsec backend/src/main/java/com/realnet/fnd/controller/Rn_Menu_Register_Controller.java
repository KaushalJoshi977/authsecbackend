package com.realnet.fnd.controller;

import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.CrossOrigin;
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

import com.realnet.fnd.entity.Rn_Menu_Register;
import com.realnet.fnd.response.Rn_Menu_Register_Response;
import com.realnet.fnd.service.Rn_Menu_Register_Service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { "Menu Register" })
@CrossOrigin("*")
public class Rn_Menu_Register_Controller {

	@Autowired
	private Rn_Menu_Register_Service rn_menu_register_service;

//	@Autowired
//	private TokenUtil tokenUtil;

	// GET ALL
	@ApiOperation(value = "List of Menu", response = Rn_Menu_Register_Response.class)
	@GetMapping("/menu-register")
	public Rn_Menu_Register_Response getRegisteredMenu(
			@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
			@RequestParam(value = "size", defaultValue = "20", required = false) Integer size) {

		// SORTED DATA
		// Pageable paging = PageRequest.of(page, size,
		// Sort.by("createdAt").descending());

		Pageable paging = PageRequest.of(page, size);
		Page<Rn_Menu_Register> result = rn_menu_register_service.getAll(paging);
		Rn_Menu_Register_Response resp = new Rn_Menu_Register_Response();
		resp.setPageStats(result, true);
		resp.setItems(result.getContent());
		return resp;
	}

	// GET BY ID
	@ApiOperation(value = "Get a Menu", response = Rn_Menu_Register.class)
	@GetMapping("/menu-register/{id}")
	public ResponseEntity<Rn_Menu_Register> getRegisteredMenuById(@PathVariable(value = "id") Integer id) {
		Rn_Menu_Register rn_menu_register = rn_menu_register_service.getById(id);
		return ResponseEntity.ok().body(rn_menu_register);
	}

	// getByAccountId
	// *** NEED MODIFICATION ***
	// GET BY ACCOUNT_ID
	@ApiOperation(value = "Get Menu By Accout Id", response = Rn_Menu_Register.class)
	@GetMapping("/menu-register/user-menu")
	public ResponseEntity<List<Rn_Menu_Register>> getRegisteredMenuByAccountId(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken) {
//		String accountId = tokenUtil.getUserId(authToken);
//		System.out.println("get Menu List by Account Id --> " + accountId);
		// ** NEED TO MODIFY **
//		List<Rn_Menu_Register> rn_menu_register = rn_menu_register_service.getByAccountId(accountId);
		List<Rn_Menu_Register> rn_menu_register = rn_menu_register_service.getAll();
		return ResponseEntity.ok().body(rn_menu_register);
	}

	// SAVE
	@ApiOperation(value = "Add A New Menu", response = Rn_Menu_Register.class)
	@PostMapping("/menu-register")
	public ResponseEntity<Rn_Menu_Register> SaveMenuRegister(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
			@Valid @RequestBody Rn_Menu_Register rn_menu_register) {
//		String userId = tokenUtil.getUserId(authToken);
//		rn_menu_register.setCreatedBy(userId);
//		rn_menu_register.setAccountId(userId);
		Rn_Menu_Register savedRn_Forms_Setup = rn_menu_register_service.save(rn_menu_register);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedRn_Forms_Setup);
	}

	// UPDATE
	@ApiOperation(value = "Update A Menu", response = Rn_Menu_Register.class)
	@PutMapping("/menu-register/{id}")
	public ResponseEntity<Rn_Menu_Register> updateMenu(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
			@PathVariable(value = "id") Integer id, @Valid @RequestBody Rn_Menu_Register rn_menu_register) {
		// String userId = tokenUtil.getUserId(authToken);
		// rn_menu_register.setUpdatedBy(userId);
		Rn_Menu_Register updatedMenu = rn_menu_register_service.updateById(id, rn_menu_register);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedMenu);
	}

	// DELETE
	@DeleteMapping("/menu-register/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteRn_Forms_Setup(@PathVariable(value = "id") Integer id) {
		boolean deleted = rn_menu_register_service.deleteById(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		// response.put("deleted", deleted);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
