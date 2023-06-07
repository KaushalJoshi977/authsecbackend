"package com.realnet.Abc_1665647440047_back.Controllers;" + "\r\n" + 
"import java.util.List;" + "\r\n" + 
"import org.springframework.beans.factory.annotation.Autowired;" + "\r\n" + 
" import org.springframework.web.bind.annotation.*;" + "\r\n" + 
"import com.realnet.Abc_1665647440047_back.Entity.Abc;" + "\r\n" + 
"import com.realnet.Abc_1665647440047_back.Services.AbcService ;" + "\r\n" + 
"@RequestMapping(value = \"/_1665647440047_back\")" + "\r\n" + 
"@RestController" + "\r\n" + 
"public class AbcController {" + "\r\n" + 
"	" + "\r\n" + 
"	@Autowired" + "\r\n" + 
"	private AbcService Service;" + "\r\n" + 
"" + "\r\n" + 
"	@PostMapping(\"/Abc\")" + "\r\n" + 
"	" + "\r\n" + 
"	  public Abc Savedata(@RequestBody Abc data) {" + "\r\n" + 
"		Abc save = Service.Savedata(data)	;" + "\r\n" + 
"		 return save;" + "\r\n" + 
"	  }" + "\r\n" + 
"		 " + "\r\n" + 
"	" + "\r\n" + 
"	@GetMapping(\"/Abc\")" + "\r\n" + 
"	public List<Abc> getdetails() {" + "\r\n" + 
"		 List<Abc> get = Service.getdetails();		" + "\r\n" + 
"		return get;" + "\r\n" + 
"}" + "\r\n" + 
"@GetMapping(\"/Abc/{id}\")" + "\r\n" + 
"	public  Abc  getdetailsbyId(@PathVariable Long id ) {" + "\r\n" + 
"		Abc  get = Service.getdetailsbyId(id);" + "\r\n" + 
"		return get;" + "\r\n" + 
"	}" + "\r\n" + 
"@DeleteMapping(\"/Abc/{id}\")" + "\r\n" + 
"	public  void delete_by_id(@PathVariable Long id ) {" + "\r\n" + 
"	Service.delete_by_id(id);" + "\r\n" + 
"		" + "\r\n" + 
"	}" + "\r\n" + 
"@PutMapping(\"/Abc/{id}\")" + "\r\n" + 
"	public  Abc update(@RequestBody Abc data,@PathVariable Long id ) {" + "\r\n" + 
"		Abc update = Service.update(data,id);" + "\r\n" + 
"		return update;" + "\r\n" + 
"	}" + "\r\n" + 
"}" 