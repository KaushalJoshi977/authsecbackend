package com.realnet.entitybuilder.Services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.realnet.exceptions.ResourceNotFoundException;
import com.realnet.utils.Constant;

@Service
public class AppbuilderdeployService {

	@Value("${projectPath}")
	private String projectPath;

	public void Createonefile(List<String> lineList, String workflow_model, Long profile_id, String addString)
			throws UnsupportedEncodingException, JsonProcessingException {

//		Appending data to Sting builder object

		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(workflow_model);
		JsonArray Array = element.getAsJsonArray();

		for (JsonElement ar : Array) {

			JsonObject obj = ar.getAsJsonObject();

			JsonElement workflowvalue = obj.get("workflow");
			String Workflow_value = workflowvalue.getAsString();

//			String table_url = "http://"+Constant.LOCAL_HOST+":9191/token/fnd1/callingsureops/workflowtable/" + Workflow_value;

//			Object body = GET(table_url).getBody();
//			String tableid = callfortable(body);

			String tableid_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.BACKEND_PORT_9191
					+ "/token/fnd1/callingsureops/workflowlinebytable_id/" + Workflow_value;
			Object body2 = GET(tableid_url).getBody();
			String workflow = callforline(body2);
			CreateFiles(lineList, workflow, profile_id, Workflow_value, addString);

//				}			

//			}
		}

	}

//	Creating files like yaml, shell SCript ,docker File

	public ResponseEntity<?> CreateFiles(List<String> lineList, String workflow_model, Long profile_id,
			String Workflow_value, String addString) throws UnsupportedEncodingException, JsonProcessingException {

//		Appending data to Sting builder object

		HashMap<String, String> json = new HashMap<>();

		String workflow_id = lineList.get(0);
		String PRJ_NAME = lineList.get(1);
		String portNumber = lineList.get(2);
		String prj_id = lineList.get(3);

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
				String key = entry.getKey();
				String string = key.toString();
				String value = entry.getValue().getAsString();
				keys.add(value);

				if (value.equalsIgnoreCase("Yamel File")) {
					System.out.println("yaml data");
					yml(workflow_model, PRJ_NAME, prj_id, portNumber, profile_id, Workflow_value, workflow_id,
							addString);
					i++;
				} else if (value.equalsIgnoreCase("Shell Script")) {
					System.out.println("script data");
					script(workflow_model, PRJ_NAME, prj_id, portNumber, profile_id, Workflow_value, workflow_id,
							addString);
					i++;
				} else if (value.equalsIgnoreCase("Docker File")) {
					System.out.println("docker data");
					docker(workflow_model, PRJ_NAME, prj_id, portNumber, profile_id, Workflow_value, workflow_id,
							addString);
					i++;
				}

			}
		}
		if (i == 1) {
			Createonefile(lineList, workflow_model, profile_id, addString);

		}

		return new ResponseEntity<HashMap<String, String>>(json, HttpStatus.CREATED);
	}

//      CREATE YML FILE
//		if (file_type.equals("yaml")) {
	public void yml(String str, String prj, String prj_id, String portNumber, Long deployment_line_id,
			String Workflow_value, String workflow_id, String addString) throws JsonProcessingException {

		ArrayList<Object> namelist = new ArrayList<>();
		ArrayList<Object> filepath_list = new ArrayList<>();
		ArrayList<Object> list = new ArrayList<>();
		String sureops_dir = projectPath + File.separator + workflow_id + addString + File.separator + "sureops";

		StringBuilder yamldata = new StringBuilder();

		List<String> keys = new ArrayList<>();
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(str);
		JsonArray Array = element.getAsJsonArray();

		for (JsonElement ar : Array) {
			JsonObject obj = ar.getAsJsonObject();

			Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();
			for (Map.Entry<String, JsonElement> entry : entries) {
				String key = entry.getKey();
				String value = entry.getValue().getAsString();
				keys.add(value);

				if (key.equalsIgnoreCase("a_uri")) {
					String full_url = value + "/" + prj_id + "/" + deployment_line_id + "/" + prj + "/" + "commit_msg";
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
						yamldata.append(value);
						System.out.println(yamldata);
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

						String finalreplace_value = replace10.replace("<MSG>",
								"\"" + list.get(7).toString().replace("\"", "") + "\"");

						// for testing
						yamldata.append(finalreplace_value);
						System.out.println("yamldata make");
					}

				}

			}
		}

		String file_text = null;

		if (!namelist.isEmpty()) {
			file_text = namelist.get(0).toString().replace("\"", "") + ".yml";

		} else {
			file_text = "test" + addString + ".yml";
		}

		try {

			// Creating Folder

			other(addString, yamldata.toString(), file_text, deployment_line_id, filepath_list, workflow_id, prj_id,
					prj);

		} catch (IOException i) {

		}
	}

//      CREATE SCRIPT FILE

//		if (file_type.equalsIgnoreCase("script")) {
	public void script(String str, String prj, String prj_id, String portNumber, Long deployment_line_id,
			String Workflow_value, String workflow_id, String addString) throws JsonProcessingException {

		ArrayList<Object> namelist = new ArrayList<>();
		ArrayList<Object> filepath_list = new ArrayList<>();
		ArrayList<Object> list = new ArrayList<>();
		String sureops_dir = projectPath + File.separator + workflow_id + addString + File.separator + "sureops";

		StringBuilder scriptdata = new StringBuilder();

		List<String> keys = new ArrayList<>();
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(str);
		JsonArray Array = element.getAsJsonArray();

		for (JsonElement ar : Array) {
			JsonObject obj = ar.getAsJsonObject();

			Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();
			for (Map.Entry<String, JsonElement> entry : entries) {
				String key = entry.getKey();

				String value = entry.getValue().getAsString();
				keys.add(value);

				if (key.equalsIgnoreCase("a_uri")) {
					String full_url = value + "/" + prj_id + "/" + deployment_line_id + "/" + prj + "/" + "commit_msg";
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

						String finalreplace_value = replace10.replace("<MSG>",
								"\"" + list.get(7).toString().replace("\"", "") + "\"");

						// for testing
						scriptdata.append(finalreplace_value);
						System.out.println("scriptdata make");
					}

				}
			}
		}
		String file_text2 = null;

		if (!namelist.isEmpty()) {
			file_text2 = namelist.get(0).toString().replace("\"", "") + ".sh";

		} else {
			file_text2 = "test" + addString + ".sh";
		}

		try {

//				Creating Folder

			other(addString, scriptdata.toString(), file_text2, deployment_line_id, filepath_list, workflow_id, prj_id,
					prj);

		} catch (IOException i) {

		}

	}

