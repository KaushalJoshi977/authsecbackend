package com.realnet.codeextractor.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.realnet.codeextractor.entity.Rn_Bcf_Extractor;
import com.realnet.codeextractor.entity.Rn_Bcf_Extractor_Params;
import com.realnet.codeextractor.repository.Rn_Bcf_Extractor_Repository;
import com.realnet.exceptions.ResourceNotFoundException;
import com.realnet.fnd.service.FileStorageService;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserService;
import com.realnet.users.service1.AppUserServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Rn_Bcf_Extractor_ServiceImpl implements Rn_Bcf_Extractor_Service {

	@Autowired
	private AppUserServiceImpl userService;
	
	@Autowired
	private FileStorageService fileStorageService;

	@Autowired
	private Rn_Bcf_Extractor_Repository rn_bcf_extractor_repository;
	
	@Autowired
	private Rn_Bcf_Extractor_Params_Service rn_bcf_extractor_params_service;

	@Override
	public List<Rn_Bcf_Extractor> getAll() {
		return rn_bcf_extractor_repository.findAll();
	}

	@Override
	public Page<Rn_Bcf_Extractor> getAll(Pageable page) {
		return rn_bcf_extractor_repository.findAll(page);
	}

	@Override
	public Rn_Bcf_Extractor getById(int id) {
		Rn_Bcf_Extractor bcf_extractor = rn_bcf_extractor_repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Extractor Table Data Not Found :: " + id));
		return bcf_extractor;
	}

	@Override
	public Rn_Bcf_Extractor save(Rn_Bcf_Extractor bcf_extractor) {
		// User loggedInUser = userService.getLoggedInUser();
		// bcf_extractor.setCreatedBy(loggedInUser.getUserId());
		Rn_Bcf_Extractor savedExtractor = rn_bcf_extractor_repository.save(bcf_extractor);
		return savedExtractor;
	}

	@Override
	public Rn_Bcf_Extractor updateById(int id, Rn_Bcf_Extractor extractorRequest) {
		// User loggedInUser = userService.getLoggedInUser();

		Rn_Bcf_Extractor old_extractor = rn_bcf_extractor_repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Menu not found :: " + id));

		old_extractor.setTech_stack(extractorRequest.getTech_stack());
		old_extractor.setTech_stack_key(extractorRequest.getTech_stack_key());
		old_extractor.setObject_type(extractorRequest.getObject_type());
		old_extractor.setSub_object_type(extractorRequest.getSub_object_type());
		old_extractor.setForm_type_name(extractorRequest.getForm_type_name());
		old_extractor.setStd_wf_name(extractorRequest.getStd_wf_name());
		old_extractor.setIcon_file_name(extractorRequest.getIcon_file_name());
		old_extractor.setSample_file_name(extractorRequest.getSample_file_name());
		old_extractor.setExtractor_stage(extractorRequest.getExtractor_stage());
		// line part
		old_extractor.setRn_bcf_extractor_Params(extractorRequest.getRn_bcf_extractor_Params());
		// old_extractor.setUpdatedBy(loggedInUser.getUserId());
		final Rn_Bcf_Extractor updated_function = rn_bcf_extractor_repository.save(old_extractor);
		return updated_function;
	}

	@Override
	public boolean deleteById(int id) {
		if (!rn_bcf_extractor_repository.existsById(id)) {
			throw new ResourceNotFoundException("Extractor Data not exist");
		}
		Rn_Bcf_Extractor bcf_extractor = rn_bcf_extractor_repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Menu not found :: " + id));
		rn_bcf_extractor_repository.delete(bcf_extractor);
		return true;
	}

	@Override
	public void saveListOFiles(int headerId, String tech_stack, String obj_type, String sub_obj_type,
			String destDirectory) throws IOException {
		File destDir = new File(destDirectory);
		
		System.out.println("Destination Folder Path = " + destDirectory);

		ArrayList<String> files = new ArrayList<String>();

		// Get all files from a directory.
		File[] fList = destDir.listFiles();
		if (fList != null) {
			for (File file : fList) {
				if (file.isFile()) {
					files.add(file.getAbsolutePath());
					// System.out.println("directory:" + file.getAbsolutePath());
				} else if (file.isDirectory()) {
					saveListOFiles(headerId, tech_stack, obj_type, sub_obj_type, file.getAbsolutePath());
				}
			}
		}
		
		AppUser user = userService.getLoggedInUser();
		Long userId = user.getUserId();
		
		Rn_Bcf_Extractor rn_bcf_extractor = this.getById(headerId);
		String sampleFileName = rn_bcf_extractor.getSample_file_name();
		Long accId = rn_bcf_extractor.getAccountId();
		System.out.println("SAMPLE FILE NAME = " + sampleFileName);
		// REMOVING .ZIP
		sampleFileName = sampleFileName.substring(0, sampleFileName.lastIndexOf("."));
//		System.out.println("AFTER REMOVING .ZIP = " + sampleFileName);
		// FOR CONVERTING OBJECT NAMES
		// String insertPrefix = "Rn_";

		//List<Rn_Bcf_Extractor_Params> params = new ArrayList<Rn_Bcf_Extractor_Params>();
		// GET FILE PATHS
		for (int i = 0; i < files.size(); i++) {
			//List<Rn_Bcf_Extractor_Params> params = new ArrayList<Rn_Bcf_Extractor_Params>();
			String filePath = files.get(i).replace("\\", "/");
			File finalFile = new File(filePath);
			String name = finalFile.getName();

			/*
			 * ***ADD RN PREFIX LOGIC*** if(!name.contains(insertPrefix)) { name =
			 * name.substring(0,0).concat(insertPrefix) + name.substring(0); }
			 */

			// DESTINATION ADDRESS PATH(FOR OUTPUT FILES)
			/*
			 * ZIP FILE NAME = Angular_project INPUT :
			 * {PROJECT_PATH}/src/main/resources/extracted-files/
			 * Angular_project_1599226114430/rn_header_test1-details.component.html OUTPUT :
			 * /frontend/src/app/admin/{{DYNAMIC VARIABLE}}/details/
			 */
			String ref_address_string = filePath.substring(filePath.lastIndexOf(sampleFileName));
			ref_address_string = ref_address_string.substring(ref_address_string.indexOf("/"));
			// System.out.println("REF ADDRESS STRING = " + ref_address_string);

//			// DYNAMIC UI_NAME LOGIC FOR ANGULAR FILE STRUCTURE
//			final String ng_prj_struct = "/frontend/src/app/admin/";
//			if (ref_address_string.contains(ng_prj_struct)) {
//				int len = ng_prj_struct.length();
//			    String data = ref_address_string.substring(0,len) + "ui_name";
//			    int tail = ref_address_string.indexOf("/", len + 1);
//			    String temp = ref_address_string.substring(tail);
//			    data += temp;
//			    ref_address_string = data;
//			}

			// ========ADD IT INTO PARAMETERS TABLE=========
			Rn_Bcf_Extractor_Params rn_bcf_extractor_params = new Rn_Bcf_Extractor_Params();
			// System.out.println("SET HEADER ID = " + headerId);

			// rn_bcf_extractor_params_t.setHeader_id(headerId);
			rn_bcf_extractor_params.setTech_stack(tech_stack);
			rn_bcf_extractor_params.setObject_type(obj_type);
			rn_bcf_extractor_params.setSub_object_type(sub_obj_type);
			rn_bcf_extractor_params.setFile_code("200");
			rn_bcf_extractor_params.setName_string(name);
			rn_bcf_extractor_params.setAddress_string(filePath);
			rn_bcf_extractor_params.setReference_address_string(ref_address_string); // FOR OUTPUT FILE PATH

			String fileType = net.lingala.zip4j.util.FileUtils.getFileExtension(finalFile);
			rn_bcf_extractor_params.setDescription(fileType);
			// IF IT'S YES, THEN FILE WILL BE PROCESSED FOR STATIC AND DYNAMIC EXTRACTION.
			rn_bcf_extractor_params.setIs_extraction_enabled(true);
			rn_bcf_extractor_params.setIs_creation_enabled(true);
			rn_bcf_extractor_params.setAccountId(accId);
			rn_bcf_extractor_params.setCreatedBy(userId);

			// SAVING BASE PROJECT FILE PATH IN PARAMS TABLE
			 //bcf_extractor_params_service.save(rn_bcf_extractor_params_t);
			 //rn_bcf_extractor.setRn_bcf_extractor_Params(rn_bcf_extractor_params);
//			params.add(rn_bcf_extractor_params);
//			rn_bcf_extractor.setRn_bcf_extractor_Params(params);
//			this.updateById(headerId, rn_bcf_extractor); // need to test
			rn_bcf_extractor_params.setRn_bcf_extractor(rn_bcf_extractor);
			rn_bcf_extractor_params_service.save(rn_bcf_extractor_params);
		}

	}

	@Override
	public void moveFiles(int id, String toDir) throws IOException {
		File moveToFile = new File(toDir);
		// List<Rn_Bcf_Extractor_Params> params_t = getById(id).getRn_bcf_extractor_Params();
		//Rn_Bcf_Extractor extractor = this.getById(id);
		//log.debug("extractor : {} ",extractor);
		//List<Rn_Bcf_Extractor_Params> params_t = extractor.getRn_bcf_extractor_Params(); 
		List<Rn_Bcf_Extractor_Params> params_t = rn_bcf_extractor_params_service.getByHeaderId(id);
		//log.debug("extractor : {} ", params_t);
		for (Rn_Bcf_Extractor_Params params : params_t) {
			String fileDirectory = params.getAddress_string();

			File file = new File(fileDirectory);
			String fileName = file.getName();

			// MOVE ALL FILES INTO PARENT DIRECTORY
			System.out.println("File name = " + fileName + "\n" + "Move to = " + toDir);
			file.renameTo(new File(moveToFile, file.getName()));

			String destDir = toDir + "/" + fileName;

			// SAVE MOVED DIRECTORY PATH INTO THE PARAMS TABLE
			params.setMoved_address_string(destDir);
			rn_bcf_extractor_params_service.save(params);
		}
		// DELETE EMPTY DIRECTORY
		this.deleteEmptyDirectory(moveToFile);

	}

	@Override
	public void deleteEmptyDirectory(File dir) throws IOException {
		if (dir.isDirectory()) {
			File[] fList = dir.listFiles();
			if (fList != null) {
				for (File file : fList) {
					if (!file.isFile()) {
						System.out.println("Removing empty directory : " + file.getName());
						file.delete();
						deleteEmptyDirectory(file);
					}
				}
				dir.delete();
			}
		}
	}

}
