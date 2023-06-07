package com.realnet.fnd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.realnet.exceptions.ResourceNotFoundException;
import com.realnet.fnd.entity.Rn_Main_Menu;
import com.realnet.fnd.repository.Rn_Main_Menu_Repository;

@Service
public class Rn_Main_Menu_ServiceImpl implements Rn_Main_Menu_Service {

	@Autowired
	private Rn_Main_Menu_Repository rn_menu_register_repository;

	@Override
	public List<Rn_Main_Menu> getAll() {
		return rn_menu_register_repository.findAll();
	}

	@Override
	public Page<Rn_Main_Menu> getAll(Pageable page) {
		return rn_menu_register_repository.findAll(page);
	}

	@Override
	public List<Rn_Main_Menu> getByAccountId(String account_id) {
		List<Rn_Main_Menu> rn_menu_register = rn_menu_register_repository.findByAccountId(account_id);
		if (rn_menu_register.isEmpty()) {
			throw new ResourceNotFoundException("Menu not found With Account id :: " + account_id);
		}
		return rn_menu_register;
	}

	@Override
	public Rn_Main_Menu getById(int id) {
		Rn_Main_Menu rn_menu_register = rn_menu_register_repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Menu not found :: " + id));
		return rn_menu_register;
	}

	@Override
	public Rn_Main_Menu save(Rn_Main_Menu rn_menu_register) {
		Rn_Main_Menu savedRn_Main_Menu = rn_menu_register_repository.save(rn_menu_register);
		return savedRn_Main_Menu;
	}

	@Override
	public Rn_Main_Menu updateById(int id, Rn_Main_Menu menuRequest) {
		Rn_Main_Menu old_menu = rn_menu_register_repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Menu not found :: " + id));

		old_menu.setMenu_name(menuRequest.getMenu_name());
		old_menu.setMenu_action_link(menuRequest.getMenu_action_link());
		old_menu.setMenu_icon(menuRequest.getMenu_icon());
		old_menu.setMenu_type(menuRequest.getMenu_type());
		// This is for line part // need to improve
		old_menu.setSub_menus(menuRequest.getSub_menus());
		old_menu.setUpdatedBy(menuRequest.getUpdatedBy());
		final Rn_Main_Menu updated_menu = rn_menu_register_repository.save(old_menu);
		return updated_menu;
	}

	@Override
	public boolean deleteById(int id) {
		if (!rn_menu_register_repository.existsById(id)) {
			throw new ResourceNotFoundException("Rn_Menu not exist");
		}
		Rn_Main_Menu rn_menu_register = rn_menu_register_repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Menu not found :: " + id));
		rn_menu_register_repository.delete(rn_menu_register);
		return true;
	}

}
