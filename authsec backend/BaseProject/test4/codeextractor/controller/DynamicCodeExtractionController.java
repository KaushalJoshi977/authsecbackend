package com.realnet.codeextractor.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.boot.model.source.spi.CascadeStyleSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.realnet.codeextractor.entity.Rn_Bcf_Exception_Rules;
import com.realnet.codeextractor.entity.Rn_Bcf_Extractor;
import com.realnet.codeextractor.entity.Rn_Bcf_Extractor_Params;
import com.realnet.codeextractor.entity.Rn_Bcf_Rules;
import com.realnet.codeextractor.service.Rn_Bcf_Exception_Rule_Library_Service;
import com.realnet.codeextractor.service.Rn_Bcf_Extractor_Params_Service;
import com.realnet.codeextractor.service.Rn_Bcf_Extractor_Service;
import com.realnet.codeextractor.service.Rn_Bcf_Rule_Library_Service;
import com.realnet.fnd.entity.Error;
import com.realnet.fnd.entity.ErrorPojo;
import com.realnet.fnd.entity.Success;
import com.realnet.fnd.entity.SuccessPojo;
import com.realnet.logging.LogExecutionTime;
import com.realnet.utils.Constant;
import com.realnet.utils.RealNetUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { "Code Extractor" })
public class DynamicCodeExtractionController {
	@Value("${projectPath}")
	private String projectPath;

	@Autowired
	private Rn_Bcf_Extractor_Service rn_bcf_extractor_service;

	@Autowired
	private Rn_Bcf_Extractor_Params_Service rn_bcf_extractor_params_service;

	@Autowired
	private Rn_Bcf_Rule_Library_Service bcfRuleLibraryService;

//	@Autowired
//	private RealNetUtils utils;

	@Autowired
	Rn_Bcf_Exception_Rule_Library_Service rn_bcf_rule_exception_library_service;

