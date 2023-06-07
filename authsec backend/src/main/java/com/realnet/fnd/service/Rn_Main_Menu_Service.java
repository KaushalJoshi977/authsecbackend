package com.realnet.fnd.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.realnet.fnd.entity.Rn_Main_Menu;

public interface Rn_Main_Menu_Service {
	List<Rn_Main_Menu> getAll();

	Page<Rn_Main_Menu> getAll(Pageable p);

	List<Rn_Main_Menu> getByAccountId(String account_id);

	Rn_Main_Menu getById(int id);

	Rn_Main_Menu save(Rn_Main_Menu rn_Main_Menu);

	Rn_Main_Menu updateById(int id, Rn_Main_Menu rn_main_menu);

	boolean deleteById(int id);

}
