package com.realnet.Abc_1665647440047_back.Services;
import com.realnet.Abc_1665647440047_back.Repository.AbcRepository;
import com.realnet.Abc_1665647440047_back.Entity.Abc;import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;

@Service
 public class AbcService {
@Autowired
private AbcRepository Repository;
public Abc Savedata(Abc data) {
				return Repository.save(data);	
			}

			
public List<Abc> getdetails() {
				return (List<Abc>) Repository.findAll();
			}


public Abc getdetailsbyId(Long id) {
	return Repository.findById(id).get();
			}


	public void delete_by_id(Long id) {
 Repository.deleteById(id);
}


public Abc update(Abc data,Long id) {
	Abc old = Repository.findById(id).get();
old.setName(data.getName());
old.setName_id(data.getName_id());
final Abc test = Repository.save(old);
		return test;}}