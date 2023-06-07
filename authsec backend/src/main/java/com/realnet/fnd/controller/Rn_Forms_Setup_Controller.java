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

import com.realnet.fnd.entity.Rn_Forms_Setup;
import com.realnet.fnd.response.Rn_Forms_SetupResponse;
import com.realnet.fnd.service.Rn_Forms_Setup_Service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { "Rn_Forms_Setup" })
public class Rn_Forms_Setup_Controller {

	@Autowired
	private Rn_Forms_Setup_Service rn_forms_setup_service;

//	@Autowired
//	private TokenUtil tokenUtil;

	// GET ALL
	@ApiOperation(value = "List of Forms", response = Rn_Forms_SetupResponse.class)
	@GetMapping("/form_setup")
	public Rn_Forms_SetupResponse getForms(
			@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
			@RequestParam(value = "size", defaultValue = "20", required = false) Integer size) {
		Rn_Forms_SetupResponse resp = new Rn_Forms_SetupResponse();
		Pageable paging = PageRequest.of(page, size);
		Page<Rn_Forms_Setup> result = rn_forms_setup_service.getAll(paging);
		resp.setPageStats(result, true);
		resp.setItems(result.getContent());
		return resp;
	}

	// GET BY ID
	@ApiOperation(value = "Get a Form", response = Rn_Forms_Setup.class)
	@GetMapping("/form_setup/{id}")
	public ResponseEntity<Rn_Forms_Setup> getFormsById(@PathVariable(value = "id") int id) {
		Rn_Forms_Setup rn_forms_setup = rn_forms_setup_service.getById(id);
		return ResponseEntity.ok().body(rn_forms_setup);
	}

	// SAVE
	@ApiOperation(value = "Add A New Form", response = Rn_Forms_Setup.class)
	@PostMapping("/form_setup")
	public ResponseEntity<Rn_Forms_Setup> createRn_Forms_Setup(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
			@Valid @RequestBody Rn_Forms_Setup rn_forms_setup) {
//		String userId = tokenUtil.getUserId(authToken);
//		rn_forms_setup.setCreatedBy(userId);
//		rn_forms_setup.setAccountId(userId);
		Rn_Forms_Setup savedRn_Forms_Setup = rn_forms_setup_service.save(rn_forms_setup);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedRn_Forms_Setup);
	}

	// UPDATE
	@ApiOperation(value = "Update A Form", response = Rn_Forms_Setup.class)
	@PutMapping("/form_setup/{id}")
	public ResponseEntity<Rn_Forms_Setup> updateRn_Forms_Setup(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
			@PathVariable(value = "id") Integer id, @Valid @RequestBody Rn_Forms_Setup rn_forms_setup) {
//		String userId = tokenUtil.getUserId(authToken);
//		rn_forms_setup.setUpdatedBy(userId);
		Rn_Forms_Setup updatedRn_Forms_Setup = rn_forms_setup_service.updateById(id, rn_forms_setup);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedRn_Forms_Setup);
	}

	// DELETE
	@DeleteMapping("/form_setup/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteRn_Forms_Setup(@PathVariable(value = "id") Integer id) {
		boolean deleted = rn_forms_setup_service.deleteById(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		// response.put("deleted", deleted);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	// BUILD DYNAMIC FORM
	@GetMapping("/dynamic_form_build")
	public ResponseEntity<Map<String, Boolean>> buildDynamicForm(@RequestParam(value = "form_id", defaultValue = "0") int form_id) {
		System.out.println("Dynamic form build form start");
		rn_forms_setup_service.buildDynamicForm(form_id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	

}
