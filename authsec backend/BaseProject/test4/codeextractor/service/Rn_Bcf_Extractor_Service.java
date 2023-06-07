package com.realnet.codeextractor.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.realnet.codeextractor.entity.Rn_Bcf_Extractor;

public interface Rn_Bcf_Extractor_Service {
	List<Rn_Bcf_Extractor> getAll();
	Page<Rn_Bcf_Extractor> getAll(Pageable p);
	Rn_Bcf_Extractor getById(int id);
	Rn_Bcf_Extractor save(Rn_Bcf_Extractor bcf_extractor);
	Rn_Bcf_Extractor updateById(int id, Rn_Bcf_Extractor bcf_extractor);
	boolean deleteById(int id);
	
	public void saveListOFiles(int headerId, String tech_stack, String obj_type, String sub_obj_type, String destDirectory)
			throws IOException;
	
	public void moveFiles(int id, String toDir) throws IOException;
	public void deleteEmptyDirectory(File dir) throws IOException;
	

}
