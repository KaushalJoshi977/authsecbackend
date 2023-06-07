"package com.realnet."+repo_name+".Services;" + "\r\n" + 
"import com.realnet."+repo_name+".Repository."+table_name+"Repository;" + "\r\n" + 
"import com.realnet."+repo_name+".Entity."+table_name+";import java.util.List;" + "\r\n" + 
"" + "\r\n" + 
"import org.springframework.beans.factory.annotation.Autowired;" + "\r\n" + 
"	import org.springframework.stereotype.Service;" + "\r\n" + 
"" + "\r\n" + 
"@Service" + "\r\n" + 
" public class "+table_name+"Service {" + "\r\n" + 
"@Autowired" + "\r\n" + 
"private "+table_name+"Repository Repository;" + "\r\n" + 
"public "+table_name+" Savedata("+table_name+" data) {" + "\r\n" + 
"				return Repository.save(data);	" + "\r\n" + 
"			}" + "\r\n" + 
"" + "\r\n" + 
"			" + "\r\n" + 
"public List<"+table_name+"> getdetails() {" + "\r\n" + 
"				return (List<"+table_name+">) Repository.findAll();" + "\r\n" + 
"			}" + "\r\n" + 
"" + "\r\n" + 
"" + "\r\n" + 
"public "+table_name+" getdetailsbyId(Long id) {" + "\r\n" + 
"	return Repository.findById(id).get();" + "\r\n" + 
"			}" + "\r\n" + 
"" + "\r\n" + 
"" + "\r\n" + 
"	public void delete_by_id(Long id) {" + "\r\n" + 
" Repository.deleteById(id);" + "\r\n" + 
"}" + "\r\n" + 
"" + "\r\n" + 
"" + "\r\n" + 
"public "+table_name+" update("+table_name+" data,Long id) {" + "\r\n" + 
"	"+table_name+" old = Repository.findById(id).get();" + "\r\n" + 
"old.setName(data.getName());" + "\r\n" + 
"old.setName_id(data.getName_id());" + "\r\n" + 
"final "+table_name+" test = Repository.save(old);" + "\r\n" + 
"		return test;}}" 