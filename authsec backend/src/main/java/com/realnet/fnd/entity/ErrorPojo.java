package com.realnet.fnd.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ErrorPojo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Error error;
	
	public ErrorPojo() {
		
	}
	
	public ErrorPojo(Error error) {
		this.error = error;
	}

    public Error getError ()
    {
        return error;
    }

    public void setError (Error error)
    {
        this.error = error;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [error = "+error+"]";
    }
}
		
