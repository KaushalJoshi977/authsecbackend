package com.realnet.entitybuilder.Services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.realnet.entitybuilder.response.EntityResponse;
import com.realnet.utils.Constant;

@Service
public class BuilderService {

	@Value("${projectPath}")
	private String projectPath;

//	Creating files like yaml, shell SCript ,docker File

	public ResponseEntity<?> CreateFiles(Integer prj_id, String workflow_id,String java_class) throws IOException {

//		String table_name = tble.replaceAll(" ", "_").toLowerCase();

		long Deployment_profile = 6;

		String prj_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.BACKEND_PORT_9191
				+ "/token/fnd1/callingsureops/getproject/" + prj_id;

		// get project
		ResponseEntity<Object> prj = GET(prj_url);
		Object prj_body = prj.getBody();
		List<String> lineList = callforproject(prj_body);

		// get workflowline
		String line_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.BACKEND_PORT_9191
				+ "/token/fnd1/callingsureops/workflowline/" + workflow_id;
		ResponseEntity<Object> get = GET(line_url);
		Object body = get.getBody();
		String workflow_model = callforline(body);

		String PRJ_NAME = lineList.get(1);
		String portNumber = lineList.get(2);
		String gitea_url = lineList.get(3);
//		String prj_id = lineList.get(4);

//		Parsing Json data 

		JsonParser parser1 = new JsonParser();
		JsonElement element1 = parser1.parse(workflow_model);
		JsonArray Array1 = element1.getAsJsonArray();

		int i = 1;
		for (JsonElement ar1 : Array1) {
			JsonObject obj1 = ar1.getAsJsonObject();
//			System.out.println(obj1);
			Set<Map.Entry<String, JsonElement>> entries = obj1.entrySet();
			for (Map.Entry<String, JsonElement> entry : entries) {
				String key = entry.getKey();
				String value1 = entry.getValue().getAsString();

				if (value1.equalsIgnoreCase("Shell Script")) {
					System.out.println("script data");
					script(prj_id, workflow_model, PRJ_NAME, portNumber, gitea_url, Deployment_profile,java_class);
					i++;
				}
			}
		}
		if (i == 1) {

			Createonefile(prj_id, lineList, workflow_model, Deployment_profile,java_class);
		}

		return new ResponseEntity<>(new EntityResponse("script file created"), HttpStatus.CREATED);

	}

	public void Createonefile(Integer prj_id, List<String> lineList, String workflow_model, Long Deployment_profile,String java_class)
			throws IOException {

//		Parsing Json data 

		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(workflow_model);
		JsonArray Array = element.getAsJsonArray();

		for (JsonElement ar : Array) {

			JsonObject obj = ar.getAsJsonObject();

			JsonElement workflowvalue = obj.get("workflow");
			String value = workflowvalue.getAsString();

			String tableid_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.BACKEND_PORT_9191
					+ "/token/fnd1/callingsureops/workflowlinebytable_id/" + value;
			Object body2 = GET(tableid_url).getBody();
			String workflow = callforline(body2);
			CreateFiles(prj_id, lineList, workflow, Deployment_profile,java_class);

//				}			

//			}
		}

	}

//	Creating files like yaml, shell SCript ,docker File

	public ResponseEntity<?> CreateFiles(Integer prj_id, List<String> lineList, String workflow_model,
			Long Deployment_profile,String java_class) throws IOException {

//		Appending data to Sting builder object

		HashMap<String, String> json = new HashMap<>();

		String workflow_id = lineList.get(0);
		String PRJ_NAME = lineList.get(1);
		String portNumber = lineList.get(2);
		String gitea_url = lineList.get(3);

//		Parsing Json data 

		List<String> keys = new ArrayList<>();

		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(workflow_model);
		JsonArray Array = element.getAsJsonArray();

		int i = 1;
		for (JsonElement ar : Array) {
			JsonObject obj = ar.getAsJsonObject();
			Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();
			for (Map.Entry<String, JsonElement> entry : entries) {
//				String key = entry.getKey();
				String value = entry.getValue().getAsString();
				keys.add(value);

				if (value.equalsIgnoreCase("Shell Script")) {
					System.out.println("script data");
					script(prj_id, workflow_model, PRJ_NAME, portNumber, gitea_url, Deployment_profile,java_class);
					i++;
				}

			}
		}
		if (i == 1) {
			Createonefile(prj_id, lineList, workflow_model, Deployment_profile,java_class);

		}

		return new ResponseEntity<HashMap<String, String>>(json, HttpStatus.CREATED);
	}

//      CREATE SCRIPT FILE

