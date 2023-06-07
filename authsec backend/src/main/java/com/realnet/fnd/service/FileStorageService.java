package com.realnet.fnd.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//import com.realnet.codeextractor.service.Rn_Bcf_Extractor_Service;
import com.realnet.exceptions.StorageException;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileStorageService {

	//private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

	@Autowired
	private AppUserServiceImpl userService;

	@Autowired
//	private Rn_Bcf_Extractor_Service extractorService;

	@Value("${projectPath}")
	private String projectPath;

	public void uploadProfilePicture(MultipartFile file, String location) {
		if (file.isEmpty()) {
			throw new StorageException("Failed to store empty file");
		}
		// create path for every new File
		// File fileLoc = new File(location);
		try {
//			if(!fileLoc.exists()) {
//				fileLoc.mkdir();
//				//System.out.println("Path Created...");
//			}
			AppUser user = userService.getLoggedInUser();
			String UserId = String.valueOf(user.getUserId());
			String fileName = file.getOriginalFilename();

			String ext = fileName.substring(fileName.lastIndexOf("."));
			String extension = FilenameUtils.getExtension(fileName); // lol
			String newName = "profile-pic-" + UserId + ext;
			
			System.out.println(System.getProperty("java.io.tmpdir"));
		    File convFile = new File(System.getProperty("java.io.tmpdir")+System.getProperty("file.separator")+newName);
		    file.transferTo(convFile);
		} catch (IOException e) {
			String msg = String.format("Failed to store file %s", file.getName());
			log.info(msg);
			throw new StorageException("Failed to store file ", e);
		}

	}

	// ========================== BCF EXTRACTOR =============================
	// max size 100 mb
	public void uploadFile(MultipartFile file, String location) {
		if (file.isEmpty()) {
			throw new StorageException("Failed to store empty file");
		}
		try {
			String fileName = file.getOriginalFilename();
			InputStream is = file.getInputStream();
			Files.copy(is, Paths.get(location + fileName), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			String msg = String.format("Failed to store file : %s", file.getName());
			log.debug(msg);
			throw new StorageException("Failed to store file", e);
		}

	}

//	private static ArrayList<String> fileList = new ArrayList<String>();
//
//	public ArrayList<String> listOFiles(String directory) throws IOException {
//		File destDir = new File(directory);
//		System.out.println("Destination Folder Path = " + directory);
//		// ArrayList<String> files = new ArrayList<String>();
//		// Get all files from a directory.
//		File[] fList = destDir.listFiles();
//		if (fList != null) {
//			for (File file : fList) {
//				if (file.isFile()) {
//					fileList.add(file.getAbsolutePath());
//					// System.out.println("directory:" + file.getAbsolutePath());
//				} else if (file.isDirectory()) {
//					listOFiles(file.getAbsolutePath());
//				}
//			}
//		}
//		return fileList;
//	}

	// =============== MOVE FILE FROM ONE DIRECTORY TO ANTHER ==============
//	public void move(String fromDir, String toDir) throws IOException {
//		File fromDirFile = new File(fromDir);
//		File moveToFile = new File(toDir);
//
//		String fileName = fromDirFile.getName();
//
//		// MOVE ALL FILES INTO PARENT DIRECTORY
//		System.out.println("File name = " + fileName + "\n" + "Move to = " + toDir);
//		fromDirFile.renameTo(new File(moveToFile, fromDirFile.getName()));
//	}

	// ============== DELETE EMPTY DIRECTORY ===========
//	public static void deleteEmptyDirectory(File dir) {
//		if (dir.isDirectory()) {
//			File[] fList = dir.listFiles();
//			if (fList != null) {
//				for (File file : fList) {
//					if (!file.isFile()) {
//						System.out.println("Removing empty directory : " + file.getName());
//						file.delete();
//						deleteEmptyDirectory(file);
//					}
//				}
//				dir.delete();
//			}
//		}
//	}

}
