package com.realnet.users.controller1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.realnet.users.entity1.AppUser;
import com.realnet.users.entity1.AppUserSessions;
import com.realnet.users.repository1.AppUserSessionsRepository;
import com.realnet.utils.Constant;

@RestController
@RequestMapping(value = "/user1/session") // , produces = MediaType.APPLICATION_JSON_VALUE)
public class AppuserSessionController {
	
	@Value("${projectPath}")
	private String projectPath;

	@Autowired
	private AppUserSessionsRepository sessionsRepository;
	

	
	//get all app user session
	@GetMapping("/getAll")
	public ResponseEntity<?> getAllLog() {
		List<AppUserSessions> l = sessionsRepository.findAll();
		return new ResponseEntity<>(l, HttpStatus.OK);
	}

	//delete session by userid
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<?> getOne(@PathVariable("userId") AppUser userId) {
		List<AppUserSessions> a = sessionsRepository.findByUserId(userId);

		for (AppUserSessions appUserSessions : a) {
			sessionsRepository.delete(appUserSessions);
		}

		if (a != null) {
			return new ResponseEntity<>("deleted", HttpStatus.OK);
		}
		return new ResponseEntity<>("Not Found", HttpStatus.BAD_REQUEST);
	}
	
	
}