//       CREATE DOCKER FILE
	public void docker(String str, String prj, String prj_id, String portNumber, Long deployment_line_id,
			String Workflow_value, String workflow_id, String addString) throws JsonProcessingException {

		ArrayList<Object> namelist = new ArrayList<>();
		ArrayList<Object> filepath_list = new ArrayList<>();
		ArrayList<Object> list = new ArrayList<>();
		String sureops_dir = projectPath + File.separator + workflow_id + addString + File.separator + "sureops";

		StringBuilder dockerdata = new StringBuilder();

		List<String> keys = new ArrayList<>();
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(str);
		JsonArray Array = element.getAsJsonArray();

		for (JsonElement ar : Array) {
			JsonObject obj = ar.getAsJsonObject();

			Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();
			for (Map.Entry<String, JsonElement> entry : entries) {
				String key = entry.getKey();
				String value = entry.getValue().getAsString();
				keys.add(value);

				if (key.equalsIgnoreCase("a_uri")) {
					String full_url = value + "/" + prj_id + "/" + deployment_line_id + "/" + prj + "/" + "commit_msg";
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
						dockerdata.append(value);
						System.out.println("dockerdata make");
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

						String finalreplace_value = replace10.replace("<MSG>",
								"\"" + list.get(7).toString().replace("\"", "") + "\"");

						// for testing
						dockerdata.append(finalreplace_value);
						System.out.println("dockerdata make");
					}

				}

			}
		}

		String file_text3 = null;

		if (!namelist.isEmpty()) {
			file_text3 = namelist.get(0).toString().replace("\"", "");

		} else {
			file_text3 = "test" + addString;
		}
		try {

//				Creating Folder

			other(addString, dockerdata.toString(), file_text3, deployment_line_id, filepath_list, workflow_id, prj_id,
					prj);

		} catch (IOException i) {

		}
	}

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
		System.out.println(workflowid);

		String prj_name = obj.get("projectName").getAsString();
		list.add(prj_name);

		String port_number = obj.get("portNumber").getAsString();
		list.add(port_number);

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

	private void other(String addString, String yamldata, String file_text, Long profile_id, List<Object> filepath_list,
			String workflow_id, String prj_id, String proj_name) throws IOException {

		String scriptDir = null;
		String ref_path = null;
		workflow_id = workflow_id + addString;

		// when destination is not empty
		if (!file_text.contains("app_build_deploy_exec")) {
			String sureopspath_name = filepath_list.get(0).toString().replace("\"", "");

			ref_path = File.separator + proj_name;

			scriptDir = projectPath + File.separator + "deploy";
			System.out.println(scriptDir);

			File Dir1 = new File(scriptDir);
			if (!Dir1.exists()) {
				boolean mkdir = Dir1.mkdir();
				if (!mkdir) {
					System.out.println("folder not created");
					throw new ResourceNotFoundException("path not fouund");

				}
			}
			scriptDir = scriptDir + ref_path;
			System.out.println(scriptDir);

			File Dir2 = new File(scriptDir);
			if (!Dir2.exists()) {
				boolean mkdir = Dir2.mkdir();
				if (!mkdir) {
					System.out.println("folder not created");
					throw new ResourceNotFoundException("path not fouund");

				}
			}

			// when destination is empty
		} else {

			Date d = new Date();
			String add = "_"+d.getTime();
			file_text=file_text+add;
			
			scriptDir = projectPath + File.separator + "deploy";
			System.out.println(scriptDir);

			File Dir1 = new File(scriptDir);
			if (!Dir1.exists()) {
				boolean mkdir = Dir1.mkdir();
				if (!mkdir) {
					System.out.println("folder not created");
					throw new ResourceNotFoundException("path not fouund");

				}
			}

		}

		// creating files
//		String dir2 = projectPath + "/yaml_files" + addString + "/" + file_text;
		String dir2 = scriptDir + File.separator + file_text;

		File file = new File(dir2);

		if (!file.exists()) {
			file.createNewFile();
		}
		System.out.println("file created successfully");
		System.out.println("file path : " + dir2);

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(yamldata);
		bw.close();

		if (file_text.contains("app_build_deploy_exec.sh")) {

			String filepath = scriptDir;
			String filename = file_text;
			// INSERT DATA IN JOB PRO FOR RUN SCRIPT
			insertin_jobpro(filepath, filename, prj_id, addString);

		}
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

	public void insertin_jobpro(String filepath, String filename, String prj_id, String addstring)
			throws JsonProcessingException {

		Map<String, String> jobdata = new HashMap<String, String>();
//		jobdata.put("parameters", builder.toString());
		jobdata.put("url", "http://" + Constant.LOCAL_HOST + ":" + Constant.SCRIPT_RUNNER_8901
				+ "/api/runScript?filepath=" + filepath + "&filename=" + filename);
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
		System.out.println(res1.getBody());

	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return headers;
	}

}
