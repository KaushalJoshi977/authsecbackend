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

import com.realnet.exceptions.ResourceNotFoundException;
import com.realnet.fnd.entity.Rn_Dynamic_Transaction;
import com.realnet.fnd.response.Rn_DynamicTransactionResponse;
import com.realnet.fnd.service.Rn_DynamicTransactionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { "Rn_Dynamic_Transaction" })
public class Rn_DynamicTransactionController {

	@Autowired
	private Rn_DynamicTransactionService rn_dynamicTransactionService;

//	@Autowired
//	private TokenUtil tokenUtil;

	// GET ALL
	@ApiOperation(value = "List of Dynamic Transaction", response = Rn_DynamicTransactionResponse.class)
	@GetMapping("/dynamic_transaction/all")
	public Rn_DynamicTransactionResponse getDynamicTx(
			@RequestParam(value = "page", defaultValue = "0", required = false) int page,
			@RequestParam(value = "size", defaultValue = "20", required = false) int size) {

		Rn_DynamicTransactionResponse resp = new Rn_DynamicTransactionResponse();
		Pageable paging = PageRequest.of(page, size);
		Page<Rn_Dynamic_Transaction> result = rn_dynamicTransactionService.getAll(paging);
		resp.setPageStats(result, true);
		resp.setItems(result.getContent());
		return resp;
	}

	// GET BY FROM ID (GRID)
	@ApiOperation(value = "Get A Dynamic Transaction")
	@GetMapping("/dynamic_transaction")
	public ResponseEntity<List<Rn_Dynamic_Transaction>> getDynamicTxByFormId(
			@RequestParam(value = "form_id") int form_id) {
		List<Rn_Dynamic_Transaction> rn_dynamic_transaction = rn_dynamicTransactionService.getByFormId(form_id);
		return ResponseEntity.ok().body(rn_dynamic_transaction);
	}

	// SAVE
	@ApiOperation(value = "Add A New Dynamic Transaction", response = Rn_DynamicTransactionResponse.class)
	@PostMapping("/dynamic_transaction")
	public ResponseEntity<Rn_Dynamic_Transaction> createDynamicTx(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
			@Valid @RequestBody Rn_Dynamic_Transaction rn_dynamic_transaction) {
//		String userId = tokenUtil.getUserId(authToken);
//		rn_dynamic_transaction.setCreatedBy(userId);
//		rn_dynamic_transaction.setAccountId(userId);
		Rn_Dynamic_Transaction savedRn_Dynamic_Transaction = rn_dynamicTransactionService.save(rn_dynamic_transaction);
		if (savedRn_Dynamic_Transaction == null) {
			throw new ResourceNotFoundException("Dynamic Transaction Is Not Saved");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(savedRn_Dynamic_Transaction);
	}

	// GET BY ID
	@ApiOperation(value = "Get a Single Form", response = Rn_Dynamic_Transaction.class)
	@GetMapping("/dynamic_transaction/{id}")
	public ResponseEntity<Rn_Dynamic_Transaction> getById(@PathVariable(value = "id") int id,
			@RequestParam(value = "form_id") int form_id) {
		Rn_Dynamic_Transaction dynamicTransaction = rn_dynamicTransactionService.getByIdAndFormId(id, form_id);
		return ResponseEntity.ok().body(dynamicTransaction);
	}

	// UPDATE
	@ApiOperation(value = "Update A Dynamic Transaction", response = Rn_Dynamic_Transaction.class)
	@PutMapping("/dynamic_transaction/{id}")
	public ResponseEntity<Rn_Dynamic_Transaction> updateDynamicTx(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
			@PathVariable(value = "id") int id, @RequestParam(value = "form_id") int form_id,
			@Valid @RequestBody Rn_Dynamic_Transaction rn_dynamic_transaction) {
//		String userId = tokenUtil.getUserId(authToken);
//		rn_dynamic_transaction.setUpdatedBy(userId);
		Rn_Dynamic_Transaction updatedDynamicTransaction = rn_dynamicTransactionService.updateByFormId(id, form_id,
				rn_dynamic_transaction);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedDynamicTransaction);
	}

	// DELETE
	@DeleteMapping("/dynamic_transaction/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteDynamicTx(@PathVariable(value = "id") int id) {
		boolean deleted = rn_dynamicTransactionService.deleteById(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		// response.put("deleted", deleted);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
