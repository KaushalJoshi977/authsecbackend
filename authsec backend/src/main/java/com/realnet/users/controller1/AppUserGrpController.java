package com.realnet.users.controller1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.users.entity1.AppUserRole;
import com.realnet.users.repository1.AppUserRoleRepository;
import com.realnet.users.service1.AppUserRoleServiceImpl;

@RestController
@RequestMapping(value = "/api")
public class AppUserGrpController {
	private AppUserRoleServiceImpl appUserRoleServiceImpl;
	
	@Autowired
	private AppUserRoleRepository appUserRoleRepository;


	@Autowired
	public AppUserGrpController(AppUserRoleServiceImpl appUserRoleServiceImpl) {
		super();
		this.appUserRoleServiceImpl = appUserRoleServiceImpl;
	}

	@GetMapping("/getAllUsrGrp")
	public ResponseEntity<?> getAll() {
		DateFormat simple = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
		List<AppUserRole> l = appUserRoleServiceImpl.getAll();
		for (AppUserRole o : l) {
			if (o.getCreatedate() != null) {
				o.setCreateDateFormated(simple.format(o.getCreatedate()));
			}
			if (o.getUpdatedate() != null) {
				o.setUpdateDateFormated(simple.format(o.getUpdatedate()));
			}
		}
		return new ResponseEntity<>(l, HttpStatus.OK);
	}

	@GetMapping("/getOneUsrGrp/{id}")
	public ResponseEntity<?> getOne(@PathVariable("id") Long id) {
		DateFormat simple = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
		Optional<AppUserRole> r = appUserRoleServiceImpl.getOne(id);
		if (r.get() != null) {
			if (r.get().getCreatedate() != null) {
				r.get().setCreateDateFormated(simple.format(r.get().getCreatedate()));
			}
			if (r.get().getUpdatedate() != null) {
				r.get().setUpdateDateFormated(simple.format(r.get().getUpdatedate()));
			}
			return new ResponseEntity<>(r.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>("not found", HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/addOneUsrGrp")
	public ResponseEntity<?> addOne(@RequestBody AppUserRole appUserRole) {
		AppUserRole a = appUserRoleServiceImpl.addOne(appUserRole);
		return new ResponseEntity<>(a, HttpStatus.OK);
	}

	@PostMapping("/updateOneUsrGrp")
	public ResponseEntity<?> updateOne(@RequestBody AppUserRole appUserRole) {
		AppUserRole a = appUserRoleServiceImpl.addOne(appUserRole);
		return new ResponseEntity<>(a, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete_usrgrp/{id}")
	public ResponseEntity<?> deleteusrgroup(@PathVariable("id") Long id) {
		AppUserRole r = appUserRoleServiceImpl.getOne(id).get();
		appUserRoleRepository.delete(r);

		return new ResponseEntity<>("deleted", HttpStatus.OK);
	}
}
