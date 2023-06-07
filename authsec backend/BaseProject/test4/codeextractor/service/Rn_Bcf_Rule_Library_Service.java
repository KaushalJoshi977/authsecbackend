package com.realnet.codeextractor.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.realnet.codeextractor.entity.Rn_Bcf_Rules;

public interface Rn_Bcf_Rule_Library_Service {
	List<Rn_Bcf_Rules> getAll();

	Page<Rn_Bcf_Rules> getAll(Pageable p);

	Rn_Bcf_Rules getById(int id);

	Rn_Bcf_Rules save(Rn_Bcf_Rules bcf_rule);

	Rn_Bcf_Rules updateById(int id, Rn_Bcf_Rules bcf_rule);

	boolean deleteById(int id);

	List<Rn_Bcf_Rules> copyRules(String to_tech_stack, String to_object_type, String to_sub_object_type,
			String from_tech_stack, String from_object_type, String from_sub_object_type);
	
	List<Rn_Bcf_Rules> copyRules2(String from_tech_stack, String from_object_type, String from_sub_object_type);
	
	// String stringReplace(String str, String start, String end, String
	// replaceWith, String file_type);

}
