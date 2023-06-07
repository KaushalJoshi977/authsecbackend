package com.realnet.Connector.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.realnet.Connector.Entity.Connector;
import com.realnet.Connector.Entity.Mapping;
import com.realnet.Connector.Repository.Connectorrepository;

@RestController
@RequestMapping("/token/connector/mapping")
public class MappingController {
	
	@Autowired
	private Connectorrepository connectorrepository;
	
	@PostMapping("/mapping")
	public Object connector(@RequestBody Mapping mapping) {
		List<Object> list = new ArrayList<>();

//		Map<String> param = new HashMap<String>();
		String str =mapping.getMappingString();
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(str);
		JsonObject obj = element.getAsJsonObject();
		
		//get key from get api
		Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();
		for (Map.Entry<String, JsonElement> entry : entries) {
	
			String key = entry.getKey();
			String string = key.toString();
			list.add(string);
	}
		return list;

}
		@PostMapping("/mapping1")
	public Object connector1(@RequestBody Mapping mapping) {
		List<Object> list = new ArrayList<>();

//		Map<String> param = new HashMap<String>();
		String str =mapping.getMappingString();
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(str);
		JsonObject obj = element.getAsJsonObject();
		
		//get values from get api
		Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();
		for (Map.Entry<String, JsonElement> entry : entries) {
	
			String string2 = entry.getValue().getAsString();
			list.add(string2);
	}
		return list;

}
		//get api for call connector by name for job(by gk)
		@GetMapping("/connectorname/{name}")
		public ResponseEntity<?> getconnector(@PathVariable String name) {
			Connector save = connectorrepository.findconnectorbyname(name);
			return new ResponseEntity<>(save, HttpStatus.OK);

		}

}
