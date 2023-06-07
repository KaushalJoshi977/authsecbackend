package com.realnet.entitybuilder.Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
import com.realnet.entitybuilder.response.EntityResponse;
import com.realnet.utils.Constant;

@Service
//@RestController
//@RequestMapping("/entityBuilder")
public class Script_Making {

	@Value("${projectPath}")
	private String projectPath;

	@Autowired
	private ScriptSrvice sureservice;

//	Creating files like yaml, shell SCript ,docker File

	public ResponseEntity<?> CreateFiles(Integer prj_id,String workflow_id)
			throws IOException {
		
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
					sureservice.script(prj_id, workflow_model, PRJ_NAME, portNumber, gitea_url,
							Deployment_profile);
					i++;
				} 
			}
		}
		if (i == 1) {

			sureservice.Createonefile(prj_id, lineList, workflow_model,   Deployment_profile);
		}
		
		
			


		return new ResponseEntity<>(new EntityResponse("script file created"), HttpStatus.CREATED);

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
//		System.out.println(workflowid);

		String prj_name = obj.get("projectName").getAsString();
		list.add(prj_name);

		String port_number = obj.get("portNumber").getAsString();
		list.add(port_number);

		String gitea_url = obj.get("gitea_url").getAsString();
		list.add(gitea_url);

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
//		System.out.println(workflowid);
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
//		System.out.println(workflowid);
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
