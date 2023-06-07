package com.realnet.codeextractor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realnet.codeextractor.entity.Rule_library_keyword;
import com.realnet.codeextractor.repository.Rule_library_keywordRepository;


@Service
public class Rule_library_keywordService {
	
	@Autowired
	private Rule_library_keywordRepository Repository;

	public Rule_library_keyword Savedata(Rule_library_keyword data) {
		return Repository.save(data);
	}

	public List<Rule_library_keyword> getdetails() {
		return (List<Rule_library_keyword>) Repository.findAll();
	}

	public Rule_library_keyword getdetailsbyId(Integer id) {
		return Repository.findById(id).get();
	}

	public void delete_by_id(Integer id) {
		Repository.deleteById(id);
	}

	public Rule_library_keyword update(Rule_library_keyword data, Integer id) {
		Rule_library_keyword old = Repository.findById(id).get();
		old.setTech_stack(data.getTech_stack());
		old.setObject_type(data.getObject_type());
		old.setSub_object_type(data.getSub_object_type());
		old.setVersion(data.getVersion());
		old.setReplcement_string(data.getReplcement_string());
		final Rule_library_keyword  test = Repository.save(old);
		return test;
	}
}