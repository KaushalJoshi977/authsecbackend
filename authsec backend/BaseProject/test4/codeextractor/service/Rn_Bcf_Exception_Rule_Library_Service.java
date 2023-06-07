package com.realnet.codeextractor.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.realnet.codeextractor.entity.Rn_Bcf_Exception_Rules;

public interface Rn_Bcf_Exception_Rule_Library_Service {
	List<Rn_Bcf_Exception_Rules> getAll();
	Page<Rn_Bcf_Exception_Rules> getAll(Pageable p);
	Rn_Bcf_Exception_Rules getById(int id);
	Rn_Bcf_Exception_Rules save(Rn_Bcf_Exception_Rules bcf_rule);
	Rn_Bcf_Exception_Rules updateById(int id, Rn_Bcf_Exception_Rules bcf_rule);
	boolean deleteById(int id);
	
	List<Rn_Bcf_Exception_Rules> getByType(String tech_stack, String object_type, String sub_object_type);

}
