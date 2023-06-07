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
"public class TemplateFileUpload {" + "\r\n" + 
"	@Id" + "\r\n" + 
"	@GeneratedValue(strategy = GenerationType.IDENTITY)" + "\r\n" + 
"	private Long id;" + "\r\n" + 
"	" + "\r\n" + 
"	private Long user_id;" + "\r\n" + 
"	private String file_name;" + "\r\n" + 
"	private String file_changed_name;" + "\r\n" + 
"	private String file_location;" + "\r\n" + 
"	private String file_type;" + "\r\n" + 
"	private Integer status;" + "\r\n" + 
"}" 