package com.realnet.fnd.service1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.realnet.fnd.entity1.MenuDet;
import com.realnet.fnd.repository1.MenuDetRepository;

@Service
public class MenuDetServiceImpl {
	private MenuDetRepository menuDetRepository;
	@Autowired
	public MenuDetServiceImpl(MenuDetRepository menuDetRepository) {
		super();
		this.menuDetRepository = menuDetRepository;
	}
	public List<MenuDet> getAll(){
		return menuDetRepository.findAll();
	}
	public List<Object> getAllObject(Pageable page){
		List<Object> l = menuDetRepository.getAll(page).getContent();
		return l;
	}
	public List<Object> getById(Long usrGrp,Long menuId){
		return menuDetRepository.getById(usrGrp, menuId);
	}
	public List<Object> getByUserId(Long usrGrp,Long menuId) {
		return menuDetRepository.getByUserId(usrGrp,menuId);
	}
}
