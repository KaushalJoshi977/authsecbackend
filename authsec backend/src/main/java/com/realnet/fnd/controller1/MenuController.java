package com.realnet.fnd.controller1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.exceptions.ResourceNotFoundException;
import com.realnet.fnd.entity1.GrpMenuAccess;
import com.realnet.fnd.entity1.MenuDet;
import com.realnet.fnd.entity1.MixMenuNew;
import com.realnet.fnd.repository1.GrpMenuAccessRepository;
import com.realnet.fnd.repository1.MenuDetRepository;
import com.realnet.fnd.service1.GrpMenuAccessServiceImpl;
import com.realnet.fnd.service1.MenuDetServiceImpl;
import com.realnet.users.entity1.AppUserRole;
import com.realnet.users.repository1.AppUserRepository;
import com.realnet.users.repository1.AppUserRoleRepository;
import com.realnet.users.service1.AppUserServiceImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/api1", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { "Rn_Menu_Group" })
@CrossOrigin("*")

public class MenuController {
	@Autowired
	private AppUserServiceImpl userService;

	@Autowired
	private AppUserRoleRepository appUserRoleRepository;

	@Autowired
	private AppUserRepository appUserRepository;

	private MenuDetServiceImpl menuDetServiceImpl;
	private GrpMenuAccessServiceImpl grpMenuAccessServiceImpl;
	private AppUserServiceImpl appUserServiceImpl;
	private MenuDetRepository menuDetRepository;
	private GrpMenuAccessRepository grpMenuAccessRepository;

	@Autowired
	public MenuController(MenuDetServiceImpl menuDetServiceImpl, GrpMenuAccessServiceImpl grpMenuAccessServiceImpl,
			AppUserServiceImpl appUserServiceImpl, MenuDetRepository menuDetRepository,
			GrpMenuAccessRepository grpMenuAccessRepository) {
		super();
		this.menuDetServiceImpl = menuDetServiceImpl;
		this.grpMenuAccessServiceImpl = grpMenuAccessServiceImpl;
		this.appUserServiceImpl = appUserServiceImpl;
		this.menuDetRepository = menuDetRepository;
		this.grpMenuAccessRepository = grpMenuAccessRepository;
	}

	@GetMapping("/allmenus")
	public ResponseEntity<?> getallmenu() {
		List<MixMenuNew> menu = menuDetRepository.getallmenu();
		return new ResponseEntity<>(menu, HttpStatus.OK);
	}

////	//	ADD DATA FOR SEC MENU DETAIL
	@PostMapping("/Sec_menuDet")
	public ResponseEntity<?> adddata(@RequestBody MenuDet menuDet) {
		MenuDet save = menuDetRepository.save(menuDet);
		return new ResponseEntity<>(save, HttpStatus.CREATED);
	}

//	GET BY MENU ITEM ID
	@GetMapping("/Sec_menuDet/{menu_item_id}")
	public ResponseEntity<?> getdata(@PathVariable Long menu_item_id) {
		MenuDet save = menuDetRepository.findById(menu_item_id)
				.orElseThrow(() -> new ResourceNotFoundException("id not found"));
		return new ResponseEntity<>(save, HttpStatus.CREATED);
	}

//	GET MENU AND SUBMENU of MENU DET
	@GetMapping("/submenu1")
	public List<MenuDet> submenu() {
		List<MenuDet> root = menuDetRepository.findAllRootsByMenuId();

		for (MenuDet m : root) {

			List<MenuDet> allSubmenu = menuDetRepository.findAllSubmenuByMenuId(m.getMenuItemId());
			MenuDet menu = menuDetRepository.findById(m.getMenuItemId()).orElse(null);
			menu.setSubMenus(allSubmenu);
		}

		return root;
	}

//	 Get Only SubMenu OF MENU DET
	@GetMapping("/submenu1/{menu_item_id}")
	public ResponseEntity<?> getonlysubmenu(@PathVariable Long menu_item_id) {
		List<MenuDet> submenu = menuDetRepository.findAllSubmenuByMenuId(menu_item_id);

		return new ResponseEntity<>(submenu, HttpStatus.OK);

	}

//	 GET MENU AND SUBMENU of GROUP MENU ACCESS
	@GetMapping("/grpmenuandsubmenu")
	public List<GrpMenuAccess> GRPMENUACCESS() {
		List<GrpMenuAccess> root = grpMenuAccessRepository.findAllRoots();

		for (int i = 0; i < root.size(); i++) {

			// find menu item id by menu id =0
			List<GrpMenuAccess> menu = grpMenuAccessRepository.findById(root.get(i).getUsrGrp().getUsrGrp(),
					root.get(i).getMenuItemId().getMenuItemId()); // menuItem id will be unique
			for (GrpMenuAccess g : menu) {

				List<GrpMenuAccess> allSubmenu = grpMenuAccessRepository
						.findAllSubmenuByMenuId(g.getMenuItemId().getMenuItemId(), g.getUsrGrp().getUsrGrp()); // find
																												// all
																												// submenu
																												// by
																												// menuid
																												// =
																												// menu
																												// itemid
				g.setSubMenus(allSubmenu);
			}

		}

		return root;
	}

//	 Get Only SubMenu of GROUP MENU ACCESS
	@GetMapping("/grpmenuandsubmenu/{menu_item_id}")
	public ResponseEntity<?> grpmenuaccess(@PathVariable Long menu_item_id) {
		List<GrpMenuAccess> submenu = grpMenuAccessRepository.findAllSubmenu(menu_item_id);

		return new ResponseEntity<>(submenu, HttpStatus.OK);

	}

//	 update by menu_item_id
	@PutMapping("/submenu1/{menu_item_id}")
	public ResponseEntity<?> updatedata(@RequestBody MenuDet d, @PathVariable Long menu_item_id) {
		MenuDet menu = menuDetRepository.findById(menu_item_id).orElseThrow(null);

		menu.setItemSeq(d.getItemSeq());
		menu.setMain_menu_action_name(d.getMain_menu_action_name());
		menu.setMain_menu_icon_name(d.getMain_menu_icon_name());
		menu.setMenuId(d.getMenuId());
		menu.setMenuItemDesc(d.getMenuItemDesc());
		menu.setModuleName(d.getModuleName());
		menu.setStatus(d.getStatus());

		MenuDet save = menuDetRepository.save(menu);
		return new ResponseEntity<>(save, HttpStatus.CREATED);
	}

//	 delete DATA SEC MENU DET WITH GRPMENUACCESS by menuItemId
	@DeleteMapping("/menu/{menu_item_id}")
	public void delete(@PathVariable Long menu_item_id) {
		
		
		List<GrpMenuAccess> findlist = grpMenuAccessRepository.findlist(menu_item_id);
		for(GrpMenuAccess g:findlist) {
			grpMenuAccessRepository.delete(g);	
		}
	
		MenuDet menu = menuDetRepository.findById(menu_item_id).orElseThrow(null);
		menuDetRepository.delete(menu);
	}

//	 GET GROUP MENU ACCESS BY USR_GROUP with 	WITH SUBMENU

