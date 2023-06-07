package com.realnet.template.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.realnet.Notification.Notification.Entity.NotEntity;
import com.realnet.fileupload.helper.Fileuploadhelper;
import com.realnet.incident.entity.IncidentEntity;
import com.realnet.template.entity.Dynamic_template;
import com.realnet.template.repository.DynamicTempRepo;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserServiceImpl;

@RestController
@RequestMapping("/sureserve/template/dynamic")
public class DynamicTemplateController {

	@Autowired
	private DynamicTempRepo tempRepo;

	@Autowired
	private AppUserServiceImpl userService;

	@Autowired
	private Fileuploadhelper fileuploadhelper;

	@GetMapping("/templatetojson")
	public ResponseEntity<?> saveTemplateInJson(@RequestParam MultipartFile file) throws IOException {

		BufferedReader br;
		InputStream is = file.getInputStream();
		br = new BufferedReader(new InputStreamReader(is));

		Workbook workbook = WorkbookFactory.create(is);

// 			Create a DataFormatter to format and get each cell's value as String
		DataFormatter dataFormatter = new DataFormatter();

		Sheet sheet = workbook.getSheetAt(0);

		// Getting number of columns in the Sheet
		int cols = sheet.getRow(0).getLastCellNum();
		int firstRowNum = sheet.getFirstRowNum();

		List<String> keys = new ArrayList<String>();
		List<JSONObject> jsonList = new ArrayList<JSONObject>();

		for (Row row : sheet) {
			JSONObject object = new JSONObject();
			if (row.getRowNum() == 0) {
				for (int i = 0; i < cols; i++) {
					String value = dataFormatter.formatCellValue(row.getCell(i));
					keys.add(value);
				}

			}

			if (row.getRowNum() != 0) {

				for (int i = 0; i < cols; i++) {
					String key = keys.get(i);
					String value = dataFormatter.formatCellValue(row.getCell(i));

					object.put(key, value);

					jsonList.add(object);
				}

			}

		}

		workbook.close();

		return new ResponseEntity<>(jsonList, HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/jsonkeychange")

	public ResponseEntity<?> changeKeysOfJson(@RequestParam String newjson, @RequestParam String body)
			throws IOException {

		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(newjson);
		JsonElement element2 = parser.parse(body);
		JsonObject object2 = element.getAsJsonObject();
		JsonArray array = element2.getAsJsonArray();
		Iterator iterator = array.iterator();

		List<Map> jsonList = new ArrayList<Map>();

		Set<String> keySet = object2.keySet();
		Iterator<String> it = keySet.iterator();

//		String key = it.next();
		while (iterator.hasNext()) {

			Map<String, String> map = new HashMap<String, String>();
			Object next = iterator.next();
			JsonElement parse = parser.parse(next.toString());
			JsonObject jsonObject = parse.getAsJsonObject();
			for (String k : keySet) {
				String next2 = object2.get(k).getAsString();
				String value2 = jsonObject.get(k).getAsString();
				map.put(next2, value2);
			}

			jsonList.add(map);

		}

		return new ResponseEntity<>(jsonList, HttpStatus.BAD_REQUEST);
	}

	// create data with file upload
	@PostMapping("/dynamic_temp")
	public ResponseEntity<?> addIssues(@RequestParam String data, @RequestParam MultipartFile file)
			throws JsonMappingException, JsonProcessingException {

		AppUser user = userService.getLoggedInUser();
		String userId = user.getUsername();
		Dynamic_template dynamic_template;

		dynamic_template = new ObjectMapper().readValue(data, Dynamic_template.class);

		if (!file.isEmpty()) {
//			for (MultipartFile e : attachmentFile) {
			System.out.println(file.getOriginalFilename());

			boolean f = fileuploadhelper.uploadFile(file);

			if (f) {
				System.out.println("file uploaded successfully");
				dynamic_template.setFile_name(file.getOriginalFilename());

			}

		} else {
			dynamic_template.setFile_name("No file");

		}

		dynamic_template.setUser_id(userId);

		Dynamic_template save = tempRepo.save(dynamic_template);

		if (save.getId() == 0) {
			return new ResponseEntity<String>("template Not Added", HttpStatus.BAD_REQUEST);
		} else {

			return new ResponseEntity<>(save, HttpStatus.ACCEPTED);
		}
	}

	// get all
	@GetMapping("/dynamic_temp")
	public ResponseEntity<?> getAll() {

		return new ResponseEntity<>(tempRepo.findAll(), HttpStatus.ACCEPTED);

	}

	// get by id
	@GetMapping("/dynamic_temp/{id}")
	public ResponseEntity<?> getIssueById(@PathVariable Long id) {

		return new ResponseEntity<>(tempRepo.findById(id).get(), HttpStatus.ACCEPTED);

	}

	// delete by id
	@DeleteMapping("/dynamic_temp/{id}")
	public ResponseEntity<?> deleteIssue(@PathVariable Long id) {
		tempRepo.deleteById(id);
		return new ResponseEntity<String>("Success", HttpStatus.ACCEPTED);

	}

	// update by id
	@PutMapping("/dynamic_temp/{id}")
	public ResponseEntity<?> updateIssue(@RequestParam String data, @PathVariable Long id,
			@RequestParam MultipartFile file) throws Exception {

		Dynamic_template dynamic_template;
		dynamic_template = new ObjectMapper().readValue(data, Dynamic_template.class);

		Dynamic_template template = tempRepo.findById(id).get();

		if (!file.isEmpty()) {
			System.out.println(file.getOriginalFilename());

			boolean f = fileuploadhelper.uploadFile(file);

			if (f) {
				System.out.println("file uploaded successfully");
				template.setFile_name(file.getOriginalFilename());

			}

		} else {
			template.setFile_name("No file");

		}

		template.setEntity_name(dynamic_template.getEntity_name());
		template.setUser_id(dynamic_template.getUser_id());

		Dynamic_template save = tempRepo.save(template);
		return new ResponseEntity<>(save, HttpStatus.ACCEPTED);

	}

}
