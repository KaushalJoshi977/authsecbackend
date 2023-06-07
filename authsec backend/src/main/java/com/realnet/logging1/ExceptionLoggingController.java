//package com.realnet.logging1;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.nio.file.FileSystems;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//import javax.servlet.http.HttpSession;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import com.realnet.config.HttpLoggingFilter;
//import com.realnet.users.service1.AppUserServiceImpl;
//
//@RestControllerAdvice
//public class ExceptionLoggingController implements ErrorController{
//	private static final String ERROR_PATH = "/error";
//	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
//	@Autowired
//	private AppUserServiceImpl appUserServiceImpl;
//	
//	public ExceptionLoggingController(AppUserServiceImpl appUserServiceImpl) {
//		super();
//		this.appUserServiceImpl = appUserServiceImpl;
//	}
//
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<?> internalServerEror(Exception exception,HttpSession session){
//		StackTraceElement[] e = exception.getStackTrace();
//		System.out.println(e[0].getMethodName());
//		System.out.println(e[0].getClassName());
//		LOGGER.error("Unhandled Error By  "+appUserServiceImpl.getLoggedInUser().getUsername()+" = "+exception.getMessage());
//		LOGGER.error("Request Info = "+HttpLoggingFilter.logInfo+"Error at Mehod= "+e[0].getMethodName()+" In Class= "+e[0].getClassName());
//		if(session.getAttribute("generate_log").equals("Y")) {
//			try {
//				if (session.getAttribute("LogginLevel").equals("error")) {
//			        Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
//			        Path filePath = Paths.get(root.toString(),"logs",session.getAttribute("LogFileName").toString());
//					File f=filePath.toFile();
//		//			File f = new File("/home/jboss/EAP-7.1.0/logs/sysadmin2_5463783.log");
//					FileWriter fw = new FileWriter(f,true);
//					StringBuilder logInfo = HttpLoggingFilter.logInfo;
//					logInfo.append(" [RESPONSE:").append(" ERROR]").append(" [ERROR AT ").append(e[0].getMethodName())
//							.append(" IN CLASS ").append(e[0].getClassName()).append("]");
//					fw.write(logInfo + "\n");
//					fw.close();
//				} else if (session.getAttribute("LogginLevel").equals("debug")) {
//			        Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
//			        Path filePath = Paths.get(root.toString(),"logs",session.getAttribute("LogFileName").toString());
//					File f=filePath.toFile();
//		//			File f = new File("/home/jboss/EAP-7.1.0/logs/sysadmin2_5463783.log");
//					FileWriter fw = new FileWriter(f,true);
//					StringBuilder logInfo = HttpLoggingFilter.logInfo;
//					logInfo.append(" [RESPONSE:").append(" DEBUG]").append(" [ERROR AT ").append(e[0].getMethodName())
//							.append(" IN CLASS ").append(e[0].getClassName()).append("]");
//					fw.write(logInfo + "\n");
//					fw.close();
//					HttpLoggingFilter.logged=true;
//				} else if (session.getAttribute("LogginLevel").equals("warn")) {
//			        Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
//			        Path filePath = Paths.get(root.toString(),"logs",session.getAttribute("LogFileName").toString());
//					File f=filePath.toFile();
//		//			File f = new File("/home/jboss/EAP-7.1.0/logs/sysadmin2_5463783.log");
//					FileWriter fw = new FileWriter(f,true);
//					StringBuilder logInfo = HttpLoggingFilter.logInfo;
//					logInfo.append(" [RESPONSE:").append(" WARN]").append(" [ERROR AT ").append(e[0].getMethodName())
//							.append(" IN CLASS ").append(e[0].getClassName()).append("]");
//					fw.write(logInfo + "\n");
//					fw.close();
//				}
//			} catch (Exception e1) {
//
//			}
//		}
//		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//	}
//	
//	@Override
//	public String getErrorPath() {
//		// TODO Auto-generated method stub
//		return ERROR_PATH;
//	}
//	
//}
