package com.realnet.codeextractor.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realnet.codeextractor.entity.FileDetails;
import com.realnet.codeextractor.entity.FileListDto;
import com.realnet.codeextractor.entity.Rn_Bcf_Extractor;
import com.realnet.codeextractor.entity.Rn_Bcf_Extractor_Params;
import com.realnet.codeextractor.response.CodeExtractorParamsResponse;
import com.realnet.codeextractor.response.CodeExtractorResponse;
import com.realnet.codeextractor.service.Rn_Bcf_Extractor_Params_Service;
import com.realnet.codeextractor.service.Rn_Bcf_Extractor_Service;
import com.realnet.fnd.entity.Error;
import com.realnet.fnd.entity.ErrorPojo;
import com.realnet.fnd.entity.Success;
import com.realnet.fnd.entity.SuccessPojo;
import com.realnet.fnd.service.FileStorageService;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserServiceImpl;
import com.realnet.utils.Constant;
import com.realnet.utils.RealNetUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

@Slf4j
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { "Code Extractor" })
public class CodeExtractorController {

	@Autowired
	private AppUserServiceImpl userService;

	@Autowired
	private FileStorageService fileStorageService;

	@Autowired
	private Rn_Bcf_Extractor_Service extractorService;

	@Autowired
	private Rn_Bcf_Extractor_Params_Service extractorParamsService;

	@Value("${projectPath}")
	private String projectPath;

	// GET ALL SORTED AND PAGINATED DATA
	@ApiOperation(value = "List of of Bcf Extractor", response = Rn_Bcf_Extractor.class)
	@GetMapping("/bcf-extractor")
	public CodeExtractorResponse getExtractors(
			@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
			@RequestParam(value = "size", defaultValue = "20", required = false) Integer size) {
		// sorted data
		Pageable paging = PageRequest.of(page, size, Sort.by("createdAt").descending());
		Page<Rn_Bcf_Extractor> result = extractorService.getAll(paging);

		CodeExtractorResponse resp = new CodeExtractorResponse();
		resp.setPageStats(result, true);
		resp.setItems(result.getContent());
//		//Map<String, List<Rn_Bcf_Extractor>> extractorMap = Collections.singletonMap("extractior", result.getContent());
//		return new ResponseEntity<Map<String, List<Rn_Bcf_Extractor>>>(extractorMap, HttpStatus.OK);
		return resp;

	}

//	// GET ALL
//	@ApiOperation(value = "List of Bcf Extractor")
//	@GetMapping("/bcf-extractor")
//	public ResponseEntity<?> getExtractors() {
//		List<Rn_Bcf_Extractor> result = extractorService.getAll();
//		Map<String, List<Rn_Bcf_Extractor>> extractorMap = Collections.singletonMap("extractior", result);
//		System.out.println(extractorMap);
//		return ResponseEntity.ok().body(result);
//	}

	// GET BY ID
	@ApiOperation(value = "Get an Bcf Extractor", response = Rn_Bcf_Extractor.class)
	@GetMapping("/bcf-extractor/{id}")
	public ResponseEntity<?> getExtractorById(@PathVariable(value = "id") int id) {
		Rn_Bcf_Extractor bcf_extractor = extractorService.getById(id);

		// Map<String, Rn_Bcf_Extractor> extractorMap =
		// Collections.singletonMap("extractior", bcf_extractor);
		// return new ResponseEntity<Map<String, Rn_Bcf_Extractor>>(extractorMap,
		// HttpStatus.OK);
		// return ResponseEntity.ok().body(bcf_extractor);
		return new ResponseEntity<Rn_Bcf_Extractor>(bcf_extractor, HttpStatus.OK);

	}