	public void script(Integer prj_id, String workflow_model, String prj, String portNumber, String gitea_url,
			Long deployment_line_id,String java_class) throws IOException {

//		String file_text2 = "script_test" + addString + ".sh";

//		ArrayList<Object> list = calldeploymentprofile(Deployment_profile);
		ArrayList<Object> list = new ArrayList<>();

		ArrayList<Object> getdatafromgiturl = getdatafromgiturl(gitea_url);

		ArrayList<Object> namelist = new ArrayList<>();
		ArrayList<Object> filepath_list = new ArrayList<>();
		String sureops_dir = projectPath + File.separator + "builder" + File.separator + prj_id + File.separator
				+ "index";

		StringBuilder scriptdata = new StringBuilder();
//		StringBuilder scriptvalue = new StringBuilder();

		List<String> keys = new ArrayList<>();
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(workflow_model);
		JsonArray Array = element.getAsJsonArray();

		for (JsonElement ar : Array) {
			JsonObject obj = ar.getAsJsonObject();
			Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();
			for (Map.Entry<String, JsonElement> entry : entries) {
				String key = entry.getKey();
				String value = entry.getValue().getAsString();
				keys.add(value);

				if (key.equalsIgnoreCase("a_uri")) {
					String full_url = value + "/" + prj_id + "/" + deployment_line_id + "/" + prj + "/" + "builder_class";
					ArrayList<Object> jsonlist = call_url_byjson(full_url);

					String git_user = jsonlist.get(0).toString().replace("\"", "");
					list.add(git_user);

					String git_password = jsonlist.get(1).toString().replace("\"", "");
					list.add(git_password);

					String git_email = jsonlist.get(2).toString().replace("\"", "");
					list.add(git_email);

					String dokr_username = jsonlist.get(3).toString().replace("\"", "");
					list.add(dokr_username);

					String dokr_url = jsonlist.get(4).toString().replace("\"", "");
					list.add(dokr_url);

					String dokr_pass = jsonlist.get(5).toString().replace("\"", "");
					list.add(dokr_pass);

					String PRJ_NAME = jsonlist.get(6).toString().replace("\"", "");
					list.add(PRJ_NAME);

					String MSG = jsonlist.get(7).toString().replace("\"", "");
					list.add(MSG);

					String DOMAIN = jsonlist.get(8).toString().replace("\"", "");
					list.add(DOMAIN);

					String REPO_NAME = jsonlist.get(9).toString().replace("\"", "");
					list.add(REPO_NAME);

					String REPO_NAME_TO = jsonlist.get(10).toString().replace("\"", "");
					list.add(REPO_NAME_TO);

				}

				if (key.equals("destination")) {
					filepath_list.add(value);
				}
				if (key.equals("name")) {
					namelist.add(value);
				}

			}
		}

		for (JsonElement ar : Array) {
			JsonObject obj = ar.getAsJsonObject();

			Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();
			for (Map.Entry<String, JsonElement> entry : entries) {
				String key = entry.getKey();

				String value = entry.getValue().getAsString();
				if (key.equalsIgnoreCase("script")) {

					if (list.isEmpty()) {
						scriptdata.append(value);
						System.out.println(scriptdata);
					} else {

						String replace = value.replace("<REPO_NAME>", list.get(9).toString().replace("\"", ""));
						String replace2 = replace.replace("<REPO_NAME_TO>", prj);

						String replace3 = replace2.replace("<GITEA_USER>", list.get(0).toString().replace("\"", ""));

						String replace4 = replace3.replace("<GITEA_PASS>", list.get(1).toString().replace("\"", ""));

						String replace5 = replace4.replace("<GITEA_EMAIL>", list.get(2).toString().replace("\"", ""));

						String replace6 = replace5.replace("<DOMAIN>", list.get(8).toString().replace("\"", ""));

						String replace7 = replace6.replace("<DOCKER_USER>", list.get(3).toString().replace("\"", ""));

						String replace8 = replace7.replace("<DOCKER_PASS>", list.get(5).toString().replace("\"", ""));

						String replace9 = replace8.replace("<DOCKER_URL>", list.get(4).toString().replace("\"", ""));

						String replace10 = replace9.replace("<SUREOPS_FOLDER>", sureops_dir);
						
						String replace11 = replace10.replace("<JAVA-CLASS>", java_class);


						String finalreplace_value = replace11.replace("<MSG>",
								"\"" + list.get(7).toString().replace("\"", "") + "\"");

						// for testing
						scriptdata.append(finalreplace_value);
						System.out.println("scriptdata make");
					}

				}
			}
		}

		String file_text = null;

		if (!namelist.isEmpty()) {
			file_text = namelist.get(0).toString().replace("\"", "") + ".sh";

		} else {
			file_text = "copy_exec" + ".sh";
		}

//				Creating Folder

		String Path1 = null;
		String ref_path = null;

		// when destination is not empty
		if (!filepath_list.isEmpty()) {
			String sureopspath_name = filepath_list.get(0).toString().replace("\"", "");

			ref_path = File.separator + sureopspath_name;

			Path1 = projectPath + File.separator + "cns-portal";

			File builderMainDir1 = new File(Path1);
			if (!builderMainDir1.exists()) {
				boolean mkdir = builderMainDir1.mkdir();
				System.out.println("builder folder " + mkdir);
			}

			Path1 = Path1 + File.separator + "code-extractor";

			System.out.println(Path1);

			File staticMainDir1 = new File(Path1);
			if (!staticMainDir1.exists()) {
				boolean mkdir = staticMainDir1.mkdir();
				System.out.println(mkdir);
			}

			Path1 = Path1 + ref_path;
			System.out.println(Path1);

			File Dir1 = new File(Path1);
			if (!Dir1.exists()) {
				boolean mkdir = Dir1.mkdir();
				if (!mkdir) {
					System.out.println("folder not created");

				}
			}

			// when destination is empty
		} else {

			Path1 = projectPath + File.separator + "cns-portal";

			File builderMainDir1 = new File(Path1);
			if (!builderMainDir1.exists()) {
				boolean mkdir = builderMainDir1.mkdir();
				System.out.println("builder folder " + mkdir);
			}

			Path1 = Path1 + File.separator + "code-extractor";

			File staticMainDir1 = new File(Path1);
			if (!staticMainDir1.exists()) {
				boolean mkdir = staticMainDir1.mkdir();
				System.out.println(mkdir);
			}

			ref_path = File.separator + "builders";

			Path1 = Path1 + ref_path;
			System.out.println(Path1);

			File Dir2 = new File(Path1);
			if (!Dir2.exists()) {
				boolean mkdir = Dir2.mkdir();
				if (!mkdir) {
					System.out.println("folder not created");

				}
			}

		}

		// creating files
//			String dir2 = projectPath + "/yaml_files" + addString + "/" + file_text;
		String dir2 = Path1 + File.separator + file_text;

		File file = new File(dir2);

		if (!file.exists()) {
			file.createNewFile();
			Files.setPosixFilePermissions(file.toPath(), PosixFilePermissions.fromString("rwxrwxrwx"));

			file.setExecutable(true);
			file.setReadable(true);
			file.setWritable(true);
		}
		System.out.println("file created successfully");
		System.out.println("file path : " + dir2);

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(scriptdata.toString());
		bw.close();

		String filepath = Path1;
		String filename = file_text;

		// INSERT DATA IN JOB PRO FOR RUN SCRIPT
		insertin_jobpro(prj_id, filename, filepath);

//			}
	}

