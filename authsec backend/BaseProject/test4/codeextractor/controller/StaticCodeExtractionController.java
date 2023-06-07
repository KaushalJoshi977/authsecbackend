package com.realnet.codeextractor.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.codeextractor.entity.Rn_Bcf_Extractor;
import com.realnet.codeextractor.entity.Rn_Bcf_Extractor_Params;
import com.realnet.codeextractor.service.Rn_Bcf_Extractor_Params_Service;
import com.realnet.codeextractor.service.Rn_Bcf_Extractor_Service;
import com.realnet.fnd.entity.ErrorPojo;
import com.realnet.fnd.entity.Success;
import com.realnet.fnd.entity.SuccessPojo;
import com.realnet.utils.Constant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { "Code Extractor" })
public class StaticCodeExtractionController {
	@Value("${projectPath}")
	private String projectPath;

	@Autowired
	private Rn_Bcf_Extractor_Service bcf_extractorService;
	
	@Autowired
	private Rn_Bcf_Extractor_Params_Service bcf_extractor_params_service;

	@ApiOperation(value = "Static Code Extraction")
	@GetMapping(value = "/static_code_extraction")
	public ResponseEntity<?> staticCodeExtraction(@RequestParam(value = "header_id") Integer headerId) throws IOException {
		Rn_Bcf_Extractor bcf_extractor = bcf_extractorService.getById(headerId);
		List<Rn_Bcf_Extractor_Params> bcf_extractor_params_t_values = bcf_extractor.getRn_bcf_extractor_Params();
		log.debug("bcf_extractor_params_t_values {}", bcf_extractor_params_t_values);
		
		List<Rn_Bcf_Extractor_Params> bcf_extractor_params = bcf_extractor_params_service.getByHeaderId(headerId);
		log.debug("bcf_extractor_params {}", bcf_extractor_params);

		for (Rn_Bcf_Extractor_Params params : bcf_extractor_params_t_values) {
			String path = params.getMoved_address_string();
			boolean is_extraction_enabled = params.isIs_extraction_enabled();
			boolean is_creation_enabled = params.isIs_creation_enabled();

			if (is_creation_enabled && is_extraction_enabled) {
				try {
					File file = new File(path);

					// STEP 1. CONVERT THE FILE INTO STRING
					String fileContents = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
					//if (!fileContents.isEmpty()) {
						fileContents = fileContents.replace("\\", "\\\\");
						fileContents = fileContents.replace("\"", (File.separator + "\""));
					
						// STEP 2. CREATE A TEMP FILE AND WRITE THE CONVERTED STRING
						String parentPath = file.getParent();

						String name = file.getName();
						String fileName = "temp_" + name;
						File tempFile = new File(parentPath + File.separator + fileName);
						if (tempFile.createNewFile()) {
							System.out.println("------ TEMP FILE CREATED-------\n" + tempFile.getAbsolutePath());
						}
						BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
						writer.write(fileContents);
						writer.close();
						System.out.println("Successfully wrote to the file...");

						StringBuilder code = new StringBuilder();

						// STEP 3. READ THE TEMP FILE AND MAKE IT "STRING"
						BufferedReader br = new BufferedReader(new FileReader(tempFile));
						String line = br.readLine();
						while (line != null) {
							code.append("\"" + line + "\" + \"\\r\\n\" + \r\n");
							line = br.readLine();
						}
						br.close();

						// STEP 4: REMOVING WHITE SPACE
						String finalString = code.toString().trim();
						if (!finalString.isEmpty()) {
						// STEP 5: REMOVING LAST CHARS(+ "\r\n" +)
						finalString = finalString.substring(0, finalString.length() - 10);
						// System.out.println(finalString);
						}
						boolean deleted = tempFile.delete();
						if(deleted) {
							log.info(tempFile.getName(), "{} File deleted");
						}

						// MODIFIED NAME
						// CONVERT EVERY FILE INTO .JAVA FILE
						// String ext = FilenameUtils.getExtension(name);
						// String fileNameWithOutExt = FilenameUtils.removeExtension(name);

						String ConvertedFileName = "SE_" + name;

						// STAIC CODE OUTPUT DIRECTORY & PATH
						String staticDirString = parentPath + File.separator + "static_code";
						File staticDir = new File(staticDirString);
						if (!staticDir.exists()) {
							staticDir.mkdir();
						}

						File staticFile = new File(staticDirString + File.separator + ConvertedFileName);
						System.out.println("FILES WITH STATIC CODE DIRECTORY = " + staticFile);

						if (!staticFile.exists()) {
							staticFile.createNewFile();
						}
						BufferedWriter writer2 = new BufferedWriter(new FileWriter(staticFile.getAbsoluteFile()));
						writer2.write(finalString);
						writer2.close();
						/*
						 * //===========DELETE MAIN FILES=========== if(deleted) { file.delete();
						 * logger.info("Deleted = " + file.getName() + " File"); }
						 */
					//}
				} catch (FileNotFoundException e) {
					log.error("File Not Found... " + e);
					ErrorPojo errorPojo = new ErrorPojo();
					com.realnet.fnd.entity.Error error = new com.realnet.fnd.entity.Error();
					error.setTitle(Constant.EXTRACTOR_API_TITLE);
					error.setMessage(Constant.STATIC_EXTRACTION_FAILED);
					errorPojo.setError(error);
					return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
				}
			}
		}
		
		SuccessPojo successPojo = new SuccessPojo();
		Success success = new Success();
		success.setTitle(Constant.EXTRACTOR_API_TITLE);
		success.setMessage(Constant.STATIC_EXTRACTION_SUCCESS);
		successPojo.setSuccess(success);
		log.debug("Response {} ", successPojo);
		return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.OK);
		
	}
	
}
