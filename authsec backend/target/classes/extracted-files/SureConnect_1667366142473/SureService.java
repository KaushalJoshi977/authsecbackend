package com.realnet.SureConnect.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realnet.SureConnect.Entities.Sure_Connect;
import com.realnet.SureConnect.Repository.SureRepository;
import com.realnet.exceptions.ResourceNotFoundException;

@Service
public class SureService {
	
	@Autowired
	private SureRepository sureRepository;
	
	public Sure_Connect create(Sure_Connect sure_Connect) {
		return sureRepository.save(sure_Connect);
	}

	
	public List<Sure_Connect> getall() {
		return (List<Sure_Connect>) sureRepository.findAll();
	}

	
	public Sure_Connect getbyid(int id) {
		return sureRepository.findById(id);
	}
	
	public Sure_Connect getbyname(String connection_name) {
		return sureRepository.findByConnection_name(connection_name);
	}


	public Sure_Connect update(Sure_Connect sure, int id) {
		Sure_Connect pm = sureRepository.findById(id);
//				.orElseThrow(()->new ResourceNotFoundException("not found"));
		
		pm.setAccess_token(sure.getAccess_token());
		pm.setClient_id(sure.getClient_id());
		pm.setConnection_name(sure.getConnection_name());
		pm.setDescription(sure.getDescription());
		pm.setPassword(sure.getPassword());
		pm.setType(sure.getType());
		pm.setUsername(sure.getUsername());
		return sureRepository.save(pm);
	}


	public void deletebyid(int id) {
		sureRepository.deleteById(id);
	}






}
