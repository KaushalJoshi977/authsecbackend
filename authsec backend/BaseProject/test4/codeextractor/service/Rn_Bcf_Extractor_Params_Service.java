package com.realnet.codeextractor.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.realnet.codeextractor.entity.Rn_Bcf_Extractor_Params;

public interface Rn_Bcf_Extractor_Params_Service {
	List<Rn_Bcf_Extractor_Params> getAll();
	Page<Rn_Bcf_Extractor_Params> getAll(Pageable p);
	
	List<Rn_Bcf_Extractor_Params> getByHeaderId(int headerId);
	List<Rn_Bcf_Extractor_Params> getByHeaderIdOrderByDate(int headerId);
	
	
	Rn_Bcf_Extractor_Params getById(int id);
	Rn_Bcf_Extractor_Params save(Rn_Bcf_Extractor_Params bcf_extractor_params);
	Rn_Bcf_Extractor_Params updateById(int id, Rn_Bcf_Extractor_Params bcf_extractor_params);
	
	boolean deleteById(int id);

}
