package com.realnet.entitybuilder.controller;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/entityBuilder/file_delete")
public class File_delete {

	@GetMapping("/delete_file")
	public ResponseEntity<?> filedelete(@RequestParam String filepath, @RequestParam String filename) {

		String path = filepath + File.separator + filename;
		File index = new File(path);

		boolean delete = index.delete();
		if (delete) {
			return new ResponseEntity<>(HttpStatus.OK);

		}else {
			return new ResponseEntity<>("file not deleted", HttpStatus.BAD_REQUEST);

		}


	}

	@GetMapping("/delete_folder")
	public ResponseEntity<?> filedelete1(@RequestParam String filepath	) throws IOException {

		File index = new File(filepath);

		FileUtils.deleteDirectory(index);

		if (index.exists()) {
			return new ResponseEntity<>("folder not deleted ! please give right path", HttpStatus.BAD_REQUEST);

		}
		
		return new ResponseEntity<>(HttpStatus.OK);

	}

}
