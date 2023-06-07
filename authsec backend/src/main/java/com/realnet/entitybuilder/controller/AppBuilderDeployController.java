package com.realnet.entitybuilder.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.realnet.entitybuilder.Services.AppbuilderdeployService;
import com.realnet.entitybuilder.response.EntityResponse;
import com.realnet.utils.Constant;
@RestController
@RequestMapping(value = "/entityBuilder", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppBuilderDeployController {

	@Autowired
	private AppbuilderdeployService sureservice;

	@Value("${projectPath}")
	private String projectPath;

//	Creating files like yaml, shell SCript ,docker File

	@GetMapping("/deployfile_appbuild/{id}")
	public ResponseEntity<?> CreateFiles(@PathVariable Integer id) throws IOException {

		String profile_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.BACKEND_PORT_9191
				+ "/token/fnd1/callingsureops/deplomentprofile/" + 3;

		// get project
		ResponseEntity<Object> deploy_line_id = GET(profile_url);

		String straing_line_id = deploy_line_id.getBody().toString();
		long deployment_line_id = Long.parseLong(straing_line_id);
		Integer current_state = 1;

//		current_state++;

		Date d = new Date();
		String addString = "_" + d.getTime();

		String prj_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.BACKEND_PORT_9191
				+ "/token/fnd1/callingsureops/getproject/" + id;

		// get project
		ResponseEntity<Object> prj = GET(prj_url);
		Object prj_body = prj.getBody();
		List<String> lineList = callforproject(prj_body);

		String workflow_id = "66";

		// get workflowline
		String line_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.BACKEND_PORT_9191
				+ "/token/fnd1/callingsureops/workflowline/" + workflow_id;
		ResponseEntity<Object> get = GET(line_url);
		Object body = get.getBody();
		String workflow_model = callforline(body);

		String PRJ_NAME = lineList.get(1);
		String portNumber = lineList.get(2);
		String prj_id = lineList.get(3);

//		Parsing Json data 

		List<String> keys = new ArrayList<>();

		JsonParser parser1 = new JsonParser();
		JsonElement element1 = parser1.parse(workflow_model);
		JsonArray Array1 = element1.getAsJsonArray();

		String Workflow_value = "yaml";
		String Workflow_value1 = "shell";

		String Workflow_value2 = "docker";

		int i = 1;
		for (JsonElement ar1 : Array1) {
			JsonObject obj1 = ar1.getAsJsonObject();
//			System.out.println(obj1);
			Set<Map.Entry<String, JsonElement>> entries = obj1.entrySet();
			for (Map.Entry<String, JsonElement> entry : entries) {
				String key = entry.getKey();
				String value1 = entry.getValue().getAsString();
				keys.add(value1);

				if (value1.equals("Yamel File")) {
					System.out.println("yaml data");
					sureservice.yml(workflow_model, PRJ_NAME, prj_id, portNumber, deployment_line_id, Workflow_value,
							workflow_id, addString);
					i++;
				} else if (value1.equalsIgnoreCase("Shell Script")) {
					System.out.println("script data");
					sureservice.script(workflow_model, PRJ_NAME, prj_id, portNumber, deployment_line_id,
							Workflow_value1, workflow_id, addString);
					i++;
				} else if (value1.equalsIgnoreCase("Docker File")) {
					System.out.println("docker data");
					sureservice.docker(workflow_model, PRJ_NAME, prj_id, portNumber, deployment_line_id,
							Workflow_value2, workflow_id, addString);
					i++;
				}

			}
		}
		if (i == 1) {

			sureservice.Createonefile(lineList, workflow_model, deployment_line_id, addString);
			current_state++;
		}

		// DATA INSERT IN TABLE

		return new ResponseEntity<>(new EntityResponse("all file created"), HttpStatus.CREATED);

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

		String prj_id = obj.get("id").getAsString();
		list.add(prj_id);

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

}
