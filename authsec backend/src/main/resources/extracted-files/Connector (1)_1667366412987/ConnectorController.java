package com.realnet.Connector.Controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.Connector.Entity.Connector;
import com.realnet.Connector.Repository.Connectorrepository;
import com.realnet.users.entity.Sys_Accounts;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserServiceImpl;

@RestController
@RequestMapping("/connector")
public class ConnectorController {

	@Autowired
	private Connectorrepository connectorrepository;
	@Autowired
	private AppUserServiceImpl userService;

	
	//ADD DATA
	@PostMapping("/Connectorjson")
	public ResponseEntity<?> save(@RequestBody Connector connector) {
		AppUser loggedInUser = userService.getLoggedInUser();
		 Long account_id = loggedInUser.getAccount().getAccount_id();
		 connector.setAccountId(account_id);
		Connector save = connectorrepository.save(connector);
		return new ResponseEntity<>(save, HttpStatus.OK);

	}

	//UPDATE BY ID
	@PutMapping("/Connectorjson/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Connector connector) {
		Connector con = connectorrepository.findById(id).orElseThrow(null);
		con.setName(connector.getName());
		con.setConnector_json(connector.getConnector_json());

		Connector save = connectorrepository.save(con);
		return new ResponseEntity<>(save, HttpStatus.OK);

	}

	//GET ALL
	@GetMapping("/Connectorjson")
	public ResponseEntity<?> getall() {
		List<Connector> save = connectorrepository.findAll();
		return new ResponseEntity<>(save, HttpStatus.OK);

	}

	//GET BY ID
	@GetMapping("/Connectorjson/{id}")
	public ResponseEntity<?> getbyid(@PathVariable Long id) {
		Connector con = connectorrepository.findById(id).orElseThrow(null);
		return new ResponseEntity<>(con, HttpStatus.OK);

	}

	//DELETE BY ID
	@DeleteMapping("/Connectorjson/{id}")
	public void delete(@PathVariable Long id){
		Connector con = connectorrepository.findById(id).orElseThrow(null);
		connectorrepository.delete(con);		
		
	}
}
