package com.realnet.codeextractor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.realnet.codeextractor.entity.Rn_Bcf_Rules;
import com.realnet.codeextractor.repository.Rn_Bcf_Rule_Library_Repository;
import com.realnet.exceptions.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Rn_Bcf_Rule_Library_ServiceImpl implements Rn_Bcf_Rule_Library_Service {

	@Autowired
	private Rn_Bcf_Rule_Library_Repository ruleLibraryRepository;

	@Override
	public List<Rn_Bcf_Rules> getAll() {
		return ruleLibraryRepository.findAll();
	}

	@Override
	public Page<Rn_Bcf_Rules> getAll(Pageable page) {
		return ruleLibraryRepository.findAll(page);
	}

	@Override
	public Rn_Bcf_Rules getById(int id) {
		Rn_Bcf_Rules bcf_extractor = ruleLibraryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Rule Not Found :: " + id));
		return bcf_extractor;
	}

	@Override
	public Rn_Bcf_Rules save(Rn_Bcf_Rules bcf_extractor) {
		Rn_Bcf_Rules savedExtractor = ruleLibraryRepository.save(bcf_extractor);
		return savedExtractor;
	}

	@Override
	public Rn_Bcf_Rules updateById(int id, Rn_Bcf_Rules ruleRequest) {
		Rn_Bcf_Rules old_extractor = ruleLibraryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Rule not found :: " + id));

		old_extractor.setGroup_id(ruleRequest.getGroup_id());
		old_extractor.setRule_name(ruleRequest.getRule_name());
		old_extractor.setTech_stack(ruleRequest.getTech_stack());
		old_extractor.setObject_type(ruleRequest.getObject_type());
		old_extractor.setSub_object_type(ruleRequest.getSub_object_type());
		old_extractor.setFile_code(ruleRequest.getFile_code());
		old_extractor.setRule_type(ruleRequest.getRule_type());
		old_extractor.setIdentifier_start_string(ruleRequest.getIdentifier_start_string());
		old_extractor.setIdentifier_end_string(ruleRequest.getIdentifier_end_string());
		old_extractor.setReplacement_string(ruleRequest.getReplacement_string());

		final Rn_Bcf_Rules updated_rule = ruleLibraryRepository.save(old_extractor);
		return updated_rule;
	}

	@Override
	public boolean deleteById(int id) {
		if (!ruleLibraryRepository.existsById(id)) {
			throw new ResourceNotFoundException("Rule not exist");
		}
		Rn_Bcf_Rules rule = ruleLibraryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Rule not found :: " + id));
		ruleLibraryRepository.delete(rule);
		return true;
	}

//	@Override
//	public String stringReplace(String str, String start, String end, String replaceWith, String file_type) {
//		int i = str.indexOf(start);
//		while (i != -1) {
//			int j = str.indexOf(end, i + 1);
//			if (j != -1) {
//				/* @Include starting and ending string
//				 * String data = str.substring(0, i + start.length()) + "\n" + replaceWith + "\n";
//				 * String temp = str.substring(j);
//				 * 
//				 * @Not Include starting and ending string
//				 * String data = str.substring(0, i) + "\n" + replaceWith + "\n";
//				 * String temp = str.substring(j + end.length());
//				 * */
//				String data = str.substring(0, i) + "\n" + replaceWith + "\n";
//				String temp = str.substring(j + end.length());
//				data += temp;
//				str = data;
//				i = str.indexOf(start, i + replaceWith.length() + end.length() + 1);
//			} else {
//				break;
//			}
//		}
//
////		if (replaced) {
////			String newStart = "";
////			String newEnd = "";
////			if(file_type.equals("html") || file_type.equals("jsp")) {
////				newStart = "<!-- bcf-fieldloop-startshere-processed -->";
////				newEnd = "<!-- bcf-fieldloop-endshere-processed -->";
////				str = str.replace(start, newStart);
////				str = str.replace(end, newEnd);
////			}
////			if(file_type.equals("java") || file_type.equals("ts") || file_type.equals("js")) {
////				newStart = "/* bcf-fieldloop-startshere-processed */";
////				newEnd = "/* bcf-fieldloop-endshere-processed */";
////				str = str.replace(start, newStart);
////				str = str.replace(end, newEnd);
////			}
////		}
//		return str;
//	}

	public List<Rn_Bcf_Rules> copyRules(String to_tech_stack, String to_object_type, String to_sub_object_type,String from_tech_stack,String from_object_type, String from_sub_object_type){
		List<Rn_Bcf_Rules> rules = ruleLibraryRepository.copyRules(to_tech_stack, to_object_type, to_sub_object_type, from_tech_stack, from_object_type, from_sub_object_type);
		log.debug("Nil Copied Rules: {}", rules);
		return ruleLibraryRepository.copyRules(to_tech_stack, to_object_type, to_sub_object_type, from_tech_stack, from_object_type, from_sub_object_type);
	}

	@Override
	public List<Rn_Bcf_Rules> copyRules2(String from_tech_stack, String from_object_type, String from_sub_object_type) {
		return ruleLibraryRepository.copyRules2(from_tech_stack, from_object_type, from_sub_object_type);
	}

}