	// CALL PROJECT PORTAL
	public List<String> callforproject(Object object) throws JsonProcessingException {

		List<String> list = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		String str = mapper.writeValueAsString(object);
//		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object));// print
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(str);

		JsonObject obj = element.getAsJsonObject();
		String workflowid = obj.get("workflow_id").getAsString();
		list.add(workflowid);

		String prj_name = obj.get("projectName").getAsString();
		list.add(prj_name);

		String port_number = obj.get("portNumber").getAsString();
		list.add(port_number);

		String gitea_url = obj.get("gitea_url").getAsString();
		list.add(gitea_url);

		return list;
	}

	public String callforline(Object object) throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		String str = mapper.writeValueAsString(object);
//		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object));// print
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(str);

		JsonObject obj = element.getAsJsonObject();
		JsonElement workflowid = obj.get("model");
		System.out.println(workflowid);
		return workflowid.getAsString();
	}

	public String callfortable(Object object) throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		String str = mapper.writeValueAsString(object);
//		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object));// print
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(str);

		JsonObject obj = element.getAsJsonObject();
		JsonElement workflowid = obj.get("id");
		System.out.println(workflowid);
		return workflowid.getAsString();
	}

	public ResponseEntity<Object> GET(String get) {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Object> u = restTemplate.getForEntity(get, Object.class);

		return u;

	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return headers;
	}

	public ArrayList<Object> calldeploymentprofile(Long Deployment_profile) throws JsonProcessingException {
		ArrayList<Object> list = new ArrayList<>();

		String tableid_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.BACKEND_PORT_9191
				+ "/token/fnd1/callingsureops/deplomentprofile_line/" + Deployment_profile;
		Object body2 = GET(tableid_url).getBody();

		ObjectMapper mapper = new ObjectMapper();
		String str = mapper.writeValueAsString(body2);
//		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body2));// print
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(str);

		JsonObject obj = element.getAsJsonObject();

		JsonElement git_user = obj.get("git_user");
		list.add(git_user);

		JsonElement git_password = obj.get("git_password");
		list.add(git_password);

		JsonElement git_email = obj.get("git_email");
		list.add(git_email);

		System.out.println(list);
		return list;
	}

	public ArrayList<Object> call_url_byjson(String full_url) throws JsonProcessingException {
		ArrayList<Object> list = new ArrayList<>();

		Object body2 = GET(full_url).getBody();

		ObjectMapper mapper = new ObjectMapper();
		String str = mapper.writeValueAsString(body2);
//		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body2));// print
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(str);

		JsonObject obj = element.getAsJsonObject();

		JsonElement git_user = obj.get("GITEA_USER");
		list.add(git_user);

		JsonElement git_password = obj.get("GITEA_PASS");
		list.add(git_password);

		JsonElement git_email = obj.get("GITEA_EMAIL");
		list.add(git_email);

		JsonElement dokr_username = obj.get("DOCKER_USER");
		list.add(dokr_username);

		JsonElement dokr_url = obj.get("DOCKER_URL");
		list.add(dokr_url);

		JsonElement dokr_pass = obj.get("DOCKER_PASS");
		list.add(dokr_pass);

		JsonElement PRJ_NAME = obj.get("PRJ_NAME");
		list.add(PRJ_NAME);

		JsonElement MSG = obj.get("MSG");
		list.add(MSG);

		JsonElement DOMAIN = obj.get("DOMAIN");
		list.add(DOMAIN);

		JsonElement REPO_NAME = obj.get("REPO_NAME");
		list.add(REPO_NAME);

		JsonElement REPO_NAME_TO = obj.get("REPO_NAME_TO");
		list.add(REPO_NAME_TO);

		System.out.println(list);
		return list;
	}

	public ArrayList<Object> getdatafromgiturl(String str) throws JsonProcessingException {

		ArrayList<Object> list = new ArrayList<>();

		int indexOf = str.indexOf("://");

//		int indexOf2 = str.indexOf("/a");

		int ordinalIndexOf = StringUtils.ordinalIndexOf(str, "/", 3);
		String domain = str.substring(indexOf + 3, ordinalIndexOf);

//		String domain = str.substring(indexOf + 3, indexOf2);

		System.out.println("\n" + domain);
		list.add(domain);

		int namelast = str.lastIndexOf("/");
		int namelast2 = str.lastIndexOf(".");
		String name = str.substring(namelast + 1, namelast2);
		System.out.println(name);
		list.add(name);

		return list;
	}

	public ResponseEntity<?> geterror() {
		return new ResponseEntity<>("file not created", HttpStatus.BAD_REQUEST);
	}

	public void insertin_jobpro(Integer prj_id, String filename, String filepath) throws JsonProcessingException {

		Map<String, String> jobdata = new HashMap<String, String>();
//		jobdata.put("parameters", builder.toString());
		jobdata.put("url", "http://" + Constant.LOCAL_HOST + ":" + Constant.APP_BUILD_19001
				+ "/entityBuilder/runScript?filepath=" + filepath + "&filename=" + filename);
		jobdata.put("method", "GET");
		jobdata.put("connection_name", "jobprtal");
		jobdata.put("createdBy", "2022");
		jobdata.put("updatedBy", "2022");
		jobdata.put("job_type", "CreatesureprjPrj");
		jobdata.put("ref", prj_id.toString());
		System.out.println(jobdata);

		RestTemplate restTemplate = new RestTemplate();
		String jobprourl = "http://" + Constant.LOCAL_HOST + ":" + Constant.JOBPRO_PORT_8087 + "/jobpro/pipiline";
		HttpHeaders headers = getHeaders();
		HttpEntity<Object> request = new HttpEntity<Object>(jobdata, headers);
		ResponseEntity<Object> res1 = restTemplate.postForEntity(jobprourl, request, Object.class);
		if (res1.getStatusCodeValue() == 200) {
			System.out.println("script runner data inserted in job pro");
		}
//		System.out.println(res1.getBody());
	}

}
