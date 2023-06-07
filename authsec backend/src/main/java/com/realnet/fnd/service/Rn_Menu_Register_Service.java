package com.realnet.fnd.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.realnet.fnd.entity.Rn_Menu_Register;

public interface Rn_Menu_Register_Service {
	List<Rn_Menu_Register> getAll();
	Page<Rn_Menu_Register> getAll(Pageable p);
	List<Rn_Menu_Register> getByAccountId(String account_id);
	Rn_Menu_Register getById(int id);
	Rn_Menu_Register save(Rn_Menu_Register rn_Menu_Register);
	Rn_Menu_Register updateById(int id, Rn_Menu_Register rn_Menu_Register);
	boolean deleteById(int id);

}
