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
import com.realnet.codeextractor.service.Rn_Bcf_Extractor_Service;
import com.realnet.flf.service.FieldTypeService;
import com.realnet.fnd.entity.Error;
import com.realnet.fnd.entity.ErrorPojo;
import com.realnet.fnd.entity.Success;
import com.realnet.fnd.entity.SuccessPojo;
import com.realnet.utils.Constant;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { "Build Master Builder" })
public class MasterCodeBuild_gk {
	@Value("${projectPath}")
	private String projectPath;

	@Value("${angularProjectPath}")
	private String angularProjectPath;

	@Autowired
	private Rn_Bcf_Extractor_Service rn_bcf_extractor_service;

	@Autowired
	private FieldTypeService fieldTypeService;
	// private static final Logger logger =
	// Logger.getLogger(BuildMasterBuilderController.class);

	@GetMapping("/build_master_builder")
	public ResponseEntity<?> masterControllerBuilder(@RequestParam(value = "id") Integer id)
			throws IOException, FileNotFoundException {
		StringBuilder variablesDynamicCode = new StringBuilder();
		StringBuilder stringBuilderDynamicCode = new StringBuilder();
//		StringBuilder stringBuilder = new StringBuilder();

		// RN_BCF_CODE_EXTRACTOR_T ID
		// int eid = Integer.parseInt(id);

		Rn_Bcf_Extractor extractor = rn_bcf_extractor_service.getById(id);
		String technology_stack = extractor.getTech_stack();
		String object_type = extractor.getObject_type();
		String sub_object_type = extractor.getSub_object_type();

		// RN_BCF_CODE_EXTRACTOR_PARAMS_T VALUES
		// List<Rn_Bcf_Extractor_Params> params =
		// rn_bcf_extractor_params_service.getByHeaderId(eid);
		List<Rn_Bcf_Extractor_Params> params = extractor.getRn_bcf_extractor_Params();

		int j = 0;
		for (Rn_Bcf_Extractor_Params param : params) {
			boolean is_creation_enabled = param.isIs_creation_enabled();
//			param.isIs_extraction_enabled();
			String path = param.getMoved_address_string();
			File file = new File(path);
			String parentPath = file.getParent();

			String name = file.getName();
			String convertedFileName = "SE_" + name;

			// STATIC CODE DIRECTORY
			String staticFileParentDir = parentPath + File.separator + "static_code";
			File staticFile = new File(staticFileParentDir + File.separator + convertedFileName);

			// HERE WE GET FILE INSIDE DATA
			String fileToString = FileUtils.readFileToString(staticFile, StandardCharsets.UTF_8);

			// ex. controller_file (FROM PARAMS TABLE)
			String file_name_var = param.getFile_name_var() + j;

			// ex. ui_name + "controller"
			String file_name_dynamic_string = param.getFile_name_dynamic_string();

			// ex. String controller_file = ui_name + "controller";
			variablesDynamicCode
					.append("String " + file_name_var + " = " + "\"" + file_name_dynamic_string + "\"" + ";\r\n");

			System.out.println("file name dynamic str  " + file_name_dynamic_string);
			// change file name entity
// 			String mainstr1=file_name_dynamic_string;
//			String saleesent1=mainstr1.replace(".java", " ");
//			System.out.println("updated filename"+saleesent1);
//			System.out.println(file_name_dynamic_string.contains(".java"));
//			System.out.println(file_name_dynamic_string.contains(".ts"));
//			System.out.println(file_name_dynamic_string.contains(".scss"));
//			System.out.println(file_name_dynamic_string.contains(".html"));

			// HERE WE CHECK AND APPEND FILE NAME
			if (file_name_dynamic_string.contains(".java")) {
				variablesDynamicCode
						.append("String  mainstr" + j + " = " + file_name_var + ";\r\n" + "String " + file_name_var + ""
								+ 1 + "=mainstr" + j + ".replace(\".java\", \"\");\r\n" + "" + "\r\n" + "\n");
			} else if (file_name_dynamic_string.contains(".ts")) {
				variablesDynamicCode.append("String  mainstr" + j + " = " + file_name_var + ";\r\n" + "String "
						+ file_name_var + "" + 1 + "=mainstr" + j + ".replace(\".ts\", \"\");\r\n" + "" + "\r\n"
						+ "String " + file_name_var + "" + 2 + "=mainstr" + j + ".replace(\".component.ts\", \"\");\r\n"
						+ "" + "\r\n" + "\n");

			} else if (file_name_dynamic_string.contains(".html")) {
				variablesDynamicCode
						.append("String  mainstr" + j + " = " + file_name_var + ";\r\n" + "String " + file_name_var + ""
								+ 1 + "=mainstr" + j + ".replace(\".html\", \"\");\r\n" + "" + "\r\n" + "\n");
			} else if (file_name_dynamic_string.contains(".scss")) {
				variablesDynamicCode
						.append("String  mainstr" + j + " = " + file_name_var + ";\r\n" + "String " + file_name_var + ""
								+ 1 + "=mainstr" + j + ".replace(\".scss\", \"\");\r\n" + "" + "\r\n" + "\n");
			} else {
				System.out.println("not found");
			}

			// ======= MODULE NAME SHOULD COME FROM THE SESSION ========
			// String moduleName = "\" + module_name + \"/\"";
			String moduleName = "\" + module_name + \"/";

			String modulePath = param.getTotal_project_path_dynamic_string();
			// System.out.println("MODULE PATH = " + modulePath);
			if (modulePath.endsWith(".java")) {
				String parent = modulePath.substring(0, modulePath.lastIndexOf("/")); // 1
				String lvl2Parent = parent.substring(0, parent.lastIndexOf("/") + 1); // 2
				String tail0 = modulePath.substring(parent.lastIndexOf("/") + 1); // 3
				tail0 = tail0.substring(0, tail0.lastIndexOf("/") + 1); // remove the .java file name
				modulePath = lvl2Parent + moduleName + tail0;
			} else {
				// add module name in spring project
				modulePath = modulePath.substring(0, modulePath.lastIndexOf("/") + 1);
				String data0 = modulePath.substring(0, modulePath.lastIndexOf("/") + 1);
				String tail0 = modulePath.substring(modulePath.lastIndexOf("/") + 1);
				data0 += moduleName + tail0;
				modulePath = data0;
			}
//			System.out.println("MANUPULATED module PATH = " + modulePath);

//			String ref_address_string = param.getReference_address_string();
//			ref_address_string = ref_address_string.substring(0, ref_address_string.lastIndexOf("/")+1);

			String total_address_path = param.getTotal_project_path_dynamic_string();
			total_address_path = total_address_path.substring(0, total_address_path.lastIndexOf("/") + 1);
			System.out.println("total path : " + total_address_path + "\n");
			String finalDir = "";

//	
			// here we set file path
			String dest_path = "projectPath + \"" + "/Projects/\" + project_name + \"" + total_address_path;
			System.out.println("dest path : " + dest_path);
			// String finalDir = dirString + "/" + "\" + " + file_name_var;
			finalDir = dest_path + "\" + " + file_name_var;
			System.out.println(finalDir);

			// module_dest_path = "projectPath + \"" + "/Projects/\" + project_name + " +
			// modulePath;
//				String dest_path =  "projectPath + \"" + "/Projects/\" + project_name + \"" + modulePath;

			// String finalDir = dirString + "/" + "\" + " + file_name_var;
			finalDir = dest_path + "\" + " + file_name_var;
			System.out.println("NIL FINAL DIR = " + finalDir + "\n");

			if (is_creation_enabled) {
				StringBuilder fileCode = new StringBuilder();

				if (file_name_dynamic_string.contains("entity")) {
//					String entity = entity(path, "test");
					if (fileToString.isEmpty()) {
						fileCode.append(" " + file_name_var + "Code.append(\"" + fileToString + "\");\r\n");
					} else {
						fileCode.append(" " + file_name_var + "Code.append(" + fileToString + ");\r\n");
					}

				} else if (file_name_dynamic_string.contains("repository")
						|| file_name_dynamic_string.contains("repo")) {
//					String repo = repo(path, "test");
					if (fileToString.isEmpty()) {
						fileCode.append(" " + file_name_var + "Code.append(\"" + fileToString + "\");\r\n");
					} else {
						fileCode.append(" " + file_name_var + "Code.append(" + fileToString + ");\r\n");
					}

				} else if (file_name_dynamic_string.contains("service")) {
//					String service = service(path, "test");
					if (fileToString.isEmpty()) {
						fileCode.append(" " + file_name_var + "Code.append(\"" + fileToString + "\");\r\n");
					} else {
						fileCode.append(" " + file_name_var + "Code.append(" + fileToString + ");\r\n");
					}

				} else if (file_name_dynamic_string.contains("controller")) {
//					String controller = controller(path, "test");
					if (fileToString.isEmpty()) {
						fileCode.append(" " + file_name_var + "Code.append(\"" + fileToString + "\");\r\n");
					} else {
						fileCode.append(" " + file_name_var + "Code.append(" + fileToString + ");\r\n");
					}

				} else {
//					String other = other(path, "test");

					if (file_name_dynamic_string.isEmpty()) {
						fileCode.append(" " + file_name_var + "Code.append(\"" + fileToString + "\"\r\n");
					} else {
						fileCode.append(" " + file_name_var + "Code.append(" + fileToString + "\r\n");
					}

				}
				// EMPTY FILE CODE WILL NOT GO IN THIS LOOP

				stringBuilderDynamicCode.append(" StringBuilder " + file_name_var + "Code = new StringBuilder();\r\n"
				// + " " + file_name_var + "Code.append(" + fileToString + ");\r\n"
						+ fileCode + "\r\n"

						+ "	File " + file_name_var + "File = new File(" + finalDir + ");\r\n"
						+ "	System.out.println(\"Directory name = \" + " + file_name_var + "File);\r\n"
						// == CREATE PARENT DIR IF NOT EXIST===
						+ "	File " + file_name_var + "FileParentDir = new File(" + file_name_var
						+ "File.getParent());\r\n" + "	if(!" + file_name_var + "FileParentDir.exists()) {\r\n" + "	"
						+ file_name_var + "FileParentDir.mkdirs();\r\n" + "			}\r\n"
						// ==
						+ "	if (!" + file_name_var + "File.exists()) {\r\n" + "				" + file_name_var
						+ "File.createNewFile();\r\n" + "			}\r\n" + "			" + "fw = new FileWriter("
						+ file_name_var + "File.getAbsoluteFile());\r\n" + "	bw = new BufferedWriter(fw);\r\n"
						+ "		" + "	bw.write(" + file_name_var + "Code.toString());\r\n" + "	bw.close();\r\n"
						+ "\r\n");

			}
			j++;
		}

		// CHILD MASTER BUILDER NAME DEPENDS ON (TECH_STACK, OBJ_TYPE, SUB_OBJ_TYPE)
		String childMasterBuilderName = technology_stack + "_" + object_type + "_" + sub_object_type + "_Builder";
		childMasterBuilderName = childMasterBuilderName.replace(" ", "_");
		childMasterBuilderName = childMasterBuilderName.replaceAll("[-]+", "_");

		StringBuilder childMasterBuilderCode = new StringBuilder();
//		String action_builder_code = fieldTypeService.angular_action_builder_code();

		childMasterBuilderCode.append("package com.realnet.builders;\r\n" + "\r\n"
				+ "import java.io.BufferedWriter;\r\n" + "import java.io.File;\r\n" + "import java.io.FileWriter;\r\n"
				+ "import java.io.IOException;\r\n" + "import java.util.ArrayList;\r\n" + "import java.util.Date;\r\n"
				+ "import java.util.List;\r\n" + "import java.util.Optional;\r\n" + "\r\n"
				+ "import org.modelmapper.ModelMapper;\r\n"
				+ "import org.springframework.beans.factory.annotation.Autowired;\r\n"
				+ "import org.springframework.beans.factory.annotation.Value;\r\n"
				+ "import org.springframework.http.HttpStatus;\r\n" + "import org.springframework.http.MediaType;\r\n"
				+ "import org.springframework.http.ResponseEntity;\r\n"
				+ "import org.springframework.web.bind.annotation.GetMapping;\r\n"
				+ "import org.springframework.web.bind.annotation.PathVariable;\r\n"
				+ "import org.springframework.web.bind.annotation.RequestMapping;\r\n"
				+ "import org.springframework.web.bind.annotation.RestController;\r\n" + "\r\n"
				+ "import com.google.gson.JsonElement;\r\n" + "import com.google.gson.JsonObject;\r\n"
				+ "import com.google.gson.JsonParser;\r\n" + "import com.google.gson.JsonArray;\r\n" + "\r\n"
				+ "import com.realnet.actionbuilder.service.Rn_Cff_ActionBuilder_Service;\r\n"
				+ "import com.realnet.flf.service.FieldTypeService;\r\n"
				+ "import com.realnet.fnd.service.Rn_LookUp_Service;\r\n"
				+ "import com.realnet.formdrag.repository.Rn_wf_lines_3Repository;\r\n"
				+ "import com.realnet.formdrag.entity.Rn_wf_lines_3;\r\n" + ""
				+ "import com.realnet.wfb.service.Rn_WireFrame_Service;\r\n" + "\r\n"
				+ "import io.swagger.annotations.Api;"

				+ "\r\n" + "@RestController\r\n"
				// CONTROLLER NAME SHOULD CHANGE
				// DEPENDS ON TECH_STACK/OBJECT_tYPE/SUB_OBJECT_TYPE
				+ "@RequestMapping(value = \"/api\", produces = MediaType.APPLICATION_JSON_VALUE)\r\n"
				+ "@Api(tags = { \"Master Builder\" })\r\n" + "public class " + childMasterBuilderName + " {\r\n"
				+ "\r\n" + "\r\n" + " @Value(\"${angularProjectPath}\")\r\n" + "	private String angularProjectPath;"
				+ " @Value(\"${projectPath}\")\r\n" + "	private String projectPath;"

				// DEPENDENCIES FOR WIREFRAME
				+ " @Autowired\r\n" + "	private Rn_WireFrame_Service wireFrameService;\r\n" + "\r\n" +

				"	@Autowired\r\n" + "	private Rn_LookUp_Service lookUpService;\r\n" + "@Autowired\r\n"
				+ "	private Rn_wf_lines_3Repository repo;\n"

				+ "\r\n" + "	@Autowired\r\n" + "	private Rn_Cff_ActionBuilder_Service actionBuilderService;\r\n"
				+ "\r\n" + "	@Autowired\r\n" + "	private ModelMapper modelMapper;\r\n" + "\r\n" + "	@Autowired\r\n"
				+ "	private FieldTypeService fieldTypeService;\r\n\n\n" + "@GetMapping(value = \"/"
				+ childMasterBuilderName + "/{header_id}\")\r\n"
				+ "	public ResponseEntity<?> createbyjson(@PathVariable Integer header_id) throws IOException {\r\n"
				+ "		Optional<Rn_wf_lines_3> wireframe = repo.findheader(header_id);\r\n" + "\r\n"
				+ "		List<String> tablename = new ArrayList<>();\r\n"
				+ "		List<String> entityname = new ArrayList<>();\r\n" + "\r\n"
				+ "		JsonParser parser = new JsonParser();\r\n"
				+ "		JsonElement element = parser.parse(wireframe.get().getModel());\r\n"
				+ "		JsonObject jsonObject = element.getAsJsonObject();\r\n" + "\r\n"
				+ "		JsonElement name = jsonObject.get(\"name\");\r\n" + "		System.out.println(name);\r\n"
				+ "		tablename.add(name.getAsString());\r\n" + "\r\n"
				+ "		JsonElement desc = jsonObject.get(\"description\");\r\n"
				+ "		System.out.println(desc);\r\n" + "//		keys.add(\"desc :\"+desc.getAsString());\r\n"
				+ "\r\n" + "		JsonElement element2 = jsonObject.get(\"attributes\");\r\n"
				+ "		System.out.println(element2);\r\n" + "\r\n"
				+ "		JsonArray jsonArray = element2.getAsJsonArray();\r\n"
				+ "		System.out.println(jsonArray);\r\n" + "\r\n" + "		for (JsonElement ar : jsonArray) {\r\n"
				+ "\r\n" + "			JsonObject obj = ar.getAsJsonObject();\r\n" + "\r\n"
				+ "			JsonElement type = obj.get(\"type\");\r\n" + "			System.out.println(type);\r\n"
				+ "//			keys.add(\"type :\"+type.getAsString());\r\n" + "\r\n"
				+ "			JsonElement description = obj.get(\"description\");\r\n"
				+ "			System.out.println(description);\r\n"
				+ "//			keys.add(\"description :\"+description.getAsString());\r\n" + "\r\n"
				+ "			JsonElement placeholder = obj.get(\"placeholder\");\r\n"
				+ "//			System.out.println(placeholder);\r\n"
				+ "//			keys.add(\"placeholder :\"+placeholder.getAsString());\r\n" + "\r\n"
				+ "			JsonElement label = obj.get(\"label\");\r\n" + "			System.out.println(label);\r\n"
				+ "			entityname.add(label.getAsString());\r\n" + "\r\n" + "		}\r\n" + "\r\n"
				+ "		Date d = new Date();\r\n" + "		String addString = \"_\";\r\n" + "\r\n"
				+ "		// CALL BACKEND\r\n"
//				+ "		backendservice.buildbackend(tablename, entityname, addString);\r\n" + "\r\n"
//				+ "		// CALL FRONTEND\r\n"
//				+ "		frontendservice.buildFrontend(tablename, entityname, addString);\r\n"
//				+ ""

//				+ "\r\n" + "				System.out.println(\"id ::\"+id);"
//				+ "\n			 lookUpService.createTable(id);"
//				+ " \n // extra button    \n    List<Rn_Fb_Line> extraButton = wireFrameService.getExtraButton(id);"
//				+ "	\n	// HEADER VALUE\r\n" + "		Rn_Fb_Header rn_fb_header = wireFrameService.getById(id);\r\n"
//				+ "		\r\n" + "		// LINE VALUES\r\n"
//				+ "		List<Rn_Fb_Line> rn_fb_lines = rn_fb_header.getRn_fb_lines();\r\n" + "		\r\n"
//				+ "		// MODULE DETAILS\r\n" + "		Rn_Module_Setup module = rn_fb_header.getModule();\r\n"
//				+ "		\r\n" + "		// PROJECT DETAILS\r\n"
//				+ "		Rn_Project_Setup project = module.getProject();\r\n" + "		\r\n"
//				// ATTRIBUTE FLEX
//				+ "		// ATTRIBUTE FLEX VALUES\r\n"
//				+ "		List<Rn_Lookup_Values> attribute_flex_values = lookUpService.getExtensions();\r\n"
//				+ "		String project_name = project.getProjectName();\r\n"
				+ "		String project_name =" + "\"" + "test" + j + "\"" + ";\r\n"

//				+ "		String module_name = module.getModuleName();\r\n\n"
				+ "\r\r\rFileWriter fw = null;\r\n" + "BufferedWriter bw = null;\r\n" + variablesDynamicCode);
		childMasterBuilderCode.append(""

				// ACTION BUILDER CODE

				+ // =========== VARIABLE CODE WILL BE APPEND HERE ===============//
				"\n" + stringBuilderDynamicCode.toString() + "\r\n"
				+ "	return new ResponseEntity<>(\"created\", HttpStatus.CREATED);\r\n" + "}\r\n }");

//		
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			// FILE NAME SHOULD CHANGE DEPENDS ON TECH_STACK/OBJECT_tYPE/SUB_OBJECT_TYPE
			File masterBuilderFile = new File(
					projectPath + "/src/main/java/com/realnet/builders/" + childMasterBuilderName + ".java");
			if (!masterBuilderFile.exists()) {
				masterBuilderFile.createNewFile();
			}
			fw = new FileWriter(masterBuilderFile.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			bw.write(childMasterBuilderCode.toString());
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.MASTER_BUILDER_API_TITLE);
			error.setMessage(Constant.MASTER_BUILDER_FAILURE);
			errorPojo.setError(error);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		}

