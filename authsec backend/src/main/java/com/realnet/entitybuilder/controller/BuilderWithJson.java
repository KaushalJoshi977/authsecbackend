package com.realnet.entitybuilder.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.realnet.entitybuilder.entity.EntityBuild;
import com.realnet.entitybuilder.entity.TableBuild;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/entityBuilder", produces = MediaType.APPLICATION_JSON_VALUE)
public class BuilderWithJson {


	@Value("${projectPath}")
	private String projectPath;

	@PostMapping(value = "/Entity_builder")
	public ResponseEntity<?> createHeaderTable(@Valid @RequestBody TableBuild table)
			throws JsonParseException, IOException {

		
		Date d = new Date();	
		String addString = "_" +d.getTime() ;
		StringBuilder entityclass = new StringBuilder();
		StringBuilder repoclass = new StringBuilder();
		StringBuilder sericeclass = new StringBuilder();
		StringBuilder controllerclass = new StringBuilder();

		// MAKE ENTITY
		entityclass.append("\nimport lombook.Data ;\n\n\n");
		entityclass.append("import javax.persistence.*;\r\n");
		entityclass.append("@Entity \n@Data");
		entityclass.append("\npublic class    " + table.getTable_name() + "{ \n");
		entityclass.append(
				" \n\r\r@Id\r\n" + "@GeneratedValue(strategy = GenerationType.IDENTITY)\r\n" + "	private Long id;");
		List<EntityBuild> ent = table.getEntity_name();
			
		
		JsonFactory factory = new JsonFactory();

		JsonParser jsonParser = factory.createParser(table.getJson());
		while (!jsonParser.isClosed()) {

			if (jsonParser.nextToken() == JsonToken.FIELD_NAME) {

				System.out.println(jsonParser.getCurrentName());

			
			String add = "\nprivate String " + jsonParser.getCurrentName() + ";";
			entityclass.append(add);
			
			}

		}
		entityclass.append("\n\n}");
		System.out.println(entityclass);

		// MAKE REPOSITORY
		repoclass.append("\n\nimport org.springframework.data.jpa.repository.JpaRepository;\r\n"
				+ "\nimport org.springframework.stereotype.Repository;\r\n" + " \n\n");

		repoclass.append("@Repository\n");
		repoclass.append("\npublic interface  " + table.getTable_name() + "Repostiory" + "  " + "extends  "
				+ "JpaRepository<" + table.getTable_name() + ", Long>  { ");

		repoclass.append("\n}");

		// MAKE SERVICE

		sericeclass.append("package com.realnet.Dashboard1.Service;\r\n" + "\r\n" + "import java.util.List;\r\n"
				+ "\r\n" + "import org.springframework.beans.factory.annotation.Autowired;\r\n"
				+ "	import org.springframework.stereotype.Service;\n");

//			import com.realnet.Dashboard1.Entity.Dashbord1_Line;
//			import com.realnet.Dashboard1.Entity.Dashbord_Header;
//			import com.realnet.Dashboard1.Repository.Dashboard_lineRepository;
//			import com.realnet.Dashboard1.Repository.HeaderRepository;

		sericeclass.append("@Service\n");
		sericeclass.append(" public class " + table.getTable_name() + "Service {\n");
		sericeclass.append("@Autowired\r\n" + "private " + table.getTable_name() + "Repostiory " + "Repository;\n");

		sericeclass.append("public " + table.getTable_name() + " Savedata(" + table.getTable_name() + " data) {\r\n"
				+ "				return Repository.save(data);	\r\n" + "			}\r\n" + "\r\n" + "			\r\n"
				+ "public List<" + table.getTable_name() + "> getdetails() {\r\n" + "				return (List<"
				+ table.getTable_name() + ">) Repository.findAll();\r\n" + "			}\r\n" + "\r\n" + "\r\n"
				+ "public " + table.getTable_name() + " getdetailsbyId(Long id) {\r\n"
				+ "	return Repository.findById(id).get();\r\n" + "			}\r\n" + "\r\n" + "\r\n"
				+ "	public void delete_by_id(Long id) {\r\n" + " Repository.deleteById(id);\r\n" + "}\r\n" + "\r\n"
				+ "\r\n");

		sericeclass.append("public " + table.getTable_name() + " update(" + table.getTable_name() + " data,Long id) {\n"
				+ "	" + table.getTable_name() + " old = Repository.findById(id).get();\n");
		for (EntityBuild en : ent) {
			sericeclass.append("old.set" + en.getName() + "(data.get" + en.getName() + "());\r\n");
		}
		sericeclass.append("final " + table.getTable_name() + " new = Repository.save(old);\r\n" + "		return new;"
				+ "}" + "}");

		// MAKE CONTROLLER CLASS
		controllerclass.append("import java.util.List;\r\n"
				+ "import org.springframework.beans.factory.annotation.Autowired;\r\n"
				+ "import org.springframework.web.bind.annotation.DeleteMapping;\r\n"
				+ "import org.springframework.web.bind.annotation.GetMapping;\r\n"
				+ "import org.springframework.web.bind.annotation.PathVariable;\r\n"
				+ "import org.springframework.web.bind.annotation.PostMapping;\r\n"
				+ "import org.springframework.web.bind.annotation.PutMapping;\r\n"
				+ "import org.springframework.web.bind.annotation.RequestBody;\r\n"
				+ "import org.springframework.web.bind.annotation.RequestParam;\r\n"
				+ "import org.springframework.web.bind.annotation.RestController;\r\n"

				+ "@RestController\r\n" + "public class " + table.getTable_name() + "Controller {\r\n" + "	\r\n"
				+ "	@Autowired\r\n" + "	private " + table.getTable_name() + "Service Service;\r\n"

				+ "	@PostMapping(\"/Save" + table.getTable_name() + "\")\r\n" + "	\r\n" + "	  public "
				+ table.getTable_name() + " Savedata(@RequestBody " + table.getTable_name() + " data) {\r\n"
				+ "		" + table.getTable_name() + " save = Service.Savedata(data)	;\r\n" + "		 return save;\r\n"
				+ "	  }\r\n" + "		 \r\n" + "	\r\n" + "	@GetMapping(\"/get_" + table.getTable_name() + "\")\r\n"
				+ "	public List<" + table.getTable_name() + "> getdetails() {\r\n" + "		 List<"
				+ table.getTable_name() + "> get = Service.getdetails();		\r\n" + "		return get;\r\n}\n"
				+ "@GetMapping(\"/get_" + table.getTable_name() + "byid/{id}\")\r\n" + "	public  "
				+ table.getTable_name() + "  getdetailsbyId(@PathVariable Long id ) {\r\n" + "		"
				+ table.getTable_name() + "  get = Service.getdetailsbyId(id);\r\n" + "		return get;\r\n" + "	}\n"
				+ "@DeleteMapping(\"/delete_by_" + table.getTable_name() + "_id/{id}\")\r\n"
				+ "	public  void delete_by_id(@PathVariable Long id ) {\r\n" + "	Service.delete_by_id(id);\r\n"
				+ "		\r\n" + "	}\n" + "@PutMapping(\"/update_" + table.getTable_name() + "\"+/{id})\r\n"
				+ "	public  " + table.getTable_name() + " update(@RequestBody " + table.getTable_name()
				+ " data,@PathVariable Long id ) {\r\n" + "		" + table.getTable_name()
				+ " update = Service.update(data,id);\r\n" + "		return update;\r\n" + "	}\n}");

		// CEATE PACKAGE
		String Path1 = projectPath + "/entityclass/" + File.separator + table.getTable_name()+addString;

		File staticMainDir = new File(Path1);
		if (!staticMainDir.exists()) {
			boolean mkdir = staticMainDir.mkdir();
			System.out.println(mkdir);
		}

		// CREATING FOLDER
		String staticDirString =  Path1+ File.separator + "Entity";
		File staticDir = new File(staticDirString);
		if (!staticDir.exists()) {
			staticDir.mkdir();
		}
		String staticDirString1 = Path1 + File.separator + "Repository";
		File staticDir1 = new File(staticDirString1);
		if (!staticDir1.exists()) {
			staticDir1.mkdir();
		}
		String staticDirString2 = Path1 + File.separator + "Services";
		File staticDir2 = new File(staticDirString2);
		if (!staticDir2.exists()) {
			staticDir2.mkdir();
		}
		String staticDirString3 = Path1 + File.separator + "Controllers";
		File staticDir3 = new File(staticDirString3);
		if (!staticDir3.exists()) {
			staticDir3.mkdir();
		}

		// CREATING JAVA CLASS
		String Path = staticDirString + File.separator + table.getTable_name() + ".java";
		String repoPath = staticDirString1 + File.separator + table.getTable_name() + "Repository.java";
		String servicepath = staticDirString2 + File.separator + table.getTable_name() + "Service.java";
		String controllerpath = staticDirString3 + File.separator + table.getTable_name() + "controller.java";

		System.out.println("entity Path= " + Path);
		System.out.println("repo Path= " + repoPath);

		System.out.println("service Path= " + servicepath);

		System.out.println("controller Path= " + controllerpath);

		try {
//			creating files
			File file0 = new File(Path);
			File file1 = new File(repoPath);

			File file2 = new File(servicepath);
			File file3 = new File(controllerpath);

			if (!file0.exists()) {
				file0.createNewFile();
			}
			if (!file1.exists()) {
				file1.createNewFile();
			}
			if (!file2.exists()) {
				file2.createNewFile();
			}
			if (!file3.exists()) {
				file3.createNewFile();
			}
//				Writing files

			FileWriter fw = new FileWriter(file0.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(entityclass.toString());
			bw.close();
			FileWriter fw1 = new FileWriter(file1.getAbsoluteFile());
			BufferedWriter bw1 = new BufferedWriter(fw1);
			bw1.write(repoclass.toString());
			bw1.close();
			FileWriter fw2 = new FileWriter(file2.getAbsoluteFile());
			BufferedWriter bw2 = new BufferedWriter(fw2);
			bw2.write(sericeclass.toString());
			bw2.close();
			FileWriter fw3 = new FileWriter(file3.getAbsoluteFile());
			BufferedWriter bw3 = new BufferedWriter(fw3);
			bw3.write(controllerclass.toString());
			bw3.close();

		} catch (FileNotFoundException e) {
			log.error("File not found exception Handled", e.getMessage());
			return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
		} catch (IOException e) {
			log.error("IO Exception exception Handled", e.getMessage());
			return new ResponseEntity<>("not found", HttpStatus.NOT_IMPLEMENTED);
		}

		return new ResponseEntity<StringBuilder>(entityclass, HttpStatus.CREATED);

	}
}
