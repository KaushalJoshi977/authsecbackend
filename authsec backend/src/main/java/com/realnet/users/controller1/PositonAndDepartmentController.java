package com.realnet.users.controller1;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.users.entity1.AppUserDepartment;
import com.realnet.users.entity1.AppUserPosition;
import com.realnet.users.service1.PositionAndDepartmentServiceImpl;

@RestController
@RequestMapping(value = "/api")
public class PositonAndDepartmentController {
	private PositionAndDepartmentServiceImpl positionAndDepartmentServiceImpl;
	@Autowired
	public PositonAndDepartmentController(PositionAndDepartmentServiceImpl positionAndDepartmentServiceImpl) {
		super();
		this.positionAndDepartmentServiceImpl = positionAndDepartmentServiceImpl;
	}
	@GetMapping("/getAllDepartments")
	public ResponseEntity<?> getAll(){
		List<AppUserDepartment> l = positionAndDepartmentServiceImpl.getAll();
		return new ResponseEntity<>(l,HttpStatus.OK);
	}
	@GetMapping("/getDepartment/{id}")
	public ResponseEntity<?> getOne(@PathVariable("id") String id){
		Optional<AppUserDepartment> o = positionAndDepartmentServiceImpl.getOne(id);
		if(o.get()!=null) {
			return new ResponseEntity<>(o.get(),HttpStatus.OK);
		}
		return new ResponseEntity<>("Department not found",HttpStatus.BAD_REQUEST);
	}
	@GetMapping("/getAllPositions")
	public ResponseEntity<?> getAllPosition(){
		List<AppUserPosition> l = positionAndDepartmentServiceImpl.getAllPosition();
		return new ResponseEntity<>(l,HttpStatus.OK);
	}
	@GetMapping("/getPosition/{id}")
	public ResponseEntity<?> getOnePosition(@PathVariable("id") String id){
		Optional<AppUserPosition> o = positionAndDepartmentServiceImpl.getOnePosition(id);
		if(o.get()!=null) {
			return new ResponseEntity<>(o.get(),HttpStatus.OK);
		}
		return new ResponseEntity<>("Position not found",HttpStatus.BAD_REQUEST);
	}
}
