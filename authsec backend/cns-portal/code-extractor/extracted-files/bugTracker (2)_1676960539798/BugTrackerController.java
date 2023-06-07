package com.realnet.bugTracker.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realnet.activity.service.ActivityService;
import com.realnet.bugTracker.Entity.BugTracker;
import com.realnet.bugTracker.Service.BugTrackerService;
import com.realnet.fileupload.helper.Fileuploadhelper;
import com.realnet.incident.entity.IncidentEntity;

@RestController
@RequestMapping("/sureserve/bugtracker")
public class BugTrackerController {

	@Autowired
	private BugTrackerService bugTrackerService;

	@Autowired
	private Fileuploadhelper fileuploadhelper;

	@PostMapping("/bugtracker")
	public ResponseEntity<String> createIssue(@RequestParam String data, @RequestParam MultipartFile file)
			throws JsonMappingException, JsonProcessingException {

		// Deserialize the "data" parameter to a BugTracker object using Jackson's
		// ObjectMapper
		BugTracker bugTracker = new ObjectMapper().readValue(data, BugTracker.class);

		// Check if a file was uploaded
		if (!file.isEmpty()) {
			System.out.println(file.getOriginalFilename());

			// Use a file upload helper method to upload the file and return a boolean
			// indicating success or failure
			boolean isUploaded = fileuploadhelper.uploadFile(file);

			if (isUploaded) {
				System.out.println("File uploaded successfully");
				bugTracker.setFileName(file.getOriginalFilename());
			}
		} else {
			bugTracker.setFileName("No file");
		}

		// Pass the updated BugTracker object to the bugTrackerService's createIssue
		// method to create a new BugTracker object
		BugTracker createdTracker = bugTrackerService.createIssue(bugTracker);

		return ResponseEntity.ok("addded successfully");
	}

	@GetMapping("/bugtracker/{id}")
	public ResponseEntity<BugTracker> getIssueById(@PathVariable Long id) {
		BugTracker tracker = bugTrackerService.getIssueById(id);
		return ResponseEntity.ok(tracker);
	}

	@GetMapping("/bugtracker")
	public ResponseEntity<List<BugTracker>> getAllIssues() {
		List<BugTracker> allIssues = bugTrackerService.getAllIssues();
		return ResponseEntity.ok(allIssues);
	}

	@PutMapping("/bugtracker/{id}")
	public ResponseEntity<String> updateIssue(@RequestParam String data, @PathVariable Long id,
			@RequestParam MultipartFile file) throws Exception {

		BugTracker bugTracker = new ObjectMapper().readValue(data, BugTracker.class);

		if (!file.isEmpty()) {
			System.out.println(file.getOriginalFilename());
			boolean f = fileuploadhelper.uploadFile(file);

			if (f) {
				System.out.println("file uploaded successfully");
				bugTracker.setFileName(file.getOriginalFilename());
			}
		} else {
			bugTracker.setFileName("No file");
		}

		// Update the bug tracker with the new incident entity
		bugTrackerService.updateIssue(id, bugTracker);

		return ResponseEntity.ok("Update successfully");
	}

	@DeleteMapping("/bugtracker/{id}")
	public ResponseEntity<String> deleteIssue(@PathVariable Long id) {
		bugTrackerService.deleteIssue(id);
		String message = "Issue with ID " + id + " has been deleted";
		return ResponseEntity.ok(message);
	}
}