	@GetMapping("/grpmenuaccess/{usr_grp}")
	public ResponseEntity<?> GETGROUPMENU(@PathVariable Long usr_grp) {

		List<Object> list = new ArrayList<>();
		GrpMenuAccess grp = grpMenuAccessRepository.findByUsrGrp(usr_grp);

//		  List<MenuDet> sub = menuDetRepository.findAllSubmenuByMenuId(grp.getMenuItemId().getMenuItemId());
		List<MenuDet> sub = menuDetRepository.findAllSubmenuforusrgrp(grp.getMenuItemId().getMenuItemId());

		list.add(grp);
		list.add(sub);
		return new ResponseEntity<>(list, HttpStatus.OK);

	}

//	get all
	@GetMapping("/getAllData")
	public ResponseEntity<List<GrpMenuAccess>> getAllGroupMenuAccess() {
		List<GrpMenuAccess> listgrp = grpMenuAccessRepository.findAll();
		return new ResponseEntity<>(listgrp, HttpStatus.OK);
	}

//	get by  USRGRP WITHOUT SUBMENU

//			@GetMapping("/getById/{userId}")
//			public ResponseEntity<?> getGrpMenuByUserId(@PathVariable Long userId){
//				GrpMenuAccess grpMenuAccess=grpMenuAccessRepository.findByUsrGrp(userId);
//				if(grpMenuAccess==null)throw new ResourceNotFoundException("no resource found");
//				return new ResponseEntity<>(grpMenuAccess,HttpStatus.OK);
//			}

//			update by usr grp id
	@PutMapping("/updatemenuaccess/{userId}")
	public ResponseEntity<?> updateMenuAccess(@PathVariable Long userId, @RequestBody GrpMenuAccess grpMenuAccess) {
		GrpMenuAccess g1 = grpMenuAccessRepository.findByUsrGrp(userId);
		if (g1 == null) {
			throw new ResourceNotFoundException("no resource found");
		}
		g1.setMCreate(grpMenuAccess.getMCreate());
		g1.setMDelete(grpMenuAccess.getMDelete());
		g1.setMEdit(grpMenuAccess.getMEdit());
		g1.setMQuery(grpMenuAccess.getMQuery());
		g1.setMVisible(grpMenuAccess.getMVisible());
		g1.setMexport(grpMenuAccess.getMexport());

		GrpMenuAccess grpMenuAccess2 = grpMenuAccessRepository.save(g1);
		return new ResponseEntity<>(grpMenuAccess2, HttpStatus.OK);
	}

//			delete by usrgrp
	@DeleteMapping("/deleteMenuAcces/{userId}")
	public void deleteMenuAccess(@PathVariable Long userId) {
		GrpMenuAccess grpMenuAccess = grpMenuAccessRepository.findByUsrGrp(userId);
		if (Objects.isNull(grpMenuAccess))
			throw new ResourceNotFoundException("no resource found");
		grpMenuAccessRepository.delete(grpMenuAccess);
	}

//	 ADD MULTIPLE DATA FOR GROUP MENU ACCESS
	@PostMapping("/group")
	public ResponseEntity<?> addgroupmenuaccess(@RequestBody List<GrpMenuAccess> grp) {

		for (GrpMenuAccess g : grp) {
			MenuDet menu1 = menuDetRepository.findById(g.getGmenuid()).orElseThrow(null);
			AppUserRole a = appUserRoleRepository.findById(g.getGrpid()).orElseThrow(null);

			g.setUsrGrp(a);
			g.setMenuItemId(menu1);
		}
		List<GrpMenuAccess> save = grpMenuAccessRepository.saveAll(grp);
		return new ResponseEntity<>(save, HttpStatus.CREATED);
	}

//		 ADD DATA FOR group menu access
	@PostMapping("/singlegroup")
	public ResponseEntity<?> addsinglegroup(@RequestBody GrpMenuAccess grp) {

		MenuDet menu1 = menuDetRepository.findById(grp.getGmenuid()).orElseThrow(null);
		AppUserRole a = appUserRoleRepository.findById(grp.getGrpid()).orElseThrow(null);

		grp.setUsrGrp(a);
		grp.setMenuItemId(menu1);

		GrpMenuAccess save = grpMenuAccessRepository.save(grp);
		return new ResponseEntity<>(save, HttpStatus.CREATED);
	}

//			NEWWWW post MENU AND SUBMENU IN GROUPMENUACCESS

