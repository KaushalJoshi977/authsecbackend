package com.realnet.codeextractor.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.codeextractor.service.Rule_library_service_gk;
@RestController
@RequestMapping("/codeextractor/rulelib_new")
public class RuleLibraryNew_gk {
	
	@Autowired
	private Rule_library_service_gk rule_service;
	
	
	@GetMapping("/test13")
	public  ResponseEntity<?> getKeysInJsonUsingMaps(@RequestParam String keyword) throws ParseException, IOException {
		
		//ENTITY
		if (keyword.contains("entity_package")) {
			String path ="C:\\Users\\Dell\\Desktop\\26 AUG\\CallingAnotherAPi\\testing/Abc.java";
			String start ="package com";
			String end =";\r\n"
					+ "\r\n"
					+ "import javax.persistence.GeneratedValue;";
			String replaceWith ="Test_1";
			
			rule_service.rule(path,start,end,replaceWith);
		}
		if (keyword.contains("ENTITYCLASS")) {
			String path ="C:\\Users\\Dell\\Desktop\\26 AUG\\CallingAnotherAPi\\testing/Abc.java";
			String start ="public class";
			String end ="{";
			String replaceWith ="Test";
			
			rule_service.rule(path,start,end,replaceWith);
		}
		

		if (keyword.contains("entity_loop")) {
			String path ="C:\\Users\\Dell\\Desktop\\26 AUG\\CallingAnotherAPi\\testing/Abc.java";
			String start ="private int id;";
			String end ="}";
			String replaceWith =" \nfor (int i = 0; i < entityname.size(); i++) {\r\n"
					+ "			String string = entityname.get(i);\r\n"
					+ "			String lowerCase = string.replaceAll(\" \", \"_\").toLowerCase();\r\n"
					+ "			String add = \"\\n private \" + \"String\" + \" \" + lowerCase + \";\";\r\n"
					+ "			entityclass.append(add);\r\n"
					+ "		}";
			
			rule_service.rule(path,start,end,replaceWith);
		}

		//REPOSITORY
		
		if (keyword.contains("repo_class")) {
			String path ="C:\\Users\\Dell\\Desktop\\Demo Code Extractor\\Abc_1665647440047_back\\Repository/AbcRepository.java";
			String start ="public interface";
			String end ="JpaRepository<Abc, Long>  { ";
			String replaceWith ="Test_1";
			
			rule_service.rule(path,start,end,replaceWith);
		}
		
		//SERVICE
		if (keyword.contains("service_class")) {
			String path ="C:\\Users\\Dell\\Desktop\\Demo Code Extractor\\Abc_1665647440047_back\\Services/AbcService.java";
			String start ="public class";
			String end ="{";
			String replaceWith ="Test_1";
			
			rule_service.rule(path,start,end,replaceWith);
		}
		
		if (keyword.contains("service_body")) {
			
			String path ="C:\\Users\\Dell\\Desktop\\Demo Code Extractor\\Abc_1665647440047_back\\Services/AbcService.java";
			
			File staticFile = new File(path);
			String fileString = FileUtils.readFileToString(staticFile, StandardCharsets.UTF_8);
			int length = fileString.length();
			
			
			String start ="@Autowired";
			String end ="";
			String replaceWith ="sericeclass.append(\"\\r\\n\" + \"private \" + table_name + addString + \"Repository \" + \"Repository;\\n\");\r\n"
					+ "\r\n"
					+ "		sericeclass.append(\"public \" + table_name + addString + \" Savedata(\" + table_name + addString + \" data) {\\r\\n\"\r\n"
					+ "				+ \"				return Repository.save(data);	\\r\\n\" + \"			}\\r\\n\" + \"\\r\\n\" + \"			\\r\\n\"\r\n"
					+ "				+ \"public List<\" + table_name + addString + \"> getdetails() {\\r\\n\" + \"				return (List<\"\r\n"
					+ "				+ table_name + addString + \">) Repository.findAll();\\r\\n\" + \"			}\\r\\n\" + \"\\r\\n\" + \"\\r\\n\"\r\n"
					+ "				+ \"public \" + table_name + addString + \" getdetailsbyId(Long id) {\\r\\n\"\r\n"
					+ "				+ \"	return Repository.findById(id).get();\\r\\n\" + \"			}\\r\\n\" + \"\\r\\n\" + \"\\r\\n\"\r\n"
					+ "				+ \"	public void delete_by_id(Long id) {\\r\\n\" + \" Repository.deleteById(id);\\r\\n\" + \"}\\r\\n\" + \"\\r\\n\"\r\n"
					+ "				+ \"\\r\\n\");\r\n"
					+ "\r\n"
					+ "		sericeclass.append(\"public \" + table_name + addString + \" update(\" + table_name + addString\r\n"
					+ "				+ \" data,Long id) {\\n\" + \"	\" + table_name + addString + \" old = Repository.findById(id).get();\\n\");\r\n"
					+ "		for (int i = 0; i < entityname.size(); i++) {\r\n"
					+ "			String name1 = entityname.get(i);\r\n"
					+ "			String name3 = name1.replaceAll(\" \", \"_\").toLowerCase();\r\n"
					+ "			String string = name3.substring(0, 1).toUpperCase() + name3.substring(1);\r\n"
					+ "			sericeclass.append(\"old.set\" + string + \"(data.get\" + string + \"());\\r\\n\");\r\n"
					+ "		}\r\n"
					+ "		sericeclass.append(\"final \" + table_name + addString + \" test = Repository.save(old);\\r\\n\"\r\n"
					+ "				+ \"		return test;\" + \"}}\" + \"\");\r\n"
					+ "";
			
			rule_service.rule(path,start,end,replaceWith);
		}
		
		//CONTROLLER
		if (keyword.contains("controller_class")) {
			String path ="C:\\Users\\Dell\\Desktop\\Demo Code Extractor\\Abc_1665647440047_back\\Controllers/AbcController.java";
			String start ="public class";
			String end ="{";
			String replaceWith ="Test_1";
			
			rule_service.rule(path,start,end,replaceWith);
		}
		if (keyword.contains("controller_body")) {
			String path ="C:\\Users\\Dell\\Desktop\\Demo Code Extractor\\Abc_1665647440047_back\\Controllers/AbcController.java";
			String start ="@Autowired";
			String end ="";
			String replaceWith ="controllerclass.append(\"\r\n"
					+ "				+ \"	private \" + table_name + addString + \"Service Service;\\n\\n\"\r\n"
					+ "\r\n"
					+ "				+ \"	@PostMapping(\" + \"\\\"/\" + table_name + \"\\\")\\r\\n\" + \"	  public \" + table_name + addString\r\n"
					+ "				+ \" Savedata(@RequestBody \" + table_name + addString + \" data) {\\r\\n\" + \"		\" + table_name\r\n"
					+ "				+ addString + \" save = Service.Savedata(data)	;\\r\\n\" + \"		 return save;\\r\\n\" + \"	  }\\r\\n\"\r\n"
					+ "				+ \"		 \\r\\n\" + \"	\\r\\n\" + \"	@GetMapping(\\\"/\" + table_name + \"\\\")\\r\\n\" + \"	public List<\"\r\n"
					+ "				+ table_name + addString + \"> getdetails() {\\r\\n\" + \"		 List<\" + table_name + addString\r\n"
					+ "				+ \"> get = Service.getdetails();		\\r\\n\" + \"		return get;\\r\\n}\\n\" + \"@GetMapping(\\\"/\"\r\n"
					+ "				+ table_name + \"/{id}\\\")\\r\\n\" + \"	public  \" + table_name + addString\r\n"
					+ "				+ \"  getdetailsbyId(@PathVariable Long id ) {\\r\\n\" + \"		\" + table_name + addString\r\n"
					+ "				+ \"  get = Service.getdetailsbyId(id);\\r\\n\" + \"		return get;\\r\\n\" + \"	}\\n\" + \"@DeleteMapping(\\\"/\"\r\n"
					+ "				+ table_name + \"/{id}\\\")\\r\\n\" + \"	public  void delete_by_id(@PathVariable Long id ) {\\r\\n\"\r\n"
					+ "				+ \"	Service.delete_by_id(id);\\r\\n\" + \"		\\r\\n\" + \"	}\\n\" + \"@PutMapping(\\\"/\" + table_name\r\n"
					+ "				+ \"/{id}\\\")\\r\\n\" + \"	public  \" + table_name + addString + \" update(@RequestBody \" + table_name\r\n"
					+ "				+ addString + \" data,@PathVariable Long id ) {\\r\\n\" + \"		\" + table_name + addString\r\n"
					+ "				+ \" update = Service.update(data,id);\\r\\n\" + \"		return update;\\r\\n\" + \"	}\\n}\");\r\n"
					+ "";
			
			rule_service.rule(path,start,end,replaceWith);
		}
		return new ResponseEntity<>("created", HttpStatus.CREATED);

		}


}
