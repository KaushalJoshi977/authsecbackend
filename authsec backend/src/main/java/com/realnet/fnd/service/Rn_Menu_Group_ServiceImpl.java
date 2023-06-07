package com.realnet.fnd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.realnet.exceptions.ResourceNotFoundException;
import com.realnet.fnd.entity.Rn_Menu_Group_Header;
import com.realnet.fnd.repository.Rn_Menu_Group_Repository;

@Service
public class Rn_Menu_Group_ServiceImpl implements Rn_Menu_Group_Service {
	

	@Autowired
	private Rn_Menu_Group_Repository rn_menu_group_repository;

	@Override
	public List<Rn_Menu_Group_Header> getAll() {
		return rn_menu_group_repository.findAll();
	}

	@Override
	public Page<Rn_Menu_Group_Header> getAll(Pageable page) {
		return rn_menu_group_repository.findAll(page);
	}

	@Override
	public Rn_Menu_Group_Header getById(Long id) {
		Rn_Menu_Group_Header rn_forms_setup = rn_menu_group_repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Rn_Menu_Group not found :: " + id));
		return rn_forms_setup;
	}

	@Override
	public Rn_Menu_Group_Header save(Rn_Menu_Group_Header rn_menu_group_header) {
		Rn_Menu_Group_Header savedMenu_Group = rn_menu_group_repository.save(rn_menu_group_header);
		return savedMenu_Group;
	}

	@Override
	public Rn_Menu_Group_Header updateById(Long id, Rn_Menu_Group_Header menu_groupRequest) {
		Rn_Menu_Group_Header old_rn_menu_group = rn_menu_group_repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Rn_Menu_Group not found :: " + id));
		old_rn_menu_group.setMenu_name(menu_groupRequest.getMenu_name());
		old_rn_menu_group.setDescription(menu_groupRequest.getDescription());
//		old_rn_menu_group.setActive(menu_groupRequest.getActive());
		old_rn_menu_group.setStart_date(menu_groupRequest.getStart_date());
		old_rn_menu_group.setEnd_date(menu_groupRequest.getEnd_date());
		// line part
		old_rn_menu_group.setMenu_group_lines(menu_groupRequest.getMenu_group_lines());
		// updated by
		old_rn_menu_group.setUpdatedBy(menu_groupRequest.getUpdatedBy());
		final Rn_Menu_Group_Header updated_rn_forms_setup = rn_menu_group_repository.save(old_rn_menu_group);
		return updated_rn_forms_setup;
	}

	@Override
	public boolean deleteById(Long id) {
		Rn_Menu_Group_Header rn_forms_setup = rn_menu_group_repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Rn_Menu_Group_Header not found :: " + id));
		rn_menu_group_repository.delete(rn_forms_setup);
		return true;
	}

	// this is not done
//	@Override
//	public Rn_Main_Menu loadmenuGroupByUser(int menu_group_id) {
//		Rn_Menu_Group_Header menu_group_header = getById(menu_group_id);
//		List<Rn_Menu_Group_Line> menu_group_lines = menu_group_header.getMenu_group_lines();
//		for(Rn_Menu_Group_Line menu_group_line : menu_group_lines) {
//			int menu_id = menu_group_line.getMenu_id();
//			
//		}
//		return null;
//	}
	
	/*
	 * @Override public List<Rn_Menu_Group_Header> getByFormId(int form_id) { return
	 * rn_menu_group_repository.findByFormId(form_id); }
	 */

}
