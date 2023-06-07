"package com.realnet.template.controller;" + "\r\n" + 
"" + "\r\n" + 
"import java.io.BufferedReader;" + "\r\n" + 
"import java.io.IOException;" + "\r\n" + 
"import java.io.InputStream;" + "\r\n" + 
"import java.io.InputStreamReader;" + "\r\n" + 
"import java.text.DateFormat;" + "\r\n" + 
"import java.text.SimpleDateFormat;" + "\r\n" + 
"import java.util.ArrayList;" + "\r\n" + 
"import java.util.Calendar;" + "\r\n" + 
"import java.util.HashMap;" + "\r\n" + 
"import java.util.Iterator;" + "\r\n" + 
"import java.util.List;" + "\r\n" + 
"import java.util.Map;" + "\r\n" + 
"import java.util.Set;" + "\r\n" + 
"" + "\r\n" + 
"import org.apache.poi.ss.usermodel.DataFormatter;" + "\r\n" + 
"import org.apache.poi.ss.usermodel.Row;" + "\r\n" + 
"import org.apache.poi.ss.usermodel.Sheet;" + "\r\n" + 
"import org.apache.poi.ss.usermodel.Workbook;" + "\r\n" + 
"import org.apache.poi.ss.usermodel.WorkbookFactory;" + "\r\n" + 
"import org.json.simple.JSONObject;" + "\r\n" + 
"import org.springframework.beans.factory.annotation.Autowired;" + "\r\n" + 
"import org.springframework.http.HttpStatus;" + "\r\n" + 
"import org.springframework.http.ResponseEntity;" + "\r\n" + 
"import org.springframework.web.bind.annotation.DeleteMapping;" + "\r\n" + 
"import org.springframework.web.bind.annotation.GetMapping;" + "\r\n" + 
"import org.springframework.web.bind.annotation.PathVariable;" + "\r\n" + 
"import org.springframework.web.bind.annotation.PostMapping;" + "\r\n" + 
"import org.springframework.web.bind.annotation.PutMapping;" + "\r\n" + 
"import org.springframework.web.bind.annotation.RequestMapping;" + "\r\n" + 
"import org.springframework.web.bind.annotation.RequestParam;" + "\r\n" + 
"import org.springframework.web.bind.annotation.RestController;" + "\r\n" + 
"import org.springframework.web.multipart.MultipartFile;" + "\r\n" + 
"" + "\r\n" + 
"import com.fasterxml.jackson.core.JsonProcessingException;" + "\r\n" + 
"import com.fasterxml.jackson.databind.JsonMappingException;" + "\r\n" + 
"import com.fasterxml.jackson.databind.ObjectMapper;" + "\r\n" + 
"import com.google.gson.JsonArray;" + "\r\n" + 
"import com.google.gson.JsonElement;" + "\r\n" + 
"import com.google.gson.JsonObject;" + "\r\n" + 
"import com.google.gson.JsonParser;" + "\r\n" + 
"import com.realnet.Notification.Notification.Entity.NotEntity;" + "\r\n" + 
"import com.realnet.fileupload.helper.Fileuploadhelper;" + "\r\n" + 
"import com.realnet.incident.entity.IncidentEntity;" + "\r\n" + 
"import com.realnet.template.entity.Dynamic_template;" + "\r\n" + 
"import com.realnet.template.repository.DynamicTempRepo;" + "\r\n" + 
"import com.realnet.users.entity1.AppUser;" + "\r\n" + 
"import com.realnet.users.service1.AppUserServiceImpl;" + "\r\n" + 
"" + "\r\n" + 
"@RestController" + "\r\n" + 
"@RequestMapping(\"/sureserve/template/dynamic\")" + "\r\n" + 
"public class DynamicTemplateController {" + "\r\n" + 
"" + "\r\n" + 
"	@Autowired" + "\r\n" + 
"	private DynamicTempRepo tempRepo;" + "\r\n" + 
"" + "\r\n" + 
"	@Autowired" + "\r\n" + 
"	private AppUserServiceImpl userService;" + "\r\n" + 
"" + "\r\n" + 
"	@Autowired" + "\r\n" + 
"	private Fileuploadhelper fileuploadhelper;" + "\r\n" + 
"" + "\r\n" + 
"	@GetMapping(\"/templatetojson\")" + "\r\n" + 
"	public ResponseEntity<?> saveTemplateInJson(@RequestParam MultipartFile file) throws IOException {" + "\r\n" + 
"" + "\r\n" + 
"		BufferedReader br;" + "\r\n" + 
"		InputStream is = file.getInputStream();" + "\r\n" + 
"		br = new BufferedReader(new InputStreamReader(is));" + "\r\n" + 
"" + "\r\n" + 
"		Workbook workbook = WorkbookFactory.create(is);" + "\r\n" + 
"" + "\r\n" + 
"// 			Create a DataFormatter to format and get each cell's value as String" + "\r\n" + 
"		DataFormatter dataFormatter = new DataFormatter();" + "\r\n" + 
"" + "\r\n" + 
"		Sheet sheet = workbook.getSheetAt(0);" + "\r\n" + 
"" + "\r\n" + 
"		// Getting number of columns in the Sheet" + "\r\n" + 
"		int cols = sheet.getRow(0).getLastCellNum();" + "\r\n" + 
"		int firstRowNum = sheet.getFirstRowNum();" + "\r\n" + 
"" + "\r\n" + 
"		List<String> keys = new ArrayList<String>();" + "\r\n" + 
"		List<JSONObject> jsonList = new ArrayList<JSONObject>();" + "\r\n" + 
"" + "\r\n" + 
"		for (Row row : sheet) {" + "\r\n" + 
"			JSONObject object = new JSONObject();" + "\r\n" + 
"			if (row.getRowNum() == 0) {" + "\r\n" + 
"				for (int i = 0; i < cols; i++) {" + "\r\n" + 
"					String value = dataFormatter.formatCellValue(row.getCell(i));" + "\r\n" + 
"					keys.add(value);" + "\r\n" + 
"				}" + "\r\n" + 
"" + "\r\n" + 
"			}" + "\r\n" + 
"" + "\r\n" + 
"			if (row.getRowNum() != 0) {" + "\r\n" + 
"" + "\r\n" + 
"				for (int i = 0; i < cols; i++) {" + "\r\n" + 
"					String key = keys.get(i);" + "\r\n" + 
"					String value = dataFormatter.formatCellValue(row.getCell(i));" + "\r\n" + 
"" + "\r\n" + 
"					object.put(key, value);" + "\r\n" + 
"" + "\r\n" + 
"					jsonList.add(object);" + "\r\n" + 
"				}" + "\r\n" + 
"" + "\r\n" + 
"			}" + "\r\n" + 
"" + "\r\n" + 
"		}" + "\r\n" + 
"" + "\r\n" + 
"		workbook.close();" + "\r\n" + 
"" + "\r\n" + 
"		return new ResponseEntity<>(jsonList, HttpStatus.BAD_REQUEST);" + "\r\n" + 
"	}" + "\r\n" + 
"" + "\r\n" + 
"	@GetMapping(\"/jsonkeychange\")" + "\r\n" + 
"" + "\r\n" + 
"	public ResponseEntity<?> changeKeysOfJson(@RequestParam String newjson, @RequestParam String body)" + "\r\n" + 
"			throws IOException {" + "\r\n" + 
"" + "\r\n" + 
"		JsonParser parser = new JsonParser();" + "\r\n" + 
"		JsonElement element = parser.parse(newjson);" + "\r\n" + 
"		JsonElement element2 = parser.parse(body);" + "\r\n" + 
"		JsonObject object2 = element.getAsJsonObject();" + "\r\n" + 
"		JsonArray array = element2.getAsJsonArray();" + "\r\n" + 
"		Iterator iterator = array.iterator();" + "\r\n" + 
"" + "\r\n" + 
"		List<Map> jsonList = new ArrayList<Map>();" + "\r\n" + 
"" + "\r\n" + 
"		Set<String> keySet = object2.keySet();" + "\r\n" + 
"		Iterator<String> it = keySet.iterator();" + "\r\n" + 
"" + "\r\n" + 
"//		String key = it.next();" + "\r\n" + 
"		while (iterator.hasNext()) {" + "\r\n" + 
"" + "\r\n" + 
"			Map<String, String> map = new HashMap<String, String>();" + "\r\n" + 
"			Object next = iterator.next();" + "\r\n" + 
"			JsonElement parse = parser.parse(next.toString());" + "\r\n" + 
"			JsonObject jsonObject = parse.getAsJsonObject();" + "\r\n" + 
"			for (String k : keySet) {" + "\r\n" + 
"				String next2 = object2.get(k).getAsString();" + "\r\n" + 
"				String value2 = jsonObject.get(k).getAsString();" + "\r\n" + 
"				map.put(next2, value2);" + "\r\n" + 
"			}" + "\r\n" + 
"" + "\r\n" + 
"			jsonList.add(map);" + "\r\n" + 
"" + "\r\n" + 
"		}" + "\r\n" + 
"" + "\r\n" + 
"		return new ResponseEntity<>(jsonList, HttpStatus.BAD_REQUEST);" + "\r\n" + 
"	}" + "\r\n" + 
"" + "\r\n" + 
"	// create data with file upload" + "\r\n" + 
"	@PostMapping(\"/dynamic_temp\")" + "\r\n" + 
"	public ResponseEntity<?> addIssues(@RequestParam String data, @RequestParam MultipartFile file)" + "\r\n" + 
"			throws JsonMappingException, JsonProcessingException {" + "\r\n" + 
"" + "\r\n" + 
"		AppUser user = userService.getLoggedInUser();" + "\r\n" + 
"		String userId = user.getUsername();" + "\r\n" + 
"		Dynamic_template dynamic_template;" + "\r\n" + 
"" + "\r\n" + 
"		dynamic_template = new ObjectMapper().readValue(data, Dynamic_template.class);" + "\r\n" + 
"" + "\r\n" + 
"		if (!file.isEmpty()) {" + "\r\n" + 
"//			for (MultipartFile e : attachmentFile) {" + "\r\n" + 
"			System.out.println(file.getOriginalFilename());" + "\r\n" + 
"" + "\r\n" + 
"			boolean f = fileuploadhelper.uploadFile(file);" + "\r\n" + 
"" + "\r\n" + 
"			if (f) {" + "\r\n" + 
"				System.out.println(\"file uploaded successfully\");" + "\r\n" + 
"				dynamic_template.setFile_name(file.getOriginalFilename());" + "\r\n" + 
"" + "\r\n" + 
"			}" + "\r\n" + 
"" + "\r\n" + 
"		} else {" + "\r\n" + 
"			dynamic_template.setFile_name(\"No file\");" + "\r\n" + 
"" + "\r\n" + 
"		}" + "\r\n" + 
"" + "\r\n" + 
"		dynamic_template.setUser_id(userId);" + "\r\n" + 
"" + "\r\n" + 
"		Dynamic_template save = tempRepo.save(dynamic_template);" + "\r\n" + 
"" + "\r\n" + 
"		if (save.getId() == 0) {" + "\r\n" + 
"			return new ResponseEntity<String>(\"template Not Added\", HttpStatus.BAD_REQUEST);" + "\r\n" + 
"		} else {" + "\r\n" + 
"" + "\r\n" + 
"			return new ResponseEntity<>(save, HttpStatus.ACCEPTED);" + "\r\n" + 
"		}" + "\r\n" + 
"	}" + "\r\n" + 
"" + "\r\n" + 
"	// get all" + "\r\n" + 
"	@GetMapping(\"/dynamic_temp\")" + "\r\n" + 
"	public ResponseEntity<?> getAll() {" + "\r\n" + 
"" + "\r\n" + 
"		return new ResponseEntity<>(tempRepo.findAll(), HttpStatus.ACCEPTED);" + "\r\n" + 
"" + "\r\n" + 
"	}" + "\r\n" + 
"" + "\r\n" + 
"	// get by id" + "\r\n" + 
"	@GetMapping(\"/dynamic_temp/{id}\")" + "\r\n" + 
"	public ResponseEntity<?> getIssueById(@PathVariable Long id) {" + "\r\n" + 
"" + "\r\n" + 
"		return new ResponseEntity<>(tempRepo.findById(id).get(), HttpStatus.ACCEPTED);" + "\r\n" + 
"" + "\r\n" + 
"	}" + "\r\n" + 
"" + "\r\n" + 
"	// delete by id" + "\r\n" + 
"	@DeleteMapping(\"/dynamic_temp/{id}\")" + "\r\n" + 
"	public ResponseEntity<?> deleteIssue(@PathVariable Long id) {" + "\r\n" + 
"		tempRepo.deleteById(id);" + "\r\n" + 
"		return new ResponseEntity<String>(\"Success\", HttpStatus.ACCEPTED);" + "\r\n" + 
"" + "\r\n" + 
"	}" + "\r\n" + 
"" + "\r\n" + 
"	// update by id" + "\r\n" + 
"	@PutMapping(\"/dynamic_temp/{id}\")" + "\r\n" + 
"	public ResponseEntity<?> updateIssue(@RequestParam String data, @PathVariable Long id," + "\r\n" + 
"			@RequestParam MultipartFile file) throws Exception {" + "\r\n" + 
"" + "\r\n" + 
"		Dynamic_template dynamic_template;" + "\r\n" + 
"		dynamic_template = new ObjectMapper().readValue(data, Dynamic_template.class);" + "\r\n" + 
"" + "\r\n" + 
"		Dynamic_template template = tempRepo.findById(id).get();" + "\r\n" + 
"" + "\r\n" + 
"		if (!file.isEmpty()) {" + "\r\n" + 
"			System.out.println(file.getOriginalFilename());" + "\r\n" + 
"" + "\r\n" + 
"			boolean f = fileuploadhelper.uploadFile(file);" + "\r\n" + 
"" + "\r\n" + 
"			if (f) {" + "\r\n" + 
"				System.out.println(\"file uploaded successfully\");" + "\r\n" + 
"				template.setFile_name(file.getOriginalFilename());" + "\r\n" + 
"" + "\r\n" + 
"			}" + "\r\n" + 
"" + "\r\n" + 
"		} else {" + "\r\n" + 
"			template.setFile_name(\"No file\");" + "\r\n" + 
"" + "\r\n" + 
"		}" + "\r\n" + 
"" + "\r\n" + 
"		template.setEntity_name(dynamic_template.getEntity_name());" + "\r\n" + 
"		template.setUser_id(dynamic_template.getUser_id());" + "\r\n" + 
"" + "\r\n" + 
"		Dynamic_template save = tempRepo.save(template);" + "\r\n" + 
"		return new ResponseEntity<>(save, HttpStatus.ACCEPTED);" + "\r\n" + 
"" + "\r\n" + 
"	}" + "\r\n" + 
"" + "\r\n" + 
"}" 