"package com.realnet.bugTracker.Entity;" + "\r\n" + 
"" + "\r\n" + 
"import javax.persistence.Entity;" + "\r\n" + 
"import javax.persistence.GeneratedValue;" + "\r\n" + 
"import javax.persistence.GenerationType;" + "\r\n" + 
"import javax.persistence.Id;" + "\r\n" + 
"import javax.persistence.Lob;" + "\r\n" + 
"import javax.persistence.Table;" + "\r\n" + 
"" + "\r\n" + 
"import com.realnet.incident.entity.WhooColomns;" + "\r\n" + 
"" + "\r\n" + 
"import lombok.Data;" + "\r\n" + 
"" + "\r\n" + 
"@Entity" + "\r\n" + 
"@Data" + "\r\n" + 
"" + "\r\n" + 
"public class BugTracker extends WhooColomns {" + "\r\n" + 
"	" + "\r\n" + 
"	@Id" + "\r\n" + 
"    @GeneratedValue(strategy = GenerationType.IDENTITY)" + "\r\n" + 
"    private Long id;" + "\r\n" + 
"" + "\r\n" + 
"    private String type;" + "\r\n" + 
"    private String key_no;" + "\r\n" + 
"    private String summary;" + "\r\n" + 
"    private String assignee;" + "\r\n" + 
"    private String reporter;" + "\r\n" + 
"    " + "\r\n" + 
"    private String select_project;" + "\r\n" + 
"    private String title;" + "\r\n" + 
"    private String select_status;" + "\r\n" + 
"    private String select_priority;" + "\r\n" + 
"    private String steps;" + "\r\n" + 
"    private String description;" + "\r\n" + 
"    " + "\r\n" + 
"    " + "\r\n" + 
"    private String fileName;" + "\r\n" + 
"    " + "\r\n" + 
"    private String fileType;" + "\r\n" + 
" " + "\r\n" + 
"    @Lob" + "\r\n" + 
"    private byte[] data;" + "\r\n" + 
"    " + "\r\n" + 
"    " + "\r\n" + 
"}" 