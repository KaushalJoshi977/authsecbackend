package com.realnet.entitybuilder.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.realnet.entitybuilder.Services.DashBuilderService;
import com.realnet.entitybuilder.Services.Index_Service_be;
import com.realnet.entitybuilder.entity.Dashboardaxis;
import com.realnet.entitybuilder.repo.DashboardAxisrepo;
import com.realnet.utils.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/entityBuilder/dashboardbuilder", produces = MediaType.APPLICATION_JSON_VALUE)
public class DashBuilderController {

	@Value("${projectPath}")
	private String projectPath;

	@Autowired
	private DashboardAxisrepo axisrepo;

	@Autowired
	private Index_Service_be index_Service;

	@Autowired
	private DashBuilderService dashboardBuilderService;

	@GetMapping(value = "/dashbuilder/{proj_id}")
	public ResponseEntity<?> dashboardbuilder(@PathVariable Integer proj_id) throws IOException {
		int j = 1;
		String copy_from = null;
		String Copy_to_path = null;
		index_Service.build_index(proj_id, "test", j, copy_from, Copy_to_path);
		j++;
		ResponseEntity<?> dash = dashboardBuilderService.dashboardbuilder(proj_id);

		return new ResponseEntity<>(dash.getBody(), HttpStatus.CREATED);

	}

	@GetMapping(value = "/dashbuilder/{id}/{type}")
	public ResponseEntity<?> dashboardr(@PathVariable Long id, @PathVariable String type) throws IOException {
		Dashboardaxis get = axisrepo.findById(id).get();

		HashMap<String, String> dash = new HashMap<>();
		List<String> tablename = new ArrayList<>();

		ObjectMapper mapper = new ObjectMapper();
		String str = mapper.writeValueAsString(get);
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(str);
		JsonObject obj = element.getAsJsonObject();
		obj.entrySet().forEach(j -> dash.put(j.getKey().toString().replaceAll("\"", ""), j.getValue().getAsString()));

		tablename.add("test");

		String proj_id = "94";
		String moduleName = "builder_test";
		String name2 = tablename.get(0);
		System.out.println(name2);
		String table_name1 = name2.replaceAll(" ", "_");
		String table_name = table_name1.substring(0, 1).toUpperCase() + table_name1.substring(1);
		String replace_entity = dash.toString().replace("{", "@").replace("}", "$");

		String file_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.APP_BUILD_19001 + "/token/api/"
				+ "Shital_form_REST_API_Builder__1685042333131_1" + "/" + proj_id + "/" + type + "/" + moduleName + "/"
				+ table_name + "?entityname=" + replace_entity;
		ResponseEntity<Object> f = GET(file_url);
		Object body = f.getBody();
		System.out.println(body);

		return new ResponseEntity<>(body, HttpStatus.CREATED);

	}

	@GetMapping(value = "/getdashjson/{id}/{job_type}")
	public ResponseEntity<?> jsonretun(@PathVariable Long id, @PathVariable String job_type) throws IOException {

		HashMap<String, String> jsonbody = new HashMap<>();
		Dashboardaxis get = axisrepo.findById(id).get();

		ObjectMapper mapper = new ObjectMapper();
		String str = mapper.writeValueAsString(get);
		JsonParser parser = new JsonParser();
		JsonElement el = parser.parse(str);
		JsonObject ob = el.getAsJsonObject();
		ob.entrySet()
				.forEach(j -> jsonbody.put(j.getKey().toString().replaceAll("\"", ""), j.getValue().getAsString()));

		JsonElement element = parser.parse(jsonbody.toString());
		JsonObject obj = element.getAsJsonObject();
		Set<Entry<String, JsonElement>> entrySet = obj.entrySet();
		StringBuilder jsonmap = new StringBuilder();

		if (job_type.equalsIgnoreCase("bar_chart")) {
			jsonmap.append("  barChartData = [\n");

		} else if (job_type.equalsIgnoreCase("line_chart")) {
			jsonmap.append(
					"\n  lineCharData = \r\n" + "    {\r\n" + "      \"chartData\": [\r\n" + "        { \"data\": [");
		} else if (job_type.equalsIgnoreCase("doughnut")) {
			jsonmap.append("doughnutData ={\r\n" + "  \"chartData\": [[");
		}
		for (Entry<String, JsonElement> entry : entrySet) {
			String key = entry.getKey().toString().replaceAll("\"", "");
			String value = entry.getValue().getAsString();
			if (job_type.equalsIgnoreCase("bar_chart")) {

				jsonmap.append("{ \"name\": \"" + key + "\", \"progress\":" + value + " },\n");

			} else if (job_type.equalsIgnoreCase("line_chart")) {
				jsonmap.append(value + ",");

			} else if (job_type.equalsIgnoreCase("doughnut")) {
				jsonmap.append(value + ",");

			}

		}
		if (job_type.equalsIgnoreCase("bar_chart")) {
			jsonmap.append("  \n]\n");

		} else if (job_type.equalsIgnoreCase("line_chart")) {

			jsonmap = jsonmap.deleteCharAt(jsonmap.length() - 1);

			jsonmap.append("], \"label\": \"Test Projects\" }\r\n" + "      ],\r\n" + "      \"chartLabels\": [ ");
		} else if (job_type.equalsIgnoreCase("doughnut")) {
			jsonmap.append("]],\r\n" + "  \"chartLabels\": [");

		}

		for (Entry<String, JsonElement> entry : entrySet) {
			String key = entry.getKey().toString().replaceAll("\"", "");
			String value = entry.getValue().getAsString();
			if (job_type.equalsIgnoreCase("line_chart")) {

				jsonmap.append("\"" + key + "\",");

			} else if (job_type.equalsIgnoreCase("doughnut")) {
				jsonmap.append("\"" + key + "\",");

			}

		}

		if (job_type.equalsIgnoreCase("line_chart")) {
			jsonmap = jsonmap.deleteCharAt(jsonmap.length() - 1);
			jsonmap.append("] \n }\n");

		} else if (job_type.equalsIgnoreCase("doughnut")) {
			jsonmap = jsonmap.deleteCharAt(jsonmap.length() - 1);

			jsonmap.append("]\r\n" + "}\r\n");

		}
		return new ResponseEntity<>(jsonmap, HttpStatus.CREATED);
	}

	public ResponseEntity<Object> GET(String get) {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Object> u = restTemplate.getForEntity(get, Object.class);

		return u;

	}
}
