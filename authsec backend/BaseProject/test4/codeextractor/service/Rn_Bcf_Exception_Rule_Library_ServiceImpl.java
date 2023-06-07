package com.realnet.codeextractor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.realnet.codeextractor.entity.Rn_Bcf_Exception_Rules;
import com.realnet.codeextractor.repository.Rn_Bcf_Exception_Rule_Library_Repository;
import com.realnet.exceptions.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Rn_Bcf_Exception_Rule_Library_ServiceImpl implements Rn_Bcf_Exception_Rule_Library_Service {

	@Autowired
	private Rn_Bcf_Exception_Rule_Library_Repository exceptionRuleLibraryRepository;

	@Override
	public List<Rn_Bcf_Exception_Rules> getAll() {
		return exceptionRuleLibraryRepository.findAll();
	}

	@Override
	public Page<Rn_Bcf_Exception_Rules> getAll(Pageable page) {
		return exceptionRuleLibraryRepository.findAll(page);
	}

	@Override
	public Rn_Bcf_Exception_Rules getById(int id) {
		Rn_Bcf_Exception_Rules bcf_extractor = exceptionRuleLibraryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Rule Not Found :: " + id));
		return bcf_extractor;
	}

	@Override
	public Rn_Bcf_Exception_Rules save(Rn_Bcf_Exception_Rules bcf_extractor) {
		Rn_Bcf_Exception_Rules savedExtractor = exceptionRuleLibraryRepository.save(bcf_extractor);
		return savedExtractor;
	}

	@Override
	public Rn_Bcf_Exception_Rules updateById(int id, Rn_Bcf_Exception_Rules ruleRequest) {
		Rn_Bcf_Exception_Rules old_extractor = exceptionRuleLibraryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Exception Rule not found :: " + id));

		old_extractor.setTech_stack(ruleRequest.getTech_stack());
		old_extractor.setObject_type(ruleRequest.getObject_type());
		old_extractor.setSub_object_type(ruleRequest.getSub_object_type());
		old_extractor.setObject_name_variable(ruleRequest.getObject_name_variable());
		old_extractor.setObject_name_dynamic_string(ruleRequest.getObject_name_dynamic_string());

		final Rn_Bcf_Exception_Rules updated_rule = exceptionRuleLibraryRepository.save(old_extractor);
		return updated_rule;
	}

	@Override
	public boolean deleteById(int id) {
		if (!exceptionRuleLibraryRepository.existsById(id)) {
			throw new ResourceNotFoundException("Rule not exist");
		}
		Rn_Bcf_Exception_Rules rule = exceptionRuleLibraryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Rule not found :: " + id));
		exceptionRuleLibraryRepository.delete(rule);
		return true;
	}

	@Override
	public List<Rn_Bcf_Exception_Rules> getByType(String tech_stack, String object_type, String sub_object_type) {
		return exceptionRuleLibraryRepository.findByType(tech_stack, object_type, sub_object_type);
	}

	
}
