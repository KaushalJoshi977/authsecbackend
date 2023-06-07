package com.realnet.codeextractor.entity;

//@Data
public class RuleCopyDTO {
	private String from_tech_stack;
	private String from_object_type;
	private String from_sub_object_type;
	private String to_tech_stack;
	private String to_object_type;
	private String to_sub_object_type;

	public String getFrom_tech_stack() {
		return from_tech_stack;
	}

	public void setFrom_tech_stack(String from_tech_stack) {
		this.from_tech_stack = from_tech_stack;
	}

	public String getFrom_object_type() {
		return from_object_type;
	}

	public void setFrom_object_type(String from_object_type) {
		this.from_object_type = from_object_type;
	}

	public String getFrom_sub_object_type() {
		return from_sub_object_type;
	}

	public void setFrom_sub_object_type(String from_sub_object_type) {
		this.from_sub_object_type = from_sub_object_type;
	}

	public String getTo_tech_stack() {
		return to_tech_stack;
	}

	public void setTo_tech_stack(String to_tech_stack) {
		this.to_tech_stack = to_tech_stack;
	}

	public String getTo_object_type() {
		return to_object_type;
	}

	public void setTo_object_type(String to_object_type) {
		this.to_object_type = to_object_type;
	}

	public String getTo_sub_object_type() {
		return to_sub_object_type;
	}

	public void setTo_sub_object_type(String to_sub_object_type) {
		this.to_sub_object_type = to_sub_object_type;
	}

}
