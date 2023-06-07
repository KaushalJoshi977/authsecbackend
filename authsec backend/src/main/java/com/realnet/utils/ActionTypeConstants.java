package com.realnet.utils;

public class ActionTypeConstants {
	public static final String AC1_COMMENT = "comment";
	public static final String AC1_LOG = "log";
	public static final String AC1_VARIABLE = "variable";
	public static final String AC1_OPERATION = "operation";
	
	public static final String AC1_FOR_LOOP = "forloop";
	public static final String AC1_IF = "if";
	public static final String AC1_ELSE = "else";
	public static final String AC1_ELSE_IF = "else-if";
	public static final String AC1_WHILE = "while";
	public static final String AC1_WHILE_OP = "while-op";
	public static final String AC1_WHILE_FWD = "while-fwd";
	public static final String AC1_DO = "do";
	public static final String AC1_DO_FWD = "do-fwd";
	public static final String AC1_LIST = "list";
	public static final String AC1_MAP = "map";
	public static final String AC1_SET = "set";
	
	public static final String AC1_MODEL = "model";
	public static final String AC1_DAO = "dao";
	public static final String AC1_SERVICE = "service";
	
	public static final String AC2_OPEN = "open";
	public static final String AC2_CLOSE = "close";
	public static final String AC2_CONSOLE = "console";
	public static final String AC2_CONDITION = "condition";
	
	
//	public static final String AC1_DAO_UPDATE = "update";
//	public static final String AC1_DAO_INSERT = "insert";
	
	// table update operation 
	public static final String AC1_UPDATE_TABLE = "update-table";
	public static final String AC2_HIBERNATE = null;
	public static final String AC2_JDBC_TEMPLATE = "jdbc-template";
	public static final String AC2_SQL_STATEMENT = "sql-stmt";
	
	// TABLE INSERT OPERATION
	public static final String AC1_INSERT_TABLE = "insert-table";
	
	// string replace operations for files and variable
	public static final String AC1_STRING_REPLACE = "string-replace";
	public static final String AC2_FILE_STRING_REPLACE = "file";
	public static final String AC2_VARIABLE_STRING_REPLACE = "variable";
	
	// file & variable string append
	public static final String AC1_STRING_APPEND = "string-append";
	public static final String AC2_FILE_STRING_APPEND = "file";
	public static final String AC2_VARIABLE_STRING_APPEND = "variable";
	
}