		SuccessPojo successPojo = new SuccessPojo();
		Success success = new Success();
		success.setTitle(Constant.MASTER_BUILDER_API_TITLE);
		success.setMessage(Constant.MASTER_BUILDER_SUCCESS);
		successPojo.setSuccess(success);
		return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.CREATED);
	}

	public String entity(String path, String classname) throws IOException {

		return UpdateEntity(path, classname);

	}

	public String repo(String path, String classname) throws IOException {
		return UpdateRepo(path, classname);

	}

	public String service(String path, String classname) throws IOException {
		return UpdateService(path, classname);

	}

	public String controller(String path, String classname) throws IOException {
		return UpdateController(path, classname);

	}

	public String other(String path, String classname) throws IOException {

		return UpdateRepo(path, classname);

	}

	public String UpdateEntity(String path, String classname) throws IOException {

//		String path = "C:\\Users\\Aniket\\Documents\\Entity.txt";
//		String classname = "gk";
		String addition = "for (int i = 0; i < entityname.size(); i++) {\r\n"
				+ "			String string = entityname.get(i);\r\n"
				+ "			String lowerCase = string.replaceAll(\" \", \"_\").toLowerCase();\r\n"
				+ "			String add = \"\\n private \" + \"String\" + \" \" + lowerCase + \";\";\r\n"
				+ "			intialize.append(add);\r\n"
				+ "		}";
		/*
		 * RandomAccessFile writer=new
		 * RandomAccessFile("C:/Users/lenovo/Documents/demo.txt","rw");
		 *
		 * writer.seek(position); writer.writeBytes(addition); writer.close();
		 */
		String line = "";
		StringBuilder intialize = new StringBuilder();
		StringBuilder class_name = new StringBuilder();
		StringBuilder middle = new StringBuilder();
		StringBuilder end = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(path));
		while ((line = br.readLine()) != null) {
			String[] data = line.split(",");
			for (String d : data) {
				if (d.contains("import") || d.contains("@Data") || d.contains("@Entity")) {
					intialize.append("\"");
					intialize.append(d);
					intialize.append("\"+\n");
				} else if (d.contains("public")) {
					
					class_name.append("\"public class "+classname +" {\"+ \n");
//					intialize.append("\"+\n");
				} else if (d.contains("}")) {
					intialize.append("\"");
					end.append(d);
					intialize.append("\"+\n");
				}

			}
		}
		br.close();
		
		middle.append(addition);
		StringBuilder finalstring = new StringBuilder();
		finalstring.append("\"StringBuilder intialize = new StringBuilder();\"+\r\n"
				+ "\" intialize.append(\"+");
		finalstring.append(intialize.toString());
		finalstring.append(class_name.toString());
		finalstring.append("\t\"@Id\"+\r\n"
				+ "\"	@GeneratedValue(strategy = GenerationType.IDENTITY)\"+\r\n"
				+ "\"	private int id;\");\n");
		finalstring.append(middle.toString());
		
		

		return finalstring.toString();
	}

	public String UpdateRepo(String path, String classname) throws IOException {

		String line = "";
		StringBuilder intialize = new StringBuilder();
		StringBuilder class_name = new StringBuilder();
		StringBuilder middle = new StringBuilder();
		StringBuilder end = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(path));
		intialize.append("\"");
		
		
		while ((line = br.readLine()) != null) {
			String[] data = line.split(",");
			for (String d : data) {
				if (d.contains("import") || d.contains("@Repository") ) {
					intialize.append("\""+d);
					intialize.append("\"+\n");
				} else if (d.contains("public")) {
					class_name.append("\""+"public class "+classname +" extends JpaRepository<"+classname + ", Long> " +"{"+"\"+");
					class_name.append("\n");
				} 

			}
		}
		br.close();

		StringBuilder finalstring = new StringBuilder();
		finalstring.append("	\""+"	StringBuilder repo = new StringBuilder();"+"\"+\n"
		+"	\""+""
						+" "+ "repo.append(");
		finalstring.append(intialize.toString());
		finalstring.append(class_name.toString());
		finalstring.append("\"+}\"");
		finalstring.append("\");"+"\"");
		
		return finalstring.toString();
	}

	public String UpdateService(String path, String classname) throws IOException {

		String line = "";
		String apiName ="Api";
		
		StringBuilder intialize = new StringBuilder();
		StringBuilder class_name = new StringBuilder();
		StringBuilder middle = new StringBuilder();
		StringBuilder end = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(path));

		intialize.append("\"import java.util.List;\"+\r\n"
				+ "\r\n" + "\"import org.springframework.beans.factory.annotation.Autowired;\"+\r\n"
				+ "\"	import org.springframework.stereotype.Service;\"+\n");

		intialize.append("\n\"@Service\"+\n");
		
		intialize.append("\"@RequestMapping(value = " +"\"/"+ apiName+ "\")\"+\n"
				+ "\"@RestController\"+\r\n");
		
		

			class_name.append("\"public class "+  classname +"Service {\"+\r\n"

				+ "\"	@Autowired\"+\r\n"
				+ "\"	private " + classname+ "Service Service;\"+\n");
				
		
				middle.append(
						"\" @Autowired\r\n" + "private " + classname + "Repository " + "Repository;\"+\n");

				middle.append("\"public "+  classname +" Savedata("+classname+ " data) {\"+\r\n"
						+ "	\"			return Repository.save(data);\"+	\r\n" + "	\"		}\"+\r\n" + "\r\n"
						+ "	\"		\r\n" + "public List<" + classname + "> getdetails() {\"+\r\n"
						+ "	\"			return (List<" + classname + ">) Repository.findAll();\"+\r\n" + "			}\r\n"
						+ "\r\n" + "\r\n" + "\"public "+  classname +" getdetailsbyId(Long id) {\"+\r\n"
						+ "\"	return Repository.findById(id).get();\"+\r\n" + "\"			}\"+\r\n" + "\r\n" + "\r\n"
						+ "\"	public void delete_by_id(Long id) {\"+\r\n"
						+ "\" Repository.deleteById(id);\"+\r\n" + "}\"+\r\n" + "\r\n" + "\r\n");

			middle.append("\"public "+  classname +" update("+  classname +" data,Long id) {\"+\n"
					+ "	\""+  classname +" old = Repository.findById(id).get();\"+\n");
