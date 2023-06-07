//package com.realnet.users.service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.realnet.users.entity.CompanyDto;
//import com.realnet.users.entity.Sys_Accounts;
//import com.realnet.users.entity.User;
//import com.realnet.users.repository.CompanyRepo;
//import com.realnet.users.repository.RoleRepo;
//import com.realnet.users.repository.UserRepo;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Service
//public class CompanyServiceImpl implements CompanyService {
//
//	@Autowired
//	private UserRepo userRepo;
//
//	@Autowired
//	private RoleRepo roleRepo;
//
//	@Autowired
//	private CompanyRepo companyRepo;
//
//	public List<Sys_Accounts> getAll() {
//		return companyRepo.findAll();
//
//	}
//
//	@Override
//	public void delete(long id) {
//		companyRepo.deleteById(id);
//	}
//
//	@Override
//	public Sys_Accounts getById(Long id) {
//		return companyRepo.findById(id).get();
//	}
//
//	public boolean addNewCompany(Sys_Accounts company) {
//		boolean exists = companyRepo.existsById(company.getAccount_id());
//		if (exists) {
//			return false;
//		} else {
//			return this.insertOrSaveCompany(company);
//
//		}
//	}
//
////	public User resister(UserDto user) {
////		User newUser = new User();
////		newUser.setEmail(user.getEmail());
////		newUser.setFirstName(user.getFirstName());
////		newUser.setLastName(user.getLastName());
////		Role admin_role = roleRepo.findByName("ADMIN");
////		Role user_role = roleRepo.findByName("USER");
////		Role billing_role = roleRepo.findByName("BILLING");
////		Set<Role> roles = new HashSet<Role>();
////		roles.add(admin_role);
////		roles.add(user_role);
////		roles.add(billing_role);
////		newUser.setRoles(roles);
////		return userRepo.save(newUser);
////	}
//
//	//########## MOD NEEDED ##########
//	@Override
//	public Sys_Accounts companyResister(CompanyDto company) {
//		Sys_Accounts newCompany = new Sys_Accounts();
//		newCompany.setCompanyName(company.getCompanyName());
//		newCompany.setGstNumber(company.getGstNumber());
//		newCompany.setWorkspace(company.getWorkspace());
//		log.info("companyResister() -> CompanyDto : {}", newCompany);
//		Sys_Accounts savedCompany = companyRepo.save(newCompany);
//		log.info("companyResister() -> savedCompany : {}", savedCompany);
//		
//		String userEmail = company.getUserEmail();
//		log.info("companyResister() -> userEmail : {}", userEmail);
//		User user = userRepo.findByEmail(userEmail);
//		//user.setCompany(savedCompany); 
//		user.setSys_account(savedCompany);
//		userRepo.save(user);
//		log.info("companyResister() -> savedUser : {}", user);
//		return savedCompany;
//	}
//
//	@Override
//	public boolean insertOrSaveCompany(Sys_Accounts company) {
//		this.companyRepo.save(company);
//		return true;
//	}
//
//}
