package com.realnet.entitybuilder.Services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.realnet.entitybuilder.response.EntityResponse;

@Service
public class ActionService_Insert {

	@Value("${projectPath}")
	private String projectPath;
	@Autowired
	private Index_Service_be index_Service;

	public ResponseEntity<?> insert_action(Integer proj_id, String prj_name, String repo_name, String tablename9,
			HashMap<String, String> entityname) throws IOException {

		ObjectMapper mapper = new ObjectMapper();
		String asString = mapper.writeValueAsString(entityname);
		JsonParser parser = new JsonParser();
		JsonElement ele = parser.parse(asString);
		JsonObject ob = ele.getAsJsonObject();
		Set<Entry<String, JsonElement>> entry = ob.entrySet();
		for (Entry<String, JsonElement> e1 : entry) {
			String tablename = e1.getKey().toString().replaceAll("\"", "");

			String value = e1.getValue().getAsString();
			int j = 2;
			Date date = new Date();
			String addString = "_" + date.getTime();

			String copy_from = "";
			String Copy_to_path = "";
			String table_name1 = tablename.replaceAll(" ", "_");
			String table_name = table_name1.substring(0, 1).toUpperCase() + table_name1.substring(1);
			String project_name = repo_name;
			String object_name = table_name;
			String field_name = value;

//		String field_name = entityname.toString().replace("@", "{").replace("$", "}").replaceAll("\"", "");

			JsonElement element = parser.parse(field_name);
			JsonObject obj = element.getAsJsonObject();
			Set<Entry<String, JsonElement>> entrySet = obj.entrySet();
			FileWriter fw = null;
			BufferedWriter bw = null;

			String AbcService1 = object_name + "Service" + addString + ".java";
			copy_from = "/data/cns-portal/code-extractor/builders/" + proj_id + "/" + proj_id + "/" + repo_name
					+ "/Services/";
			Copy_to_path = "/" + proj_id + "/index/" + prj_name + "/backend/src/main/java/com/realnet/" + repo_name
					+ "/";
			index_Service.build_index(proj_id, prj_name, j, copy_from, Copy_to_path);
			j++;

			String AbcController3 = object_name + "Controller" + addString + ".java";

			copy_from = "/data/cns-portal/code-extractor/builders/" + proj_id + "/" + proj_id + "/" + repo_name
					+ "/Controllers/";
			Copy_to_path = "/" + proj_id + "/index/" + prj_name + "/backend/src/main/java/com/realnet/" + repo_name
					+ "/";
			index_Service.build_index(proj_id, prj_name, j, copy_from, Copy_to_path);
			j++;

			String fieldvalue = "";

			StringBuilder AbcController3Code = new StringBuilder();
			AbcController3Code.append("package com.realnet." + repo_name + ".Controllers;" + "\r\n"
					+ "import java.util.List;" + "\r\n"
					+ "import org.springframework.beans.factory.annotation.Autowired;" + "\r\n"
					+ " import org.springframework.web.bind.annotation.*;" + "\r\n" + "import com.realnet." + repo_name
					+ ".Entity." + table_name + ";" + "\r\n" + "import com.realnet." + repo_name + ".Services."
					+ table_name + "Service" + addString + " ;" + "\r\n" + "@RequestMapping(value = \"/" + table_name
					+ "\")" + "\r\n" + "@RestController" + "\r\n" + "public class " + table_name + "Controller"
					+ addString + " {" + "\r\n" + "	" + "\r\n" + "	@Autowired" + "\r\n" + "	private " + table_name
					+ "Service" + addString + " Service;");
//							+ "\r\n" + "" + "\r\n" + "@PutMapping(\"/" + table_name + "_insert" + "/{id}\")" + "\r\n");

			for (Entry<String, JsonElement> e : entrySet) {
				fieldvalue = e.getValue().toString().replaceAll("\"", "");
				String replace_value = fieldvalue.replaceAll("\\$", "").replaceAll("%", "");

				if (field_name.contains("%") && fieldvalue.startsWith("%")) {
					AbcController3Code.append("@PutMapping(\"/" + table_name + "_insert" + "/{" + replace_value + "}\")"
							+ "\r\n" + "	public  " + table_name + " update(\r@RequestBody " + table_name
							+ " data, @PathVariable String " + replace_value);
					AbcController3Code.append(" ) {" + "\r\n" + "		" + table_name
							+ " update = Service.updateinsert(data, " + replace_value + ");" + "\r\n"
							+ "		return update;" + "\r\n" + "	}" + "\r\n" + "}");

				}
			}

//		AbcController3Code.append("@PathVariable Long  " + "id" + ",\r@RequestBody " + table_name + " data");
//
//		AbcController3Code.append(" ) {" + "\r\n" + "		" + table_name + " update = Service.updateinsert(data,"
//				+ "id" + ");" + "\r\n" + "		return update;" + "\r\n" + "	}" + "\r\n" + "}");

			File AbcController3File = new File(projectPath + "/cns-portal/code-extractor/builders/" + proj_id + "/"
					+ proj_id + "/" + project_name + "/Controllers/" + AbcController3);
			System.out.println("Directory name = " + AbcController3File);
			File AbcController3FileParentDir = new File(AbcController3File.getParent());
			if (!AbcController3FileParentDir.exists()) {
				AbcController3FileParentDir.mkdirs();
			}
			if (!AbcController3File.exists()) {
				AbcController3File.createNewFile();
			}
			fw = new FileWriter(AbcController3File.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			bw.write(AbcController3Code.toString());
			bw.close();
			fw.close();

//			
			StringBuilder AbcService1Code = new StringBuilder();
			AbcService1Code.append("package com.realnet." + repo_name + ".Services;" + "\r\n" + "import com.realnet."
					+ repo_name + ".Repository." + table_name + "Repository;" + "\r\n" + "import com.realnet."
					+ repo_name + ".Entity." + table_name + ";" + "\n import java.util.List;" + "\r\n" + "" + "\r\n"
					+ "import org.springframework.beans.factory.annotation.Autowired;" + "\r\n"
					+ "	import org.springframework.stereotype.Service;" + "\r\n" + "" + "\r\n" + "@Service" + "\r\n"
					+ " public class " + table_name + "Service" + addString + " {" + "\r\n" + "@Autowired" + "\r\n"
					+ "private " + table_name + "Repository repository1;\n\n");

//					+ "\r\n" + "\r\n" + "" + "\r\n" + "" + "\r\n" + "public " + table_name + " updateinsert("
//					+ table_name + " data,");
//			AbcService1Code.append(" Long " + " id){\n");

			for (Entry<String, JsonElement> e : entrySet) {
				fieldvalue = e.getValue().toString().replaceAll("\"", "");
				String replace_value = fieldvalue.replaceAll("\\$", "").replaceAll("%", "");

				if (field_name.contains("%") && fieldvalue.startsWith("%")) {

					AbcService1Code.append("public " + table_name + " updateinsert(" + table_name + " data, String "
							+ replace_value + " ){\n");

				} else if (!field_name.contains("%")) {

					AbcService1Code.append("public " + table_name + " updateinsert(" + table_name + " data){\n");
				}
			}

			for (Entry<String, JsonElement> e : entrySet) {
				fieldvalue = e.getValue().toString().replaceAll("\"", "");
				String name1 = e.getKey().toString().replaceAll(" ", "_").toLowerCase();
				String field_key = name1.substring(0, 1).toUpperCase() + name1.substring(1);
				String replace_value = fieldvalue.replaceAll("\\$", "").replaceAll("%", "");

				if (fieldvalue.contains("$")) {
					replace_value = replace_value.substring(0, 1).toUpperCase() + replace_value.substring(1);
					AbcService1Code.append("data.set" + field_key + "(data.get" + replace_value + "());\r\n");

				} else if (fieldvalue.contains("%")) {
					AbcService1Code.append("data.set" + field_key + "(" + replace_value + ");\r\n");

				} else {

					AbcService1Code.append("data.set" + field_key + "(\"" + replace_value + "\");\r\n");
				}
			}

			AbcService1Code.append(
					"  final " + table_name + " test = repository1.save(data);" + "\r\n" + "		return test;}}");

			File AbcService1File = new File(projectPath + "/cns-portal/code-extractor/builders/" + proj_id + "/"
					+ proj_id + "/" + project_name + "/Services/" + AbcService1);
			System.out.println("Directory name = " + AbcService1File);
			File AbcService1FileParentDir = new File(AbcService1File.getParent());
			if (!AbcService1FileParentDir.exists()) {
				AbcService1FileParentDir.mkdirs();
			}
			if (!AbcService1File.exists()) {
				AbcService1File.createNewFile();
			}
			fw = new FileWriter(AbcService1File.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			bw.write(AbcService1Code.toString());
			bw.close();
			fw.close();
		}

		return new ResponseEntity<>(new EntityResponse("insert file created"), HttpStatus.CREATED);
	}

	public ResponseEntity<?> insertactionfront(Integer proj_id, String prj_name, String repo_name, String tablename9,
			Integer l, HashMap<String, String> entityname) throws IOException {

		ObjectMapper mapper = new ObjectMapper();
		String asString = mapper.writeValueAsString(entityname);
		JsonParser parser = new JsonParser();
		JsonElement ele = parser.parse(asString);
		JsonObject ob = ele.getAsJsonObject();
		Set<Entry<String, JsonElement>> entry = ob.entrySet();

		for (Entry<String, JsonElement> e1 : entry) {
			String tablename = e1.getKey().toString().replaceAll("\"", "");

			String value = e1.getValue().getAsString();

			String table_name1 = tablename.replaceAll(" ", "_");
			String table_name = table_name1.substring(0, 1).toUpperCase() + table_name1.substring(1);
			String object_name = table_name;
//		String field_name = entityname.toString().replace("@", "{").replace("$", "}").replaceAll("\"", "");
			String field_name = value;

			JsonElement element = parser.parse(field_name);
			JsonObject obj = element.getAsJsonObject();
			Set<Entry<String, JsonElement>> entrySet = obj.entrySet();
//		UPDATE ACTION
			String htmlappend = "";
			String htmlpopup = "";

			String tsappend = "";
			String serviceappend = "";

			String fieldvalue = "";

			for (Entry<String, JsonElement> e : entrySet) {
				String name1 = e.getKey().toString().replaceAll(" ", "_").toLowerCase();
				String field_key = name1.substring(0, 1).toUpperCase() + name1.substring(1);
				fieldvalue = e.getValue().toString().replaceAll("\"", "");
				String replace_value = fieldvalue.replaceAll("\\$", "").replaceAll("%", "");

				if (field_name.contains("%") && fieldvalue.startsWith("%")) {
					htmlappend = "<button type=\"button\" class=\"btn btn-primary\" (click)=\"updateTestB(rowSelected.id)\">UpdateL_"
							+ field_key + "</button>\n";

					htmlpopup = "</clr-modal>\n"
							+ "<clr-modal [(clrModalOpen)]=\"modalTest\" [clrModalSize]=\"'md'\" [clrModalStaticBackdrop]=\"true\">\n"
							+ "  <h3 class=\"modal-title\">User Edit</h3>\n"
							+ "  <div class=\"modal-body\" *ngIf=\"rowSelected.id\">\n"
							+ "    <h2 class=\"heading\">{{rowSelected.id}}</h2>\n"
							+ "    <form clrForm (ngSubmit)=\"onUpdate(rowSelected.id)\">\n" + "\n"
							+ "      <clr-input-container>\n" + "        <label>" + field_key
							+ "<span class=\"required-field\">*</span></label>\n"
							+ "        <input id=\"name\" clrInput type=\"text\" [(ngModel)]=\"rowSelected." + field_key
							+ "\" name=\"" + field_key + "\" />\n" + "      </clr-input-container>\n"
							+ "      <div class=\"modal-footer\">\n"
							+ "      <button type=\"button\" class=\"btn btn-outline\" (click)=\"modalTest = false\">Cancel</button>\n"
							+ "      <button type=\"submit\" class=\"btn btn-primary\" (click)=\"updateI(rowSelected.id)\">Update</button>\n"
							+ "      </div>\n" + "    </form>\n" + "  </div>\n" + "</clr-modal>";

					tsappend = "modalTest = false;\n" + "\n" + "updateTestB(){\n" + "  this.modalTest =true;\n" + "}\n"
							+ "\n" + "updateI(id) {\n" + "  this.modalTest =false;\n" + "  this.mainService.update"
							+ replace_value + "(id,this.rowSelected." + field_key + ")\n"
							+ "    .subscribe((updatedTest) => {\n"
							+ "      console.log('Updated Test:', updatedTest);\n" + "    });\n" + "}";

					serviceappend = "updatestr(id: number, " + replace_value + ": string): Observable<any> {\n"
							+ "    const url = `" + table_name + "/" + table_name + "_update/${id}/${str}`;\n"
							+ "    return this.apiRequest.put(url, {});\n" + "  }";
				} else if (!field_name.contains("%")) {

					htmlappend = "<button type=\"button\" class=\"btn btn-primary\" (click)=\"updateI(rowSelected.id)\">Update"
							+ field_key + "</button>\n";

					tsappend = "updateI(id){\n" + "  this.mainService.update" + field_key + "(id).subscribe(\n"
							+ "    (data) => {\n" + "      console.log(data);\n" + "    });\n" + "}";

					serviceappend = "update" + field_key + "(id: number): Observable<any> {\n" + "    const url = `"
							+ table_name + "/" + table_name + "_update" + "/${id}`;\n"
							+ "    return this.apiRequest.put(url, {});\n" + "  }";

				}
			}

//         UPDATE ACTION IN HTML

			String directoryName = projectPath + File.separator + "cns-portal/code-extractor/builders" + File.separator
					+ proj_id + File.separator + proj_id + File.separator + repo_name + File.separator + "T1";

//					+ File.separator + "angular-clarity-master" + File.separator + "src" + File.separator + "app"
//					+ File.separator + "modules" + File.separator + "main" + File.separator + "BuilderComponents/"
//					+ repo_name;

//		  String directoryName = "/Users/Gaurav Kumar/Desktop/Job Pro/app_builder/build_backend/cns-portal/code-extractor/builders/7429/7429/Builder_test/T1";

			String htmlfile_name = object_name + ".component.html";

			String lineString = "<!-- button -->";

			actionappend(lineString, htmlappend, directoryName, htmlfile_name);

			String popuplineString = "<!-- htmlpopup -->";
			actionappend(popuplineString, htmlpopup, directoryName, htmlfile_name);

//           UPDATE ACTION IN TS

			String tsfile_name = object_name + ".component.ts";

			String tsString = "// updateaction";

			actionappend(tsString, tsappend, directoryName, tsfile_name);

//UPDATE ACTION IN SERVICE

			String servicefile_name = object_name + ".service.ts";

			String serviceString = "// updateaction";

			actionappend(serviceString, serviceappend, directoryName, servicefile_name);
			l++;
		}

		return null;

	}

	public String actionappend(String linestring, String appendingline, String directoryName, String file_name)
			throws IOException {

		StringBuilder frontend = new StringBuilder();

		// CEATE PACKAGE

		String filePath = directoryName + File.separator + file_name;
		System.out.println("routing path is " + filePath);

		String str = appendingline;

		int length = str.length();

		// CEATE PACKAGE

		File file = null;

		file = new File(filePath);

		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line;

		while ((line = br.readLine()) != null) {
			frontend.append(line + "\n");
//			System.out.println(line);

		}
		fr.close();
		br.close();
		System.out.println(frontend.length());

//		String back = "// buildercomponents";
		String back = linestring;

		int back_l = back.length();

		int ord = StringUtils.ordinalIndexOf(frontend.toString(), back, 1);

		String front = frontend.substring(0, ord + back_l) + "\n" + str + "\n"
				+ frontend.substring(ord + back_l, frontend.length());

		String codee = front.substring(0, front.lastIndexOf("\n")); // remove last line break

//		creating files
		File file1 = new File(filePath);

//		Writing files

		FileWriter fr1 = new FileWriter(file1.getAbsoluteFile());
		BufferedWriter br1 = new BufferedWriter(fr1);
//		PrintWriter out = new PrintWriter(br1);
//		out.println(r_import);

		br1.write(codee.toString());
		br1.close();
		return codee;

	}

}
