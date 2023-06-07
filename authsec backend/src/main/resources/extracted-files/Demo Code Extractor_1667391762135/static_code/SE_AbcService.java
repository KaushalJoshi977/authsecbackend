"package com.realnet.Abc_1665647440047_back.Services;" + "\r\n" + 
"import com.realnet.Abc_1665647440047_back.Repository.AbcRepository;" + "\r\n" + 
"import com.realnet.Abc_1665647440047_back.Entity.Abc;import java.util.List;" + "\r\n" + 
"" + "\r\n" + 
"import org.springframework.beans.factory.annotation.Autowired;" + "\r\n" + 
"	import org.springframework.stereotype.Service;" + "\r\n" + 
"" + "\r\n" + 
"@Service" + "\r\n" + 
" public class AbcService {" + "\r\n" + 
"@Autowired" + "\r\n" + 
"private AbcRepository Repository;" + "\r\n" + 
"public Abc Savedata(Abc data) {" + "\r\n" + 
"				return Repository.save(data);	" + "\r\n" + 
"			}" + "\r\n" + 
"" + "\r\n" + 
"			" + "\r\n" + 
"public List<Abc> getdetails() {" + "\r\n" + 
"				return (List<Abc>) Repository.findAll();" + "\r\n" + 
"			}" + "\r\n" + 
"" + "\r\n" + 
"" + "\r\n" + 
"public Abc getdetailsbyId(Long id) {" + "\r\n" + 
"	return Repository.findById(id).get();" + "\r\n" + 
"			}" + "\r\n" + 
"" + "\r\n" + 
"" + "\r\n" + 
"	public void delete_by_id(Long id) {" + "\r\n" + 
" Repository.deleteById(id);" + "\r\n" + 
"}" + "\r\n" + 
"" + "\r\n" + 
"" + "\r\n" + 
"public Abc update(Abc data,Long id) {" + "\r\n" + 
"	Abc old = Repository.findById(id).get();" + "\r\n" + 
"old.setName(data.getName());" + "\r\n" + 
"old.setName_id(data.getName_id());" + "\r\n" + 
"final Abc test = Repository.save(old);" + "\r\n" + 
"		return test;}}" 