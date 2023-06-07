"package com.realnet.model;" + "\r\n" + 
"" + "\r\n" + 
"import javax.persistence.GeneratedValue;" + "\r\n" + 
"import javax.persistence.GenerationType;" + "\r\n" + 
"import javax.persistence.Id;" + "\r\n" + 
"" + "\r\n" + 
"public class TestModel {" + "\r\n" + 
"" + "\r\n" + 
"	@Id" + "\r\n" + 
"	@GeneratedValue(strategy = GenerationType.IDENTITY)" + "\r\n" + 
"	private int id;" + "\r\n" + 
"	private String name;" + "\r\n" + 
"	private String email;" + "\r\n" + 
"	private String mob_no;" + "\r\n" + 
"}" 