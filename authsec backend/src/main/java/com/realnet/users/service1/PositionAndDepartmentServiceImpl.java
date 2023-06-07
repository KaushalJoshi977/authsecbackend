package com.realnet.users.service1;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realnet.users.entity1.AppUserDepartment;
import com.realnet.users.entity1.AppUserPosition;
import com.realnet.users.repository1.AppUserDepartmentRepository;
import com.realnet.users.repository1.AppUserPositionRepository;

@Service
public class PositionAndDepartmentServiceImpl {
	private AppUserDepartmentRepository appUserDepartmentRepository;
	private AppUserPositionRepository appUserPositionRepository;
	@Autowired
	public PositionAndDepartmentServiceImpl(AppUserDepartmentRepository appUserDepartmentRepository,
			AppUserPositionRepository appUserPositionRepository) {
		super();
		this.appUserDepartmentRepository = appUserDepartmentRepository;
		this.appUserPositionRepository = appUserPositionRepository;
	}
	public List<AppUserDepartment> getAll(){
		return appUserDepartmentRepository.findAll();
	}
	public List<AppUserPosition> getAllPosition(){
		return appUserPositionRepository.findAll();
	}
	public Optional<AppUserDepartment> getOne(String departmentCode){
		return appUserDepartmentRepository.findById(departmentCode);
	}
	public Optional<AppUserPosition> getOnePosition(String positionCode){
		return appUserPositionRepository.findById(positionCode);
	}
}
