package com.realnet.Abc_1665647440047_back.Controllers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.*;
import com.realnet.Abc_1665647440047_back.Entity.Abc;
import com.realnet.Abc_1665647440047_back.Services.AbcService ;
@RequestMapping(value = "/_1665647440047_back")
@RestController
public class AbcController {
	
	@Autowired
	private AbcService Service;

	@PostMapping("/Abc")
	
	  public Abc Savedata(@RequestBody Abc data) {
		Abc save = Service.Savedata(data)	;
		 return save;
	  }
		 
	
	@GetMapping("/Abc")
	public List<Abc> getdetails() {
		 List<Abc> get = Service.getdetails();		
		return get;
}
@GetMapping("/Abc/{id}")
	public  Abc  getdetailsbyId(@PathVariable Long id ) {
		Abc  get = Service.getdetailsbyId(id);
		return get;
	}
@DeleteMapping("/Abc/{id}")
	public  void delete_by_id(@PathVariable Long id ) {
	Service.delete_by_id(id);
		
	}
@PutMapping("/Abc/{id}")
	public  Abc update(@RequestBody Abc data,@PathVariable Long id ) {
		Abc update = Service.update(data,id);
		return update;
	}
}