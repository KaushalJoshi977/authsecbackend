package com.realnet.entitybuilder.Services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.realnet.entitybuilder.entity.frontendtable;
import com.realnet.entitybuilder.repo.FrontendRepo;
import com.realnet.utils.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Index_Service_be {

	@Value("${projectPath}")
	private String projectPath;

	public boolean build_index(Integer proj_id, String prj_name, int j, String copy_from_path, String copy_to_path)
			throws JsonProcessingException {

//		/data/Builder/3210/index

//		String name2 = tablename.get(0);
//		System.out.println(name2);
//		String table_name1 = name2.replaceAll(" ", "_");
//		String table_name= table_name1.substring(0, 1).toUpperCase() + table_name1.substring(1);

//		System.out.println(table_name);

		StringBuilder index_builder = new StringBuilder();
		StringBuilder index_builder1 = new StringBuilder();
		String Path1 = projectPath + File.separator + "cns-portal/code-extractor/builders";


		index_builder.append("#!/bin/bash\n"
				+ "path=\""+Path1+File.separator+proj_id+"/index/"+prj_name+"/frontend/angular-clarity-master/src/app/modules/main/BuilderComponents/Builder_test/T1\"\n"
				+ "if [ -d \"$path\" ]; then\n"
				+ "  echo \"Path $path already exists.\"\n"
				+ "else\n"
				+ "  mkdir -p \"$path\"\n"
				+ "  echo \"Path $path created.\"\n"
				+ "fi\n"
				+ "\n"
				+ "path=\""+Path1+File.separator+proj_id+"/index/"+prj_name+"/backend/src/main/java/com/realnet/Builder_test\"\n"
				+ "if [ -d \"$path\" ]; then\n"
				+ "  echo \"Path $path already exists.\"\n"
				+ "else\n"
				+ "  mkdir -p \"$path\"\n"
				+ "  echo \"Path $path created.\"\n"
				+ "fi\n"
				+ "#next script" 

				
				);


		File builderMainDir1 = new File(Path1);
		if (!builderMainDir1.exists()) {
			boolean mkdir = builderMainDir1.mkdir();
			System.out.println("builder folder " + mkdir);
		}

		Path1 = Path1 + File.separator + proj_id;
		File staticMainDir1 = new File(Path1);
		if (!staticMainDir1.exists()) {
			boolean mkdir = staticMainDir1.mkdir();
			System.out.println(mkdir);
		}

		Path1 = Path1 + File.separator + "index";

		File staticMainDir = new File(Path1);
		if (!staticMainDir.exists()) {
			boolean mkdir = staticMainDir.mkdir();
			System.out.println("index directory " + mkdir);
		}

		// CREATING SHELL SCRIPT
		String fileName = "index" + ".sh";
		String Path = Path1 + File.separator + fileName;

		System.out.println("transferfile Path= " + Path);

		try {
//		creating files
			File file0 = new File(Path);

			if (!file0.exists()) {
				file0.createNewFile();
				Files.setPosixFilePermissions(file0.toPath(), PosixFilePermissions.fromString("rwxrwxrwx"));

				file0.setExecutable(true);
				file0.setReadable(true);
				file0.setWritable(true);
			}

//			Writing files

			if (j == 1) {
				FileWriter fw = new FileWriter(file0.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(index_builder.toString());

				bw.close();

//				INSERT IN JOB PRO
//				insertin_jobpro(proj_id, fileName, Path1);
			}

			if (j != 1) {
				index_builder1.append("\n" + "copyfrom=null\n" + "copyto=null\n" + "copyfrom=" + copy_from_path + "\n"
						+ "copyto=" + projectPath + File.separator + "cns-portal/code-extractor/builders" + copy_to_path
						+ "\n" + "cp -r $copyfrom/ $copyto");
				enterscript(index_builder1, Path, fileName);

			}

		} catch (FileNotFoundException e) {
			log.error("File not found exception Handled", e.getMessage());
		} catch (IOException e) {
			log.error("IO Exception exception Handled", e.getMessage());
		}

		return true;
	}

	public void enterscript(StringBuilder index, String pathwith_file, String file_name) throws IOException {

		StringBuilder frontend = new StringBuilder();

		// CEATE PACKAGE
//		String filePath = projectPath + File.separator + file_name;

		String str = index.toString();

		int length = str.length();

		// CEATE PACKAGE

		File file = null;

		file = new File(pathwith_file);

		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line;

		while ((line = br.readLine()) != null) {
			frontend.append(line + "\n");
//			System.out.println(line);

		}
		fr.close();
		br.close();
//		System.out.println(frontend.length());

		String back = "#next script";
		int back_l = back.length();

		int ord = StringUtils.ordinalIndexOf(frontend.toString(), back, 1);

		String front = frontend.substring(0, ord + back_l) + "\n" + str + "\n"
				+ frontend.substring(ord + back_l, frontend.length());

		String codee = front.substring(0, front.lastIndexOf("\n")); // remove last line break

//		creating files
		File file1 = new File(pathwith_file);

//		Writing files

		FileWriter fr1 = new FileWriter(file1.getAbsoluteFile());
		BufferedWriter br1 = new BufferedWriter(fr1);
		PrintWriter out = new PrintWriter(br1);

		br1.write(codee.toString());
		br1.close();

	}

	public void insertin_jobpro(Integer prj_id, String filename, String filepath) throws JsonProcessingException {

		Map<String, String> jobdata = new HashMap<String, String>();
//		jobdata.put("parameters", builder.toString());
		jobdata.put("url", "http://" + Constant.LOCAL_HOST + ":" + Constant.SCRIPT_RUNNER_8901
				+ "/api/runScript?filepath=" + filepath + "&filename=" + filename);
		jobdata.put("method", "GET");
		jobdata.put("connection_name", "jobprtal");
		jobdata.put("createdBy", "2022");
		jobdata.put("updatedBy", "2022");
		jobdata.put("job_type", "CreatesureprjPrj");
		jobdata.put("ref", prj_id.toString());
		System.out.println(jobdata);

		RestTemplate restTemplate = new RestTemplate();
		String jobprourl = "http://" + Constant.LOCAL_HOST + ":" + Constant.JOBPRO_PORT_8087 + "/jobpro/pipiline";
		HttpHeaders headers = getHeaders();
		HttpEntity<Object> request = new HttpEntity<Object>(jobdata, headers);
		ResponseEntity<Object> res1 = restTemplate.postForEntity(jobprourl, request, Object.class);
		if (res1.getStatusCodeValue() == 200) {
			System.out.println("script runner data inserted in job pro");
		}
//		System.out.println(res1.getBody());
	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return headers;
	}

}
