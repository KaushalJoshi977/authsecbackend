"package com.realnet.bugTracker.Controller;" + "\r\n" + 
"" + "\r\n" + 
"import java.util.List;" + "\r\n" + 
"" + "\r\n" + 
"import org.springframework.beans.factory.annotation.Autowired;" + "\r\n" + 
"import org.springframework.http.HttpStatus;" + "\r\n" + 
"import org.springframework.http.ResponseEntity;" + "\r\n" + 
"import org.springframework.web.bind.annotation.DeleteMapping;" + "\r\n" + 
"import org.springframework.web.bind.annotation.GetMapping;" + "\r\n" + 
"import org.springframework.web.bind.annotation.PathVariable;" + "\r\n" + 
"import org.springframework.web.bind.annotation.PostMapping;" + "\r\n" + 
"import org.springframework.web.bind.annotation.PutMapping;" + "\r\n" + 
"import org.springframework.web.bind.annotation.RequestBody;" + "\r\n" + 
"import org.springframework.web.bind.annotation.RequestMapping;" + "\r\n" + 
"import org.springframework.web.bind.annotation.RequestParam;" + "\r\n" + 
"import org.springframework.web.bind.annotation.RestController;" + "\r\n" + 
"import org.springframework.web.multipart.MultipartFile;" + "\r\n" + 
"" + "\r\n" + 
"import com.fasterxml.jackson.core.JsonProcessingException;" + "\r\n" + 
"import com.fasterxml.jackson.databind.JsonMappingException;" + "\r\n" + 
"import com.fasterxml.jackson.databind.ObjectMapper;" + "\r\n" + 
"import com.realnet.activity.service.ActivityService;" + "\r\n" + 
"import com.realnet.bugTracker.Entity.BugTracker;" + "\r\n" + 
"import com.realnet.bugTracker.Service.BugTrackerService;" + "\r\n" + 
"import com.realnet.fileupload.helper.Fileuploadhelper;" + "\r\n" + 
"import com.realnet.incident.entity.IncidentEntity;" + "\r\n" + 
"" + "\r\n" + 
"@RestController" + "\r\n" + 
"@RequestMapping(\"/sureserve/bugtracker\")" + "\r\n" + 
"public class BugTrackerController {" + "\r\n" + 
"" + "\r\n" + 
"	@Autowired" + "\r\n" + 
"	private BugTrackerService bugTrackerService;" + "\r\n" + 
"" + "\r\n" + 
"	@Autowired" + "\r\n" + 
"	private Fileuploadhelper fileuploadhelper;" + "\r\n" + 
"" + "\r\n" + 
"	@PostMapping(\"/bugtracker\")" + "\r\n" + 
"	public ResponseEntity<String> createIssue(@RequestParam String data, @RequestParam MultipartFile file)" + "\r\n" + 
"			throws JsonMappingException, JsonProcessingException {" + "\r\n" + 
"" + "\r\n" + 
"		// Deserialize the \"data\" parameter to a BugTracker object using Jackson's" + "\r\n" + 
"		// ObjectMapper" + "\r\n" + 
"		BugTracker bugTracker = new ObjectMapper().readValue(data, BugTracker.class);" + "\r\n" + 
"" + "\r\n" + 
"		// Check if a file was uploaded" + "\r\n" + 
"		if (!file.isEmpty()) {" + "\r\n" + 
"			System.out.println(file.getOriginalFilename());" + "\r\n" + 
"" + "\r\n" + 
"			// Use a file upload helper method to upload the file and return a boolean" + "\r\n" + 
"			// indicating success or failure" + "\r\n" + 
"			boolean isUploaded = fileuploadhelper.uploadFile(file);" + "\r\n" + 
"" + "\r\n" + 
"			if (isUploaded) {" + "\r\n" + 
"				System.out.println(\"File uploaded successfully\");" + "\r\n" + 
"				bugTracker.setFileName(file.getOriginalFilename());" + "\r\n" + 
"			}" + "\r\n" + 
"		} else {" + "\r\n" + 
"			bugTracker.setFileName(\"No file\");" + "\r\n" + 
"		}" + "\r\n" + 
"" + "\r\n" + 
"		// Pass the updated BugTracker object to the bugTrackerService's createIssue" + "\r\n" + 
"		// method to create a new BugTracker object" + "\r\n" + 
"		BugTracker createdTracker = bugTrackerService.createIssue(bugTracker);" + "\r\n" + 
"" + "\r\n" + 
"		return ResponseEntity.ok(\"addded successfully\");" + "\r\n" + 
"	}" + "\r\n" + 
"" + "\r\n" + 
"	@GetMapping(\"/bugtracker/{id}\")" + "\r\n" + 
"	public ResponseEntity<BugTracker> getIssueById(@PathVariable Long id) {" + "\r\n" + 
"		BugTracker tracker = bugTrackerService.getIssueById(id);" + "\r\n" + 
"		return ResponseEntity.ok(tracker);" + "\r\n" + 
"	}" + "\r\n" + 
"" + "\r\n" + 
"	@GetMapping(\"/bugtracker\")" + "\r\n" + 
"	public ResponseEntity<List<BugTracker>> getAllIssues() {" + "\r\n" + 
"		List<BugTracker> allIssues = bugTrackerService.getAllIssues();" + "\r\n" + 
"		return ResponseEntity.ok(allIssues);" + "\r\n" + 
"	}" + "\r\n" + 
"" + "\r\n" + 
"	@PutMapping(\"/bugtracker/{id}\")" + "\r\n" + 
"	public ResponseEntity<String> updateIssue(@RequestParam String data, @PathVariable Long id," + "\r\n" + 
"			@RequestParam MultipartFile file) throws Exception {" + "\r\n" + 
"" + "\r\n" + 
"		BugTracker bugTracker = new ObjectMapper().readValue(data, BugTracker.class);" + "\r\n" + 
"" + "\r\n" + 
"		if (!file.isEmpty()) {" + "\r\n" + 
"			System.out.println(file.getOriginalFilename());" + "\r\n" + 
"			boolean f = fileuploadhelper.uploadFile(file);" + "\r\n" + 
"" + "\r\n" + 
"			if (f) {" + "\r\n" + 
"				System.out.println(\"file uploaded successfully\");" + "\r\n" + 
"				bugTracker.setFileName(file.getOriginalFilename());" + "\r\n" + 
"			}" + "\r\n" + 
"		} else {" + "\r\n" + 
"			bugTracker.setFileName(\"No file\");" + "\r\n" + 
"		}" + "\r\n" + 
"" + "\r\n" + 
"		// Update the bug tracker with the new incident entity" + "\r\n" + 
"		bugTrackerService.updateIssue(id, bugTracker);" + "\r\n" + 
"" + "\r\n" + 
"		return ResponseEntity.ok(\"Update successfully\");" + "\r\n" + 
"	}" + "\r\n" + 
"" + "\r\n" + 
"	@DeleteMapping(\"/bugtracker/{id}\")" + "\r\n" + 
"	public ResponseEntity<String> deleteIssue(@PathVariable Long id) {" + "\r\n" + 
"		bugTrackerService.deleteIssue(id);" + "\r\n" + 
"		String message = \"Issue with ID \" + id + \" has been deleted\";" + "\r\n" + 
"		return ResponseEntity.ok(message);" + "\r\n" + 
"	}" + "\r\n" + 
"}" 