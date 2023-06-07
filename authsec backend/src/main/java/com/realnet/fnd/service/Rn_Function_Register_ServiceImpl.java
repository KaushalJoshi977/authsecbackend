package com.realnet.fnd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.realnet.exceptions.ResourceNotFoundException;
import com.realnet.fnd.entity.Rn_Function_Register;
import com.realnet.fnd.repository.Rn_Function_Register_Repository;

@Service
public class Rn_Function_Register_ServiceImpl implements Rn_Function_Register_Service {

	@Autowired
	private Rn_Function_Register_Repository rn_function_register_repository;

	@Override
	public List<Rn_Function_Register> getAll() {
		return rn_function_register_repository.findAll();
	}

	@Override
	public Page<Rn_Function_Register> getAll(Pageable page) {
		return rn_function_register_repository.findAll(page);
	}

	@Override
	public Rn_Function_Register getById(int id) {
		Rn_Function_Register rn_function_register = rn_function_register_repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Menu not found :: " + id));
		return rn_function_register;
	}

	@Override
	public Rn_Function_Register save(Rn_Function_Register rn_function_register) {
		Rn_Function_Register savedRn_Function_Register = rn_function_register_repository.save(rn_function_register);
		return savedRn_Function_Register;
	}

	@Override
	public Rn_Function_Register updateById(int id, Rn_Function_Register functionRequest) {
		Rn_Function_Register old_function = rn_function_register_repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Menu not found :: " + id));

		old_function.setMenu_id(functionRequest.getMenu_id());
		old_function.setFunction_name(functionRequest.getFunction_name());
		old_function.setFunction_action_name(functionRequest.getFunction_action_name());
		old_function.setFunction_icon(functionRequest.getFunction_icon());
		old_function.setEnd_date(functionRequest.getEnd_date());
		old_function.setEnable_flag(functionRequest.getEnable_flag());
		//This is for line part
		// old_function.setStudents(functionRequest.getStudents()); // need to improve 
		old_function.setUpdatedBy(functionRequest.getUpdatedBy());
		final Rn_Function_Register updated_function = rn_function_register_repository.save(old_function);
		return updated_function;
	}

	@Override
	public boolean deleteById(int id) {
		if (!rn_function_register_repository.existsById(id)) {
			throw new ResourceNotFoundException("Rn_Menu not exist");
		}
		Rn_Function_Register rn_function_register = rn_function_register_repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Menu not found :: " + id));
		rn_function_register_repository.delete(rn_function_register);
		return true;
	}

	@Override
	public List<Rn_Function_Register> getByMenuId(int menu_id) {
//		List<Rn_Function_Register> functions = rn_function_register_repository.findByMenuId(menu_id);
//		if(functions.isEmpty()) {
//			throw new ResourceNotFoundException("Rn_Function_Register Is Empty");
//		}
		return rn_function_register_repository.findByMenuId(menu_id);
	}

}
