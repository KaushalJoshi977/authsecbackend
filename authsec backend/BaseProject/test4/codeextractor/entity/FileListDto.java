package com.realnet.codeextractor.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class FileListDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String fileName;
}