	// ========= SAVE AND UNZIP FILE AND MOVE TO A SPECIFIC PATH ========
	@ApiOperation(value = "Add new Bcf Extractor")
	@PostMapping(value = "/bcf-extractor")
	public ResponseEntity<?> saveExtractor(// @Valid @RequestBody Rn_Bcf_Extractor bcf_extractor,
			@RequestParam(value = "bcf_extractor", required = true) String extractorReq,
			@RequestParam(value = "file", required = true) MultipartFile file)
			throws IOException, JsonParseException, JsonMappingException {

		System.out.println("extractorReq = " + extractorReq);
		System.out.println("file = " + file.getOriginalFilename());

		log.debug("extractorReq => {} ", extractorReq);
		log.debug("file => {} ", file.getOriginalFilename());
		ObjectMapper mapper = new ObjectMapper();
		Rn_Bcf_Extractor bcf_extractor = mapper.readValue(extractorReq, Rn_Bcf_Extractor.class);
		log.debug("bcf_extractor => {}", bcf_extractor);
		AppUser loggedInUser = userService.getLoggedInUser();
		bcf_extractor.setAccountId(loggedInUser.getAccount().getAccount_id());
		bcf_extractor.setCreatedBy(loggedInUser.getUserId());

		String techStackKey = RealNetUtils.getTechnologyStackKey(bcf_extractor.getTech_stack());
		System.out.println("TECHNOLOGY STACK KEY = " + techStackKey);
		bcf_extractor.setTech_stack_key(techStackKey);

		// upload to directory
		String uploadPath = projectPath.concat("/src/main/resources/zip-uploads/");
		fileStorageService.uploadFile(file, uploadPath);

		String fileName = file.getOriginalFilename();
		System.out.println(
				"UPLOAD PATH = " + uploadPath + "\nFILE NAME = " + fileName + "\nFile Size = " + file.getSize());

		// SAVE ZIP FILE NAME INTO DATABASE
		bcf_extractor.setSample_file_name(fileName);
		extractorService.save(bcf_extractor);

		// ZIP TO UNZIP
		String zipFilePath = uploadPath + fileName;
		String fileNameWithOutExt = FilenameUtils.removeExtension(fileName);
		Date d = new Date();
		String destFolderName = fileNameWithOutExt + "_" + d.getTime();
		String destPath = projectPath.concat("/src/main/resources/extracted-files/" + destFolderName);
		System.out.println("ZIP FILE PATH = " + zipFilePath + "\nDESTINATION PATH = " + destPath);
		try {
			ZipFile zipFile = new ZipFile(zipFilePath);
			/*
			 * // Get the list of file headers from the zip file List fileHeaderList =
			 * zipFile.getFileHeaders(); // Loop through the file headers for (int i = 0; i<
			 * fileHeaderList.size(); i++) { FileHeader fileHeader = (FileHeader)
			 * fileHeaderList.get(i); String name = fileHeader.getFileName();
			 * if(name.contains(".html") || name.contains(".css") || name.contains(".java")
			 * || name.contains(".jsp") || name.contains(".ts") ) {
			 * zipFile.extractFile(fileHeader, destPath); } }
			 */
			zipFile.extractAll(destPath);
		} catch (ZipException e) {
			e.printStackTrace();
		}

		int headerId = bcf_extractor.getId();
		System.out.println("Code Extractor Parameter Header Id = " + headerId);
		String tech_stack = bcf_extractor.getTech_stack();
		String obj_type = bcf_extractor.getObject_type();
		String sub_obj_type = bcf_extractor.getSub_object_type();

		// MOVE FILES TO PARENT DIRECTORY & DELETE EMPTY FILES & SAVE PATHS INTO PARAMS
		// TABLE
		extractorService.saveListOFiles(headerId, tech_stack, obj_type, sub_obj_type, destPath);

		// SAVE MOVED PATHS INTO DB
		extractorService.moveFiles(headerId, destPath);

		SuccessPojo successPojo = new SuccessPojo();
		Success success = new Success();
		success.setTitle(Constant.EXTRACTOR_API_TITLE);
		success.setMessage(Constant.EXTRACTOR_CREATED_SUCCESSFULLY);
		successPojo.setSuccess(success);
		log.debug("Response {} ", successPojo);
		return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.CREATED);
//		Map<String, Boolean> response = new HashMap<>();
//		response.put("SUCCESS", Boolean.TRUE);
//		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// UPDATE bcf extractor
	@ApiOperation(value = "Update an Extension", response = Rn_Bcf_Extractor.class)
	@PutMapping("/bcf-extractor/{id}")
	public ResponseEntity<?> updateExtractor(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
			@PathVariable(value = "id") int id, @Valid @RequestBody Rn_Bcf_Extractor bcf_extractor) {
		AppUser loggedInUser = userService.getLoggedInUser();
		bcf_extractor.setUpdatedBy(loggedInUser.getUserId());

		Rn_Bcf_Extractor updatedExtractor = extractorService.updateById(id, bcf_extractor);

		if (bcf_extractor.getId() != updatedExtractor.getId()) {
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.EXTRACTOR_API_TITLE);
			error.setMessage(Constant.EXTRACTOR_NOT_DELETED);
			errorPojo.setError(error);
			// return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		}

		SuccessPojo successPojo = new SuccessPojo();
		Success success = new Success();
		success.setTitle(Constant.EXTRACTOR_API_TITLE);
		success.setMessage(Constant.EXTRACTOR_UPDATED_SUCCESSFULLY);
		successPojo.setSuccess(success);
		return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.OK);

