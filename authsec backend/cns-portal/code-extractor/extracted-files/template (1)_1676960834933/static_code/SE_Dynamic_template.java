"package com.realnet.template.entity;" + "\r\n" + 
"" + "\r\n" + 
"import javax.persistence.Entity;" + "\r\n" + 
"import javax.persistence.GeneratedValue;" + "\r\n" + 
"import javax.persistence.GenerationType;" + "\r\n" + 
"import javax.persistence.Id;" + "\r\n" + 
"" + "\r\n" + 
"import lombok.AllArgsConstructor;" + "\r\n" + 
"import lombok.Data;" + "\r\n" + 
"import lombok.NoArgsConstructor;" + "\r\n" + 
"" + "\r\n" + 
"@Data" + "\r\n" + 
"@Entity" + "\r\n" + 
"@AllArgsConstructor" + "\r\n" + 
"@NoArgsConstructor" + "\r\n" + 
"public class Dynamic_template {" + "\r\n" + 
"" + "\r\n" + 
"	@Id" + "\r\n" + 
"	@GeneratedValue(strategy = GenerationType.IDENTITY)" + "\r\n" + 
"	private Long id;" + "\r\n" + 
"	" + "\r\n" + 
"	private String user_id;" + "\r\n" + 
"	private String file_name;" + "\r\n" + 
"	private String entity_name;" + "\r\n" + 
"}" 