"package com.realnet."+repo_name+".Entity;" + "\r\n" + 
" import lombok.*;" + "\r\n" + 
" import javax.persistence.*;" + "\r\n" + 
" import java.time.LocalDateTime;" + "\r\n" + 
" import java.util.*;" + "\r\n" + 
"" + "\r\n" + 
" @Entity " + "\r\n" + 
" @Data" + "\r\n" + 
" public class    "+table_name+"{ " + "\r\n" + 
"" + "\r\n" + 
" "+  for (Entry<String, String> e : entrySet) {

			String string = e.getKey().toLowerCase();
			String datatype = e.getValue().toString();
			String lowerCase = string.replaceAll(" ", "_").toLowerCase();
			String add = "n private " + datatype + " " + lowerCase + ";";
			"+table_name+"1Code.append(add);
		}
		"+table_name+"1Code.append("n}nr"); +"" + "\r\n" + 
" @GeneratedValue(strategy = GenerationType.IDENTITY)" + "\r\n" + 
" private Long id;" + "\r\n" + 
"" + "\r\n" + 
" private String name;" + "\r\n" + 
" private int name_id;" + "\r\n" + 
"" + "\r\n" + 
" }" 