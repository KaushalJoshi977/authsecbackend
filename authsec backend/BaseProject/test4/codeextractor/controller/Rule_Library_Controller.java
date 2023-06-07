package com.realnet.codeextractor.controller;

import java.util.List;

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

import com.realnet.codeextractor.entity.Rn_Bcf_Exception_Rules;
import com.realnet.codeextractor.entity.Rn_Bcf_Rules;
import com.realnet.codeextractor.entity.RuleCopyDTO;
import com.realnet.codeextractor.response.ExceptionRuleLibraryResponse;
import com.realnet.codeextractor.response.RuleLibraryResponse;
import com.realnet.codeextractor.service.Rn_Bcf_Exception_Rule_Library_Service;
import com.realnet.codeextractor.service.Rn_Bcf_Rule_Library_Service;
import com.realnet.fnd.entity.Error;
import com.realnet.fnd.entity.ErrorPojo;
import com.realnet.fnd.entity.Success;
import com.realnet.fnd.entity.SuccessPojo;
import com.realnet.userDTO.User;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserService;
import com.realnet.utils.Constant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { "Rule Library" })
public class Rule_Library_Controller {

	@Autowired
	private AppUserService userService;

	@Autowired
	private Rn_Bcf_Rule_Library_Service ruleLibraryService;

	@Autowired
	private Rn_Bcf_Exception_Rule_Library_Service exceptionRuleLibraryService;

//	@Autowired
//	private TokenUtil tokenUtil;
	// ==== RULE LIBRARY AND EXCEPTION RULE LIBRARY REST APIS ==== //

