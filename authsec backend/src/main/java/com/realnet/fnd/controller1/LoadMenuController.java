package com.realnet.fnd.controller1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realnet.fnd.entity1.GrpMenuAccess;
import com.realnet.fnd.entity1.MenuDet;
import com.realnet.fnd.repository1.GrpMenuAccessRepository;
import com.realnet.fnd.repository1.MenuDetRepository;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.entity1.AppUserRole;
import com.realnet.users.repository1.AppUserRepository;
import com.realnet.users.repository1.AppUserRoleRepository;
import com.realnet.users.service1.AppUserServiceImpl;

@RequestMapping(value = "/fndMenu", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class LoadMenuController {

	@Autowired
	private AppUserRoleRepository appUserRoleRepository;
	@Autowired
	private MenuDetRepository menuDetRepository;
	@Autowired
	private GrpMenuAccessRepository grpMenuAccessRepository;
	@Autowired
	private AppUserServiceImpl userService;

	@Autowired
	private AppUserRepository appUserRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(LoadMenuController.class);

//	GROUP MENU ACCESS MENU LOAD BY USER
	@GetMapping("/menuloadbyuser")
	public ResponseEntity<?> setmenuforuser() {
		AppUser loggedInUser = userService.getLoggedInUser();
		Long usrGrp = loggedInUser.getUsrGrp().getUsrGrp();

		List<GrpMenuAccess> root = grpMenuAccessRepository.findAllRootsByMenuId(usrGrp); // menu id =0
		for (int i = 0; i < root.size(); i++) {

			// find menu item id by menu id =0
			List<GrpMenuAccess> menu = grpMenuAccessRepository.findById(root.get(i).getUsrGrp().getUsrGrp(),
					root.get(i).getMenuItemId().getMenuItemId());  // menuItem id will be unique
			for (GrpMenuAccess g : menu) {

				// all submenu by menuid = menu itemid with status
				List<GrpMenuAccess> allSubmenu = grpMenuAccessRepository
						.findAllSubmenuByMenuIdWithStatus(g.getMenuItemId().getMenuItemId(), g.getUsrGrp().getUsrGrp()); // find
																												
				g.setSubMenus(allSubmenu);
			}

		}
		return new ResponseEntity<>(root, HttpStatus.OK);
	}

//		GET LIST OF TABLE
	@GetMapping("/loadcolumn/{TABLE_NAME}")
	public List<Object> getcolumnname(@PathVariable Object TABLE_NAME) {
		List<Object> colun = menuDetRepository.findcolumnbytablename(TABLE_NAME);

		return colun;

	}

//	GET LIST OF USRGRP
	@GetMapping("/listofusrgrp")
	public ResponseEntity<?> listofusrgrp() {
		AppUser loggedInUser = userService.getLoggedInUser();
		Long usrGrp = loggedInUser.getUsrGrp().getUsrGrp();

		List<GrpMenuAccess> root = grpMenuAccessRepository.findAllRootsByMenuId(usrGrp);
		return new ResponseEntity<>(root, HttpStatus.OK);

	}

//	GET LIST OF USRGRP
	@GetMapping("/getuser/{user_id}")
	public ResponseEntity<?> getuser(@PathVariable Long user_id) {

		AppUser root = appUserRepository.findById(user_id).orElseThrow(null);
		return new ResponseEntity<>(root, HttpStatus.OK);

	}

//	SINK GRPMENU ACCESS TO SEC MENU DET SUBMENU BY PASSING GMENUID
	@PutMapping("/sink/{gmenuid}")
	public ResponseEntity<?> adds(
			@PathVariable Long gmenuid,
			@RequestBody GrpMenuAccess gr) {

		List<Object> list = new ArrayList<>();
		MenuDet formenu = menuDetRepository.findById(gmenuid).orElseThrow(null);

		List<GrpMenuAccess> all = grpMenuAccessRepository.findByGrpAndMenuid1(formenu.getMenuItemId());

		for (GrpMenuAccess a : all) {

			a.setMenuItemId(formenu);
			a.setItemSeq(formenu.getItemSeq());
			a.setMenuItemDesc(formenu.getMenuItemDesc());
			a.setModuleName(formenu.getModuleName());
			a.setStatus(formenu.getStatus());
			a.setMain_menu_action_name(formenu.getMain_menu_action_name());
			a.setMain_menu_icon_name(formenu.getMain_menu_icon_name());
			a.setMenuId(formenu.getMenuId());
			GrpMenuAccess save = grpMenuAccessRepository.save(a);
			list.add(save);
		}

		List<MenuDet> submenu = menuDetRepository.findAllSubmenuByMenuId(gmenuid);
		for (int i = 0; i < submenu.size(); i++) {
			MenuDet m = menuDetRepository.findById(submenu.get(i).getMenuItemId()).orElseThrow(null);
			List<GrpMenuAccess> li = grpMenuAccessRepository.findlist(m.getMenuItemId());

			if (li.isEmpty()) {
				List<GrpMenuAccess> grp = grpMenuAccessRepository.findAllById(gmenuid);

				for (GrpMenuAccess a : grp) {
					AppUserRole app = appUserRoleRepository.findById(a.getUsrGrp().getUsrGrp()).orElseThrow(null);
					gr.setUsrGrp(app);
					gr.setMenuItemId(m);

					gr.setItemSeq(m.getItemSeq());
					gr.setMenuItemDesc(m.getMenuItemDesc());
					gr.setModuleName(m.getModuleName());
					gr.setStatus(m.getStatus());
					gr.setMain_menu_action_name(m.getMain_menu_action_name());
					gr.setMain_menu_icon_name(m.getMain_menu_icon_name());
					gr.setMenuId(m.getMenuId());

					gr.setMCreate("true");
					gr.setMDelete("true");
					gr.setMEdit("true");
					gr.setMQuery("true");
					gr.setMVisible("true");
					gr.setIsdisable("true");
					gr.setMexport("true");

					gr.setCreatedAt(new Date());
					gr.setUpdatedAt(new Date());

					GrpMenuAccess save2 = grpMenuAccessRepository.save(gr);
					list.add(save2);
				}

			}

			else {
				for (GrpMenuAccess g : li) {

					g.setMenuItemId(m);

					g.setItemSeq(m.getItemSeq());
					g.setMenuItemDesc(m.getMenuItemDesc());
					g.setModuleName(m.getModuleName());
					g.setStatus(m.getStatus());
					g.setMain_menu_action_name(m.getMain_menu_action_name());
					g.setMain_menu_icon_name(m.getMain_menu_icon_name());
					g.setMenuId(m.getMenuId());

					GrpMenuAccess save1 = grpMenuAccessRepository.save(g);
					list.add(save1);
				}
			}
		}

		return new ResponseEntity<>(list, HttpStatus.CREATED);

	}

//	COMPLETED
//	SINK GRPMENU ACCESS FROM MENU DET BY PASSING MENU ITEM ID OF MENU BY LOGIN USER
	@PutMapping("/sink1/{gmenuid}")
	public ResponseEntity<?> sink(@PathVariable Long gmenuid, @RequestBody GrpMenuAccess gr) {

		AppUser loggedInUser = userService.getLoggedInUser();
		Long usrGrp = loggedInUser.getUsrGrp().getUsrGrp();

		List<Object> list = new ArrayList<>();
		MenuDet formenu = menuDetRepository.findById(gmenuid).orElseThrow(null);

		GrpMenuAccess a = grpMenuAccessRepository.findAllRootsByMenuIdAndGrp(gmenuid, usrGrp);
		if (a != null) {

			a.setMenuItemId(formenu);
			a.setItemSeq(formenu.getItemSeq());
			a.setMenuItemDesc(formenu.getMenuItemDesc());
			a.setModuleName(formenu.getModuleName());
			a.setStatus(formenu.getStatus());
			a.setMain_menu_action_name(formenu.getMain_menu_action_name());
			a.setMain_menu_icon_name(formenu.getMain_menu_icon_name());
			a.setMenuId(formenu.getMenuId());
			GrpMenuAccess save = grpMenuAccessRepository.save(a);
			list.add(save);

			List<MenuDet> submenu = menuDetRepository.findAllSubmenuByMenuId(gmenuid);
			for (int i = 0; i < submenu.size(); i++) {
				MenuDet m = menuDetRepository.findById(submenu.get(i).getMenuItemId()).orElseThrow(null);
//			List<GrpMenuAccess> listofsubmenu = grpMenuAccessRepository.findAllSubmenuByMenuitemidAndUsrGrp(gmenuid,usrGrp);
//			for (int j = 0; j < listofsubmenu.size(); j++) {
				GrpMenuAccess g = grpMenuAccessRepository.findById2(m.getMenuItemId(), usrGrp);
				if (g != null) {

					g.setMenuItemId(m);

					g.setItemSeq(m.getItemSeq());
					g.setMenuItemDesc(m.getMenuItemDesc());
					g.setModuleName(m.getModuleName());
					g.setStatus(m.getStatus());
					g.setMain_menu_action_name(m.getMain_menu_action_name());
					g.setMain_menu_icon_name(m.getMain_menu_icon_name());
					g.setMenuId(m.getMenuId());

					GrpMenuAccess save1 = grpMenuAccessRepository.save(g);
					list.add(save1);
				} else {

					AppUserRole app = appUserRoleRepository.findById(usrGrp).orElseThrow(null);
					gr.setUsrGrp(app);
					gr.setMenuItemId(m);

					gr.setItemSeq(m.getItemSeq());
					gr.setMenuItemDesc(m.getMenuItemDesc());
					gr.setModuleName(m.getModuleName());
					gr.setStatus(m.getStatus());
					gr.setMain_menu_action_name(m.getMain_menu_action_name());
					gr.setMain_menu_icon_name(m.getMain_menu_icon_name());
					gr.setMenuId(m.getMenuId());

					gr.setMCreate("true");
					gr.setMDelete("true");
					gr.setMEdit("true");
					gr.setMQuery("true");
					gr.setMVisible("true");
					gr.setIsdisable("true");
					gr.setMexport("true");

					gr.setCreatedAt(new Date());
					gr.setUpdatedAt(new Date());

					GrpMenuAccess save2 = grpMenuAccessRepository.save(gr);
					list.add(save2);

				}

			}

//		}
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(list, HttpStatus.CREATED);
	}

//	 DELETE MENU AND SUBMENU  INCLUDING GRPMENU ACCESS by menuItemId
	@DeleteMapping("/DelMenuWithSub/{menu_item_id}")
	public void delete(@PathVariable Long menu_item_id) {
			
		// delete submenu
		List<MenuDet> submenu = menuDetRepository.findAllSubmenuByMenuId(menu_item_id);
		for (int i = 0; i < submenu.size(); i++) {
			MenuDet m = menuDetRepository.findById(submenu.get(i).getMenuItemId()).orElseThrow(null);
			List<GrpMenuAccess> li = grpMenuAccessRepository.findlist(m.getMenuItemId());
			
			for(GrpMenuAccess g:li) {
				grpMenuAccessRepository.delete(g);
			}
			menuDetRepository.delete(m);
		}
		
		//delete menu
	       MenuDet formenu = menuDetRepository.findById(menu_item_id).orElseThrow(null);
			
			List<GrpMenuAccess> all = grpMenuAccessRepository.findByGrpAndMenuid1(formenu.getMenuItemId());

			for (GrpMenuAccess a : all) {
				grpMenuAccessRepository.delete(a);
			}
			
			menuDetRepository.delete(formenu);
		
	}

////LOAD MENU DET' MENU AND SUBMENU BY USER ID		
//@GetMapping("/loadmenu/{use_by}")
//public List<MenuDet> getmenubyuserid(@PathVariable Long use_by) {
//    List<MenuDet> root = menuDetRepository.findAllrootsbyuseby(use_by); 
//
//    for(MenuDet m:root) {
//    	
//    List<MenuDet> allSubmenu = menuDetRepository.findAllSubmenuByMenuId(m.getMenuItemId());
//    MenuDet menu = menuDetRepository.findById(m.getMenuItemId()).orElse(null);
//    menu.setSubMenus(allSubmenu);	        
//    }
//
//    return root;
//}

////GROUP MENU ACCESS MENU LOAD BY USER
//@GetMapping("/menuloadbyuser")
//public ResponseEntity<?> setmenuforuser(){
//	AppUser loggedInUser = userService.getLoggedInUser();
//	Long usrGrp = loggedInUser.getUsrGrp().getUsrGrp();
//			 
//	 List<GrpMenuAccess> root = grpMenuAccessRepository.findAllRootsbyusrGrp(usrGrp);
//	 for(int i =0; i <root.size(); i++) {
//		 
//	        GrpMenuAccess menu = grpMenuAccessRepository.findByparent(root.get(i).getParent());
//	        List<GrpMenuAccess> allSubmenu = grpMenuAccessRepository.findAllSubmenu(root.get(i).getParent());
//	        menu.setSubMenus(allSubmenu);
//	 }				  
//		return new ResponseEntity<>(root, HttpStatus.OK);
//}
	//DELETE MENU AND SUBMENU IN GRP MENU ACCESS BY MENU ITEM ID
	@DeleteMapping("/DelMenu_WithSub1/{menu_item_id}/{usrGrp}")
	public void deletemenuandsubmenu(@PathVariable Long menu_item_id,@PathVariable Long usrGrp) {
//		AppUser loggedInUser = userService.getLoggedInUser();
//		Long usrGrp = loggedInUser.getUsrGrp().getUsrGrp();		
		
		// delete submenu
			List<GrpMenuAccess> li = grpMenuAccessRepository.findAllSubmenuByMenuId(menu_item_id,usrGrp);		
			for(GrpMenuAccess g:li) {
				grpMenuAccessRepository.delete(g);
			}
		
		//delete menu			
			GrpMenuAccess all = grpMenuAccessRepository.findSingle(usrGrp,menu_item_id);			
			grpMenuAccessRepository.delete(all);
		
	}
}
