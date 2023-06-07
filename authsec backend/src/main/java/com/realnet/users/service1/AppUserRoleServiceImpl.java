package com.realnet.users.service1;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.realnet.users.entity1.AppUserRole;
import com.realnet.users.repository1.AppUserRoleRepository;

@Service
public class AppUserRoleServiceImpl {
	private AppUserRoleRepository appUserRoleRepository;
	private AppUserServiceImpl appUserServiceImpl;
	@Autowired
	public AppUserRoleServiceImpl(AppUserRoleRepository appUserRoleRepository,
			AppUserServiceImpl appUserServiceImpl) {
		super();
		this.appUserRoleRepository = appUserRoleRepository;
		this.appUserServiceImpl = appUserServiceImpl;
		
	}
	public List<AppUserRole> getAll(){
		
		return appUserRoleRepository.findAll(Sort.by(Sort.Direction.ASC, "usrGrp"));
	}
	public Optional<AppUserRole> getOne(Long id){
		return appUserRoleRepository.findById(id);
	}
	public AppUserRole addOne(AppUserRole appUserRole) {
//		String createdBy = appUserServiceImpl.getLoggedInUser().getUsername();
//		String updatedBy = createdBy;
//		appUserRole.setCreateby(createdBy);
//		appUserRole.setUpdateby(updatedBy);
		return appUserRoleRepository.save(appUserRole);
	}
	public AppUserRole updateOne(AppUserRole appUserRole) {
		String updatedBy = appUserServiceImpl.getLoggedInUser().getUsername();
		appUserRole.setUpdateby(updatedBy);
		return appUserRoleRepository.save(appUserRole);
	}
}
