package com.realnet.template.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.realnet.template.entity.TemplateFileUpload;
import com.realnet.template.repository.TemplatedataRepo;

@Service
public class FileUploadService {
 
	
	@Autowired
	private TemplatedataRepo repo;
	

	
	public String uploadFile(MultipartFile file,Long user_id,String file_type) {
		 try {
			 
//			 String 
			 String file_name= file.getOriginalFilename();
			 String substring = file_name.substring(0,file_name.lastIndexOf("."));
			
			 String str =  substring+System.currentTimeMillis();
			 String location =System.getProperty("user.dir")+"/src/main/resources";
			 
			 
			 String file_name2= file.getOriginalFilename();
			 
			 
			 File staticdir2 = new File(location+"/incomingfile");
			 if(!staticdir2.exists()) {
				 staticdir2.mkdir();
			 }
			 Path copyLocation2 = Paths
	                .get(staticdir2 + File.separator + StringUtils.cleanPath(file_name2));
	            
	            Files.copy(file.getInputStream(), copyLocation2, StandardCopyOption.REPLACE_EXISTING); 
			 
						 
			 File staticdir = new File(location+"/processingfile");
			 if(!staticdir.exists()) {
				 staticdir.mkdir();
			 }
			 Path copyLocation = Paths
	                .get(staticdir + File.separator + StringUtils.cleanPath(str+".xlsx"));
	            
	            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
	            
	            
	            TemplateFileUpload exceldata= new TemplateFileUpload();
	            exceldata.setFile_location(location);
	            
	            exceldata.setFile_name(file.getOriginalFilename());
	            exceldata.setFile_changed_name(str);
	            exceldata.setFile_type(file_type);
	            exceldata.setUser_id(user_id);
	            repo.save(exceldata);
	            
	            System.out.println("-----------------------saving data----------------");
	            return location;
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RuntimeException("Could not store file " + file.getOriginalFilename()
	                + ". Please try again!");
	        }
	
	}
}