//			middle.append("	for (EntityBuild en : ent) {
//				String name = en.getName();
//				
//				String string = name.substring(0,1).toUpperCase()+name.substring(1);
//				middle.append("old.set"+string+ "(data.get"+string+"());\r\n");
//			}
			middle.append("\"final "+  classname +" test = Repository.save(old);\"+\r\n"
					+ "	\"	return test;\"+"
					+ "\"}\"+"
					+ "\"}\"+");
		
		

		
		br.close();

		StringBuilder finalstring = new StringBuilder();
		finalstring.append("\"		StringBuilder service = new StringBuilder();\"+\r\n"
				+ "\" service.append(\"+");
		finalstring.append(intialize.toString());
		finalstring.append(class_name.toString());
		finalstring.append(middle.toString());
		finalstring.append("\");");
		
	//	
//		FileWriter fw = null;
//		BufferedWriter bw = null;
//		File masterBuilderFile = new File(newpath + "test" + ".java");
//		if (!masterBuilderFile.exists()) {
//			masterBuilderFile.createNewFile();
//		}
//		fw = new FileWriter(masterBuilderFile.getAbsoluteFile());
//		bw = new BufferedWriter(fw);
//		bw.write(finalstring.toString());
//		bw.close();
		
		
		return finalstring.toString();
	}

	public String UpdateController(String path, String classname) throws IOException {

		String line = "";
		String apiName ="Api";
		
		StringBuilder intialize = new StringBuilder();
		StringBuilder class_name = new StringBuilder();
		StringBuilder middle = new StringBuilder();
		StringBuilder end = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(path));
		
		
		intialize.append("import org.springframework.beans.factory.annotation.Autowired;\"+\r\n"
				+ "\" import org.springframework.web.bind.annotation.DeleteMapping;\"+\r\n"
				+ "\" import org.springframework.web.bind.annotation.GetMapping;\"+\r\n"
				+ "\" import org.springframework.web.bind.annotation.PathVariable;\"+\r\n"
				+ "\" import org.springframework.web.bind.annotation.PostMapping;\"+\r\n"
				+ "\" import org.springframework.web.bind.annotation.PutMapping;\"+\r\n"
				+ "\" import org.springframework.web.bind.annotation.RequestBody;\"+\r\n"
				+ "\" import org.springframework.web.bind.annotation.RequestParam;\"+\r\n"
				+ "\" import org.springframework.web.bind.annotation.RestController;\"+\r\n"
				+ "\" import org.springframework.web.bind.annotation.*;\"+\r\n");
		
		intialize.append("\" @RequestMapping(value = " +"\"/"+ apiName+ "\")\"+\n"
				+ "\" @RestController \"+\r\n ");
				class_name.append("\" public class "+  classname +"Controller {\"+\r\n"

				+ "\"	@Autowired \"+ \r\n"
				+ "\"	private " + classname+ "Service Service; \"+\n");
				
			middle.append( "\"	@PostMapping("+"\"/" + classname+")\"+\r\n"
				+ "	\r\n"
				+ "	\"  public " + classname+ " Savedata(@RequestBody " + classname+ " data) { \"+ \r\n"
				+ "	\"	" + classname+ " save = Service.Savedata(data)	;\"+\r\n"
				+ "	\"	 return save;\"+\r\n"
				+ "	 \"+ }\"+\r\n"
//				+ "	\"	 \r\n \"+"
//				+ "	\" \r\n \"+"
				+ "	\" @GetMapping(\"/" + classname+ "\")\"+\r\n"
				+ "	\" public List<" + classname+ "> getdetails() { \"+ \r\n"
				+ "	\"	 List<" + classname+ "> get = Service.getdetails();	\"+	\r\n"
				+ "	\"	return get;\"+\r\n\" } \"+\n"
				+ "\" @GetMapping(\"/" + classname+ "/{id}\")\"+\r\n"
				+ " \"	public  " + classname+ "  getdetailsbyId(@PathVariable Long id ) {\"+\r\n"
				+ "	\"	" + classname+ "  get = Service.getdetailsbyId(id);\"+\r\n"
				+ "\"		return get;\"+\r\n"
				+ "	\" }\"+\n"
				+ "\" @DeleteMapping(\"/" + classname+ "/{id}\")\"+\r\n"
				+ "	\" public  void delete_by_id(@PathVariable Long id ) {\"+\r\n"
				+ "	\" Service.delete_by_id(id);\"+\r\n"
//				+ "	\" \"+	\r\n"
				+ "	\"\"+ }\n"
				+ "\" @PutMapping(\"/" + classname+"/{id}\")\"+\r\n"
				+ "\"	public  " + classname+ " update(@RequestBody " + classname+ " data,@PathVariable Long id ) {\"+\r\n"
				+ "	\"	" + classname+ " update = Service.update(data,id);\"+\r\n"
				+ "\"		return update;\"+\r\n"
				+ "\"	}\"+\n\"}\"+");
		
		

		
		br.close();

		StringBuilder finalstring = new StringBuilder();
		finalstring.append("\"		StringBuilder controller = new StringBuilder();\"+\r\n"
				+ "\"controller.append(");
		finalstring.append(intialize.toString());
		finalstring.append(class_name.toString());
		finalstring.append(middle.toString());
//		finalstring.append("}");
		finalstring.append("\");");
		
	//	
//		FileWriter fw = null;
//		BufferedWriter bw = null;
//		File masterBuilderFile = new File(newpath + "test" + ".java");
//		if (!masterBuilderFile.exists()) {
//			masterBuilderFile.createNewFile();
//		}
//		fw = new FileWriter(masterBuilderFile.getAbsoluteFile());
//		bw = new BufferedWriter(fw);
//		bw.write(finalstring.toString());
//		bw.close();
		
		
		return finalstring.toString();

	}

}
