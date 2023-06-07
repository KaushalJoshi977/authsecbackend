package com.realnet.fnd.service1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.realnet.fnd.entity1.GrpMenuAccess;
import com.realnet.fnd.repository1.GrpMenuAccessRepository;

@Service
public class GrpMenuAccessServiceImpl {
	private GrpMenuAccessRepository grpMenuAccessRepository;
	@Autowired
	public GrpMenuAccessServiceImpl(GrpMenuAccessRepository grpMenuAccessRepository) {
		super();
		this.grpMenuAccessRepository = grpMenuAccessRepository;
	}
	public List<GrpMenuAccess> getAll(Pageable page){
		List<GrpMenuAccess> l = grpMenuAccessRepository.findAll(page).getContent();
		return l;
	}
	
}
