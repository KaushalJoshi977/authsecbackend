"package com.realnet."+repo_name+".Controllers;" + "\r\n" + 
"import java.util.List;" + "\r\n" + 
"import org.springframework.beans.factory.annotation.Autowired;" + "\r\n" + 
" import org.springframework.web.bind.annotation.*;" + "\r\n" + 
"import com.realnet."+repo_name+".Entity."+table_name+";" + "\r\n" + 
"import com.realnet."+repo_name+".Services."+table_name+"Service ;" + "\r\n" + 
"@RequestMapping(value = \"/_1665647440047_back\")" + "\r\n" + 
"@RestController" + "\r\n" + 
"public class "+table_name+"Controller {" + "\r\n" + 
"	" + "\r\n" + 
"	@Autowired" + "\r\n" + 
"	private "+table_name+"Service Service;" + "\r\n" + 
"" + "\r\n" + 
"	@PostMapping(\"/"+table_name+"\")" + "\r\n" + 
"	" + "\r\n" + 
"	  public "+table_name+" Savedata(@RequestBody "+table_name+" data) {" + "\r\n" + 
"		"+table_name+" save = Service.Savedata(data)	;" + "\r\n" + 
"		 return save;" + "\r\n" + 
"	  }" + "\r\n" + 
"		 " + "\r\n" + 
"	" + "\r\n" + 
"	@GetMapping(\"/"+table_name+"\")" + "\r\n" + 
"	public List<"+table_name+"> getdetails() {" + "\r\n" + 
"		 List<"+table_name+"> get = Service.getdetails();		" + "\r\n" + 
"		return get;" + "\r\n" + 
"}" + "\r\n" + 
"@GetMapping(\"/"+table_name+"/{id}\")" + "\r\n" + 
"	public  "+table_name+"  getdetailsbyId(@PathVariable Long id ) {" + "\r\n" + 
"		"+table_name+"  get = Service.getdetailsbyId(id);" + "\r\n" + 
"		return get;" + "\r\n" + 
"	}" + "\r\n" + 
"@DeleteMapping(\"/"+table_name+"/{id}\")" + "\r\n" + 
"	public  void delete_by_id(@PathVariable Long id ) {" + "\r\n" + 
"	Service.delete_by_id(id);" + "\r\n" + 
"		" + "\r\n" + 
"	}" + "\r\n" + 
"@PutMapping(\"/"+table_name+"/{id}\")" + "\r\n" + 
"	public  "+table_name+" update(@RequestBody "+table_name+" data,@PathVariable Long id ) {" + "\r\n" + 
"		"+table_name+" update = Service.update(data,id);" + "\r\n" + 
"		return update;" + "\r\n" + 
"	}" + "\r\n" + 
"}" 