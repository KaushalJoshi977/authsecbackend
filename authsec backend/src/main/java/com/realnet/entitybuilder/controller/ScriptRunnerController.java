package com.realnet.entitybuilder.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4123")
@RequestMapping("/entityBuilder")
public class ScriptRunnerController {


	@Value("${projectPath}")
	private String projectpath;



	/**
	 * runScript method by reading file
	 * 
	 * @throws IOException
	 *
	 */
	
	//RUN SCRIPT
	@GetMapping(value = "/runScript")
	public ResponseEntity<?> runScript(@RequestParam String filepath,
			                           @RequestParam String filename) throws IOException {

		System.out.println("runScript method called in ScriptRunnerController");

		String str = null;

		String path = filepath+"/"+filename;

		ProcessBuilder pb = new

		ProcessBuilder(path);

		System.out.println("file taken ="+new File(path).getAbsoluteFile());

		pb.directory(new File(System.getProperty("user.home")));

		Process process = pb.start();
		if (process.isAlive()) {


			BufferedReader br2 = new BufferedReader(new InputStreamReader(process.getInputStream()));

			System.out.println("file is running");

			while ((str = br2.readLine()) != null) {
				System.out.println(str);
			}
			br2.close();
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>("bad request", HttpStatus.BAD_REQUEST);
		}

	}
	
	
	
	
	//NOT IN USE
	@GetMapping(value = "/runScript1")
	public ResponseEntity<?> runScript1(@RequestParam String s1, @RequestParam String s2) throws IOException {



		System.out.println("runScript method called in ScriptRunnerController");

		String str = null;

		String path = projectpath + "/ScriptFiles/copy.sh";
		System.out.println(path);
		File pathfile = new File(path);
		String filename = pathfile.getName();



		String line = "";
		StringBuilder intialize = new StringBuilder();
		StringBuilder class_name = new StringBuilder();
		StringBuilder middle = new StringBuilder();
		StringBuilder end = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(path));
		intialize.append("\"");
		intialize.append("*****************************************\n" + "Below is the script to copy reporsitry\n"
				+ "*****************************************\n" + "#!/bin/bash\n");

		while ((line = br.readLine()) != null) {
			String[] data = line.split(",");
			for (String d : data) {
				if (d.contains("PRJ_NAME=")) {
					intialize.append("PRJ_NAME=gitclone");
					intialize.append("\n");
				} else if (d.contains("GIT_USER=")) {
					intialize.append("GIT_USER=admin123");
					intialize.append("\n");
				} else if (d.contains("GIT_PASS=")) {
					intialize.append("GIT_PASS=admin123");
					intialize.append("\n");
				} else if (d.contains("GIT_URL_FROM=")) {
					intialize.append("GIT_URL_FROM=http://13.126.217.36:31633/admin123/" + s1 + ".git");
					intialize.append("\n");
				} else if (d.contains("GIT_URL_TO=")) {
					intialize.append("GIT_URL_TO=http://13.126.217.36:31633/admin123/" + s2 + ".git");
					intialize.append("\n");
				}
//				
			}
		}
		intialize.append("docker build .\n" + "echo IMAGE_NAME=$GIT_URL_TO");

		System.out.println(intialize);

		String path1 = projectpath + "/testingfor script/" + filename;
		System.out.println(path1);

		FileWriter fw = null;
		BufferedWriter bw = null;

		// FILE NAME SHOULD CHANGE DEPENDS ON TECH_STACK/OBJECT_tYPE/SUB_OBJECT_TYPE
		File masterBuilderFile = new File(path1);
		if (!masterBuilderFile.exists()) {
			masterBuilderFile.createNewFile();
		}
		fw = new FileWriter(masterBuilderFile.getAbsoluteFile());
		bw = new BufferedWriter(fw);
		bw.write(intialize.toString());
		bw.close();

		ProcessBuilder pb = new

		ProcessBuilder(path1);

		System.out.println("path taken ="+path1);
		System.out.println(new File(path1).getAbsoluteFile());


		pb.directory(new File(System.getProperty("user.home")));

		Process process = pb.start();
		if (process.isAlive()) {

//			Process process = Runtime.getRuntime().exec("where java");

			BufferedReader br2 = new BufferedReader(new InputStreamReader(process.getInputStream()));

			System.out.println("file is running");

			while ((str = br2.readLine()) != null) {
				System.out.println(str);
			}
			br2.close();
			return new ResponseEntity<>("file is running", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("bad request", HttpStatus.BAD_REQUEST);
		}

	}
}
