package com.realnet.codeextractor.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.realnet.codeextractor.entity.ActiveTechStack_DTO;
import com.realnet.codeextractor.entity.Rn_Bcf_Technology_Stack1;

public interface Rn_Bcf_TechnologyStack_Service {
	List<Rn_Bcf_Technology_Stack1> getAll();
	Page<Rn_Bcf_Technology_Stack1> getAll(Pageable p);
	Rn_Bcf_Technology_Stack1 getById(int id);
	Rn_Bcf_Technology_Stack1 save(Rn_Bcf_Technology_Stack1 bcf_tech_stack);
	Rn_Bcf_Technology_Stack1 updateById(int id, Rn_Bcf_Technology_Stack1 bcf_tech_stack);
	boolean deleteById(int id);
	
	List<ActiveTechStack_DTO> getListOfActivateTechnology();

}
