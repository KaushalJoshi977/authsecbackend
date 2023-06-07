package com.realnet.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/* @Aurthor: Niladri Sen
 * This is for model class validation exceptions. need to handle this in front-end error response.
 * */
@Data
public class ErrorDetails {
	private Date timestamp;
	private String message;
	//private List<String> fieldErrors = new ArrayList<>();
	private Map<String, String> fieldErrors = new HashMap<String,String>();
	public ErrorDetails(Date timestamp, String message, Map<String,String> fieldErrors) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.fieldErrors = fieldErrors;
	}

}
