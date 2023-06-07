package com.realnet.utils;

public class TechnologyConstant {

	// TECHNOLOGY STACK
	public static final String ANGULAR_SPRING_BOOT_MYSQL = "Angular-SpringBoot-Mysql";
	public static final String ANGULAR_SPRING_BOOT_MONGODB = "Angular-SpringBoot-MongoDB";
	public static final String SPRING_MVC_HIBERNATE_MYSQL = "SpringMVC-Hibernate-Mysql";
	public static final String REACT_REACT_NATIVE_MYSQL = "React-ReactNative-Mysql";
	public static final String REACT_REACT_NATIVE_MONGODB = "React-ReactNative-MongoDB";
	public static final String PHP_LARAVEL_MYSQL = "Php-Laravel-Mysql";
	public static final String MEAN = "MEAN";

	public static enum TechStackKey {
		sphmy, // SPRING_MVC_HIBERNATE_MYSQL
		aspmy, // ANGULAR_SPRING_BOOT_MYSQL
		rrnmy, // REACT_REACT_NATIVE_MYSQL
		rrnmo, // REACT_REACT_NATIVE_MONGODB
		aspmo, // ANGULAR_SPRING_BOOT_MONGODB
		plvmy, // PHP_LARAVEL_MYSQL
		mean // MEAN
	}

	// OBJECT TYPE
	public static final String OBJ_TYPE_FORM = "form";
	public static final String OBJ_TYPE_BI = "bi";
	public static final String OBJ_TYPE_REPORT = "report";
	public static final String OBJ_TYPE_API = "api";

	// SUB OBJECT TYPE
	public static final String SUB_OBJ_TYPE_ONLY_HEADER = "only header";
	public static final String SUB_OBJ_TYPE_ONLY_LINE = "only line";
	public static final String SUB_OBJ_TYPE_HEADER_LINE = "header line";
	public static final String SUB_OBJ_TYPE_HEADER_MULTILINE = "header multiline";
	public static final String SUB_OBJ_TYPE_WORKFLOW = "wrokflow";
	public static final String SUB_OBJ_TYPE_SETUP = "setup";
	public static final String SUB_OBJ_TYPE_STD_REPORT = "std report";
	public static final String SUB_OBJ_TYPE_BI_REPORT = "bi report";
	public static final String SUB_OBJ_TYPE_REST_API = "rest api";
}
