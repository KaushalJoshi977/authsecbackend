"package com.realnet.model;" + "\r\n" + 
"" + "\r\n" + 
"import javax.persistence.GeneratedValue;" + "\r\n" + 
"import javax.persistence.GenerationType;" + "\r\n" + 
"import javax.persistence.Id;" + "\r\n" + 
"" + "\r\n" + 
"public class "+classname+" {" + "\r\n" + 
"" + "\r\n" + 
"	@Id" + "\r\n" + 
"	@GeneratedValue(strategy = GenerationType.IDENTITY)" + "\r\n" + 
"	private int id;  
for (int i = 0; i < entityname.size(); i++) {
			String string = entityname.get(i);
			String lowerCase = string.replaceAll(" ", "_").toLowerCase();
			String add = "\n private " + "String" + " " + lowerCase + ";";
			entityclass.append(add);
		} } } }" 