	@PostMapping("/addgrpwithsubmenu")
	public ResponseEntity<?> adds(@RequestBody GrpMenuAccess g) {

		List<Object> list = new ArrayList<>();

		Optional<GrpMenuAccess> a = grpMenuAccessRepository.findbygrpandmenuid(g.getGmenuid(), g.getGrpid());
		if (!a.isPresent()) {
			MenuDet me = menuDetRepository.findById(g.getGmenuid()).orElseThrow(null);
			g.setMenuItemId(me);
			g.setItemSeq(me.getItemSeq());
			g.setMenuItemDesc(me.getMenuItemDesc());
			g.setMenuItemDesc(me.getMenuItemDesc());
			g.setModuleName(me.getModuleName());
			g.setStatus(me.getStatus());
			g.setMain_menu_action_name(me.getMain_menu_action_name());
			g.setMain_menu_icon_name(me.getMain_menu_icon_name());
			g.setMenuId(me.getMenuId());

			AppUserRole app1 = appUserRoleRepository.findById(g.getGrpid()).orElseThrow(null);
			g.setUsrGrp(app1);
			g.setMCreate("true");
			g.setMDelete("true");
			g.setMEdit("true");
			g.setMQuery("true");
			g.setMVisible("true");
			g.setIsdisable("true");
			g.setMexport("true");

			g.setCreatedAt(new Date());
			g.setUpdatedAt(new Date());

			GrpMenuAccess save1 = grpMenuAccessRepository.save(g);
			list.add(save1);

			List<MenuDet> submenu = menuDetRepository.findAllSubmenuByMenuId(g.getGmenuid());

			for (int i = 0; i < submenu.size(); i++) {
				MenuDet m = menuDetRepository.findById(submenu.get(i).getMenuItemId()).orElseThrow(null);
				g.setMenuItemId(m);
				g.setItemSeq(m.getItemSeq());
				g.setMenuItemDesc(m.getMenuItemDesc());
				g.setModuleName(m.getModuleName());
				g.setStatus(m.getStatus());
				g.setMain_menu_action_name(m.getMain_menu_action_name());
				g.setMain_menu_icon_name(m.getMain_menu_icon_name());
				g.setMenuId(m.getMenuId());

				AppUserRole app = appUserRoleRepository.findById(g.getGrpid()).orElseThrow(null);
				g.setUsrGrp(app);
				g.setMCreate("true");
				g.setMDelete("true");
				g.setMEdit("true");
				g.setMQuery("true");
				g.setMVisible("true");
				g.setIsdisable("true");
				g.setMexport("true");


				g.setCreatedAt(new Date());
				g.setUpdatedAt(new Date());

				GrpMenuAccess save = grpMenuAccessRepository.save(g);
				list.add(save);
			}

			return new ResponseEntity<>(list, HttpStatus.CREATED);

		} else {
			return new ResponseEntity<>("already added menu", HttpStatus.BAD_REQUEST);
		}

	}

//		GET ALL GRPACCESS BY USRGRP
	@GetMapping("getusracces1/{usr_grp}")
	public ResponseEntity<?> getallbyusrgrp(@PathVariable Long usr_grp) {
		List<GrpMenuAccess> list = grpMenuAccessRepository.findAllByUsrGrp(usr_grp);
		return new ResponseEntity<>(list, HttpStatus.OK);

	}

//	UPDATE GRP MENU ACCESS BY MENU ITEMID
	@PutMapping("/update/{menu_item_id}/{usr_grp}")
	public ResponseEntity<?> updateMenuGRPAccess(@PathVariable Long menu_item_id, @PathVariable Long usr_grp,
			@RequestBody GrpMenuAccess grpMenuAccess) {
		GrpMenuAccess g1 = grpMenuAccessRepository.findByUsrgrpAndMenuitemid(menu_item_id, usr_grp);
		if (g1 == null) {
			throw new ResourceNotFoundException("no resource found");
		}
		g1.setMCreate(grpMenuAccess.getMCreate());
		g1.setMDelete(grpMenuAccess.getMDelete());
		g1.setMEdit(grpMenuAccess.getMEdit());
		g1.setMQuery(grpMenuAccess.getMQuery());
		g1.setMVisible(grpMenuAccess.getMVisible());
		g1.setIsdisable(grpMenuAccess.getIsdisable());
		g1.setItemSeq(grpMenuAccess.getItemSeq());
		g1.setMain_menu_action_name(grpMenuAccess.getMain_menu_action_name());
		g1.setMain_menu_icon_name(grpMenuAccess.getMain_menu_action_name());
		g1.setMenuId(grpMenuAccess.getMenuId());
		g1.setMenuItemDesc(grpMenuAccess.getMenuItemDesc());
		g1.setModuleName(grpMenuAccess.getModuleName());
		g1.setStatus(grpMenuAccess.getStatus());
		g1.setMexport(grpMenuAccess.getMexport());
		
		GrpMenuAccess grpMenuAccess2 = grpMenuAccessRepository.save(g1);
		return new ResponseEntity<>(grpMenuAccess2, HttpStatus.OK);
	}

//	DELETE GRPMENU ACCESS BY MENUITEMID AND USRGRP
	@DeleteMapping("/deleteGrpMenuAcces/{menu_item_id}/{usr_grp}")
	public void deleteGrpMenuAccess(@PathVariable Long menu_item_id, @PathVariable Long usr_grp) {
		GrpMenuAccess grpMenuAccess = grpMenuAccessRepository.findByUsrgrpAndMenuitemid(menu_item_id, usr_grp);
		if (Objects.isNull(grpMenuAccess))
			throw new ResourceNotFoundException("no resource found");
		grpMenuAccessRepository.delete(grpMenuAccess);
	}
	
//	GET GRPMENU ACCESS BY MENUITEM ID BUT NOT WORKING WITH SUBMENU
	@GetMapping("/getsec/{menu_item_id}")
	public ResponseEntity<?> getgrpmenuaccess(@PathVariable Long menu_item_id) {
		GrpMenuAccess get = grpMenuAccessRepository.findById1(menu_item_id);
				
		return new ResponseEntity<>(get, HttpStatus.CREATED);
	}

//			 @GetMapping("/categories")
////	    @Transactional(readOnly = true)
//	    public List<MenuDet> getCategories() {
//	        List<MenuDet> rootCategories = menuDetRepository.findAllRoots(); // first db call
//
//
//	        // Now Find all the subcategories
//	        List<Long> rootCategoryIds = rootCategories.stream().map(MenuDet::getMenuItemId).collect(Collectors.toList());
//	        List<MenuDet> subCategories = menuDetRepository.findAllSubCategoriesInRoot(rootCategoryIds); // second db call
//
//	        subCategories.forEach(subCategory -> {
//	            subCategory.getParentMenudet().getSubMenus().add(subCategory); // no further db call, because everyone inside the root is in the persistence context.
//	        });
//
//	        return rootCategories;
//	    }	

//			 
	// GET GROUP MENU ACCESS BY USR_GROUP with WITH SUBMENU
//			 
//			 @GetMapping("/grpmenuaccess/{usr_grp}")
//			 public ResponseEntity<?> GETGROUPMENU( @PathVariable Long usr_grp
//					){
//				 
//				 List<Object> list = new ArrayList<>();
//				 GrpMenuAccess grp = grpMenuAccessRepository.findByUsrGrp(usr_grp);
////					ArrayList<CreateMenuDetDto> dd = new ArrayList<CreateMenuDetDto>();
//					
////					for(CreateMenuDetDto dto:dd) {
	////
//////				  CreateMenuDetDto dto = new CreateMenuDetDto();
////				  dto.setCreateby(grp.getCreateby());
////				  dto.setMCreate(grp.getMCreate());
////				  dto.setMDelete(grp.getMDelete());
////				  dto.setMEdit(grp.getMEdit());
////				  dto.setMQuery(grp.getMQuery());
////				  dto.setMVisible(grp.getMVisible());
////				  dto.setMenuItemId(grp.getMenuItemId());
////				  List<MenuDet> sub = menuDetRepository.findAllSubmenuByMenuId(grp.getMenuItemId().getMenuItemId());
//				  List<MenuDet> sub = menuDetRepository.findAllSubmenuforusrgrp(grp.getMenuItemId().getMenuItemId());	
//				  
////				  for(MenuDet m: sub) {
////					  dto.setItemSeq(m.getItemSeq());
////					  dto.setMenuId(m.getMenuId());
////					  dto.setMenu_item_id(m.getMenuItemId());
////					  dto.setMenuItemDesc(m.getMenuItemDesc());
////					  dto.setModuleName(m.getModuleName());
////					  dto.setStatus(m.getStatus());
////					  dd.add(dto);
////					  }  
////					}
//				  list.add(grp);
//				  list.add(sub);
//				  return new ResponseEntity<>(list, HttpStatus.OK);  	  	  
//				  
//			 }

//			get by usrgrp

//			@GetMapping("/get/{usr_grp}")
//
//			public ResponseEntity<?> getGrp(@PathVariable Long usr_grp) {
//				GrpMenuAccess grp = grpMenuAccessRepository.findByUsrGrp(usr_grp);
//				if (grp == null)
//					throw new ResourceNotFoundException(" resource not found");
//				
//				MenuDet menuDet = grp.getMenuItemId();
//				
//				List<MenuDet> list = menuDetRepository.findAllSubmenuByMenuId(menuDet.getMenuItemId());
//				grp.setMenu(list);
//				return new ResponseEntity<>(grp, HttpStatus.OK);
//			}		

//			@GetMapping("/allMenuDet")
//			public ResponseEntity<?> getAllMenuDet(){
//				//List<MenuDet> l = menuDetServiceImpl.getAll();
//				List<Object> l =menuDetServiceImpl.getAllObject( PageRequest.of(0,5));
//				List<MixMenu> m = new ArrayList<MixMenu>();
//				for(Object o:l) {
//					Object[] e = (Object[]) o;	
//					MixMenu p = new MixMenu();
//					p.setMenuItemId( (BigInteger) e[0]);
//					p.setMenuItemDesc((String) e[1]);
//					p.setMenuId((BigInteger) e[2]);
//					p.setMCreate((String) e[3]);
//					p.setMVisible((String) e[4]);
//					p.setMEdit((String) e[5]);
//					p.setMQuery((String) e[6]);
//					p.setMDelete((String) e[7]);
//					m.add(p);
//				}
//				return new ResponseEntity<>(m,HttpStatus.OK);
//			}
//			@GetMapping("/getByMenuId/{usrGrp}/{menuId}")
//			public ResponseEntity<?> getOne(@PathVariable("usrGrp") Long usrGrp,@PathVariable("menuId") Long menuId){
//				List<Object> l =menuDetServiceImpl.getAllObject( PageRequest.of(0,5));
//				List<MixMenu> m = new ArrayList<MixMenu>();
//				for(Object o:l) {
//					Object[] e = (Object[]) o;	
//					MixMenu p = new MixMenu();
//					p.setMenuItemId( (BigInteger) e[0]);
//					p.setMenuItemDesc((String) e[1]);
//					p.setMenuId((BigInteger) e[2]);
//					p.setMCreate((String) e[3]);
//					p.setMVisible((String) e[4]);
//					p.setMEdit((String) e[5]);
//					p.setMQuery((String) e[6]);
//					p.setMDelete((String) e[7]);
//					m.add(p);
//				}
//				return new ResponseEntity<>(m,HttpStatus.OK);
//			}
	//
//			@GetMapping("/allGrpMenu")
//			public ResponseEntity<?> getAllGrpMenu(){
//				Pageable page = PageRequest.of(0,5);
//				List<GrpMenuAccess> l = grpMenuAccessServiceImpl.getAll(page);
//				return new ResponseEntity<>(l,HttpStatus.OK);
//			}
	//
//			@GetMapping("/getByUserId")
//			public ResponseEntity<?> getByUserId(){
//				AppUserRole role = appUserServiceImpl.getLoggedInUser().getUsrGrp();
//				List<Object> l =menuDetServiceImpl.getByUserId(role.getUsrGrp(),(long) 0);
//				List<MixMenu> m = new ArrayList<MixMenu>();
//				for(Object o:l) {
//					Object[] e = (Object[]) o;	
//					MixMenu p = new MixMenu();
//					p.setMenuItemId( (BigInteger) e[0]);
//					List<Object> l1 =menuDetServiceImpl.getByUserId(role.getUsrGrp(),p.getMenuItemId().longValue());
//					List<MixMenu> m1 = new ArrayList<MixMenu>();
//					for(Object o1: l1) {
//						Object[] e1 = (Object[]) o1;
//						MixMenu p1 = new MixMenu();
//						p1.setMenuItemId( (BigInteger) e1[0]);
//						p1.setMenuItemDesc((String) e1[1]);
//						p1.setMenuId((BigInteger) e1[2]);
//						p1.setMCreate((String) e1[3]);
//						p1.setMVisible((String) e1[4]);
//						p1.setMEdit((String) e1[5]);
//						p1.setMQuery((String) e1[6]);
//						p1.setMDelete((String) e1[7]);
//						p1.setMainMenuActionName((String) e1[8]);
//						p1.setMainMenuIconName((String) e1[9]);
//						m1.add(p1);
//					}
//					p.setMenuItemDesc((String) e[1]);
//					p.setMenuId((BigInteger) e[2]);
//					p.setMCreate((String) e[3]);
//					p.setMVisible((String) e[4]);
//					p.setMEdit((String) e[5]);
//					p.setMQuery((String) e[6]);
//					p.setMDelete((String) e[7]);
//					p.setMainMenuActionName((String) e[8]);
//					p.setMainMenuIconName((String) e[9]);
//					p.setSubMenus(m1);
//					m.add(p);
//				}
//				return new ResponseEntity<>(m,HttpStatus.OK);
//			}

//			GET MENU AND SUBMENU
//			@GetMapping("/Load_Menu")
//			public ResponseEntity<?> loadmenu(){
//				List<Object> list = new ArrayList<>();
////				 Map<K, V> coordinates = new HashMap<>();
//				List<Object> e1 = menuDetRepository.findEqual100();
//				List<Object> e11 = menuDetRepository.findGreater100();
//				List<Object> e2 = menuDetRepository.findEqual200();
//				List<Object> e22 = menuDetRepository.findGreater200();
//				List<Object> e3 = menuDetRepository.findEqual300();
//				List<Object> e33 = menuDetRepository.findGreater300();
//				List<Object> e4 = menuDetRepository.findEqual400();
//				List<Object> e44 = menuDetRepository.findGreater400();
//				List<Object> Li = menuDetRepository.findEqual500();
//				List<Object> Gr = menuDetRepository.findGreater500();
//				List<Object> e6 = menuDetRepository.findEqual600();
//				List<Object> e66 = menuDetRepository.findGreater600();
//				
//				//FOR menu and submenu of menu id 100
//				List<MixMenu> a11 = new ArrayList<MixMenu>();//For submenu of 100
//				for(Object o:e11) {
//					Object[] e = (Object[]) o;	
//					MixMenu p = new MixMenu();
//					p.setMenuItemId( (BigInteger) e[0]);
//					p.setMenuItemDesc((String) e[1]);
//					p.setMenuId((BigInteger) e[2]);
//					p.setMCreate((String) e[3]);
//					p.setMVisible((String) e[4]);
//					p.setMEdit((String) e[5]);
//					p.setMQuery((String) e[6]);
//					p.setMDelete((String) e[7]);
//					p.setMainMenuActionName((String) e[8]);
//					p.setMainMenuIconName((String) e[9]);
//					a11.add(p);
//				}
	//
//					List<MixMenu> a1 = new ArrayList<MixMenu>();// for menu of 100
//					for(Object o:e1) {
//						Object[] e = (Object[]) o;	
//						MixMenu p = new MixMenu();
//						p.setMenuItemId( (BigInteger) e[0]);
//						p.setMenuItemDesc((String) e[1]);
//						p.setMenuId((BigInteger) e[2]);
//						p.setMCreate((String) e[3]);
//						p.setMVisible((String) e[4]);
//						p.setMEdit((String) e[5]);
//						p.setMQuery((String) e[6]);
//						p.setMDelete((String) e[7]);
//						p.setMainMenuActionName((String) e[8]);
//						p.setMainMenuIconName((String) e[9]);
//						p.setSubMenus(a11);
//						a1.add(p);
//					}
//				
//				
	//
//				
////					FOR menu and submenu of menu id 200
//				List<MixMenu> a22 = new ArrayList<MixMenu>();//For submenu of 200
//				for(Object o:e22) {
//					Object[] e = (Object[]) o;	
//					MixMenu p = new MixMenu();
//					p.setMenuItemId( (BigInteger) e[0]);
//					p.setMenuItemDesc((String) e[1]);
//					p.setMenuId((BigInteger) e[2]);
//					p.setMCreate((String) e[3]);
//					p.setMVisible((String) e[4]);
//					p.setMEdit((String) e[5]);
//					p.setMQuery((String) e[6]);
//					p.setMDelete((String) e[7]);
//					p.setMainMenuActionName((String) e[8]);
//					p.setMainMenuIconName((String) e[9]);
//					a22.add(p);
//				}
	//
//					List<MixMenu> a2 = new ArrayList<MixMenu>();// for menu of 200
//					for(Object o:e2) {
//						Object[] e = (Object[]) o;	
//						MixMenu p = new MixMenu();
//						p.setMenuItemId( (BigInteger) e[0]);
//						p.setMenuItemDesc((String) e[1]);
//						p.setMenuId((BigInteger) e[2]);
//						p.setMCreate((String) e[3]);
//						p.setMVisible((String) e[4]);
//						p.setMEdit((String) e[5]);
//						p.setMQuery((String) e[6]);
//						p.setMDelete((String) e[7]);
//						p.setMainMenuActionName((String) e[8]);
//						p.setMainMenuIconName((String) e[9]);
//						p.setSubMenus(a22);
//						a2.add(p);
//					}
	//
////					FOR menu and submenu of menu id 300
//				List<MixMenu> a33 = new ArrayList<MixMenu>();//For submenu of 300
//				for(Object o:e33) {
//					Object[] e = (Object[]) o;	
//					MixMenu p = new MixMenu();
//					p.setMenuItemId( (BigInteger) e[0]);
//					p.setMenuItemDesc((String) e[1]);
//					p.setMenuId((BigInteger) e[2]);
//					p.setMCreate((String) e[3]);
//					p.setMVisible((String) e[4]);
//					p.setMEdit((String) e[5]);
//					p.setMQuery((String) e[6]);
//					p.setMDelete((String) e[7]);
//					p.setMainMenuActionName((String) e[8]);
//					p.setMainMenuIconName((String) e[9]);
//					a33.add(p);
//				}
	//
//					List<MixMenu> a3 = new ArrayList<MixMenu>();// for menu of 300
//					for(Object o:e3) {
//						Object[] e = (Object[]) o;	
//						MixMenu p = new MixMenu();
//						p.setMenuItemId( (BigInteger) e[0]);
//						p.setMenuItemDesc((String) e[1]);
//						p.setMenuId((BigInteger) e[2]);
//						p.setMCreate((String) e[3]);
//						p.setMVisible((String) e[4]);
//						p.setMEdit((String) e[5]);
//						p.setMQuery((String) e[6]);
//						p.setMDelete((String) e[7]);
//						p.setMainMenuActionName((String) e[8]);
//						p.setMainMenuIconName((String) e[9]);
//						p.setSubMenus(a33);
//						a3.add(p);
//					}
//					
	//
////					FOR menu and submenu of menu id 400
//				List<MixMenu> a44 = new ArrayList<MixMenu>();//For submenu of 400
//				for(Object o:e44) {
//					Object[] e = (Object[]) o;	
//					MixMenu p = new MixMenu();
//					p.setMenuItemId( (BigInteger) e[0]);
//					p.setMenuItemDesc((String) e[1]);
//					p.setMenuId((BigInteger) e[2]);
//					p.setMCreate((String) e[3]);
//					p.setMVisible((String) e[4]);
//					p.setMEdit((String) e[5]);
//					p.setMQuery((String) e[6]);
//					p.setMDelete((String) e[7]);
//					p.setMainMenuActionName((String) e[8]);
//					p.setMainMenuIconName((String) e[9]);
//					a44.add(p);
//				}
	//
//					List<MixMenu> a4 = new ArrayList<MixMenu>();// for menu of 200
//					for(Object o:e4) {
//						Object[] e = (Object[]) o;	
//						MixMenu p = new MixMenu();
//						p.setMenuItemId( (BigInteger) e[0]);
//						p.setMenuItemDesc((String) e[1]);
//						p.setMenuId((BigInteger) e[2]);
//						p.setMCreate((String) e[3]);
//						p.setMVisible((String) e[4]);
//						p.setMEdit((String) e[5]);
//						p.setMQuery((String) e[6]);
//						p.setMDelete((String) e[7]);
//						p.setMainMenuActionName((String) e[8]);
//						p.setMainMenuIconName((String) e[9]);
//						p.setSubMenus(a44);
//						a4.add(p);
//					}
//				
	//
//				
	//
////					FOR menu and submenu of menu id 500
	//
//				List<MixMenu> a55 = new ArrayList<MixMenu>();//for submenu of 500
//				for(Object o:Gr) {
//					Object[] e = (Object[]) o;	
//					MixMenu p = new MixMenu();
//					p.setMenuItemId( (BigInteger) e[0]);
//					p.setMenuItemDesc((String) e[1]);
//					p.setMenuId((BigInteger) e[2]);
//					p.setMCreate((String) e[3]);
//					p.setMVisible((String) e[4]);
//					p.setMEdit((String) e[5]);
//					p.setMQuery((String) e[6]);
//					p.setMDelete((String) e[7]);
//					p.setMainMenuActionName((String) e[8]);
//					p.setMainMenuIconName((String) e[9]);
//					a55.add(p);
//				}
	//
//					List<MixMenu> a5 = new ArrayList<MixMenu>();//for menu of 500
//					for(Object o:Li) {
//						Object[] e = (Object[]) o;	
//						MixMenu p = new MixMenu();
//						p.setMenuItemId( (BigInteger) e[0]);
//						p.setMenuItemDesc((String) e[1]);
//						p.setMenuId((BigInteger) e[2]);
//						p.setMCreate((String) e[3]);
//						p.setMVisible((String) e[4]);
//						p.setMEdit((String) e[5]);
//						p.setMQuery((String) e[6]);
//						p.setMDelete((String) e[7]);
//						p.setMainMenuActionName((String) e[8]);
//						p.setMainMenuIconName((String) e[9]);
//						p.setSubMenus(a55);
//						a5.add(p);
//					}
//					
////					FOR menu and submenu of menu id 600
	//
//					List<MixMenu> a66 = new ArrayList<MixMenu>();//For submenu of 600
//					for(Object o:e66) {
//						Object[] e = (Object[]) o;	
//						MixMenu p = new MixMenu();
//						p.setMenuItemId( (BigInteger) e[0]);
//						p.setMenuItemDesc((String) e[1]);
//						p.setMenuId((BigInteger) e[2]);
//						p.setMCreate((String) e[3]);
//						p.setMVisible((String) e[4]);
//						p.setMEdit((String) e[5]);
//						p.setMQuery((String) e[6]);
//						p.setMDelete((String) e[7]);
//						p.setMainMenuActionName((String) e[8]);
//						p.setMainMenuIconName((String) e[9]);
//						a66.add(p);
//					}
	//
//						List<MixMenu> a6 = new ArrayList<MixMenu>();// for menu of 600
//						for(Object o:e6) {
//							Object[] e = (Object[]) o;	
//							MixMenu p = new MixMenu();
//							p.setMenuItemId( (BigInteger) e[0]);
//							p.setMenuItemDesc((String) e[1]);
//							p.setMenuId((BigInteger) e[2]);
//							p.setMCreate((String) e[3]);
//							p.setMVisible((String) e[4]);
//							p.setMEdit((String) e[5]);
//							p.setMQuery((String) e[6]);
//							p.setMDelete((String) e[7]);
//							p.setMainMenuActionName((String) e[8]);
//							p.setMainMenuIconName((String) e[9]);
//							p.setSubMenus(a66);
//							a6.add(p);
//						}
//					
//					
//						list.add(a1);
//						list.add(a2);
//						list.add(a3);
//						list.add(a4);
//						list.add(a5);
//						list.add(a6);
//					return new ResponseEntity<>(list,HttpStatus.OK);
	//
//				}
	//
	//
////			GET MENU AND SUBMENU of MENU DETAILS
//			@GetMapping("/Load_MenuNew")
//			public ResponseEntity<?> loadmenuDetail(){
//				List<Object> list = new ArrayList<>();
//				
//				MenuDet a1 = menuDetRepository.findequalto100();
//				List<MenuDet> a11 = menuDetRepository.findgreaterthan100();
//				a1.setSubMenus(a11);
//				
//				MenuDet a2 = menuDetRepository.findequalto200();
//				List<MenuDet> a22 = menuDetRepository.findgreaterthan200();
//				a2.setSubMenus(a22);
//				
//				MenuDet a3 = menuDetRepository.findequalto300();
//				List<MenuDet> a33 = menuDetRepository.findgreaterthan300();
//				a3.setSubMenus(a33);
//				
////				MenuDet a4 = menuDetRepository.findequalto400();
////				List<MenuDet> a44 = menuDetRepository.findgreaterthan400();
////				a4.setSubMenus(a44);
//				
//				MenuDet a5 = menuDetRepository.findequalto500();
//				List<MenuDet> a55 = menuDetRepository.findgreaterthan500();
//				a5.setSubMenus(a55);
//				
////				MenuDet a6 = menuDetRepository.findequalto600();
////				List<MenuDet> a66 = menuDetRepository.findgreaterthan600();
////				a6.setSubMenus(a66);
	//
//				list.add(a1);
//				list.add(a2);
//				list.add(a3);	
////				list.add(a4);	
//				list.add(a5);	
////				list.add(a6);	
	//
//				
//				return new ResponseEntity<>(list,HttpStatus.OK);
	//
//			}
	//
	//
////			GET SUBMENU BY MENU ID
	//
//			@GetMapping("/menu_loading/{menu_id}")
//			public ResponseEntity<?> getmenubyid(@PathVariable Long menu_id){
//			List<MenuDet> M = menuDetRepository.findAllById(menu_id);
//			if(menu_id != null) {
//				if(menu_id==100) {
//					return new ResponseEntity<>((List<MenuDet>) menuDetRepository.findgreaterthan100(), HttpStatus.OK);
	//
//				}
//				if(menu_id==200) {
//					return new ResponseEntity<>((List<MenuDet>) menuDetRepository.findgreaterthan200(), HttpStatus.OK);
	//
//				}
//				if(menu_id==300) {
//					return new ResponseEntity<>((List<MenuDet>) menuDetRepository.findgreaterthan300(), HttpStatus.OK);
	//
//				}
//				if(menu_id==400) {
//					return new ResponseEntity<>((List<MenuDet>) menuDetRepository.findgreaterthan400(), HttpStatus.OK);
	//
//				}
//				if(menu_id==500) {
//					return new ResponseEntity<>((List<MenuDet>) menuDetRepository.findgreaterthan500(), HttpStatus.OK);
	//
//				}
//				if(menu_id==600) {
//					return new ResponseEntity<>((List<MenuDet>) menuDetRepository.findgreaterthan600(), HttpStatus.OK);
	//
//				}
//			}	
//				return new ResponseEntity<>(M,HttpStatus.OK);		
//			}
	//

}
