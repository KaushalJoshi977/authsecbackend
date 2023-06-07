package com.realnet.fnd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.realnet.exceptions.ResourceNotFoundException;
import com.realnet.fnd.entity.Rn_Dynamic_Transaction;
import com.realnet.fnd.repository.Rn_DynamicTransactionRepository;

@Service
public class Rn_DynamicTransactionServiceImpl implements Rn_DynamicTransactionService {

	@Autowired
	private Rn_DynamicTransactionRepository rn_dynamic_transactionRepository;

	@Override
	public List<Rn_Dynamic_Transaction> getAll() {
		return rn_dynamic_transactionRepository.findAll();
	}

	@Override
	public Page<Rn_Dynamic_Transaction> getAll(Pageable page) {
		return rn_dynamic_transactionRepository.findAll(page);
	}

	@Override
	public List<Rn_Dynamic_Transaction> getByFormId(int form_id) {
		// Rn_Dynamic_Transaction rn_dynamic_transaction =
		// rn_dynamic_transactionRepository.findById(id)
		List<Rn_Dynamic_Transaction> rn_dynamic_transaction = rn_dynamic_transactionRepository.findByFormId(form_id);
		return rn_dynamic_transaction;
	}

	@Override
	public Rn_Dynamic_Transaction save(Rn_Dynamic_Transaction rn_dynamic_transaction) {
		Rn_Dynamic_Transaction savedRn_Dynamic_Transaction = rn_dynamic_transactionRepository
				.save(rn_dynamic_transaction);
		return savedRn_Dynamic_Transaction;
	}

	@Override
	public Rn_Dynamic_Transaction updateByFormId(int id, int form_id, Rn_Dynamic_Transaction txRequest) {
		Rn_Dynamic_Transaction rn_dynamic_transaction = rn_dynamic_transactionRepository.findByIdAndFormId(id, form_id)
				.orElseThrow(() -> new ResourceNotFoundException("Extension Field not found :: " + form_id));
		rn_dynamic_transaction.setUpdatedBy(txRequest.getUpdatedBy());
		// rn_dynamic_transaction.setForm_id(txRequest.getForm_id()); // modify needed
		rn_dynamic_transaction.setForm_version(txRequest.getForm_version());

		rn_dynamic_transaction.setComp1(txRequest.getComp1());
		rn_dynamic_transaction.setComp2(txRequest.getComp2());
		rn_dynamic_transaction.setComp3(txRequest.getComp3());
		rn_dynamic_transaction.setComp4(txRequest.getComp4());
		rn_dynamic_transaction.setComp5(txRequest.getComp5());
		rn_dynamic_transaction.setComp6(txRequest.getComp6());
		rn_dynamic_transaction.setComp7(txRequest.getComp7());
		rn_dynamic_transaction.setComp8(txRequest.getComp8());
		rn_dynamic_transaction.setComp9(txRequest.getComp9());
		rn_dynamic_transaction.setComp10(txRequest.getComp10());
		rn_dynamic_transaction.setComp11(txRequest.getComp11());
		rn_dynamic_transaction.setComp12(txRequest.getComp12());
		rn_dynamic_transaction.setComp13(txRequest.getComp13());
		rn_dynamic_transaction.setComp14(txRequest.getComp14());
		rn_dynamic_transaction.setComp15(txRequest.getComp15());
		rn_dynamic_transaction.setComp16(txRequest.getComp16());
		rn_dynamic_transaction.setComp17(txRequest.getComp17());
		rn_dynamic_transaction.setComp18(txRequest.getComp18());
		rn_dynamic_transaction.setComp19(txRequest.getComp19());
		rn_dynamic_transaction.setComp20(txRequest.getComp20());
		rn_dynamic_transaction.setComp21(txRequest.getComp21());
		rn_dynamic_transaction.setComp22(txRequest.getComp22());
		rn_dynamic_transaction.setComp23(txRequest.getComp23());
		rn_dynamic_transaction.setComp24(txRequest.getComp24());
		rn_dynamic_transaction.setComp25(txRequest.getComp25());
		rn_dynamic_transaction.setComp_l26(txRequest.getComp_l26());
		rn_dynamic_transaction.setComp_l27(txRequest.getComp_l27());
		rn_dynamic_transaction.setComp_l28(txRequest.getComp_l28());
		rn_dynamic_transaction.setComp_l29(txRequest.getComp_l29());
		rn_dynamic_transaction.setComp_l30(txRequest.getComp_l30());
		final Rn_Dynamic_Transaction updated_ext_field = rn_dynamic_transactionRepository.save(rn_dynamic_transaction);
		return updated_ext_field;
	}

	@Override
	public boolean deleteById(int id) {
		if (!rn_dynamic_transactionRepository.existsById(id)) {
			throw new ResourceNotFoundException("Data not Exist = " + id);
		}
		Rn_Dynamic_Transaction rn_dynamic_transaction = rn_dynamic_transactionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data not found :: " + id));
		rn_dynamic_transactionRepository.delete(rn_dynamic_transaction);
		return true;
	}

	@Override
	public Rn_Dynamic_Transaction getByIdAndFormId(int id, int form_id) {
		Rn_Dynamic_Transaction rn_dynamic_transaction = rn_dynamic_transactionRepository.findByIdAndFormId(id, form_id)
				.orElseThrow(() -> new ResourceNotFoundException("Data not found with id :: " + id + " and form_id ::" + form_id));
		return rn_dynamic_transaction;
	}

}
