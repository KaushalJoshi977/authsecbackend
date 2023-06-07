package com.realnet.exceptions;

import java.net.ConnectException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import com.realnet.fnd.response.OperationResponse;
import com.realnet.fnd.response.OperationResponse.ResponseStatusEnum;

import lombok.extern.slf4j.Slf4j;

/*
@ControllerAdvice tells your spring application that this class will do the exception handling for your application.
@RestController will make it a controller and let this class render the response.
Use @ExceptionHandler annotation to define the class of Exception it will catch. (A Base class will catch all the Inherited and extended classes)
*/
@Slf4j
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public OperationResponse handleBaseException(DataIntegrityViolationException e){
        OperationResponse resp = new OperationResponse();
        resp.setOperationStatus(ResponseStatusEnum.ERROR);
        resp.setOperationMessage(e.getRootCause().getMessage());
        log.info("Global Exception Handler : " + resp.toString());
        return resp;
    }
    
    @ExceptionHandler(value = AccessDeniedException.class)
    public OperationResponse handleAccessDeniedException(AccessDeniedException e){
        OperationResponse resp = new OperationResponse();
        resp.setOperationStatus(ResponseStatusEnum.NO_ACCESS);
        resp.setOperationMessage("Your Access is Denied Plz contact Admin");
        log.info("Global Exception Handler : " + resp.toString());
        return resp;
    }
    
//    @ExceptionHandler(value = ConnectException.class)
//    private OperationResponse handleconnectionrefusedexception(ConnectException e) {
//        OperationResponse resp = new OperationResponse();
//        resp.setOperationMessage("connection refused please start server");
//        resp.setOperationStatus(ResponseStatusEnum.WARNING);
//		return resp;  	
//    }
    
    @ExceptionHandler(value = ResourceAccessException.class)
    private OperationResponse handleconnectionrefusedexception(ResourceAccessException e) {
      OperationResponse resp = new OperationResponse();
      resp.setOperationMessage("connection refused please start server "
      +"\n " +e.getLocalizedMessage());
      resp.setOperationStatus(ResponseStatusEnum.WARNING);
  		return resp;
     	
    }

}
