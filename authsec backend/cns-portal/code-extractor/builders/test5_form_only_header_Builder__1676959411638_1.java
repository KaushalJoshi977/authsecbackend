package com.realnet.Builders;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Map.Entry;
import java.util.Set;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.realnet.entitybuilder.response.EntityResponse;
import io.swagger.annotations.Api;
@RestController
@RequestMapping(value = "/token/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { "Master Builder" })
public class test5_form_only_header_Builder__1676959411638_1 {


 @Value("${projectPath}")
	private String projectPath;
@GetMapping(value = "/test5_form_only_header_Builder__1676959411638_1/{proj_id}/{repo_name}/{table_name}/{entityname}")
	public ResponseEntity<?> createbyjson(@PathVariable Integer proj_id,@PathVariable String repo_name,@PathVariable String table_name,@PathVariable HashMap<String, String> entityname) throws IOException {
		// CALL BACKEND
		String project_name = repo_name;
		String java_class =table_name;
	HashMap<String, String> field_value = entityname;
FileWriter fw = null;
BufferedWriter bw = null;
String AbcRepository0 = java_class + "Repository.java";
String  mainstr0 = AbcRepository0;
String Abc1 = java_class + ".java";
String  mainstr1 = Abc1;
String AbcController2 = java_class + "Controller.java";
String  mainstr2 = AbcController2;
String AbcService3 = java_class + "Service.java";
String  mainstr3 = AbcService3;

 StringBuilder AbcRepository0Code = new StringBuilder();
 AbcRepository0Code.append("package com.realnet."+repo_name+".Repository;" + "\r\n" + 
"" + "\r\n" + 
"" + "\r\n" + 
"import org.springframework.data.jpa.repository.JpaRepository;" + "\r\n" + 
"" + "\r\n" + 
"import org.springframework.stereotype.Repository;" + "\r\n" + 
" " + "\r\n" + 
"" + "\r\n" + 
"import com.realnet."+repo_name+".Entity."+table_name+";" + "\r\n" + 
"" + "\r\n" + 
"@Repository" + "\r\n" + 
"public interface  "+table_name+"Repository  extends  JpaRepository<"+table_name+", Long>  { " + "\r\n" + 
"}" );

	File AbcRepository0File = new File(projectPath + "/cns-portal/code-extractor/builders/"+ proj_id + "/"+proj_id+ "/" + project_name + "/Repository/" + AbcRepository0);
	System.out.println("Directory name = " + AbcRepository0File);
	File AbcRepository0FileParentDir = new File(AbcRepository0File.getParent());
	if(!AbcRepository0FileParentDir.exists()) {
	AbcRepository0FileParentDir.mkdirs();
			}
	if (!AbcRepository0File.exists()) {
				AbcRepository0File.createNewFile();
			}
			fw = new FileWriter(AbcRepository0File.getAbsoluteFile());
	bw = new BufferedWriter(fw);
			bw.write(AbcRepository0Code.toString());
	bw.close();
	fw.close();
 StringBuilder Abc1Code = new StringBuilder();
 Abc1Code.append("package com.realnet."+repo_name+".Entity;" + "\r\n" + 
" import lombok.*;" + "\r\n" + 
" import javax.persistence.*;" + "\r\n" + 
" import java.time.LocalDateTime;" + "\r\n" + 
" import java.util.*;" + "\r\n" + 
"" + "\r\n" + 
" @Entity " + "\r\n" + 
" @Data" + "\r\n" + 
" public class    "+table_name+"{ " + "\r\n" + 
"" + "\r\n" + 
" "+  for (Entry<String, String> e : entrySet) {

			String string = e.getKey().toLowerCase();
			String datatype = e.getValue().toString();
			String lowerCase = string.replaceAll(" ", "_").toLowerCase();
			String add = "n private " + datatype + " " + lowerCase + ";";
			"+table_name+"1Code.append(add);
		}
		"+table_name+"1Code.append("n}nr"); +"" + "\r\n" + 
" @GeneratedValue(strategy = GenerationType.IDENTITY)" + "\r\n" + 
" private Long id;" + "\r\n" + 
"" + "\r\n" + 
" private String name;" + "\r\n" + 
" private int name_id;" + "\r\n" + 
"" + "\r\n" + 
" }" );

	File Abc1File = new File(projectPath + "/cns-portal/code-extractor/builders/"+ proj_id + "/"+proj_id+ "/" + project_name + "/Entity/" + Abc1);
	System.out.println("Directory name = " + Abc1File);
	File Abc1FileParentDir = new File(Abc1File.getParent());
	if(!Abc1FileParentDir.exists()) {
	Abc1FileParentDir.mkdirs();
			}
	if (!Abc1File.exists()) {
				Abc1File.createNewFile();
			}
			fw = new FileWriter(Abc1File.getAbsoluteFile());
	bw = new BufferedWriter(fw);
			bw.write(Abc1Code.toString());
	bw.close();
	fw.close();
 StringBuilder AbcController2Code = new StringBuilder();
 AbcController2Code.append("package com.realnet."+repo_name+".Controllers;" + "\r\n" + 
"import java.util.List;" + "\r\n" + 
"import org.springframework.beans.factory.annotation.Autowired;" + "\r\n" + 
" import org.springframework.web.bind.annotation.*;" + "\r\n" + 
"import com.realnet."+repo_name+".Entity."+table_name+";" + "\r\n" + 
"import com.realnet."+repo_name+".Services."+table_name+"Service ;" + "\r\n" + 
"@RequestMapping(value = \"/_1665647440047_back\")" + "\r\n" + 
"@RestController" + "\r\n" + 
"public class "+table_name+"Controller {" + "\r\n" + 
"	" + "\r\n" + 
"	@Autowired" + "\r\n" + 
"	private "+table_name+"Service Service;" + "\r\n" + 
"" + "\r\n" + 
"	@PostMapping(\"/"+table_name+"\")" + "\r\n" + 
"	" + "\r\n" + 
"	  public "+table_name+" Savedata(@RequestBody "+table_name+" data) {" + "\r\n" + 
"		"+table_name+" save = Service.Savedata(data)	;" + "\r\n" + 
"		 return save;" + "\r\n" + 
"	  }" + "\r\n" + 
"		 " + "\r\n" + 
"	" + "\r\n" + 
"	@GetMapping(\"/"+table_name+"\")" + "\r\n" + 
"	public List<"+table_name+"> getdetails() {" + "\r\n" + 
"		 List<"+table_name+"> get = Service.getdetails();		" + "\r\n" + 
"		return get;" + "\r\n" + 
"}" + "\r\n" + 
"@GetMapping(\"/"+table_name+"/{id}\")" + "\r\n" + 
"	public  "+table_name+"  getdetailsbyId(@PathVariable Long id ) {" + "\r\n" + 
"		"+table_name+"  get = Service.getdetailsbyId(id);" + "\r\n" + 
"		return get;" + "\r\n" + 
"	}" + "\r\n" + 
"@DeleteMapping(\"/"+table_name+"/{id}\")" + "\r\n" + 
"	public  void delete_by_id(@PathVariable Long id ) {" + "\r\n" + 
"	Service.delete_by_id(id);" + "\r\n" + 
"		" + "\r\n" + 
"	}" + "\r\n" + 
"@PutMapping(\"/"+table_name+"/{id}\")" + "\r\n" + 
"	public  "+table_name+" update(@RequestBody "+table_name+" data,@PathVariable Long id ) {" + "\r\n" + 
"		"+table_name+" update = Service.update(data,id);" + "\r\n" + 
"		return update;" + "\r\n" + 
"	}" + "\r\n" + 
"}" );

	File AbcController2File = new File(projectPath + "/cns-portal/code-extractor/builders/"+ proj_id + "/"+proj_id+ "/" + project_name + "/Controllers/" + AbcController2);
	System.out.println("Directory name = " + AbcController2File);
	File AbcController2FileParentDir = new File(AbcController2File.getParent());
	if(!AbcController2FileParentDir.exists()) {
	AbcController2FileParentDir.mkdirs();
			}
	if (!AbcController2File.exists()) {
				AbcController2File.createNewFile();
			}
			fw = new FileWriter(AbcController2File.getAbsoluteFile());
	bw = new BufferedWriter(fw);
			bw.write(AbcController2Code.toString());
	bw.close();
	fw.close();
 StringBuilder AbcService3Code = new StringBuilder();
 AbcService3Code.append("package com.realnet."+repo_name+".Services;" + "\r\n" + 
"import com.realnet."+repo_name+".Repository."+table_name+"Repository;" + "\r\n" + 
"import com.realnet."+repo_name+".Entity."+table_name+";import java.util.List;" + "\r\n" + 
"" + "\r\n" + 
"import org.springframework.beans.factory.annotation.Autowired;" + "\r\n" + 
"	import org.springframework.stereotype.Service;" + "\r\n" + 
"" + "\r\n" + 
"@Service" + "\r\n" + 
" public class "+table_name+"Service {" + "\r\n" + 
"@Autowired" + "\r\n" + 
"private "+table_name+"Repository Repository;" + "\r\n" + 
"public "+table_name+" Savedata("+table_name+" data) {" + "\r\n" + 
"				return Repository.save(data);	" + "\r\n" + 
"			}" + "\r\n" + 
"" + "\r\n" + 
"			" + "\r\n" + 
"public List<"+table_name+"> getdetails() {" + "\r\n" + 
"				return (List<"+table_name+">) Repository.findAll();" + "\r\n" + 
"			}" + "\r\n" + 
"" + "\r\n" + 
"" + "\r\n" + 
"public "+table_name+" getdetailsbyId(Long id) {" + "\r\n" + 
"	return Repository.findById(id).get();" + "\r\n" + 
"			}" + "\r\n" + 
"" + "\r\n" + 
"" + "\r\n" + 
"	public void delete_by_id(Long id) {" + "\r\n" + 
" Repository.deleteById(id);" + "\r\n" + 
"}" + "\r\n" + 
"" + "\r\n" + 
"" + "\r\n" + 
"public "+table_name+" update("+table_name+" data,Long id) {" + "\r\n" + 
"	"+table_name+" old = Repository.findById(id).get();" + "\r\n" + 
"old.setName(data.getName());" + "\r\n" + 
"old.setName_id(data.getName_id());" + "\r\n" + 
"final "+table_name+" test = Repository.save(old);" + "\r\n" + 
"		return test;}}" );

	File AbcService3File = new File(projectPath + "/cns-portal/code-extractor/builders/"+ proj_id + "/"+proj_id+ "/" + project_name + "/Services/" + AbcService3);
	System.out.println("Directory name = " + AbcService3File);
	File AbcService3FileParentDir = new File(AbcService3File.getParent());
	if(!AbcService3FileParentDir.exists()) {
	AbcService3FileParentDir.mkdirs();
			}
	if (!AbcService3File.exists()) {
				AbcService3File.createNewFile();
			}
			fw = new FileWriter(AbcService3File.getAbsoluteFile());
	bw = new BufferedWriter(fw);
			bw.write(AbcService3Code.toString());
	bw.close();
	fw.close();


	return new ResponseEntity<>(new EntityResponse("file created"), HttpStatus.CREATED);
}
 }