	// COPY RULES
	@ApiOperation(value = "Add A New Rule", response = Rn_Bcf_Rules.class)
	@PostMapping("/rule-copy")
	public ResponseEntity<?> copyRule(@Valid @RequestBody RuleCopyDTO ruleReq) {
		AppUser user = userService.getLoggedInUser();
		Long userId = user.getUserId();
		Long accId = user.getAccount().getAccount_id();

		String from_tech_stack = ruleReq.getFrom_tech_stack();
		String from_object_type = ruleReq.getFrom_object_type();
		String from_sub_object_type = ruleReq.getFrom_sub_object_type();

		String to_tech_stack = ruleReq.getTo_tech_stack();
		String to_object_type = ruleReq.getTo_object_type();
		String to_sub_object_type = ruleReq.getTo_sub_object_type();

		// GET ALL RULES
		List<Rn_Bcf_Rules> rules = ruleLibraryService.copyRules2(from_tech_stack, from_object_type, from_sub_object_type);
		log.debug("RULE COPPIED {}", rules);
		for (Rn_Bcf_Rules rule : rules) {
			Rn_Bcf_Rules newRule = new Rn_Bcf_Rules();
			newRule.setCreatedBy(userId);
			newRule.setAccountId(accId);
			
			newRule.setGroup_id(rule.getGroup_id());
			newRule.setFile_code(rule.getFile_code());
			newRule.setRule_name(rule.getRule_name());
			newRule.setRule_type(rule.getRule_type());
			newRule.setTech_stack(to_tech_stack);
			newRule.setObject_type(to_object_type);
			newRule.setSub_object_type(to_sub_object_type);
			newRule.setIdentifier_start_string(rule.getIdentifier_start_string());
			newRule.setIdentifier_end_string(rule.getIdentifier_end_string());
			newRule.setReplacement_string(rule.getReplacement_string());
			
			Rn_Bcf_Rules savedRule = ruleLibraryService.save(newRule);
			log.debug("SAVED RULE {}", savedRule);
			if (savedRule == null) {
				ErrorPojo errorPojo = new ErrorPojo();
				Error error = new Error();
				error.setTitle(Constant.RULE_LIBRARY_API_TITLE);
				error.setMessage(Constant.RULE_NOT_CREATED);
				errorPojo.setError(error);
				return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
			}
		}
		SuccessPojo successPojo = new SuccessPojo();
		Success success = new Success();
		success.setTitle(Constant.RULE_LIBRARY_API_TITLE);
		success.setMessage(Constant.RULE_CREATED_SUCCESSFULLY);
		successPojo.setSuccess(success);
		return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.CREATED);
	}

	// GET ALL
	@ApiOperation(value = "List of Rules", response = RuleLibraryResponse.class)
	@GetMapping("/rule-library")
	public RuleLibraryResponse getAllRules(
			@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
			@RequestParam(value = "size", defaultValue = "20", required = false) Integer size) {
		RuleLibraryResponse resp = new RuleLibraryResponse();
		Pageable paging = PageRequest.of(page, size);
		Page<Rn_Bcf_Rules> result = ruleLibraryService.getAll(paging);
		resp.setPageStats(result, true);
		resp.setItems(result.getContent());
		return resp;
	}

	// GET BY ID
	@ApiOperation(value = "Get a Rule", response = Rn_Bcf_Rules.class)
	@GetMapping("/rule-library/{id}")
	public ResponseEntity<Rn_Bcf_Rules> getRuleDetails(@PathVariable(value = "id") int id) {
		Rn_Bcf_Rules rule = ruleLibraryService.getById(id);
		return ResponseEntity.ok().body(rule);
	}

	// SAVE
	@ApiOperation(value = "Add A New Rule", response = Rn_Bcf_Rules.class)
	@PostMapping("/rule-library")
	public ResponseEntity<?> createRule(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
			@Valid @RequestBody Rn_Bcf_Rules bcf_rule) {
//		String userId = tokenUtil.getUserId(authToken);
//		rn_forms_setup.setCreatedBy(userId);
//		rn_forms_setup.setAccountId(userId);
		Long userId = userService.getLoggedInUserId();
		bcf_rule.setCreatedBy(userId);

		Rn_Bcf_Rules savedRule = ruleLibraryService.save(bcf_rule);

		if (savedRule == null) {
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.RULE_LIBRARY_API_TITLE);
			error.setMessage(Constant.RULE_NOT_CREATED);
			errorPojo.setError(error);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		}

		SuccessPojo successPojo = new SuccessPojo();
		Success success = new Success();
		success.setTitle(Constant.RULE_LIBRARY_API_TITLE);
		success.setMessage(Constant.RULE_CREATED_SUCCESSFULLY);
		successPojo.setSuccess(success);
		return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.CREATED);
	}

	/* for testing purpose */
	// SAVE LIST OF DATA
	@ApiOperation(value = "Add AList Of New Rules", response = Rn_Bcf_Rules.class)
	@PostMapping("/rule-library/all")
	public ResponseEntity<?> createListOfRules(@RequestBody List<@Valid Rn_Bcf_Rules> bcf_rule) {
		for (Rn_Bcf_Rules rule : bcf_rule) {
			Long userId = userService.getLoggedInUserId();
			rule.setCreatedBy(userId);

			Rn_Bcf_Rules savedRule = ruleLibraryService.save(rule);
			if (savedRule == null) {
				ErrorPojo errorPojo = new ErrorPojo();
				Error error = new Error();
				error.setTitle(Constant.RULE_LIBRARY_API_TITLE);
				error.setMessage(Constant.RULE_NOT_CREATED);
				errorPojo.setError(error);
				return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
			}
		}
		SuccessPojo successPojo = new SuccessPojo();
		Success success = new Success();
		success.setTitle(Constant.RULE_LIBRARY_API_TITLE);
		success.setMessage(Constant.RULE_CREATED_SUCCESSFULLY);
		successPojo.setSuccess(success);
		return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.CREATED);
	}

	// UPDATE
	@ApiOperation(value = "Update A Form", response = Rn_Bcf_Rules.class)
	@PutMapping("/rule-library/{id}")
	public ResponseEntity<?> updateRule(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
			@PathVariable(value = "id") Integer id, @Valid @RequestBody Rn_Bcf_Rules rule) {
//		String userId = tokenUtil.getUserId(authToken);
//		rn_forms_setup.setUpdatedBy(userId);
		Long userId = userService.getLoggedInUserId();
		rule.setUpdatedBy(userId);
		Rn_Bcf_Rules updatedRule = ruleLibraryService.updateById(id, rule);

		if (id != updatedRule.getId()) {
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.RULE_LIBRARY_API_TITLE);
			error.setMessage(Constant.RULE_NOT_UPDATED);
			errorPojo.setError(error);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		}

		SuccessPojo successPojo = new SuccessPojo();
		Success success = new Success();
		success.setTitle(Constant.RULE_LIBRARY_API_TITLE);
		success.setMessage(Constant.RULE_UPDATED_SUCCESSFULLY);
		successPojo.setSuccess(success);
		return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.OK);
	}

	// DELETE
	@DeleteMapping("/rule-library/{id}")
	public ResponseEntity<?> deleteRule(@PathVariable(value = "id") Integer id) {
		boolean deleted = ruleLibraryService.deleteById(id);
		if (deleted) {
			SuccessPojo successPojo = new SuccessPojo();
			Success success = new Success();
			success.setTitle(Constant.RULE_LIBRARY_API_TITLE);
			success.setMessage(Constant.RULE_DELETED_SUCCESSFULLY);
			successPojo.setSuccess(success);
			return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.OK);
		} else {
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.RULE_LIBRARY_API_TITLE);
			error.setMessage(Constant.RULE_NOT_DELETED);
			errorPojo.setError(error);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		}
	}

	// =========== EXCEPTION RULE =================== //
	// GET ALL
	@ApiOperation(value = "List of Exception Rules", response = ExceptionRuleLibraryResponse.class)
	@GetMapping("/exception-rule-library")
	public ExceptionRuleLibraryResponse getAllExceptionRules(
			@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
			@RequestParam(value = "size", defaultValue = "20", required = false) Integer size) {
		ExceptionRuleLibraryResponse resp = new ExceptionRuleLibraryResponse();
		Pageable paging = PageRequest.of(page, size);
		Page<Rn_Bcf_Exception_Rules> result = exceptionRuleLibraryService.getAll(paging);
		resp.setPageStats(result, true);
		resp.setItems(result.getContent());
		return resp;
	}

	// GET BY ID
	@ApiOperation(value = "Get a Exception Rule", response = Rn_Bcf_Exception_Rules.class)
	@GetMapping("/exception-rule-library/{id}")
	public ResponseEntity<Rn_Bcf_Exception_Rules> getExceptionRuleDetails(@PathVariable(value = "id") int id) {
		Rn_Bcf_Exception_Rules rule = exceptionRuleLibraryService.getById(id);
		return ResponseEntity.ok().body(rule);
	}

	// SAVE
	@ApiOperation(value = "Add A New Rule", response = Rn_Bcf_Exception_Rules.class)
	@PostMapping("/exception-rule-library")
	public ResponseEntity<?> createExceptionRule(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
			@Valid @RequestBody Rn_Bcf_Exception_Rules bcf_rule) {
//			String userId = tokenUtil.getUserId(authToken);
//			rn_forms_setup.setCreatedBy(userId);
//			rn_forms_setup.setAccountId(userId);
		Long userId = userService.getLoggedInUserId();
		bcf_rule.setCreatedBy(userId);

		Rn_Bcf_Exception_Rules savedRule = exceptionRuleLibraryService.save(bcf_rule);

		if (savedRule == null) {
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.EXCEPTION_RULE_LIBRARY_API_TITLE);
			error.setMessage(Constant.EXCEPTION_RULE_NOT_CREATED);
			errorPojo.setError(error);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		}
		SuccessPojo successPojo = new SuccessPojo();
		Success success = new Success();
		success.setTitle(Constant.EXCEPTION_RULE_LIBRARY_API_TITLE);
		success.setMessage(Constant.EXCEPTION_RULE_CREATED_SUCCESSFULLY);
		successPojo.setSuccess(success);
		return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.CREATED);
	}

	/* for testing purpose */
	// SAVE LIST OF DATA
	@ApiOperation(value = "Add A New Rule", response = Rn_Bcf_Exception_Rules.class)
	@PostMapping("/exception-rule-library/all")
	public ResponseEntity<?> createListOfExceptionRule(@RequestBody List<@Valid Rn_Bcf_Exception_Rules> bcf_rule) {
		Long userId = userService.getLoggedInUserId();
		for (Rn_Bcf_Exception_Rules rule : bcf_rule) {
			rule.setCreatedBy(userId);
			Rn_Bcf_Exception_Rules savedRule = exceptionRuleLibraryService.save(rule);
			if (savedRule == null) {
				ErrorPojo errorPojo = new ErrorPojo();
				Error error = new Error();
				error.setTitle(Constant.EXCEPTION_RULE_LIBRARY_API_TITLE);
				error.setMessage(Constant.EXCEPTION_RULE_NOT_CREATED);
				errorPojo.setError(error);
				return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
			}
		}
		SuccessPojo successPojo = new SuccessPojo();
		Success success = new Success();
		success.setTitle(Constant.EXCEPTION_RULE_LIBRARY_API_TITLE);
		success.setMessage(Constant.EXCEPTION_RULE_CREATED_SUCCESSFULLY);
		successPojo.setSuccess(success);
		return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.CREATED);
	}

	// UPDATE
	@ApiOperation(value = "Update A Form", response = Rn_Bcf_Exception_Rules.class)
	@PutMapping("/exception-rule-library/{id}")
	public ResponseEntity<?> updateExcetionRule(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
			@PathVariable(value = "id") Integer id, @Valid @RequestBody Rn_Bcf_Exception_Rules rule) {
//			String userId = tokenUtil.getUserId(authToken);
//			rn_forms_setup.setUpdatedBy(userId);
		Long userId = userService.getLoggedInUserId();
		rule.setUpdatedBy(userId);
		Rn_Bcf_Exception_Rules updatedRule = exceptionRuleLibraryService.updateById(id, rule);

		if (id != updatedRule.getId()) {
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.EXCEPTION_RULE_LIBRARY_API_TITLE);
			error.setMessage(Constant.EXCEPTION_RULE_NOT_UPDATED);
			errorPojo.setError(error);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		}

		SuccessPojo successPojo = new SuccessPojo();
		Success success = new Success();
		success.setTitle(Constant.EXCEPTION_RULE_LIBRARY_API_TITLE);
		success.setMessage(Constant.EXCEPTION_RULE_UPDATED_SUCCESSFULLY);
		successPojo.setSuccess(success);
		return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.OK);
	}

	// DELETE
	@DeleteMapping("/exception-rule-library/{id}")
	public ResponseEntity<?> deleteExceptionRule(@PathVariable(value = "id") Integer id) {
		boolean deleted = exceptionRuleLibraryService.deleteById(id);
		if (deleted) {
			SuccessPojo successPojo = new SuccessPojo();
			Success success = new Success();
			success.setTitle(Constant.EXCEPTION_RULE_LIBRARY_API_TITLE);
			success.setMessage(Constant.EXCEPTION_RULE_DELETED_SUCCESSFULLY);
			successPojo.setSuccess(success);
			return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.OK);
		} else {
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.EXCEPTION_RULE_LIBRARY_API_TITLE);
			error.setMessage(Constant.EXCEPTION_RULE_NOT_DELETED);
			errorPojo.setError(error);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		}
	}

}
