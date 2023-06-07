package com.realnet.fnd.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.realnet.fnd.entity.Rn_Menu_Group_Header;

public interface Rn_Menu_Group_Service {
	List<Rn_Menu_Group_Header> getAll();

	Page<Rn_Menu_Group_Header> getAll(Pageable p);

	Rn_Menu_Group_Header getById(Long id);

	Rn_Menu_Group_Header save(Rn_Menu_Group_Header rn_menu_group_header);

	Rn_Menu_Group_Header updateById(Long id, Rn_Menu_Group_Header rn_menu_group_header);

	boolean deleteById(Long id);
	
	//List<Rn_Menu_Group_Header> getByFormId(int form_id);
	
	//Rn_Main_Menu loadmenuGroupByUser(int menu_group_id);
	
	

}