		// return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedRn_Ext_Field);
	}

	// DELETE bcf extractor
	@DeleteMapping("/bcf-extractor/{id}")
	public ResponseEntity<?> deleteExtractor(@PathVariable(value = "id") int id) {
		boolean deleted = extractorService.deleteById(id);
		// Map<String, Boolean> response = new HashMap<>();
		if (deleted) {
			SuccessPojo successPojo = new SuccessPojo();
			Success success = new Success();
			success.setTitle(Constant.EXTRACTOR_API_TITLE);
			success.setMessage(Constant.EXTRACTOR_DELETED_SUCCESSFULLY);
			successPojo.setSuccess(success);
			return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.OK);
			// response.put("deleted", Boolean.TRUE);
			// return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		ErrorPojo errorPojo = new ErrorPojo();
		Error error = new Error();
		error.setTitle(Constant.EXTRACTOR_API_TITLE);
		error.setMessage(Constant.EXTRACTOR_NOT_DELETED);
		errorPojo.setError(error);
		// return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
	}

	// =============== BCF CODE EXTRACTOR PARAMS ================

	// GET ALL SORTED AND PAGINATED DATA BY HEADER ID
	@ApiOperation(value = "List of of Bcf Extractor Parameters", response = Rn_Bcf_Extractor.class)
	@GetMapping("/bcf-extractor-params")
	public CodeExtractorParamsResponse codeExtractorParameters(
			@RequestParam(value = "header_id", required = true) Integer headerId, // use param id to get data
			@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
			@RequestParam(value = "size", defaultValue = "20", required = false) Integer size) {
		// sorted data
		// Pageable paging = PageRequest.of(page, size,
		// Sort.by("createdAt").descending());
		List<Rn_Bcf_Extractor_Params> result = extractorParamsService.getByHeaderIdOrderByDate(headerId);
		// log.debug("extractor params sorted data : {}", result.toString());
		CodeExtractorParamsResponse resp = new CodeExtractorParamsResponse();
		// resp.setPageStats(result, true);
		resp.setItems(result);
		return resp;
//		Map<String, List<Rn_Bcf_Extractor_Params>> extractorMap = Collections.singletonMap("extractorParams", result);
//		return new ResponseEntity<Map<String, Page<Rn_Bcf_Extractor_Params>>>(extractorMap, HttpStatus.OK);
	}

	// GET BY ID
	@ApiOperation(value = "Get an Bcf Extractor Parameter", response = Rn_Bcf_Extractor_Params.class)
	@GetMapping("/bcf-extractor-params/{id}")
	public ResponseEntity<?> codeExtractorParamsById(@PathVariable(value = "id") int id) {
		Rn_Bcf_Extractor_Params bcf_extractor_params = extractorParamsService.getById(id);

		// Map<String, Rn_Bcf_Extractor_Params> extractorMap =
		// Collections.singletonMap("extractorParams",bcf_extractor_params);
		// return new ResponseEntity<Map<String, Rn_Bcf_Extractor_Params>>(extractorMap,
		// HttpStatus.OK);
		return new ResponseEntity<Rn_Bcf_Extractor_Params>(bcf_extractor_params, HttpStatus.OK);

	}

	@ApiOperation(value = "Add new Extractor Parameter", response = Rn_Bcf_Extractor_Params.class)
	@PostMapping(value = "/bcf-extractor-params") // use param id to update data
	public ResponseEntity<?> addNewExtractorParam(@RequestParam(value = "header_id", required = true) Integer headerId,
			@Valid @RequestBody Rn_Bcf_Extractor_Params bcf_extractor_param) {
		Rn_Bcf_Extractor extractor = extractorService.getById(headerId);
		bcf_extractor_param.setRn_bcf_extractor(extractor); // set header id
		Rn_Bcf_Extractor_Params savedParam = extractorParamsService.save(bcf_extractor_param);
		if (savedParam == null) {
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.EXTRACTOR_API_TITLE);
			error.setMessage(Constant.EXTRACTOR_NOT_CREATED);
			errorPojo.setError(error);
			// return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		}
		SuccessPojo successPojo = new SuccessPojo();
		Success success = new Success();
		success.setTitle(Constant.EXTRACTOR_API_TITLE);
		success.setMessage(Constant.EXTRACTOR_CREATED_SUCCESSFULLY);
		successPojo.setSuccess(success);
		return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.OK);
	}

	// UPDATE bcf extractor param
	@ApiOperation(value = "Update an Extension Parameter", response = Rn_Bcf_Extractor_Params.class)
	@PutMapping("/bcf-extractor-params/{id}")
	public ResponseEntity<?> updatecodeExtractorParam(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
			@RequestParam(value = "header_id", required = false) Integer headerId, @PathVariable(value = "id") int id,
			@Valid @RequestBody Rn_Bcf_Extractor_Params bcf_extractor_param) {
		AppUser loggedInUser = userService.getLoggedInUser();
		bcf_extractor_param.setUpdatedBy(loggedInUser.getUserId());

		Rn_Bcf_Extractor_Params updatedExtractorParam = extractorParamsService.updateById(id, bcf_extractor_param);

		if (bcf_extractor_param.getId() != updatedExtractorParam.getId()) {
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.EXTRACTOR_API_TITLE);
			error.setMessage(Constant.EXTRACTOR_NOT_UPDATED);
			errorPojo.setError(error);
			// return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		}
		SuccessPojo successPojo = new SuccessPojo();
		Success success = new Success();
		success.setTitle(Constant.EXTRACTOR_API_TITLE);
		success.setMessage(Constant.EXTRACTOR_UPDATED_SUCCESSFULLY);
		successPojo.setSuccess(success);
		// return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedRn_Ext_Field);
		return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.OK);
	}

	// ========= TOGGLE BUTTON SERVICE FOR EXTRACTION AND CREATION ENABLE DISABLE
	// BUTTON //

	// EXTRACTION STATUS CHANGE
	@GetMapping(value = "/bcf-extractor-params/extraction-status-change")
	public ResponseEntity<?> extractionStatusChange(@RequestParam(value = "id") int id) throws IOException {
		AppUser loggedInUser = userService.getLoggedInUser();
		Rn_Bcf_Extractor_Params param = extractorParamsService.getById(id);
		log.debug("Extractor Param : {} ", param);
		param.setIs_extraction_enabled(!param.isIs_extraction_enabled());
		param.setUpdatedBy(loggedInUser.getUserId());

		Rn_Bcf_Extractor_Params updatedParam = extractorParamsService.save(param);
		log.debug("UPDATED Extractor Param : {} ", updatedParam);
		if (param.getId() != updatedParam.getId()) {
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.EXTRACTOR_API_TITLE);
			error.setMessage(Constant.EXTRACTOR_NOT_UPDATED);
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

	// CREATION STATUS CHANGE
	@GetMapping(value = "/bcf-extractor-params/creation-status-change")
	public ResponseEntity<?> creationStatusChange(@RequestParam(value = "id") int id) throws IOException {
		AppUser loggedInUser = userService.getLoggedInUser();

		Rn_Bcf_Extractor_Params param = extractorParamsService.getById(id);
		log.debug("Extractor Param : {} ", param);
		param.setIs_creation_enabled(!param.isIs_creation_enabled());

		param.setUpdatedBy(loggedInUser.getUserId());
		Rn_Bcf_Extractor_Params updatedParam = extractorParamsService.save(param);
		log.debug("UPDATED Extractor Param : {} ", updatedParam);
		if (param.getId() != updatedParam.getId()) {
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.EXTRACTOR_API_TITLE);
			error.setMessage(Constant.EXTRACTOR_NOT_UPDATED);
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

	// ========== FILE CODE EDITOR PART ============//

	/*
	 * File list(from code extractor params table) for dropDown in file editor
	 */
	@ApiOperation(value = "Get Extracted File List", response = FileDetails.class)
	@GetMapping("/bcf-extractor-file-list/{id}")
	public ResponseEntity<?> getFileListBy(@PathVariable(value = "id") int headerId) {

		List<Rn_Bcf_Extractor_Params> params = extractorParamsService.getByHeaderId(headerId);
		ArrayList<FileListDto> response = new ArrayList<>();
		for (Rn_Bcf_Extractor_Params param : params) {
			int id = param.getId();
			String fileName = param.getName_string();
			FileListDto fileList = new FileListDto();
			fileList.setId(id);
			fileList.setFileName(fileName);
			response.add(fileList);
		}

		if (response.isEmpty()) {
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.FILE_LIST_API_TITLE);
			error.setMessage(Constant.FILE_LIST_IS_EMPTY);
			errorPojo.setError(error);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		} else {
			return new ResponseEntity<List<FileListDto>>(response, HttpStatus.OK);
		}
	}

	// =====read files and get file contents =========//
	@ApiOperation(value = "Get an Bcf Extractor Parameter", response = FileDetails.class)
	@GetMapping("/file-code-read/{id}")
	public ResponseEntity<?> readFilesById(@PathVariable(value = "id") int id) {
		Rn_Bcf_Extractor_Params params = extractorParamsService.getById(id);
		String address_string = params.getAddress_string(); // uploaded path
		String moved_address_string = params.getMoved_address_string(); // in a same folder
		// String reference_address_string = params.getReference_address_string();

		String filePath = moved_address_string.replace(projectPath, "");
		filePath = projectPath.concat(filePath);
		StringBuilder code = new StringBuilder();
		File file = null;
		try {
			file = new File(filePath);
			String fileName = file.getName();
			// String fileType = FilenameUtils.getExtension(fileName);
			log.info("File Name : {}", fileName);
			// log.info("File Type : {} ", fileType);
			// BufferedReader br = new BufferedReader(new FileReader(file));
			// String fileString = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				code.append(line + "\n");
			}
			fr.close();
			br.close();
		} catch (IOException e) {
			log.debug("IO Exception Handled...");
			log.error(e.getMessage());
			e.getMessage();
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.FILE_OPERATION_API_TITLE);
			error.setMessage(Constant.FILE_NOT_FOUND_EXCEPTION);
			errorPojo.setError(error);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		}

		FileDetails fileDetails = new FileDetails();
		fileDetails.setId(id);
		fileDetails.setText(code.toString());

		return new ResponseEntity<FileDetails>(fileDetails, HttpStatus.OK);
	}

	// ============= code editor (save data into file) =========
	@ApiOperation(value = "Save File Data")
	@PostMapping(value = "/file-code-save")
	public ResponseEntity<?> codeSaveInFile(@Valid @RequestBody FileDetails fileDetails) {

		int paramsId = fileDetails.getId();
		String code = fileDetails.getText();
		log.debug("updated code : {}", code);

		Rn_Bcf_Extractor_Params params = extractorParamsService.getById(paramsId);
		// String address_string = params.getAddress_string();
		String moved_address_string = params.getMoved_address_string();
		// String reference_address_string = params.getReference_address_string();

		// projectPath
		// String filePath = projectPath.concat(reference_address_string);

		String filePath = moved_address_string.replace(projectPath, "");
		filePath = projectPath.concat(filePath);

		File file = null;
		try {
			file = new File(filePath);
			String fileName = file.getName();
			String fileType = FilenameUtils.getExtension(fileName);
			log.info("File Name : {}", fileName);
			log.info("File Type : {} ", fileType);
//			if(fileType.equalsIgnoreCase("java") || fileType.equalsIgnoreCase("ts")|| fileType.equalsIgnoreCase("txt")) {
//				code = Jsoup.parse(code).text();
//			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, false)); // OVER WRITE FILE
			bw.write(code);
			bw.close();
		} catch (FileNotFoundException e) {
			log.debug("File Not Found Exception Handled...");
			log.error(e.getMessage());
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.FILE_OPERATION_API_TITLE);
			error.setMessage(Constant.FILE_NOT_FOUND_EXCEPTION);
			errorPojo.setError(error);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		} catch (IOException e) {
			log.error(e.getMessage());
			log.debug("IOException Handled...");
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.FILE_OPERATION_API_TITLE);
			error.setMessage(Constant.FILE_CODE_SAVE_FAILURE);
			errorPojo.setError(error);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		}

		SuccessPojo successPojo = new SuccessPojo();
		Success success = new Success();
		success.setTitle(Constant.FILE_OPERATION_API_TITLE);
		success.setMessage(Constant.FILE_CODE_SAVE_SUCCESSFULLY);
		successPojo.setSuccess(success);
		return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.OK);
	}

	// ======= STATIC CODE READ =========
	@ApiOperation(value = "Read Static File and Return Code", response = FileDetails.class)
	@GetMapping("/static-file-code-read/{id}")
	public ResponseEntity<?> readStaticFilesById(@PathVariable(value = "id") int id) {
		Rn_Bcf_Extractor_Params param = extractorParamsService.getById(id);
		String name_string = param.getName_string();

		
		String moved_address_string = param.getMoved_address_string(); // in a same folder
		
		
		String staticFileName = "SE_".concat(name_string);
		String staticPath = moved_address_string.replace(projectPath, "");
		staticPath = staticPath.substring(0, staticPath.lastIndexOf("/"));
		staticPath = staticPath.concat("/static_code/"); // static code folder
		staticPath = staticPath.concat(staticFileName); // static filde name

		// projectPath
		String filePath = projectPath.concat(staticPath);

//		String filePath = staticPath; //by gk

		StringBuilder code = new StringBuilder();
		File file = null;
		try {
			file = new File(filePath);
			String fileName = file.getName();
			// String fileType = FilenameUtils.getExtension(fileName);
			log.info("Static File Name : {}", fileName);
			// BufferedReader br = new BufferedReader(new FileReader(file));
			// String fileString = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				code.append(line + "\n");
			}
			fr.close();
			br.close();
		} catch (IOException e) {
			log.debug("IO Exception Handled...");
			log.error(e.getMessage());
			e.getMessage();
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.FILE_OPERATION_API_TITLE);
			error.setMessage(Constant.FILE_NOT_FOUND_EXCEPTION);
			errorPojo.setError(error);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		}

		String codee = code.substring(0, code.lastIndexOf("\n")); // remove last line break
		FileDetails fileDetails = new FileDetails();
		fileDetails.setId(id);
		fileDetails.setText(codee);
		return new ResponseEntity<FileDetails>(fileDetails, HttpStatus.OK);
	}

	// ======= STATIC CODE SAVE INTO FILE =========
	@ApiOperation(value = "Save File Data")
	@PostMapping(value = "/static-file-code-save")
	public ResponseEntity<?> staticCodeSaveInFile(@Valid @RequestBody FileDetails fileDetails) {

		int paramsId = fileDetails.getId();
		String code = fileDetails.getText();
		// log.debug("updated code : {}", code);

		Rn_Bcf_Extractor_Params param = extractorParamsService.getById(paramsId);
		// String address_string = param.getAddress_string();
		String name_string = param.getName_string();
		String moved_address_string = param.getMoved_address_string(); // in a same folder

		String staticFileName = "SE_".concat(name_string);
		String staticPath = moved_address_string.replace(projectPath, "");
		staticPath = staticPath.substring(0, staticPath.lastIndexOf("/"));
		staticPath = staticPath.concat("/static_code/"); // static code folder
		staticPath = staticPath.concat(staticFileName); // static file name

		// projectPath
		String filePath = projectPath.concat(staticPath);

		File file = null;
		try {
			file = new File(filePath);
			String fileName = file.getName();
			log.info("Static File Name : {}", fileName);

			BufferedWriter bw = new BufferedWriter(new FileWriter(file, false)); // OVER WRITE FILE
			bw.write(code);
			bw.close();
		} catch (FileNotFoundException e) {
			log.debug("File Not Found Exception Handled...");
			e.printStackTrace();
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.FILE_OPERATION_API_TITLE);
			error.setMessage(Constant.FILE_NOT_FOUND_EXCEPTION);
			errorPojo.setError(error);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		} catch (IOException e) {
			e.printStackTrace();
			log.debug("IOException Handled...");
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.FILE_OPERATION_API_TITLE);
			error.setMessage(Constant.FILE_CODE_SAVE_FAILURE);
			errorPojo.setError(error);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		}

		SuccessPojo successPojo = new SuccessPojo();
		Success success = new Success();
		success.setTitle(Constant.FILE_OPERATION_API_TITLE);
		success.setMessage(Constant.FILE_CODE_SAVE_SUCCESSFULLY);
		successPojo.setSuccess(success);
		return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.OK);
	}

	// @PostMapping("/upload")
	@PostMapping(value = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadToLocalFileSystem(@RequestParam("file") MultipartFile file) {

		System.out.println("calling upload controller");
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Path path = Paths.get(projectPath + fileName);
		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/download/")
				.path(fileName).toUriString();
		return ResponseEntity.ok(fileDownloadUri);
	}

	@RequestMapping(value = "/redirect", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public ResponseEntity<?> registerBatchUser(@RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(projectPath)));
				stream.write(bytes);
				stream.close();

			} catch (Exception e) {
				Map<String, Object> FeedBackStatus = new HashMap<String, Object>();
				return ResponseEntity.ok(FeedBackStatus);
			}

		}
		return ResponseEntity.ok(null);
	}

	@PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		String fileName = file.getOriginalFilename();
		return fileName;
	}

	@RequestMapping(value = "/upload-file-2", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Map<String, Object>> handleFileUpload(
			@RequestParam("file") MultipartFile file) {
		String name = file.getName();
		System.out.println(name);

		Map<String, Object> FeedBackStatus = new HashMap<String, Object>();
		FeedBackStatus.put("status", "success");

		return ResponseEntity.ok(FeedBackStatus);

	}

	/*
	 * @PostMapping(value = "/uploadFile", consumes =
	 * MediaType.MULTIPART_FORM_DATA_VALUE) public String
	 * uploadFile2(@RequestParam("file") MultipartFile file)throws IOException {
	 * Path tempFile = Files.createTempFile(null, null);
	 * 
	 * Files.write(tempFile, MultipartFile.getBytes()); File fileToSend =
	 * tempFile.toFile();
	 * 
	 * MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
	 * 
	 * parameters.add("file", new FileSystemResource(fileToSend));
	 * 
	 * HttpHeaders headers = new HttpHeaders(); headers.set("Content-Type",
	 * "multipart/form-data");
	 * 
	 * HttpEntity httpEntity = new HttpEntity<>(parameters, headers);
	 * 
	 * try { restTemplate.exchange(apiUrl, HttpMethod.POST, httpEntity,
	 * MyClazz.class); } finally { fileAEnviar.delete(); }
	 * 
	 * }
	 */

	@PostMapping("/upload-gb")
	public ResponseEntity<String> uploadFileGb(@RequestParam("file") MultipartFile file) {
		return ResponseEntity.ok("working");
	}

}
