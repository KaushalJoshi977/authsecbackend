package com.realnet.fnd.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.realnet.fnd.entity.Rn_Function_Register;

public interface Rn_Function_Register_Service {
	List<Rn_Function_Register> getAll();
	Page<Rn_Function_Register> getAll(Pageable p);
	Rn_Function_Register getById(int id);
	Rn_Function_Register save(Rn_Function_Register rn_function_register);
	Rn_Function_Register updateById(int id, Rn_Function_Register rn_function_register);
	boolean deleteById(int id);
	
	List<Rn_Function_Register> getByMenuId(int menu_id);
	

}
