package com.realnet.codeextractor.controller;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.realnet.codeextractor.entity.ActiveTechStack_DTO;
import com.realnet.codeextractor.entity.Rn_Bcf_Technology_Stack1;
import com.realnet.codeextractor.repository.Rn_Bcf_TechnologyStack_Repository1;
import com.realnet.codeextractor.response.TechnologyStackResponse;
import com.realnet.codeextractor.service.Rn_Bcf_TechnologyStack_Service;
import com.realnet.fnd.entity.Error;
import com.realnet.fnd.entity.ErrorPojo;
import com.realnet.fnd.entity.Success;
import com.realnet.fnd.entity.SuccessPojo;
import com.realnet.fnd.service.FileStorageService;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserService;
import com.realnet.utils.Constant;
import com.realnet.utils.RealNetUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
@RestController
@RequestMapping(value = "/codeextractor/technologystack", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { "Technology Stack" })
public class TechnologyStackController_codeextractor {

	@Autowired
	private AppUserService userService;

	@Autowired
	private FileStorageService fileStorageService;

	@Autowired
	private Rn_Bcf_TechnologyStack_Service technologyStackService;
	
	@Autowired
	private Rn_Bcf_TechnologyStack_Repository1 technologyStackRepo;

	@Value("${projectPath}")
	private String projectPath;

	// GET ALL SORTED AND PAGINATED DATA
	@ApiOperation(value = "List of Technology Stack", response = Rn_Bcf_Technology_Stack1.class)
	@GetMapping("/technology-stack")
	public TechnologyStackResponse getTechnologyStack(
			@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
			@RequestParam(value = "size", defaultValue = "20", required = false) Integer size) {
		// sorted data
		Pageable paging = PageRequest.of(page, size, Sort.by(Constant.SORT_BY_CREATION_DATE).descending());
		Page<Rn_Bcf_Technology_Stack1> result = technologyStackService.getAll(paging);
        System.out.println("ganesh bute"+result.getContent());
		TechnologyStackResponse resp = new TechnologyStackResponse();
		resp.setPageStats(result, true);
		resp.setItems(result.getContent());
		return resp;

	}

	// GET BY ID
	@ApiOperation(value = "Get A Technology Stack", response = Rn_Bcf_Technology_Stack1.class)
	@GetMapping("/technology-stack/{id}")
	public ResponseEntity<?> getTechnologyStackDetails(@PathVariable(value = "id") int id) {
		Rn_Bcf_Technology_Stack1 bcf_tech_stack = technologyStackService.getById(id);
		// Map<String, Rn_Bcf_Technology_Stack1> extractorMap =
		// Collections.singletonMap("extractior", bcf_extractor);
		// return new ResponseEntity<Map<String, Rn_Bcf_Technology_Stack1>>(extractorMap,
		// HttpStatus.OK);
		// return ResponseEntity.ok().body(bcf_extractor);
		return new ResponseEntity<Rn_Bcf_Technology_Stack1>(bcf_tech_stack, HttpStatus.OK);

	}

	// ========= SAVE AND UNZIP FILE AND MOVE TO A SPECIFIC PATH ========
	@ApiOperation(value = "Add new Technology Stack")
	@PostMapping(value = "/technology-stack")
	public ResponseEntity<?> saveTechnologyStack(// @Valid @RequestBody Rn_Bcf_Technology_Stack1 bcf_extractor,
			@RequestParam(value = "bcf_technology_stack", required = true) String techStackRequest,
			@RequestParam(value = "file", required = true) MultipartFile file)
			throws IOException, JsonParseException, JsonMappingException {

		System.out.println("techStackRequest = " + techStackRequest);
		System.out.println("file = " + file.getOriginalFilename());

		log.debug("techStackRequest => {} ", techStackRequest);
		log.debug("file => {} ", file.getOriginalFilename());
		ObjectMapper mapper = new ObjectMapper();
		mapper.configOverride(FileDescriptor.class).setIsIgnoredType(true);
		//mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, true);
		mapper.setVisibility(mapper.getVisibilityChecker()
	             .withFieldVisibility(JsonAutoDetect.Visibility.ANY));
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		Rn_Bcf_Technology_Stack1 bcf_tech_stack = mapper.readValue(techStackRequest, Rn_Bcf_Technology_Stack1.class);
		
		
		log.debug("bcf_tech_stack => {}", bcf_tech_stack);
		AppUser loggedInUser = userService.getLoggedInUser();
		bcf_tech_stack.setAccountId(loggedInUser.getAccount().getAccount_id());
		bcf_tech_stack.setCreatedBy(loggedInUser.getUserId());

		String techStackKey = RealNetUtils.getTechnologyStackKey(bcf_tech_stack.getTech_stack());
		bcf_tech_stack.setTech_stack_key(techStackKey);
		// upload to directory
		String uploadPath = projectPath.concat("/src/main/resources/technology-stack-zip-uploads/");
		fileStorageService.uploadFile(file, uploadPath);

		String fileName = file.getOriginalFilename();
		String folderName = fileName.substring(0, fileName.lastIndexOf("."));

		System.out.println("UPLOAD PATH = " + uploadPath + "\nFILE NAME = " + fileName + "\nFile Size = " + file.getSize());

		// SAVE ZIP FILE NAME INTO DATABASE
		bcf_tech_stack.setBase_prj_file_name(folderName);
		bcf_tech_stack.setActive(false);
		System.out.println("before save");

		Rn_Bcf_Technology_Stack1 savedTechnology = technologyStackService.save(bcf_tech_stack);
		System.out.println("after save");
		
		if (savedTechnology == null) {
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.TECHNOLOGY_STACK_API_TITLE);
			error.setMessage(Constant.TECHNOLOGY_STACK_NOT_CREATED);
			errorPojo.setError(error);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		}

