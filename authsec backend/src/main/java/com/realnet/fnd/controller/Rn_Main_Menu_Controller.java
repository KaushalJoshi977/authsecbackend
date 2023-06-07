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

import com.realnet.fnd.entity.Rn_Main_Menu;
import com.realnet.fnd.response.Rn_Main_Menu_Response;
import com.realnet.fnd.service.Rn_Main_Menu_Service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { "RealNet Menu" })
public class Rn_Main_Menu_Controller {

	@Autowired
	private Rn_Main_Menu_Service rn_main_menu_service;

//	@Autowired
//	private TokenUtil tokenUtil;

	// GET ALL
	@ApiOperation(value = "List of Menu", response = Rn_Main_Menu_Response.class)
	@GetMapping("/realnet-menu")
	public Rn_Main_Menu_Response getMenus(
			@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
			@RequestParam(value = "size", defaultValue = "20", required = false) Integer size) {

		// SORTED DATA : PageRequest.of(page, size,Sort.by("createdAt").descending());
		// UN-SORTED DATA : PageRequest.of(page, size);
		
		Pageable paging = PageRequest.of(page, size);
		Page<Rn_Main_Menu> result = rn_main_menu_service.getAll(paging);
		Rn_Main_Menu_Response resp = new Rn_Main_Menu_Response();
		resp.setPageStats(result, true);
		resp.setItems(result.getContent());
		return resp;
	}

	// GET BY ID
	@ApiOperation(value = "Get a Menu", response = Rn_Main_Menu.class)
	@GetMapping("/realnet-menu/{id}")
	public ResponseEntity<Rn_Main_Menu> getMenuById(@PathVariable(value = "id") Integer id) {
		Rn_Main_Menu rn_menu_register = rn_main_menu_service.getById(id);
		return ResponseEntity.ok().body(rn_menu_register);
	}

	// getByAccountId
	// *** NEED MODIFICATION ***
	// GET BY ACCOUNT_ID
	@ApiOperation(value = "Get Menu By Accout Id", response = Rn_Main_Menu.class)
	@GetMapping("/realnet-menu/user-menu")
	public ResponseEntity<List<Rn_Main_Menu>> getMenuByAccountId(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken) {
//		String accountId = tokenUtil.getUserId(authToken);
//		System.out.println("get Menu List by Account Id --> " + accountId);
		//List<Rn_Main_Menu> rn_menu_register = rn_main_menu_service.getByAccountId(accountId);
		List<Rn_Main_Menu> rn_menu_register = rn_main_menu_service.getAll();
		return ResponseEntity.ok().body(rn_menu_register);
	}

	// SAVE
	@ApiOperation(value = "Add A New Menu", response = Rn_Main_Menu.class)
	@PostMapping("/realnet-menu")
	public ResponseEntity<Rn_Main_Menu> createMenu(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
			@Valid @RequestBody Rn_Main_Menu rn_main_menu) {
//		String userId = tokenUtil.getUserId(authToken);
//		rn_main_menu.setCreatedBy(userId);
//		rn_main_menu.setAccountId(userId);
		Rn_Main_Menu savedMenu = rn_main_menu_service.save(rn_main_menu);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedMenu);
	}

	// UPDATE
	@ApiOperation(value = "Update A Menu", response = Rn_Main_Menu.class)
	@PutMapping("/realnet-menu/{id}")
	public ResponseEntity<Rn_Main_Menu> updateMenu(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
			@PathVariable(value = "id") Integer id, @Valid @RequestBody Rn_Main_Menu rn_main_menu) {
//		String userId = tokenUtil.getUserId(authToken);
//		rn_main_menu.setUpdatedBy(userId);
		Rn_Main_Menu updatedMenu = rn_main_menu_service.updateById(id, rn_main_menu);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedMenu);
	}

	// DELETE
	@DeleteMapping("/realnet-menu/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteMenu(@PathVariable(value = "id") Integer id) {
		boolean deleted = rn_main_menu_service.deleteById(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		// response.put("deleted", deleted);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
