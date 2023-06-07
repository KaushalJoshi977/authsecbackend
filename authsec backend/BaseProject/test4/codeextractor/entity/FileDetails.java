package com.realnet.codeextractor.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class FileDetails implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String text;
}
