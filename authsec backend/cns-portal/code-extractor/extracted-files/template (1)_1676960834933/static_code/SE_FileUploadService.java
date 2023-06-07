"package com.realnet.template.service;" + "\r\n" + 
"" + "\r\n" + 
"import java.io.File;" + "\r\n" + 
"import java.nio.file.Files;" + "\r\n" + 
"import java.nio.file.Path;" + "\r\n" + 
"import java.nio.file.Paths;" + "\r\n" + 
"import java.nio.file.StandardCopyOption;" + "\r\n" + 
"" + "\r\n" + 
"import org.springframework.beans.factory.annotation.Autowired;" + "\r\n" + 
"import org.springframework.beans.factory.annotation.Value;" + "\r\n" + 
"import org.springframework.stereotype.Service;" + "\r\n" + 
"import org.springframework.util.StringUtils;" + "\r\n" + 
"import org.springframework.web.multipart.MultipartFile;" + "\r\n" + 
"" + "\r\n" + 
"import com.realnet.template.entity.TemplateFileUpload;" + "\r\n" + 
"import com.realnet.template.repository.TemplatedataRepo;" + "\r\n" + 
"" + "\r\n" + 
"@Service" + "\r\n" + 
"public class FileUploadService {" + "\r\n" + 
" " + "\r\n" + 
"	" + "\r\n" + 
"	@Autowired" + "\r\n" + 
"	private TemplatedataRepo repo;" + "\r\n" + 
"	" + "\r\n" + 
"" + "\r\n" + 
"	" + "\r\n" + 
"	public String uploadFile(MultipartFile file,Long user_id,String file_type) {" + "\r\n" + 
"		 try {" + "\r\n" + 
"			 " + "\r\n" + 
"//			 String " + "\r\n" + 
"			 String file_name= file.getOriginalFilename();" + "\r\n" + 
"			 String substring = file_name.substring(0,file_name.lastIndexOf(\".\"));" + "\r\n" + 
"			" + "\r\n" + 
"			 String str =  substring+System.currentTimeMillis();" + "\r\n" + 
"			 String location =System.getProperty(\"user.dir\")+\"/src/main/resources\";" + "\r\n" + 
"			 " + "\r\n" + 
"			 " + "\r\n" + 
"			 String file_name2= file.getOriginalFilename();" + "\r\n" + 
"			 " + "\r\n" + 
"			 " + "\r\n" + 
"			 File staticdir2 = new File(location+\"/incomingfile\");" + "\r\n" + 
"			 if(!staticdir2.exists()) {" + "\r\n" + 
"				 staticdir2.mkdir();" + "\r\n" + 
"			 }" + "\r\n" + 
"			 Path copyLocation2 = Paths" + "\r\n" + 
"	                .get(staticdir2 + File.separator + StringUtils.cleanPath(file_name2));" + "\r\n" + 
"	            " + "\r\n" + 
"	            Files.copy(file.getInputStream(), copyLocation2, StandardCopyOption.REPLACE_EXISTING); " + "\r\n" + 
"			 " + "\r\n" + 
"						 " + "\r\n" + 
"			 File staticdir = new File(location+\"/processingfile\");" + "\r\n" + 
"			 if(!staticdir.exists()) {" + "\r\n" + 
"				 staticdir.mkdir();" + "\r\n" + 
"			 }" + "\r\n" + 
"			 Path copyLocation = Paths" + "\r\n" + 
"	                .get(staticdir + File.separator + StringUtils.cleanPath(str+\".xlsx\"));" + "\r\n" + 
"	            " + "\r\n" + 
"	            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);" + "\r\n" + 
"	            " + "\r\n" + 
"	            " + "\r\n" + 
"	            TemplateFileUpload exceldata= new TemplateFileUpload();" + "\r\n" + 
"	            exceldata.setFile_location(location);" + "\r\n" + 
"	            " + "\r\n" + 
"	            exceldata.setFile_name(file.getOriginalFilename());" + "\r\n" + 
"	            exceldata.setFile_changed_name(str);" + "\r\n" + 
"	            exceldata.setFile_type(file_type);" + "\r\n" + 
"	            exceldata.setUser_id(user_id);" + "\r\n" + 
"	            repo.save(exceldata);" + "\r\n" + 
"	            " + "\r\n" + 
"	            System.out.println(\"-----------------------saving data----------------\");" + "\r\n" + 
"	            return location;" + "\r\n" + 
"	        } catch (Exception e) {" + "\r\n" + 
"	            e.printStackTrace();" + "\r\n" + 
"	            throw new RuntimeException(\"Could not store file \" + file.getOriginalFilename()" + "\r\n" + 
"	                + \". Please try again!\");" + "\r\n" + 
"	        }" + "\r\n" + 
"	" + "\r\n" + 
"	}" + "\r\n" + 
"}" 