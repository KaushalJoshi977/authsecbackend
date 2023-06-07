package com.realnet.entitybuilder.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.entitybuilder.Services.BackendBuilderService;
import com.realnet.entitybuilder.Services.FrontEndBuilderService;
import com.realnet.entitybuilder.entity.EntityBuild;
import com.realnet.entitybuilder.entity.TableBuild;
import com.realnet.entitybuilder.entity.frontendtable;
import com.realnet.entitybuilder.repo.FrontendRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/entityBuilder", produces = MediaType.APPLICATION_JSON_VALUE)
public class EntityBuilder {

	@Autowired
	private FrontendRepo front;

	@Autowired
	private BackendBuilderService backendBuilderService;

	@Autowired
	private FrontEndBuilderService front_service;



	@Value("${projectPath}")
	private String projectPath;

	@PostMapping(value = "/project_builder")
	public ResponseEntity<?> createHeaderTable(@Valid @RequestBody TableBuild table) throws IOException {

		Date d = new Date();
//		String addString = "_" +d.getTime()+"_back" ;
//		String addString = "_back";

		String name2 = table.getTable_name();
//		String table_name = name2.substring(0, 1).toUpperCase() + name2.substring(1);
		String table_name = name2.replaceAll(" ", "_").toUpperCase();

		List<EntityBuild> ent = table.getEntity_name();


		StringBuilder entityclass = new StringBuilder();
		StringBuilder repoclass = new StringBuilder();
		StringBuilder sericeclass = new StringBuilder();
		StringBuilder controllerclass = new StringBuilder();
		
		String entity_name =table_name+"_t";
		String repository_name =table_name+"_Repository";
		String service_name =table_name+"_Service";
		String controlier_name =table_name+"_Controller";



		// MAKE ENTITY
		entityclass.append("package com.realnet." + table_name  + ".Entity;\n");
		entityclass.append(" import lombok.*;\n");
		entityclass.append(" import javax.persistence.*;\r\n");
		entityclass.append(" import java.time.LocalDateTime;\n");
		entityclass.append(" import java.util.*;\n\n");
		entityclass.append(" @Entity \n @Data");
		entityclass.append("\n public class    " + entity_name + "{ \n\n");
		entityclass.append(
				" @Id\r\n" + " @GeneratedValue(strategy = GenerationType.IDENTITY)\r\n" + " private Long id;\n");
		for (EntityBuild en : ent) {
			String add = "\n private " + en.getData_type() + " " + en.getName() + ";";
			entityclass.append(add);
		}
		entityclass.append("\n\n }");
		System.out.println(entityclass);

		// MAKE REPOSITORY
		repoclass.append("package com.realnet." + table_name  + ".Repository;\n");
		repoclass.append("\n\nimport org.springframework.data.jpa.repository.JpaRepository;\r\n"
				+ "\nimport org.springframework.stereotype.Repository;\r\n" + " \n\n");
		repoclass.append("import com.realnet." + table_name + ".Entity." + entity_name + ";\n\n");

		repoclass.append("@Repository\n");
		repoclass.append("public interface  " + repository_name + "  " + "extends  " + "JpaRepository<"
				+ entity_name + ", Long>  { ");

		repoclass.append("\n}");

		System.out.println(repoclass);

		// MAKE SERVICE
		sericeclass.append("package com.realnet." + table_name + ".Services;\n");
		sericeclass
				.append("import com.realnet." + table_name + ".Repository." + repository_name+ ";\n");
		sericeclass.append("import com.realnet." + table_name + ".Entity." + entity_name + ";");

		sericeclass.append("import java.util.List;\r\n" + "\r\n"
				+ "import org.springframework.beans.factory.annotation.Autowired;\r\n"
				+ "	import org.springframework.stereotype.Service;\n");

//		import com.realnet.Dashboard1.Entity.Dashbord1_Line;
//		import com.realnet.Dashboard1.Entity.Dashbord_Header;
//		import com.realnet.Dashboard1.Repository.Dashboard_lineRepository;
//		import com.realnet.Dashboard1.Repository.HeaderRepository;

		sericeclass.append("\n@Service\n");
		sericeclass.append(" public class " + table_name + "_Service {\n");
		sericeclass.append("@Autowired\r\n" + "private " + repository_name + " Repository;\n");

		sericeclass.append("public " + entity_name + " Savedata(" + entity_name + " data) {\r\n"
				+ "				return Repository.save(data);	\r\n" + "			}\r\n" + "\r\n" + "			\r\n"
				+ "public List<" + entity_name + "> getdetails() {\r\n" + "				return (List<" + entity_name
				+ ">) Repository.findAll();\r\n" + "			}\r\n" + "\r\n" + "\r\n" + "public " + entity_name
				+ " getdetailsbyId(Long id) {\r\n" + "	return Repository.findById(id).get();\r\n" + "			}\r\n"
				+ "\r\n" + "\r\n" + "	public void delete_by_id(Long id) {\r\n" + " Repository.deleteById(id);\r\n"
				+ "}\r\n" + "\r\n" + "\r\n");

		sericeclass.append("public " + entity_name + " update(" + entity_name + " data,Long id) {\n" + "	" + entity_name
				+ " old = Repository.findById(id).get();\n");
		for (EntityBuild en : ent) {
			String name = en.getName();

			String string = name.substring(0, 1).toUpperCase() + name.substring(1);
			sericeclass.append("old.set" + string + "(data.get" + string + "());\r\n");
		}
		sericeclass
				.append("final " + entity_name + " test = Repository.save(old);\r\n" + "		return test;" + "}" + "}");

		System.out.println(sericeclass);

		// MAKE CONTROLLER CLASS
		controllerclass.append("package com.realnet." + table_name  + ".Controllers;\n");
		controllerclass.append(
				"import java.util.List;\r\n" + "import org.springframework.beans.factory.annotation.Autowired;\r\n"
//			+ "import org.springframework.web.bind.annotation.DeleteMapping;\r\n"
//			+ "import org.springframework.web.bind.annotation.GetMapping;\r\n"
//			+ "import org.springframework.web.bind.annotation.PathVariable;\r\n"
//			+ "import org.springframework.web.bind.annotation.PostMapping;\r\n"
//			+ "import org.springframework.web.bind.annotation.PutMapping;\r\n"
//			+ "import org.springframework.web.bind.annotation.RequestBody;\r\n"
//			+ "import org.springframework.web.bind.annotation.RequestParam;\r\n"
//			+ "import org.springframework.web.bind.annotation.RestController;\r\n"
						+ " import org.springframework.web.bind.annotation.*;\r\n");
		controllerclass.append("import com.realnet." + table_name  + ".Entity." + entity_name + ";\n");

		controllerclass
				.append("import com.realnet." + table_name + ".Services." + service_name + ";\n");

		controllerclass.append("@RequestMapping(value = " + "\"/" +table_name + "\")\n" + "@RestController\r\n" + ""
				+ "public class " + controlier_name + " {\r\n" + "	\r\n" + "	@Autowired\r\n" + "	private "
				+ service_name + " Service;\n\n"

				+ "	@PostMapping(" + "\"/" + table_name + "\")\r\n"  + "	  public " + entity_name
				+ " Savedata(@RequestBody " + entity_name + " data) {\r\n" + "		" + entity_name
				+ " save = Service.Savedata(data)	;\r\n" + "		 return save;\r\n" + "	  }\r\n" + "		 \r"
				+ "	\r\n" + "	@GetMapping(\"/" + table_name + "\")\r\n" + "	public List<" + entity_name
				+ "> getdetails() {\r\n" + "		 List<" + entity_name + "> get = Service.getdetails();		\r\n"
				+ "		return get;\r\n}\n\n" + "@GetMapping(\"/" + table_name + "/{id}\")\r\n" + "	public  "
				+ entity_name + "  getdetailsbyId(@PathVariable Long id ) {\r\n" + "		" + entity_name
				+ "  get = Service.getdetailsbyId(id);\r\n" + "		return get;\r\n\n" + "	}\n" + "@DeleteMapping(\"/"
				+ table_name + "/{id}\")\r\n" + "	public  void delete_by_id(@PathVariable Long id ) {\r\n"
				+ "	Service.delete_by_id(id);\r\n" + "		\r\n" + "	}\n\n" + "@PutMapping(\"/" + table_name
				+ "/{id}\")\r\n" + "	public  " + entity_name + " update(@RequestBody " + entity_name
				+ " data,@PathVariable Long id ) {\r\n" + "		" + entity_name
				+ " update = Service.update(data,id);\r\n" + "		return update;\r\n" + "	}\n}");

		System.out.println(controllerclass);

		// CEATE PACKAGE
		String Path1 = projectPath + "/src/main/java/com/realnet/" + File.separator + table_name ;

		File staticMainDir = new File(Path1);
		if (!staticMainDir.exists()) {
			boolean mkdir = staticMainDir.mkdir();
			System.out.println(mkdir);
		}

		// CREATING FOLDER
		String staticDirString = Path1 + File.separator + "Entity";
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
		String Path = staticDirString + File.separator + entity_name+ ".java";
		String repoPath = staticDirString1 + File.separator + repository_name+ ".java";
		String servicepath = staticDirString2 + File.separator + service_name + ".java";
		String controllerpath = staticDirString3 + File.separator + controlier_name + ".java";

		System.out.println("entity Path= " + Path);
		System.out.println("repo Path= " + repoPath);

		System.out.println("service Path= " + servicepath);

		System.out.println("controller Path= " + controllerpath);

		try {
//		creating files
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
//			Writing files

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

			// MAKE FRONTEND
//		service.buildFrontend(table,addString);
//		service.buildfront2(table, addString);
			frontendtable user = new frontendtable();
			user.setName(name2);
			front.save(user);
		} catch (FileNotFoundException e) {
			log.error("File not found exception Handled", e.getMessage());
			return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
		} catch (IOException e) {
			log.error("IO Exception exception Handled", e.getMessage());
			return new ResponseEntity<>("not found", HttpStatus.NOT_IMPLEMENTED);
		}

//		ArrayList<String> table_list = new ArrayList<>();
//		table_list.add(table_name);
//		
//		ArrayList<String> entity_list = new ArrayList<>();
//		for (EntityBuild en : ent) {
//			
//			entity_list.add(en.getName());
//		}
//		String addString = "_t";
//		front_service.buildFrontend(table_list, entity_list, addString);

		return new ResponseEntity<>(entityclass, HttpStatus.CREATED);

	}

	@PostMapping(value = "/project_builder1")
	public ResponseEntity<?> createHeaderTable1(@Valid @RequestBody TableBuild table) {
		String name2 = table.getTable_name();
		String table_name = name2.substring(0, 1).toUpperCase() + name2.substring(1);

		List<EntityBuild> ent = table.getEntity_name();

//		backendBuilderService.buildbackend(null, ent, table_name);

		return new ResponseEntity<>("build", HttpStatus.CREATED);

	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return headers;
	}

}
