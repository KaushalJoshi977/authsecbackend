package com.realnet.users.controller1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.session.entity.AboutWork;
import com.realnet.users.entity.Sys_Accounts;
import com.realnet.users.repository.AboutWorkRepo;
import com.realnet.users.repository.SysAccountRepo;
@RequestMapping("/users/sysaccount")
@RestController
public class SysAccountController {
	
	@Autowired
	private SysAccountRepo sysAccountRepo;
	
	@Autowired
	private AboutWorkRepo aboutWorkRepo;
	
	@PostMapping("/savesysaccount")
	public Sys_Accounts save(@RequestBody Sys_Accounts sys_Accounts)
	{
		Sys_Accounts save = sysAccountRepo.save(sys_Accounts);
		return save;
	}
	
	@PostMapping("/addaccountcusto")
	public AboutWork addaccountcusto(@RequestBody AboutWork aWork)
	{
		
		System.out.println("Here");
		AboutWork work = new AboutWork();
		work.setCompanyname(aWork.getCompanyname());
		work.setEmail(aWork.getEmail());
		work.setManaging_work(aWork.getManaging_work());
		work.setMobile(aWork.getMobile());
		work.setName(aWork.getName());
		work.setPancard(aWork.getPancard());
		work.setPassword(aWork.getPassword());
		work.setWorking(aWork.getWorking());
		
		
		AboutWork save = aboutWorkRepo.save(work);
		return save;
	}
	
	@DeleteMapping("/deleteaccount")
	public void deleteaccount()
	{
		aboutWorkRepo.deleteAll();
	}
		
	

}