	@LogExecutionTime
	@ApiOperation(value = "Dynamic Code Extraction")
	@GetMapping(value = "/dynamic_code_extraction")
	public ResponseEntity<?> dynamicCodeExtraction(@RequestParam(value = "header_id") Integer headerId)
			throws IOException 
	{

		Rn_Bcf_Extractor extractor = rn_bcf_extractor_service.getById(headerId);
		String technology_stack = extractor.getTech_stack();
		String object_type = extractor.getObject_type();
		String sub_object_type = extractor.getSub_object_type();

		System.out.println("FROM Rn_Bcf_Extractor = " + technology_stack + " || " + object_type + " || " + sub_object_type+"||"+headerId);

		// PARAMS
		List<Rn_Bcf_Extractor_Params> params = extractor.getRn_bcf_extractor_Params();
//
//		// FILE NAME VARIABLE NAMES IN BUILDER CODE
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("controller_name_first_upper", "Nil_final_controller");
//		map.put("table_name_first_upper", "Rn_nil_final");
//		map.put("table_name_lower", "rn_nil_final");
//		map.put("table_name_upper", "RN_NIL_FINAL");
//		map.put("service_name_first_upper", "Nil_final_service");
//		map.put("service_name_lower", "nil_final_service");
//		map.put("service_impl_name_first_upper", "Nil_final_serviceimpl");
//		map.put("repository_name_first_upper", "Rn_header_test1_repository");
//		map.put("repository_name_lower", "rn_header_test1_repository");
//		map.put("ng_add_form_component_name", "AddRn_header_test1Component");
//		map.put("ng_all_grid_view_component_name", "AllRn_header_test1Component");
//		map.put("ng_read_only_component_name", "Rn_header_test1DetailsComponent");
//		map.put("ng_edit_component_name", "EditRn_header_test1Component");
//		map.put("ng_extension_add_component_name", "AddExtRn_header_test1Component");
//		map.put("ng_routing_module_ts_name", "Rn_header_test1RoutingModule");
//		map.put("ng_component_ts_name", "Rn_header_test1Component");
//		map.put("ng_module_ts_name", "Rn_header_test1Module");
//		map.put("ng_service_ts_name", "Rn_header_test1Service");
//		map.put("ng_model_ts_name ", "Rn_header_test1_t");

		// STATIC FILES ( SE_FILE_NAME.EXT)
		ArrayList<String> staticFiles = new ArrayList<String>();
		
		
		
		try {

			// PARAMETERS TABLE VALUE
			for (Rn_Bcf_Extractor_Params param : params) 
			{
//				System.out.println("---Exception rule for loop--");
				boolean is_extraction_enabled = param.isIs_extraction_enabled();
				boolean is_creation_enabled = param.isIs_creation_enabled();
				String path = param.getMoved_address_string();
				

				File file = new File(path);
				String parentPath = file.getParent();

				String name = file.getName();
				String ConvertedFileName = "SE_" + name;

				// STATIC FILES DIRECTORY
				String staticFileParentDir = parentPath + File.separator + "static_code";
				File staticFile = new File(staticFileParentDir + File.separator + ConvertedFileName);

				// STATIC FILE PATH
				String staticFileDir = staticFile.getAbsolutePath().replace("\\", "/");
				// ADD STATIC FILE PATH TO ARRAYLIST
				if (is_extraction_enabled && is_creation_enabled) {
					// System.out.println("STATIC FILE DIR ADDED TO ARRAYLIST = " + staticFileDir);
					staticFiles.add(staticFileDir);
					 
				}
			}

			// package part MODULE_NAME ADD
			
		
			for (String staticDir : staticFiles) 
			{
				
				File file = new File(staticDir);
				Path path = Paths.get(staticDir);
				StringBuilder code = new StringBuilder();
				List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
			
			
				for (String line : lines) 
				{
					
					if (line.startsWith("\"package")) 
					{
						int i = line.lastIndexOf(".");
						String head = line.substring(0, i + 1);
						String tail = line.substring(i);
						String moduleName = "\" + module_name + \"";
						line = head + moduleName + tail;
					}				
						code.append(line + "\n");
					
				}
				
				//System.out.println(code);
				BufferedWriter br = new BufferedWriter(new FileWriter(file)); // replaced string
				br.write(code.toString());
				br.close();
				
			}
			
				
				
			int count=0;
			for (String staticDir : staticFiles)
			{
				File file1 = new File(staticDir);
				Path path1 = Paths.get(staticDir);
				StringBuilder code = new StringBuilder();
				StringBuilder code2 = new StringBuilder();
				List<String> lines = Files.readAllLines(path1, StandardCharsets.UTF_8);
				for (String line : lines)
				{
					if (line.startsWith("\"public class"))
					{
//						
						String file_name_var=params.get(count).getFile_name_var();
						
//						System.out.println("file namevar "+file_name_var);
									
									
						line = "\"public class \" + "+file_name_var+""+1+" + \"{\"+";
					}
					
					
					
					 if (line.startsWith("\"public interface"))
					{
//						
						String file_name_var=params.get(count).getFile_name_var();
						
//						System.out.println("file namevar "+file_name_var);
									
									
						line = "\"public interface \" + "+file_name_var+""+1+" + \"{\"+";
					} 
					 
				 
					 if (line.startsWith("\"  templateUrl"))
					{
//							"  templateUrl: './readonly.component.html'," + "\r\n" + 	
								String file_name_var=params.get(count).getFile_name_var();
								
//								System.out.println("file namevar "+file_name_var);
											
											
								line = " \" templateUrl: './\"+"+file_name_var+""+1+"+\".html',\"" + "\r\n \n" +"+"  ;
					}
					 
					 if (line.startsWith("\"  styleUrls"))
						{
//							"  styleUrls: ['./readonly.component.scss']" + "\r\n" + 	
								String file_name_var=params.get(count).getFile_name_var();
								
//								System.out.println("file namevar "+file_name_var);
											
											
								line = " \"  styleUrls: ['./\"+"+file_name_var+""+1+"+\".scss']\"" + "\r\n \n"+"+"   ;
						}
					 
					 if (line.startsWith("\"export class"))
					{
//					"export class ReadonlyComponent implements OnInit {" + "\r\n" + 	
						String file_name_var=params.get(count).getFile_name_var();
						
//						System.out.println("file namevar "+file_name_var);
									
									
						line = "\"export class \"+"+file_name_var+""+2+"+\"Component implements OnInit {\"" + "\r\n"+"+"  ;
					}
				
					
					
					code.append(line + "\n");
					
				}
				count++;
				BufferedWriter br = new BufferedWriter(new FileWriter(file1)); // replaced string
				br.write(code.toString());
				br.close();
			}
			
			
			
				
		
			

		
			// APPLY ALL RULES PRESENT HERE
			List<Rn_Bcf_Rules> rules = bcfRuleLibraryService.getAll();
		
			int rulesOuterCount = 0;
			for (Rn_Bcf_Rules rule : rules) 
			{
				rulesOuterCount++;
				// System.out.println("RULES FOR COUNT = " + ++rulesOuterCount);
				String start = rule.getIdentifier_start_string();
				String end = rule.getIdentifier_end_string();
				String replaceWith = rule.getReplacement_string();
				
				String tech_stack = rule.getTech_stack();
				String sub_object_type2 = rule.getSub_object_type();
				String object_type2 = rule.getObject_type();
				replaceWith = replaceWith.concat("");
				
				if(technology_stack.equals(tech_stack) && sub_object_type.equals(sub_object_type2) && object_type.equals(object_type2))
				{
//					System.out.println("main tech stack"+technology_stack+"  new "+tech_stack);
//					System.out.println("main subobject  "+sub_object_type+"  new "+sub_object_type2);
//					System.out.println("main object  "+object_type+"  new "+object_type2);
					
					
				int dirCount = 0;
				for (String staticDir : staticFiles) 
				{
					dirCount++;
			
					File staticFile = new File(staticDir);
					String staticFileName = staticFile.getName();

					// System.out.println("========" + staticFileName + "=============");
					String fileString = FileUtils.readFileToString(staticFile, StandardCharsets.UTF_8);
					String fileType = FilenameUtils.getExtension(staticFileName);
				
					// don't check empty file for replacement..
					if (!fileString.isEmpty()) {
						
						// RULE APPLY
						String finalString = RealNetUtils.stringReplace(fileString, start, end, replaceWith,
								fileType);			

						BufferedWriter bw = new BufferedWriter(new FileWriter(staticFile, false)); // replaced
																									// string
						bw.write(finalString);
						bw.close();
						
					}
				}
			}
				
				
//				System.out.println("loop count::"+dirCount);

			}
			System.out.println("rules outer countt::"+rulesOuterCount);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error(); 
			error.setTitle(Constant.EXTRACTOR_API_TITLE);
			error.setMessage(Constant.DYNAMIC_EXTRACTION_FAILED);
			errorPojo.setError(error);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		}

		SuccessPojo successPojo = new SuccessPojo();
		Success success = new Success();
		success.setTitle(Constant.EXTRACTOR_API_TITLE);
		success.setMessage(Constant.DYNAMIC_EXTRACTION_SUCCESS);
		successPojo.setSuccess(success);
		log.debug("Response {} ", successPojo);
		return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.OK);

	}

}