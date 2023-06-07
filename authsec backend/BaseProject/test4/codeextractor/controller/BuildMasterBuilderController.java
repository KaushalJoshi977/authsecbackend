//package com.realnet.codeextractor.controller;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//
//import org.apache.commons.io.FileUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.realnet.codeextractor.entity.Rn_Bcf_Extractor;
//import com.realnet.codeextractor.entity.Rn_Bcf_Extractor_Params;
//import com.realnet.codeextractor.service.Rn_Bcf_Exception_Rule_Library_Service;
//import com.realnet.codeextractor.service.Rn_Bcf_Extractor_Params_Service;
//import com.realnet.codeextractor.service.Rn_Bcf_Extractor_Service;
//import com.realnet.codeextractor.service.Rn_Bcf_Rule_Library_Service;
//import com.realnet.flf.service.FieldTypeService;
//import com.realnet.fnd.entity.Error;
//import com.realnet.fnd.entity.ErrorPojo;
//import com.realnet.fnd.entity.Success;
//import com.realnet.fnd.entity.SuccessPojo;
//import com.realnet.fnd.service.Rn_ModuleSetup_Service;
//import com.realnet.utils.Constant;
//
//import io.swagger.annotations.Api;
//
//@RestController
//@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
//@Api(tags = { "Build Master Builder" })
//public class BuildMasterBuilderController {
//
//	@Value("${projectPath}")
//	private String projectPath;
//	
//	@Value("${angularProjectPath}")
//	private String angularProjectPath;
//	
//	@Autowired
//	private Rn_ModuleSetup_Service rn_module_setup_service;
//	
//	@Autowired
//	private Rn_Bcf_Extractor_Service rn_bcf_extractor_service;
//
//	@Autowired
//	private Rn_Bcf_Extractor_Params_Service rn_bcf_extractor_params_service;
//
//	@Autowired
//	private Rn_Bcf_Rule_Library_Service rn_bcf_rule_library_service;
//
//	@Autowired
//	private Rn_Bcf_Exception_Rule_Library_Service rn_bcf_rule_exception_library_service;
//	
//	@Autowired
//	private FieldTypeService fieldTypeService;
//	//private static final Logger logger = Logger.getLogger(BuildMasterBuilderController.class);
//	
//	@GetMapping("/build_master_builder")
//	public ResponseEntity<?> masterControllerBuilder(@RequestParam(value = "id") Integer id) throws IOException, FileNotFoundException {
//		StringBuilder variablesDynamicCode = new StringBuilder();
//		StringBuilder stringBuilderDynamicCode = new StringBuilder();
//		StringBuilder moduleCode = new StringBuilder();
//		
//
//		// RN_BCF_CODE_EXTRACTOR_T ID 
//		//int eid = Integer.parseInt(id);
//		
//		Rn_Bcf_Extractor extractor = rn_bcf_extractor_service.getById(id);
//		String technology_stack = extractor.getTech_stack();
//		String object_type = extractor.getObject_type();
//		String sub_object_type = extractor.getSub_object_type();
//		
//		// RN_BCF_CODE_EXTRACTOR_PARAMS_T VALUES
//		//List<Rn_Bcf_Extractor_Params> params = rn_bcf_extractor_params_service.getByHeaderId(eid);
//		List<Rn_Bcf_Extractor_Params> params = extractor.getRn_bcf_extractor_Params();
//		
//		// PARAMETERS TABLE VALUE
//		int i = 0;
//		int j=0;
//		for (Rn_Bcf_Extractor_Params param : params) 
//		{
//			boolean is_creation_enabled = param.isIs_creation_enabled();
//			boolean is_extraction_enabled = param.isIs_extraction_enabled();
//			String path = param.getMoved_address_string();
//			File file = new File(path);
//			String parentPath = file.getParent();
//
//			String name = file.getName();
//			String convertedFileName = "SE_" + name;
//
//			// STATIC CODE DIRECTORY
//			String staticFileParentDir = parentPath + File.separator + "static_code";
//			File staticFile = new File(staticFileParentDir + File.separator + convertedFileName);
//			String fileToString = FileUtils.readFileToString(staticFile, StandardCharsets.UTF_8);
//			
//			// ex. controller_file (FROM PARAMS TABLE)
//			String file_name_var = param.getFile_name_var(); 
//			
//			// ex. ui_name + "controller"
//			String file_name_dynamic_string = param.getFile_name_dynamic_string();
//			
//			// ex. String controller_file = ui_name + "controller";
//			variablesDynamicCode.append("String " + file_name_var + " = " + file_name_dynamic_string + ";\r\n");
//			
//			System.out.println("file name dynamic str  "+file_name_dynamic_string);
//			//change file  name  entity
//// 			String mainstr1=file_name_dynamic_string;
////			String saleesent1=mainstr1.replace(".java", " ");
////			System.out.println("updated filename"+saleesent1);
////			System.out.println(file_name_dynamic_string.contains(".java"));
////			System.out.println(file_name_dynamic_string.contains(".ts"));
////			System.out.println(file_name_dynamic_string.contains(".scss"));
////			System.out.println(file_name_dynamic_string.contains(".html"));
//	
//			if(file_name_dynamic_string.contains(".java")) {
//			variablesDynamicCode.append("String  mainstr"+j+" = " + file_name_var + ";\r\n"+
//					"String "+file_name_var+""+1+"=mainstr"+j+".replace(\".java\", \"\");\r\n"+
//					"" + "\r\n"+"\n" );
//			}else if(file_name_dynamic_string.contains(".ts")) {
//				variablesDynamicCode.append("String  mainstr"+j+" = " + file_name_var + ";\r\n"+
//						"String "+file_name_var+""+1+"=mainstr"+j+".replace(\".ts\", \"\");\r\n"+
//						"" + "\r\n"+ 
//						"String "+file_name_var+""+2+"=mainstr"+j+".replace(\".component.ts\", \"\");\r\n"+
//						"" + "\r\n"+"\n" 
//						);
//				
//				}else if(file_name_dynamic_string.contains(".html")) {
//				variablesDynamicCode.append("String  mainstr"+j+" = " + file_name_var + ";\r\n"+
//						"String "+file_name_var+""+1+"=mainstr"+j+".replace(\".html\", \"\");\r\n"+
//						"" + "\r\n"+"\n" );
//				}else if(file_name_dynamic_string.contains(".scss")) {
//				variablesDynamicCode.append("String  mainstr"+j+" = " + file_name_var + ";\r\n"+
//						"String "+file_name_var+""+1+"=mainstr"+j+".replace(\".scss\", \"\");\r\n"+
//						"" + "\r\n"+"\n" );
//				}else {System.out.println("not found");}
//				
//			
//			
//			// ======= MODULE NAME SHOULD COME FROM THE SESSION ========
//			//String moduleName = "\" + module_name + \"/\"";
//			String moduleName = "\" + module_name + \"/";
//			
//			String modulePath = param.getTotal_project_path_dynamic_string();
//			//System.out.println("MODULE PATH = " + modulePath);
//			if(modulePath.endsWith(".java")) {
//				String parent = modulePath.substring(0, modulePath.lastIndexOf("/")); // 1
//			    String lvl2Parent = parent.substring(0, parent.lastIndexOf("/")+1); // 2
//			    String tail0 = modulePath.substring(parent.lastIndexOf("/") +1); // 3
//			    tail0 = tail0.substring(0, tail0.lastIndexOf("/")+1); // remove the .java file name
//			    modulePath = lvl2Parent + moduleName + tail0;
//			} else {
//				// add module name in spring project
//				modulePath = modulePath.substring(0, modulePath.lastIndexOf("/")+1);
//				String data0 = modulePath.substring(0, modulePath.lastIndexOf("/") + 1);
//				String tail0 = modulePath.substring(modulePath.lastIndexOf("/") + 1);
//				data0 += moduleName + tail0;
//				modulePath = data0;
//			}
////			System.out.println("MANUPULATED module PATH = " + modulePath);
//
////			String ref_address_string = param.getReference_address_string();
////			ref_address_string = ref_address_string.substring(0, ref_address_string.lastIndexOf("/")+1);
//
//			String total_address_path = param.getTotal_project_path_dynamic_string();
//			total_address_path = total_address_path.substring(0, total_address_path.lastIndexOf("/")+1);
//				System.out.println("total path : "+total_address_path +"\n");
//			String module_dest_path = "";
//			String finalDir = "";
//			
//			
////				  module_dest_path = "angularProjectPath + \"" + modulePath;
////				  // CREATE MODULE FOLDER STRUCTURE [DYNAMIC PATH]
////				  moduleCode.append("File file" + (++i) + " = new File(" + module_dest_path +"\");\n" 
////							+ "if(!file" + (i) +".exists()) {\n"
////							+ "	file" + (i) + ".mkdirs();\n"
////							+ "}\n");
//				  
//				/*
//				 * MAKE {{test1}} dynamic
//				 * INPUT : /frontend/src/app/admin/test1/details/rn_header_test1-details.component.html
//				 * OUTPUT : /frontend/src/app/admin/{{test1}}/details/
//				 * 
//				 * */
//				// DYNAMIC FOLDER NAME LOGIC FOR ANGULAR
//				// start
////				final String ng_prj_struct = "/frontend/src/app/admin/";
////				if (ref_address_string.contains(ng_prj_struct)) {
////					int len = ng_prj_struct.length();
////				    String data = ref_address_string.substring(0,len) + "\" + ng_folder_name + \"";
////				    int tail = ref_address_string.indexOf("/", len + 1);
////				    String temp = ref_address_string.substring(tail);
////				    data += temp;
////				    ref_address_string = data;
////				}
////	
//				// end
//				String dest_path = "projectPath + \"" + "/Projects/\" + project_name + \"" + total_address_path;
//				System.out.println("dest path : "+dest_path);
//				// String finalDir = dirString + "/" + "\" + " + file_name_var;
//				finalDir = dest_path + "\" + " + file_name_var;
//				System.out.println(finalDir);
//			
//			
//				//module_dest_path = "projectPath + \"" + "/Projects/\" + project_name + " + modulePath;
////				String dest_path =  "projectPath + \"" + "/Projects/\" + project_name + \"" + modulePath;
//				
//				
//				// String finalDir = dirString + "/" + "\" + " + file_name_var;
//				finalDir = dest_path + "\" + " + file_name_var;
//				System.out.println("NIL FINAL DIR = " + finalDir+"\n");
//			
//			if(is_creation_enabled) {
//				StringBuilder fileCode = new StringBuilder();
//				// EMPTY FILE CODE WILL NOT GO IN THIS LOOP
//				if(fileToString.isEmpty()) {
//					fileCode.append(" " + file_name_var + "Code.append(\"" + fileToString + "\");\r\n");
//				} else {
//					fileCode.append(" " + file_name_var + "Code.append(" + fileToString + ");\r\n");
//				}
//				stringBuilderDynamicCode.append(" StringBuilder " + file_name_var + "Code = new StringBuilder();\r\n" 
//						//+ " " + file_name_var + "Code.append(" + fileToString + ");\r\n"
//						+ fileCode
//						+ "\r\n"
//						+ "	File " + file_name_var + "File = new File(" + finalDir + ");\r\n"
//						+ "			System.out.println(\"Directory name = \" + " + file_name_var + "File);\r\n"
//						//== CREATE PARENT DIR IF NOT EXIST===
//						+ "			File " + file_name_var + "FileParentDir = new File(" + file_name_var + "File.getParent());\r\n"
//						+ "			if(!" + file_name_var +"FileParentDir.exists()) {\r\n"
//						+ "				" + file_name_var + "FileParentDir.mkdirs();\r\n"
//						+ "			}\r\n"
//						//==
//						+ "			if (!" + file_name_var + "File.exists()) {\r\n"
//						+ "				" + file_name_var + "File.createNewFile();\r\n"
//						+ "			}\r\n"
//						+ "			fw = new FileWriter(" + file_name_var + "File.getAbsoluteFile());\r\n"
//						+ "			bw = new BufferedWriter(fw);\r\n"
//						+ "			bw.write(" + file_name_var + "Code.toString());\r\n" 
//						+ "			bw.close();\r\n"
//						+ "\r\n");
//				
//			}
//			j++;
//		}
//		
//		
//
//		// CHILD MASTER BUILDER NAME DEPENDS ON (TECH_STACK, OBJ_TYPE, SUB_OBJ_TYPE)
//		String childMasterBuilderName = technology_stack + "_" + object_type + "_" + sub_object_type + "_Builder";
//		childMasterBuilderName = childMasterBuilderName.replace(" ", "_");
//		childMasterBuilderName = childMasterBuilderName.replaceAll("[-]+", "_");
//
//		StringBuilder childMasterBuilderCode = new StringBuilder();
//		childMasterBuilderCode.append(
//				"package com.realnet.builders;\r\n" 
//				+ "\r\n"
//				+ "import java.io.BufferedReader;\r\n" 
//				+ "import java.io.BufferedWriter;\r\n"
//				+ "import java.io.File;\r\n" 
//				+ "import java.io.FileNotFoundException;\r\n"
//				+ "import java.io.FileReader;\r\n" 
//				+ "import java.io.FileWriter;\r\n"
//				+ "import java.io.IOException;\r\n"
//				+ "import java.util.List;\r\n"
//				+ "import java.util.stream.Collectors;\r\n"
//				+ "\r\n"
//				+ "import org.modelmapper.ModelMapper;\r\n"
//				+ "import org.springframework.beans.factory.annotation.Autowired;\r\n"
//				+ "import org.springframework.beans.factory.annotation.Value;\r\n"
//				+ "import org.springframework.http.HttpStatus;\r\n"
//				+ "import org.springframework.http.MediaType;\r\n" 
//				+ "import org.springframework.http.ResponseEntity;\r\n"
//				+ "import org.springframework.web.bind.annotation.GetMapping;\r\n" 
//				+ "import org.springframework.web.bind.annotation.RequestMapping;\r\n" 
//				+ "import org.springframework.web.bind.annotation.RequestParam;\r\n" 
//				+ "import org.springframework.web.bind.annotation.RestController;\r\n"
//				+ "\r\n"
//				+ "import com.realnet.actionbuilder.entity.Rn_cff_ActionBuilder_Header;\r\n"
//				+ "import com.realnet.actionbuilder.entity.Rn_cff_ActionBuilder_Header;\r\n" 
//				+ "import com.realnet.actionbuilder.service.Rn_Cff_ActionBuilder_Service;\r\n" 
//				+ "import com.realnet.flf.service.FieldTypeService;\r\n" 
//				+ "import com.realnet.fnd.entity.Error;\r\n" 
//				+ "import com.realnet.fnd.entity.ErrorPojo;\r\n" 
//				+ "import com.realnet.fnd.entity.Rn_Lookup_Values;\r\n" 
//				+ "import com.realnet.fnd.entity.Rn_Module_Setup;\r\n" 
//				+ "import com.realnet.fnd.entity.Rn_Project_Setup;\r\n" 
//				+ "import com.realnet.fnd.entity.Success;\r\n" 
//				+ "import com.realnet.fnd.entity.SuccessPojo;\r\n" 
//				+ "import com.realnet.fnd.service.Rn_LookUp_Service;\r\n" 
//				+ "import com.realnet.utils.Constant;\r\n" 
//				+ "import com.realnet.utils.RealNetUtils;\r\n" 
//				+ "import com.realnet.wfb.entity.Rn_Fb_Header;\r\n" 
//				+ "import com.realnet.wfb.entity.Rn_Fb_Line;\r\n" 
//				+ "import com.realnet.wfb.entity.Rn_Fb_Line_DTO;\r\n" 
//				+ "import com.realnet.wfb.service.Rn_WireFrame_Service;\r\n"
//				+ "\r\n" 
//				+ "import io.swagger.annotations.Api;\r\n"
//				+ "\r\n"
//				+ "@RestController\r\n"
//				// CONTROLLER NAME SHOULD CHANGE 
//				// DEPENDS ON TECH_STACK/OBJECT_tYPE/SUB_OBJECT_TYPE
//				+ "@RequestMapping(value = \"/api\", produces = MediaType.APPLICATION_JSON_VALUE)\r\n" 
//				+ "@Api(tags = { \"Master Builder\" })\r\n"
//				+ "public class " + childMasterBuilderName + " {\r\n" + "\r\n" + "\r\n"
//				+ " @Value(\"${angularProjectPath}\")\r\n" 
//				+ "	private String angularProjectPath;"
//				+ " @Value(\"${projectPath}\")\r\n" 
//				+ "	private String projectPath;"
//				
//				// DEPENDENCIES FOR WIREFRAME
//				+ " @Autowired\r\n" 
//				+ "	private Rn_WireFrame_Service wireFrameService;\r\n" + 
//				"\r\n" + 
//				"	@Autowired\r\n" 
//				+ "	private Rn_LookUp_Service lookUpService;\r\n" 
//				+ "\r\n" 
//				+ "	@Autowired\r\n" 
//				+ "	private Rn_Cff_ActionBuilder_Service actionBuilderService;\r\n" 
//				+ "\r\n" 
//				+ "	@Autowired\r\n" 
//				+ "	private ModelMapper modelMapper;\r\n" 
//				+ "\r\n" 
//				+ "	@Autowired\r\n" 
//				+ "	private FieldTypeService fieldTypeService;\r\n"
//				
//				+ "	@GetMapping(value = \"/" + childMasterBuilderName + "\")\r\n"
//				+ "	public ResponseEntity<?> build_wireframe(@RequestParam(\"header_id\") Integer id) throws IOException {\r\n" + "\r\n"
//				+ "				System.out.println(\"id ::\"+id);"
//				+ "\n			 lookUpService.createTable(id);"
//				+ " \n // extra button    \n    List<Rn_Fb_Line> extraButton = wireFrameService.getExtraButton(id);"
//				+ "	\n	// HEADER VALUE\r\n" 
//				+ "		Rn_Fb_Header rn_fb_header = wireFrameService.getById(id);\r\n" 
//				+ "		\r\n" 
//				+ "		// LINE VALUES\r\n" 
//				+ "		List<Rn_Fb_Line> rn_fb_lines = rn_fb_header.getRn_fb_lines();\r\n" 
//				+ "		\r\n" 
//				+ "		// MODULE DETAILS\r\n" 
//				+ "		Rn_Module_Setup module = rn_fb_header.getModule();\r\n" 
//				+ "		\r\n" 
//				+ "		// PROJECT DETAILS\r\n" 
//				+ "		Rn_Project_Setup project = module.getProject();\r\n" 
//				+ "		\r\n" 
//				// ATTRIBUTE FLEX
//				+ "		// ATTRIBUTE FLEX VALUES\r\n" 
//				+ "		List<Rn_Lookup_Values> attribute_flex_values = lookUpService.getExtensions();\r\n"
//				+ "		String project_name = project.getProjectName();\r\n" 
//				+ "		String module_name = module.getModuleName();"
//
//				// header table values
//				+ "		/*\r\n" 
//				+ "		 *	Header Table Values\r\n" 
//				+ "		 *  @GET UI_NAME\r\n"
//				+ "		 *  @SET controller, model, repository, service name.\r\n" 
//				+ "		 */\r\n"
//				+ "		String technology_stack = rn_fb_header.getTechStack();\r\n" 
//				+ "		String ui_name = RealNetUtils.toFirstUpperCase(rn_fb_header.getUiName());\r\n" 
//				+ "		String form_code = rn_fb_header.getFormCode(); // value will come from db\r\n"  
//				
//				+ "		String controller_name = ui_name.concat(\"_Controller\");\r\n"
//				+ "		String dao_name = ui_name.concat(\"_Dao\");\r\n"
//				+ "		String dao_name_lower = dao_name.toLowerCase();"
//				+ "		String dao_impl_name = ui_name.concat(\"_DaoImpl\");\r\n"
//				+ "		String repository_name = ui_name.concat(\"_Repository\");\r\n"
//				+ "		String service_name = ui_name.concat(\"_Service\");\r\n"
//				+ "		String service_impl_name = ui_name.concat(\"_ServiceImpl\");\r\n" + "\r\n"
//				+ "		String table_name = ui_name.concat(\"_t\"); // For @Column(table=\"table_name\") && Model class name\r\n"
//				+ "\r\n" 
//				//+ "		String line_table_name = ui_name.concat(\"_line_t\");\r\n" + "\r\n"
//				+ "		/*----First Upper names (back-end)----------*/\r\n"
//				+ "		String controller_name_first_upper = RealNetUtils.toFirstUpperCase(controller_name);\r\n"
//				+ "		String repository_name_first_upper = RealNetUtils.toFirstUpperCase(repository_name);\r\n"
//				+ "		String dao_name_first_upper = RealNetUtils.toFirstUpperCase(dao_name);\r\n"
//				+ "		String dao_impl_name_first_upper = RealNetUtils.toFirstUpperCase(dao_impl_name);\r\n"
//				+ "		String service_name_first_upper = RealNetUtils.toFirstUpperCase(service_name);\r\n"
//				+ "		String service_impl_name_first_upper = RealNetUtils.toFirstUpperCase(service_impl_name);\r\n"
//				+ "		String table_name_first_upper = RealNetUtils.toFirstUpperCase(table_name);\r\n"
//				+ "		String table_name_upper = table_name.toUpperCase(); // For Model class\r\n" + "\r\n"
//				//+ "		String line_table_name_first_upper = RealNetUtils.toFirstUpperCase(line_table_name);\r\n"
//				+ "		/*-------------lower names (back-end)----------*/\r\n"
//				+ "		String table_name_lower = table_name.toLowerCase();\r\n"
//				+ "		String repository_name_lower = repository_name.toLowerCase();\r\n"
//				+ "		String service_name_lower = service_name.toLowerCase();\r\n" 
//				+ "\r\n"
//				
//				//DTO APPROACH FOR CALLING A SERVICE
//				+ "		List<Rn_Fb_Line_DTO> lineListDto = rn_fb_lines.stream().map(line -> modelMapper.map(line, Rn_Fb_Line_DTO.class))\r\n" 
//				+ "				.collect(Collectors.toList());\r\n" 
//				+ "		\r\n" 
//				+ "		// set table name in the dto class\r\n" 
//				+ "		for(Rn_Fb_Line_DTO dto : lineListDto) {\r\n" 
//				+ "			dto.setTable_name(table_name_lower);\r\n" 
//				+ "		}\r\n"
//				
//				//  ======= NEED TO IMPLEMENT LOGIC FOR DIFFERENT TECHNOLOGY TYPE ====
//				); //  
//		
//	
//		// =========== FRONT-END OBJECT NAMES ==============
//		childMasterBuilderCode.append("// ===========FRONT END FILE NAMES DEPENDS ON UI NAME===============\r\n"
//				+ "		String ng_ui_name = RealNetUtils.toFirstUpperCase(ui_name);\r\n"
//				+ "		String ng_model_ts_name = ng_ui_name.concat(\"_t\");\r\n"
//				+ "		String ng_component_ts_name = ng_ui_name.concat(\"Component\");\r\n"
//				+ "		String ng_module_ts_name = ng_ui_name.concat(\"Module\");\r\n"
//				+ "		String ng_service_ts_name = ng_ui_name.concat(\"Service\");\r\n"
//				+ "		String ng_routing_module_ts_name = ng_ui_name.concat(\"RoutingModule\");\r\n" + "\r\n"
//				+ "		String ng_service_ts_name_lower = ui_name.toLowerCase().concat(\"Service\");\r\n"
//				+ "		String ng_model_ts_name_lower = ng_model_ts_name.toLowerCase();\r\n"
//				+ "		// Routing Path names\r\n"
//				+ "		String ng_path_name = ui_name.toLowerCase();\r\n"
//				+ "		// CRUD Components name\r\n"
//				+ "		String ng_all_grid_view_component_name = \"All\" + ng_ui_name.concat(\"Component\");\r\n"
//				+ "		String ng_add_form_component_name = \"Add\" + ng_ui_name.concat(\"Component\");\r\n"
//				+ "		String ng_edit_component_name = \"Edit\" + ng_ui_name.concat(\"Component\");\r\n"
//				+ "		String ng_read_only_component_name = ng_ui_name + \"DetailsComponent\";\r\n"
//				+ "		// Extension Components name\r\n"
//				+ "		String ng_extension_add_component_name = \"AddExt\" + ng_ui_name.concat(\"Component\");\r\n"
//				+ "		// File Folder name\r\n" 
//				+ "		String ng_folder_name = ui_name.toLowerCase();\r\n"
//				+ "		String ng_file_name = ui_name.toLowerCase();\r\n"
//				+ "\r\n");
//		
//		String action_builder_code = fieldTypeService.angular_action_builder_code();
//		
//		// DYNAMIC VARIABLE FOR FILE-NAME DEPENDS ON UI NAME
//		childMasterBuilderCode.append(
//				variablesDynamicCode // this is the file name with extension(from params table)
//				+ "\r\n"
//				// MASTER CONTROLLER CODE START
//				+ "			FileWriter fw = null;\r\n" 
//				+ "			BufferedWriter bw = null;\r\n"
//				+ "			try { \r\n" 
//				// ACTION BUILDER CODE
//				+ action_builder_code
//				+ // =========== VARIABLE CODE WILL BE APPEND HERE ===============//
//				stringBuilderDynamicCode
//				+ "\r\n");
//		
//		// UPDATE MENU
//			// String angular_update_sidenav = "";
//			childMasterBuilderCode.append(moduleCode +"\r\n" +
//					// 22:30 UPDATE SIDE BAR START
//					"/*-----------------------UPDATE ADMIN ROUTING TS FILE --------------------*/\r\n"
//					+ "		//	String frontEndDir = angularProjectPath.concat(\"/frontend/\");\n"
//					+ "			File adminRoutingModule = new File(projectPath+\"/Projects/\"+project_name+ \"/webui/src/app/app-routing.module.ts\");\r\n"
//					+ "			File tempRoutingModule = new File(projectPath+\"/Projects/\"+project_name+\"/webui/src/app/temp-routing.module.ts\");\r\n"
//					+ "\r\n" + 
//					"			BufferedReader reader = new BufferedReader(new FileReader(adminRoutingModule));\r\n" + 
//					"			BufferedWriter writer = new BufferedWriter(new FileWriter(tempRoutingModule));\r\n" + 
//					"			String removeStr = \"]}];@NgModule({imports: [RouterModule.forChild(routes)],exports: [RouterModule]})export class AdminRoutingModule{}\";\r\n" + 
//					"			String currentLine;\r\n" + 
//					"			System.out.println(adminRoutingModule.getName());\r\n" + 
//					"			while ((currentLine = reader.readLine()) != null) {\r\n" + 
//					"				String trimmedLine = currentLine.trim();\r\n" + 
//					"				if (trimmedLine.equals(removeStr)) {\r\n" + 
//					"					currentLine = \"\";\r\n" + 
//					"				}\r\n" + 
//					"				writer.write(currentLine + System.getProperty(\"line.separator\"));\r\n" + 
//					"\r\n" + 
//					"			}\r\n" + 
//					"			writer.close();\r\n" + 
//					"			reader.close();\r\n" + 
//					"			boolean delete = adminRoutingModule.delete();\r\n" + 
//					"			boolean b22 = tempRoutingModule.renameTo(adminRoutingModule);\r\n" + 
//					"\r\n" + 
//					"			StringBuilder admin_routing_module_string = new StringBuilder();\r\n" + 
//					"			admin_routing_module_string.append(\" \");\r\n" + 
//					"			String adminRoutingModuleName = projectPath+\"/Projects/\"+project_name+ \"/webui/src/app/app-routing.module.ts\";\r\n" + 
//					"\r\n" + 
//					"			fw = new FileWriter(adminRoutingModuleName, true);\r\n" + 
//					"			fw.write(admin_routing_module_string.toString());\r\n" + 
//					"			fw.close();\r\n" + 
//					"\r\n" 
//
//					);
//	
//		// MODULE CREATE CODE
//		childMasterBuilderCode.append("		} catch (FileNotFoundException e) {\r\n"
//				+ "			e.printStackTrace();\r\n" 
//				+ "			ErrorPojo errorPojo = new ErrorPojo();\r\n" 
//				+ "			Error error = new Error();\r\n" 
//				+ "			error.setTitle(Constant.FORM_BUILDER_API_TITLE);\r\n" 
//				+ "			error.setMessage(Constant.FORM_BUILD_FAILURE);\r\n" 
//				+ "			errorPojo.setError(error);\r\n" 
//				+ "			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);\r\n"
//				+ "		}\r\n"
//				+ "		SuccessPojo successPojo = new SuccessPojo();\r\n" 
//				+ "		Success success = new Success();\r\n" 
//				+ "		success.setTitle(Constant.FORM_BUILDER_API_TITLE);\r\n" 
//				+ "		success.setMessage(Constant.FORM_BUILD_SUCCESS);\r\n" 
//				+ "		successPojo.setSuccess(success);\r\n" 
//				+ "		return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.OK);\r\n" 
//				+ "\r\n"
//				+"\n}"
//				+"}");
//
//		// System.out.println(masterBuilderCode.toString());
//
//		FileWriter fw = null;
//		BufferedWriter bw = null;
//		try {
//			// FILE NAME SHOULD CHANGE DEPENDS ON TECH_STACK/OBJECT_tYPE/SUB_OBJECT_TYPE
//			File masterBuilderFile = new File(
//					projectPath + "/src/main/java/com/realnet/builders/" + childMasterBuilderName + ".java");
//			if (!masterBuilderFile.exists()) {
//				masterBuilderFile.createNewFile();
//			}
//			fw = new FileWriter(masterBuilderFile.getAbsoluteFile());
//			bw = new BufferedWriter(fw);
//			bw.write(childMasterBuilderCode.toString());
//			bw.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			ErrorPojo errorPojo = new ErrorPojo();
//			Error error = new Error();
//			error.setTitle(Constant.MASTER_BUILDER_API_TITLE);
//			error.setMessage(Constant.MASTER_BUILDER_FAILURE);
//			errorPojo.setError(error);
//			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
//		}
//		
//		SuccessPojo successPojo = new SuccessPojo();
//		Success success = new Success();
//		success.setTitle(Constant.MASTER_BUILDER_API_TITLE);
//		success.setMessage(Constant.MASTER_BUILDER_SUCCESS);
//		successPojo.setSuccess(success);
//		return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.CREATED);
//	}
//}
