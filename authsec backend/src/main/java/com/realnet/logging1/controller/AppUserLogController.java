package com.realnet.logging1.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.logging1.entity.AppUserLog;
import com.realnet.logging1.service.LoggingService;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserServiceImpl;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value="/log" )
public class AppUserLogController {
	private LoggingService loggingService;
	private AppUserServiceImpl appUserServiceImpl;
	@Autowired
	public AppUserLogController(LoggingService loggingService,
			AppUserServiceImpl appUserServiceImpl) {
		super();
		this.loggingService = loggingService;
		this.appUserServiceImpl=appUserServiceImpl;
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAllLog(){
		List<AppUserLog> l = loggingService.getAll();
		return new ResponseEntity<>(l,HttpStatus.OK);
	}
	@GetMapping("/getOne/{id}")
	public ResponseEntity<?> getOne(@PathVariable("id") Long id){
		AppUserLog a = loggingService.getOne(id);
		if(a!=null) {
			return new ResponseEntity<>(a,HttpStatus.OK);
		}
		return new ResponseEntity<>("Not Found",HttpStatus.BAD_REQUEST);
	}
	@GetMapping("/startLogging/{username}/{mode}")
	public ResponseEntity<?> addOne(@PathVariable("username") String username,@PathVariable("mode")String mode,HttpSession session){
		AppUser a = appUserServiceImpl.getByUserName(username).orElse(null);
		DateFormat simple =new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
		if(a!=null) {
			AppUserLog l = loggingService.getOne(a.getUserId());
			if(l!=null) {
				l.setGenerateLog("Y");
				if(mode!=null) {
					l.setLogLevel(mode);
				}
				loggingService.update(l);
				session.setAttribute("generate_log","Y");
				Object a1 = new Object() {
					private String message="Will now Start logging";
					public String getMessage() {
						return message;
					}

					public void setMessage(String message) {
						this.message = message;
					}
					
				};
				return new ResponseEntity<>(a1,HttpStatus.OK);
			}else {
				long ut1 = Instant.now().getEpochSecond();
				AppUserLog p =new AppUserLog();
				p.setUserName(a.getUsername());
				p.setGenerateLog("Y");
				p.setLogFileName(a.getUsername()+ut1+".log");
				p.setLogLevel("info");
				if(mode!=null) {
					p.setLogLevel(mode);
				}
				p.setGenerateLog("Y");
				p.setCreatedOn(new Date());
				p.setCreatedOnFormated(simple.format(new Date()));
				loggingService.add(p);
				session.setAttribute("generate_log","Y");
				Object a1 = new Object() {
					private String message="Will now Start logging";
					public String getMessage() {
						return message;
					}

					public void setMessage(String message) {
						this.message = message;
					}
					
				};
				return new ResponseEntity<>(a1,HttpStatus.OK);
			}
		}
		Object a1 = new Object() {
			private String message="No user found with this username";
			public String getMessage() {
				return message;
			}

			public void setMessage(String message) {
				this.message = message;
			}
			
		};
		return new ResponseEntity<>(a1,HttpStatus.OK);
	}
	@GetMapping("/stopLogging/{username}")
	public ResponseEntity<?> stopLog(@PathVariable("username") String username,HttpSession session){
		AppUser a = appUserServiceImpl.getByUserName(username).orElse(null);
		DateFormat simple =new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
		if(a!=null) {
			AppUserLog l = loggingService.getOne(a.getUserId());
			if(l!=null) {
				l.setGenerateLog("N");
				loggingService.add(l);
				session.setAttribute("generate_log","N");
				Object a1 = new Object() {
					private String message="Will now Stop logging from next session";
					public String getMessage() {
						return message;
					}

					public void setMessage(String message) {
						this.message = message;
					}
					
				};
				return new ResponseEntity<>(a1,HttpStatus.OK);
			}else {
				long ut1 = Instant.now().getEpochSecond();
				//AppUserLog p = new AppUserLog(c,a.getUsername(),"Y",a.getUsername()+ut1+".log","info",null,null);
				AppUserLog p =new AppUserLog();
				p.setUserName(a.getUsername());
				p.setLogFileName(a.getUsername()+ut1+".log");
				p.setLogLevel("info");
				p.setGenerateLog("N");
				p.setCreatedOn(new Date());
				p.setCreatedOnFormated(simple.format(new Date()));
				loggingService.add(p);
				session.setAttribute("generate_log","N");
				Object a1 = new Object() {
					private String message="Will now Stop logging from next session";
					public String getMessage() {
						return message;
					}

					public void setMessage(String message) {
						this.message = message;
					}
					
				};
				return new ResponseEntity<>(a1,HttpStatus.OK);
			}
		}
		Object a1 = new Object() {
			private String message="No user found with this username";
			public String getMessage() {
				return message;
			}

			public void setMessage(String message) {
				this.message = message;
			}
			
		};
		return new ResponseEntity<>(a1,HttpStatus.OK);
	}
	
//	@GetMapping("/downloadLog/{filename}")
//	public ResponseEntity<?> sendFile(@PathVariable("filename")String filename,HttpServletRequest request) throws Exception{
//	//	AppUser a = appUserServiceImpl.getByUserName(username).orElse(null);
//				Resource resource = loggingService.loadFileAsResource(filename);
//				if(resource!=null) {
//					String contentType = null;
//			        try {
//			            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//			        } catch (IOException ex) {
//			            throw new Exception("Could not find log file for file: "+filename);
//			        }
//			        // Fallback to the default content type if type could not be determined
//			        if(contentType == null) {
//			            contentType = "application/octet-stream";
//			        }
//			        return ResponseEntity.ok()
//			                .contentType(MediaType.parseMediaType(contentType))
//			                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//			                .body(resource);
//				}else {
//					return new ResponseEntity<>("File could not be loaded",HttpStatus.BAD_REQUEST);
//				}
//		}


}