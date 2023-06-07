package com.realnet.codeextractor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.realnet.codeextractor.entity.Rn_Bcf_Extractor_Params;
import com.realnet.codeextractor.repository.Rn_Bcf_Extractor_Params_Repository;
import com.realnet.exceptions.ResourceNotFoundException;
import com.realnet.users.service1.AppUserService;

@Service
public class Rn_Bcf_Extractor_Params_ServiceImpl implements Rn_Bcf_Extractor_Params_Service {

	@Autowired
	private AppUserService userService;

	@Autowired
	private Rn_Bcf_Extractor_Params_Repository rn_bcf_extractor_params_repository;

	@Override
	public List<Rn_Bcf_Extractor_Params> getAll() {
		return rn_bcf_extractor_params_repository.findAll();
	}

	@Override
	public Page<Rn_Bcf_Extractor_Params> getAll(Pageable page) {
		return rn_bcf_extractor_params_repository.findAll(page);
	}

	@Override
	public Rn_Bcf_Extractor_Params getById(int id) {
		Rn_Bcf_Extractor_Params bcf_extractor = rn_bcf_extractor_params_repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Extractor Param Data Not Found :: " + id));
		return bcf_extractor;
	}

	@Override
	public Rn_Bcf_Extractor_Params save(Rn_Bcf_Extractor_Params bcf_extractor) {
		Rn_Bcf_Extractor_Params savedExtractor = rn_bcf_extractor_params_repository.save(bcf_extractor);
		return savedExtractor;
	}

	@Override
	public Rn_Bcf_Extractor_Params updateById(int id, Rn_Bcf_Extractor_Params extractorParamsRequest) {
		// User loggedInUser = userService.getLoggedInUser();
		Rn_Bcf_Extractor_Params old_extractor = rn_bcf_extractor_params_repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Menu not found :: " + id));
		old_extractor.setTech_stack(extractorParamsRequest.getTech_stack());
		old_extractor.setObject_type(extractorParamsRequest.getObject_type());
		old_extractor.setSub_object_type(extractorParamsRequest.getSub_object_type());
		old_extractor.setFile_code(extractorParamsRequest.getFile_code());
		old_extractor.setName_string(extractorParamsRequest.getName_string());
		old_extractor.setAddress_string(extractorParamsRequest.getAddress_string());
		old_extractor.setMoved_address_string(extractorParamsRequest.getMoved_address_string());
		old_extractor.setReference_address_string(extractorParamsRequest.getReference_address_string());
		old_extractor.setDescription(extractorParamsRequest.getDescription());
		old_extractor.setFile_name_var(extractorParamsRequest.getFile_name_var());
		old_extractor.setFile_name_dynamic_string(extractorParamsRequest.getFile_name_dynamic_string());
		old_extractor.setIs_extraction_enabled(extractorParamsRequest.isIs_extraction_enabled());
		old_extractor.setIs_creation_enabled(extractorParamsRequest.isIs_creation_enabled());
		old_extractor.setTotal_project_path_dynamic_string(extractorParamsRequest.getTotal_project_path_dynamic_string());
		final Rn_Bcf_Extractor_Params updated_function = rn_bcf_extractor_params_repository.save(old_extractor);
		return updated_function;
	}

	@Override
	public boolean deleteById(int id) {
		if (!rn_bcf_extractor_params_repository.existsById(id)) {
			throw new ResourceNotFoundException("Extractor Data not exist");
		}
		Rn_Bcf_Extractor_Params bcf_extractor = rn_bcf_extractor_params_repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Menu not found :: " + id));
		rn_bcf_extractor_params_repository.delete(bcf_extractor);
		return true;
	}

	@Override
	public List<Rn_Bcf_Extractor_Params> getByHeaderId(int headerId) {
		return rn_bcf_extractor_params_repository.findByHeaderId(headerId);
	}

	@Override
	public List<Rn_Bcf_Extractor_Params> getByHeaderIdOrderByDate(int headerId) {
		return rn_bcf_extractor_params_repository.findByHeaderIdOrderByDate(headerId);
	}

}
