"package com.realnet.entityevents.Entity;" + "\r\n" + 
"" + "\r\n" + 
"import javax.persistence.Entity;" + "\r\n" + 
"import javax.persistence.GeneratedValue;" + "\r\n" + 
"import javax.persistence.GenerationType;" + "\r\n" + 
"import javax.persistence.Id;" + "\r\n" + 
"" + "\r\n" + 
"import lombok.Data;" + "\r\n" + 
"" + "\r\n" + 
"import com.realnet.fnd.entity.Rn_Who_AccId_Column;" + "\r\n" + 
"" + "\r\n" + 
"@Entity" + "\r\n" + 
"@Data" + "\r\n" + 
"public class Entity_events_t extends Rn_Who_AccId_Column{" + "\r\n" + 
"" + "\r\n" + 
"	@Id" + "\r\n" + 
"	@GeneratedValue(strategy = GenerationType.IDENTITY)" + "\r\n" + 
"	private Long id;" + "\r\n" + 
"" + "\r\n" + 
"	private String entity_name;" + "\r\n" + 
"" + "\r\n" + 
"	private String event_type;" + "\r\n" + 
"" + "\r\n" + 
"	private boolean is_active = true;" + "\r\n" + 
"}" 