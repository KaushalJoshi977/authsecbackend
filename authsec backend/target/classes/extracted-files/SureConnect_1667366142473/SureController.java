package com.realnet.SureConnect.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.realnet.SureConnect.Entities.Sure_Connect;
import com.realnet.SureConnect.Service.SureService;

@RestController
public class SureController {
	
	@Autowired
	private SureService sureService;
	
// CREATE DATA
	@PostMapping("/Sure_Connect")
	public ResponseEntity<?> add(@RequestBody Sure_Connect sure_Connect){
			Sure_Connect order= sureService.create(sure_Connect);
			
		return new ResponseEntity<>(order, HttpStatus.OK);
		
	}
	
//	update data with file
	@PutMapping("/Sure_Connect/{id}")
	public ResponseEntity<?> update(@RequestBody Sure_Connect sure_Connect, Integer id){
			
		Sure_Connect order = sureService.update(sure_Connect, id);

	
		return new ResponseEntity<>(order, HttpStatus.OK);
	}
	

	
	
//	Get all
	@GetMapping("/Sure_Connect")
	public ResponseEntity<?> getall(){
		List<Sure_Connect> pm = sureService.getall();
	return 	new ResponseEntity<>(pm, HttpStatus.OK);
	}
	

//	get by id
	@GetMapping("/Sure_Connect/{id}")
	public ResponseEntity<?> getbyid(@PathVariable int id){
		Sure_Connect pm = sureService.getbyid(id);

		return 	new ResponseEntity<>(pm, HttpStatus.OK);
	}
	
	
	
//	delete by id
	@DeleteMapping("/Sure_Connect/{id}")
	public ResponseEntity<?> deleteOne(@PathVariable int id){

		sureService.deletebyid(id);		
		return new ResponseEntity<>(HttpStatus.OK);
		}
	
//	get by name
	@GetMapping("/token/Sure_Connectbyname/{connection_name}")
	public ResponseEntity<?> getbyname(@PathVariable String connection_name){
		Sure_Connect pm = sureService.getbyname(connection_name);

		return 	new ResponseEntity<>(pm, HttpStatus.OK);
	}



}
