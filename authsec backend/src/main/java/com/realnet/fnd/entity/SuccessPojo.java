package com.realnet.fnd.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class SuccessPojo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Success success;
	
	public SuccessPojo() {
		
	}
	
	public SuccessPojo(Success success) {
		this.success = success;
	}

   
    public Success getSuccess() {
		return success;
	}

	public void setSuccess(Success success) {
		this.success = success;
	}

	@Override
    public String toString()
    {
        return "ClassPojo [success = "+success+"]";
    }
}