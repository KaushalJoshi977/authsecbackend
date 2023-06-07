"package com.realnet." + module_name + ".model;" + "\r\n" + 
"" + "\r\n" + 
"import javax.persistence.GeneratedValue;" + "\r\n" + 
"import javax.persistence.GenerationType;" + "\r\n" + 
"import javax.persistence.Id;" + "\r\n" + 
"" + "\r\n" + 
"public class " + "+classname+" + "{"+
"" + "\r\n" + 
"	@Id" + "\r\n" + 
"	@GeneratedValue(strategy = GenerationType.IDENTITY)" + "\r\n" + 
"	for (int i = 0; i < entityname.size(); i++) { 			String string = entityname.get(i); 			String lowerCase = string.replaceAll(" ", "_").toLowerCase(); 			String add = "n private " + "String" + " " + lowerCase + ";"; 			entityclass.append(add); 		};  
for (int i = 0; i < entityname.size(); i++) {
			String string = entityname.get(i);
			String lowerCase = string.replaceAll(" ", "_").toLowerCase();
			String add = "\n private " + "String" + " " + lowerCase + ";";
			entityclass.append(add);
		} }" 
