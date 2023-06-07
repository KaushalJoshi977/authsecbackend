package com.realnet.fnd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.fnd.entity.Rn_Menu_Group_Header;
import com.realnet.fnd.entity.Rn_Menu_Group_Line;
import com.realnet.fnd.service.RnGroupMenuLineServiec;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/lines_m", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { "Rn_Menu_Group" })
@CrossOrigin("*")
public class Rn_Menu_Group_Line_Controller {
	
	@Autowired
	private RnGroupMenuLineServiec rnGroupMenuLineServiec;
	
	@PostMapping("/create")
	public ResponseEntity<?> add(@RequestBody Rn_Menu_Group_Line rn_Menu_Group_Line){
		Rn_Menu_Group_Line addToDb = this.rnGroupMenuLineServiec.addToDb(rn_Menu_Group_Line);
		return ResponseEntity.ok(addToDb);
	}
	
	@GetMapping("/get-one/{id}")
	public ResponseEntity<?> getone(@PathVariable("id") int id){
		Rn_Menu_Group_Line getoneById = this.rnGroupMenuLineServiec.getoneById(id);
		return ResponseEntity.ok(getoneById);
	}
	
	@GetMapping("/get-all")
	public List<?> getAll(){
		List<Rn_Menu_Group_Line> all = this.rnGroupMenuLineServiec.getAll();
		return all;
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody Rn_Menu_Group_Line rn_Menu_Group_Line){
		Rn_Menu_Group_Line addToDb = this.rnGroupMenuLineServiec.updateToDb(rn_Menu_Group_Line);
		return ResponseEntity.ok(addToDb);
	}
	
//	@GetMapping("/header/get-lines/{id}")
//	public List<Rn_Menu_Group_Line> getLines(@PathVariable("id") int id){
//		Rn_Menu_Group_Header header = new Rn_Menu_Group_Header();	
//		header.setId(id);
//		List<Rn_Menu_Group_Line> linesByHeader = this.rnGroupMenuLineServiec.getLinesByHeader(header);
//		return linesByHeader;
//	}
	
}