		/*
		 * ZIP TO UNZIP
		 */
		// ZIP FILE BASE PATH
		String zipFilePath = uploadPath + fileName;

		String tech_stack = savedTechnology.getTech_stack();
		// UNZIP DESTINATION PATH
		String destPath = projectPath.concat("/BaseProject/" + tech_stack);

		File path = new File(destPath);
		if (!path.exists()) {
			path.mkdirs();
		}

		System.out.println("ZIP FILE PATH = " + zipFilePath + "\nDESTINATION PATH = " + destPath);
		try {
			ZipFile zipFile = new ZipFile(zipFilePath);
			zipFile.extractAll(destPath);
		} catch (ZipException e) {
			e.printStackTrace();
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.TECHNOLOGY_STACK_API_TITLE);
			error.setMessage(Constant.UNZIP_EXCEPTION);
			errorPojo.setError(error);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		}

		SuccessPojo successPojo = new SuccessPojo();
		Success success = new Success();
		success.setTitle(Constant.TECHNOLOGY_STACK_API_TITLE);
		success.setMessage(Constant.TECHNOLOGY_STACK_CREATED_SUCCESSFULLY);
		successPojo.setSuccess(success);
		return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.CREATED);
	}

	// UPDATE
	@ApiOperation(value = "Update Technology Stack", response = Rn_Bcf_Technology_Stack1.class)
	@PutMapping("/technology-stack/{id}")
	public ResponseEntity<?> updateTechnology(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
			@PathVariable(value = "id") int id, @Valid @RequestBody Rn_Bcf_Technology_Stack1 bcf_tech_stack) {
		AppUser loggedInUser = userService.getLoggedInUser();
		bcf_tech_stack.setUpdatedBy(loggedInUser.getUserId());

		Rn_Bcf_Technology_Stack1 updatedTech = technologyStackService.updateById(id, bcf_tech_stack);

		if (bcf_tech_stack.getId() != updatedTech.getId()) {
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.EXTRACTOR_API_TITLE);
			error.setMessage(Constant.EXTRACTOR_NOT_DELETED);
			errorPojo.setError(error);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		}

		SuccessPojo successPojo = new SuccessPojo();
		Success success = new Success();
		success.setTitle(Constant.EXTRACTOR_API_TITLE);
		success.setMessage(Constant.EXTRACTOR_UPDATED_SUCCESSFULLY);
		successPojo.setSuccess(success);
		return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.OK);
	}

	// DELETE
	@DeleteMapping("/technology-stack/{id}")
	public ResponseEntity<?> deleteTechnologyStack(@PathVariable(value = "id") int id) {
		boolean deleted = technologyStackService.deleteById(id);
		if (deleted) {
			SuccessPojo successPojo = new SuccessPojo();
			Success success = new Success();
			success.setTitle(Constant.TECHNOLOGY_STACK_API_TITLE);
			success.setMessage(Constant.TECHNOLOGY_STACK_DELETED_SUCCESSFULLY);
			successPojo.setSuccess(success);
			return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.OK);
		} else {
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.TECHNOLOGY_STACK_API_TITLE);
			error.setMessage(Constant.TECHNOLOGY_STACK_NOT_DELETED);
			errorPojo.setError(error);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		}
	}

	// ACTIVE
	@GetMapping(value = "/tech-stack-active")
	public ResponseEntity<?> technologyActive(@RequestParam(value = "id") int id) throws IOException {
		//log.info("technologyActive controller start {}", id);
		AppUser loggedInUser = userService.getLoggedInUser();

		Rn_Bcf_Technology_Stack1 tech_stack = technologyStackService.getById(id);
		log.debug("TECH STACK : {} ", tech_stack);
		if (tech_stack.isActive() == false) {
			tech_stack.setActive(true);
		} else if (tech_stack.isActive()) {
			tech_stack.setActive(false);
		}
		
		//tech_stack.setActive(!tech_stack.isActive()); // LOL
		
		tech_stack.setUpdatedBy(loggedInUser.getUserId());

		Rn_Bcf_Technology_Stack1 tech = technologyStackService.save(tech_stack);
		log.debug("UPDATED TECH STACK : {} ", tech);
		System.out.println("UPDATED TECH STACK : " + tech);
		SuccessPojo successPojo = new SuccessPojo();
		Success success = new Success();
		success.setTitle(Constant.TECHNOLOGY_STACK_API_TITLE);
		success.setMessage(Constant.TECHNOLOGY_STACK_UPDATED_SUCCESSFULLY);
		successPojo.setSuccess(success);
		return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.OK);
	}
	
	// get activate technology stack list (id and name)
	@ApiOperation(value = "Get Activate Technology Stack List ", response = ActiveTechStack_DTO.class)
	@GetMapping("/active-technology")
	@ResponseBody
	public List<ActiveTechStack_DTO> getActiveTechnology() {
		 //List<Rn_Bcf_Technology_Stack1> technologyStack = technologyStackService.getAll();
		 List<ActiveTechStack_DTO> activeTechDTO = technologyStackService.getListOfActivateTechnology();
		return activeTechDTO;
	}
	
	

}
