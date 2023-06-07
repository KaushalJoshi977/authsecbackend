package com.realnet.entitybuilder.Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.realnet.utils.Constant;

@Service
public class FilebuildService {

	public boolean filebuild(JsonObject obj, String moduleName, List<String> tablename,
			HashMap<String, String> entityname, Integer proj_id, String prj_name, String techno_stack,
			String object_type,

			String sub_object_type) throws IOException {

		List<String> service_list = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		JsonParser parser = new JsonParser();
		String technology_stack = obj.get("technologyStack").getAsString();

		String tech_stack_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.BACKEND_PORT_9191
				+ "/token/flf/tech_stack/get_techstack/" + technology_stack;
		ResponseEntity<Object> tech_stack = GET(tech_stack_url);
		Object tech_stack_body = tech_stack.getBody();
		String tech_stack_str = mapper.writeValueAsString(tech_stack_body);
		JsonElement tech_stack_element = parser.parse(tech_stack_str);
		JsonObject teck_stack_object = tech_stack_element.getAsJsonObject();
		String tech_stack_id = teck_stack_object.get("id").getAsString();

		String element_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.BACKEND_PORT_9191
				+ "/token/flf/tech_stack/get_element/" + tech_stack_id;

		ResponseEntity<Object> ele = GET(element_url);
		Object ele_body = ele.getBody();
		String ele_str = mapper.writeValueAsString(ele_body);
		JsonElement elem = parser.parse(ele_str);
		JsonArray eleobject = elem.getAsJsonArray();
		for (JsonElement eleobje : eleobject) {
			JsonObject eleobject1 = eleobje.getAsJsonObject();

			JsonElement ele_model = eleobject1.get("model");

			System.out.println(ele_model);
			String model = ele_model.getAsString();

			JsonElement element1 = parser.parse(model);
			JsonArray asJsonArray = element1.getAsJsonArray();
			for (JsonElement jsonElement : asJsonArray) {

				JsonObject object = jsonElement.getAsJsonObject();
				String title = object.get("title").getAsString().toLowerCase();
				service_list.add(title);

			}

		}

		String tech_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.BACKEND_PORT_9191
				+ "/token/flf/tech_stack/get_rnbcf/" + technology_stack;
		ResponseEntity<Object> tech = GET(tech_url);
		Object tech_body = tech.getBody();
		String tech_str = mapper.writeValueAsString(tech_body);
		JsonElement tech_element = parser.parse(tech_str);
		JsonArray array = tech_element.getAsJsonArray();

		for (JsonElement jsonElement : array) {
			JsonObject object = jsonElement.getAsJsonObject();
			JsonElement build_cont = object.get("build_controller");

			if (!build_cont.isJsonNull()) {

				JsonArray array2 = build_cont.getAsJsonArray();

				for (JsonElement jsonElement2 : array2) {
					JsonObject object2 = jsonElement2.getAsJsonObject();
					String api_endpoint = object2.get("api_endpoint").getAsString();
					String service = object2.get("service").getAsString();
					String build_tech_stack = object2.get("techStack").getAsString();
					String build_object_type = object2.get("objectType").getAsString();
					String build_sub_object_type = object2.get("subObjectType").getAsString();
					for (int m = 0; m < service_list.size(); m++) {

						if (service_list.get(m).contains(service) && techno_stack.equalsIgnoreCase(build_tech_stack)
								&& object_type.equalsIgnoreCase(build_object_type)
								&& sub_object_type.equalsIgnoreCase(build_sub_object_type)) {

							for (int l = 0; l < tablename.size(); l++) {

								String name2 = tablename.get(l);
								System.out.println(name2);
								String table_name1 = name2.replaceAll(" ", "_");
								String table_name = table_name1.substring(0, 1).toUpperCase()
										+ table_name1.substring(1);

								System.out.println(table_name);

								String replace_entity = entityname.toString().replace("{", "@").replace("}", "$");

								String file_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.APP_BUILD_19001
										+ "/token/api/" + api_endpoint + "/" + proj_id + "/" + prj_name + "/"
										+ moduleName + "/" + table_name + "?entityname=" + replace_entity;
								ResponseEntity<Object> f = GET(file_url);
								Object body = f.getBody();
								System.out.println(body);

//								actionBuilderService.actionbuilder(proj_id, prj_name, moduleName, table_name, l,
//										replace_entity);

							}
						}

					}

				}

			}
		}

//		************TABLE FILE BUILD CODE END *******

		return true;
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
