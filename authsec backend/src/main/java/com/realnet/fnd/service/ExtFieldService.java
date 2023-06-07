package com.realnet.fnd.service;

import java.util.List;

import com.realnet.fnd.entity.Rn_Ext_Fields;

public interface ExtFieldService {
	List<Rn_Ext_Fields> getAll();
	//Page<Rn_Ext_Fields> getAll(Pageable p);
	Rn_Ext_Fields getById(int id);
	Rn_Ext_Fields save(Rn_Ext_Fields rn_ext_fields);
	Rn_Ext_Fields updateById(int id, Rn_Ext_Fields rn_ext_fields);
	boolean deleteById(int id);
	
//	// LOOKUP FIELDS (ATTRIBUTE, FLEX)
//	List<String> getLookupValues();
//	
//	// TEXTFIELD, DROPDOWN ETC...
//	List<String> getDataTypeValues();
	
	// BUILD HTML FOR EXTENSION
	void buildExtensionByFormCode(String acc_id, String f_code);
	
	String stringReplace(String str, String start, String end, String replaceWith);

}
