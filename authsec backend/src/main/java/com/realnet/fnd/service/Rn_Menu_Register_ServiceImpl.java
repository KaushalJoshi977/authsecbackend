package com.realnet.fnd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.realnet.exceptions.ResourceNotFoundException;
import com.realnet.fnd.entity.Rn_Menu_Register;
import com.realnet.fnd.repository.Rn_Menu_Register_Repository;

@Service
public class Rn_Menu_Register_ServiceImpl implements Rn_Menu_Register_Service {

	@Autowired
	private Rn_Menu_Register_Repository rn_menu_register_repository;

	@Override
	public List<Rn_Menu_Register> getAll() {
		return rn_menu_register_repository.findAll();
	}

	@Override
	public Page<Rn_Menu_Register> getAll(Pageable page) {
		return rn_menu_register_repository.findAll(page);
	}

	@Override
	public List<Rn_Menu_Register> getByAccountId(String account_id) {
		List<Rn_Menu_Register> rn_menu_register = rn_menu_register_repository.findByAccountId(account_id);
		if(rn_menu_register.isEmpty()) {
			throw new ResourceNotFoundException("Menus not found With Account id :: " + account_id);
		}
		return rn_menu_register;
	}
	
	@Override
	public Rn_Menu_Register getById(int id) {
		Rn_Menu_Register rn_menu_register = rn_menu_register_repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Menu not found :: " + id));
		return rn_menu_register;
	}

	@Override
	public Rn_Menu_Register save(Rn_Menu_Register rn_menu_register) {
		Rn_Menu_Register savedRn_Menu_Register = rn_menu_register_repository.save(rn_menu_register);
		return savedRn_Menu_Register;
	}

	@Override
	public Rn_Menu_Register updateById(int id, Rn_Menu_Register menuRequest) {
		Rn_Menu_Register old_menu = rn_menu_register_repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Menu not found :: " + id));

		old_menu.setMain_menu_name(menuRequest.getMain_menu_name());
		old_menu.setMain_menu_action_name(menuRequest.getMain_menu_action_name());
		old_menu.setMain_menu_icon(menuRequest.getMain_menu_icon());
		old_menu.setEnable_flag(menuRequest.getEnable_flag());
		old_menu.setEnd_date(menuRequest.getEnd_date());
		//This is for line part
		// old_menu.setStudents(menuRequest.getStudents()); // need to improve 
		old_menu.setUpdatedBy(menuRequest.getUpdatedBy());
		final Rn_Menu_Register updated_menu = rn_menu_register_repository.save(old_menu);
		return updated_menu;
	}

	@Override
	public boolean deleteById(int id) {
		if (!rn_menu_register_repository.existsById(id)) {
			throw new ResourceNotFoundException("Rn_Menu not exist");
		}
		Rn_Menu_Register rn_menu_register = rn_menu_register_repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Menu not found :: " + id));
		rn_menu_register_repository.delete(rn_menu_register);
		return true;
	}

	

}
