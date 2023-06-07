package com.realnet.fnd.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.realnet.fnd.entity.Rn_Dynamic_Transaction;

public interface Rn_DynamicTransactionService {
	List<Rn_Dynamic_Transaction> getAll();
	
	Page<Rn_Dynamic_Transaction> getAll(Pageable p);
	
	List<Rn_Dynamic_Transaction> getByFormId(int form_id);

	Rn_Dynamic_Transaction getByIdAndFormId(int id, int form_id);
	
	
	Rn_Dynamic_Transaction save(Rn_Dynamic_Transaction rn_dynamic_transaction);
	
	Rn_Dynamic_Transaction updateByFormId(int id, int form_id, Rn_Dynamic_Transaction rn_dynamic_transaction);
	
	boolean deleteById(int id);

}
