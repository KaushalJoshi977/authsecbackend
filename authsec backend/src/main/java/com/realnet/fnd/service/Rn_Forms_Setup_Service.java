package com.realnet.fnd.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.realnet.fnd.entity.Rn_Forms_Setup;

public interface Rn_Forms_Setup_Service {
	List<Rn_Forms_Setup> getAll();

	Page<Rn_Forms_Setup> getAll(Pageable p);

	Rn_Forms_Setup getById(int id);

	Rn_Forms_Setup save(Rn_Forms_Setup rn_forms_setup);

	Rn_Forms_Setup updateById(int id, Rn_Forms_Setup rn_forms_setup);

	boolean deleteById(int id);
	
	List<Rn_Forms_Setup> getByFormId(int form_id);
	
	void buildDynamicForm(int form_id);
	
	String stringReplace(String str, String start, String end, String replaceWith);

}
