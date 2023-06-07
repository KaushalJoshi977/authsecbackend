package com.realnet.logging;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
@Component
public class LoggingAspect {
	
	Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	// static Logger logger = LogManager.getLogger(LoggingAspect.class.getName());

	/*
	 * 1. (..) zero or more || (*) one or more args
	 * 2. @Before("execution(* com.realnet.*.*(..))")
	 * 3. getTarger() returns the Object name
	 */

//	@Before("packagePointcut()")
//	public void beforeMethodCall(JoinPoint joinPoint) throws JsonProcessingException {
//		String className = joinPoint.getTarget().getClass().toString(); // com.realnet.controller.Rn_InstructorController
//		String methodName = joinPoint.getSignature().getName(); //// getInstructors
//		String methodSignature = joinPoint.getSignature().toString();
//		
//		 // Rn_InstructorResponse com.realnet.controller.Rn_InstructorController.getInstructors(Integer,Integer)
//		//System.out.println(methodSignature);
//		String method = getMethodSignature(methodSignature);
//		logger.info("Executing " + className + " : " + method);
//		
//		Object[] args = joinPoint.getArgs();
//		// object into json format
//		ObjectMapper mapper = new ObjectMapper();
//		String json = mapper.writeValueAsString(args);
//		logger.debug("Arguments: " + json);
//	}
	
	// handle before after together
	@Around("anyClasses()")
	public Object LoggingAdvice(ProceedingJoinPoint pjp) throws Throwable {
		String className = pjp.getTarget().getClass().toString();
		// String methodName = pjp.getSignature().getName();
		String methodSignature = pjp.getSignature().toString();
		String methodName = methodSignature(methodSignature);
		Object[] args = pjp.getArgs();
		// object into json format
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(args);
		logger.info("Executing : " + className + " : " + methodName);
		logger.debug("Arguments: " + args);
		Object object = pjp.proceed();
		logger.info("Execution complete: " + className + " : " + methodName);
		logger.debug(className + " : " + methodName + " Response: " + json);
		return object;
	}

	
	@AfterReturning("anyClasses()")
	public void afterMethodCall(JoinPoint joinPoint) throws JsonProcessingException {
		String className = joinPoint.getTarget().getClass().toString();
		String methodSignature = joinPoint.getSignature().toString();
		String methodName = methodSignature(methodSignature);
		Object[] args = joinPoint.getArgs();
		// object into json format
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(args);
		logger.info("Successfully Executed: " + className + " : " + methodName);
	}

	// handle exception
	@AfterThrowing(pointcut = "anyClasses()", throwing = "ex")
	public void errorHandling(JoinPoint joinPoint, Exception ex) throws JsonProcessingException {
		String className = joinPoint.getTarget().getClass().toString();
		// String methodName = joinPoint.getSignature().getName();
		String methodSignature = joinPoint.getSignature().toString();
		String methodName = methodSignature(methodSignature);
		Object[] args = joinPoint.getArgs();
		// object into json format
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(args);
		logger.debug(className + " : " + methodName + "Arguments: " + json);
		logger.error("Some Exception Happens: " + className + " : " + methodName);
		logger.error(ex.getMessage());
	}

	// any package, any class, any method inside com.realnet
	@Pointcut("execution(* com.realnet.*.*.*(..)) && !@annotation(com.realnet.logging.NoLogging)")
	public void packagePointcut() {
	}
	// any public class
	@Pointcut("within(com.realnet..*) && !@annotation(com.realnet.logging.NoLogging)")
	private void anyClasses() {}
	

	// problem in config class
	@Pointcut("!within(com.realnet.config..*)")
	private void configOperations() {
	}
	
	@Around("@annotation(com.realnet.logging.LogExecutionTime)")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
	    long start = System.currentTimeMillis();

	    Object proceed = joinPoint.proceed();

	    long executionTime = System.currentTimeMillis() - start;
	    
	    long seconds = TimeUnit.MILLISECONDS.toSeconds(executionTime);
	    long minutes = (executionTime/1000)/60;

	    System.out.println(joinPoint.getSignature() + " executed in " + seconds + "sec(" + minutes + " Mins)");
	    return proceed;
	}
	
	
	

	static String methodSignature(String name) {
		name = name.substring(name.lastIndexOf(".") + 1);
		return name;
	}
	
	

//	// any public class
//	@Pointcut("execution(public * *(..))")
//	private void anyPublicOperation() {}
}
