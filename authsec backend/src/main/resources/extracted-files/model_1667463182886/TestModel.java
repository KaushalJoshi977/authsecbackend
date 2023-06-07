package com Test_1 ;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Test {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;  
for (int i = 0; i < entityname.size(); i++) {
			String string = entityname.get(i);
			String lowerCase = string.replaceAll(" ", "_").toLowerCase();
			String add = "\n private " + "String" + " " + lowerCase + ";";
			entityclass.append(add);
		} }
