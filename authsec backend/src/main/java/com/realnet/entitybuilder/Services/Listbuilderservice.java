package com.realnet.entitybuilder.Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.realnet.entitybuilder.response.EntityResponse;
import com.realnet.utils.Constant;

@Service
public class Listbuilderservice {
	@Value("${projectPath}")
	private String projectPath;

	@Autowired
	private Script_Making script_serviceMaking;

	@Autowired
	private Index_Service_be index_Service;

	@Autowired
	private FilebuildService filebuildService;

	public ResponseEntity<?> dashboardbuilder(@PathVariable Integer proj_id) throws IOException {

		List<String> dash = new ArrayList<>();
		List<String> tablename = new ArrayList<>();
		HashMap<String, String> entityname = new HashMap<>();

		String prj_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.BACKEND_PORT_9191
				+ "/token/fnd1/callingsureops/getproject/" + proj_id;

		// get project
		ResponseEntity<Object> prj = GET(prj_url);
		Object prj_body = prj.getBody();

		ObjectMapper mapper = new ObjectMapper();
		String str = mapper.writeValueAsString(prj_body);
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(str);

		// FOR WIRE FRAME CODE

		JsonObject obj = element.getAsJsonObject();
		obj.get("projectPrefix").getAsString();

		String prj_name = obj.get("projectName").getAsString();

		JsonElement modules = obj.get("modules");

		JsonArray module_list = modules.getAsJsonArray();

		if (!modules.isJsonNull()) {

			for (JsonElement modue : module_list) {
				JsonObject module = modue.getAsJsonObject();

				String modulen = module.get("moduleName").toString();
				String mo = modulen.toLowerCase().replaceAll(" ", "_");
				String mol = mo.replaceAll("\"", "");
				String moduleName = mol.substring(0, 1).toUpperCase() + mol.substring(1);

				String moduleid = module.get("id").toString().replaceAll("\"", "");

				String dash_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.BACKEND_PORT_9191
						+ "/token/fnd1/callingsureops/dashboard_header/" + moduleid;

				ResponseEntity<Object> get = GET(dash_url);

				if (get.hasBody()) {
					Object dash_body = get.getBody();

					String dashheadstr = mapper.writeValueAsString(dash_body);
					JsonElement dashhead = parser.parse(dashheadstr);
					JsonObject dashObject = dashhead.getAsJsonObject();

					JsonArray linearray = dashObject.get("dashbord1_Line").getAsJsonArray();

					for (JsonElement line : linearray) {
						JsonObject object = line.getAsJsonObject();
						String model = object.get("model").getAsString();

						tablename.add("test");
						entityname.put("test", "totable");

//						boolean filebuild = filebuildService.filebuild(obj, moduleName, tablename, entityname, proj_id,
//								prj_name);

						dash.add(model);

					}

				}

			}

		} else {
			return new ResponseEntity<>(new EntityResponse("modules is empty"), HttpStatus.BAD_REQUEST);

		}

		// WIREFRAME CODE END ***************

		return new ResponseEntity<>(dash, HttpStatus.CREATED);

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
