package com.realnet.fnd.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.config.TokenProvider;
import com.realnet.fnd.entity.Rn_Function_Register;
import com.realnet.fnd.entity.Rn_Main_Menu;
import com.realnet.fnd.entity.Rn_Menu_Group_Header;
import com.realnet.fnd.entity.Rn_Menu_Group_Line;
import com.realnet.fnd.entity.Rn_Menu_Register;
import com.realnet.fnd.entity.Rn_Sub_Menu;
import com.realnet.fnd.response.Rn_Forms_SetupResponse;
import com.realnet.fnd.response.Rn_Menu_Group_Response;
import com.realnet.fnd.service.Rn_Function_Register_Service;
import com.realnet.fnd.service.Rn_Menu_Group_Service;
import com.realnet.fnd.service.Rn_Menu_Register_Service;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { "Rn_Menu_Group" })
@CrossOrigin("*")
public class Rn_Menu_Group_Controller {

	@Autowired
	private Rn_Menu_Group_Service rn_menu_group_service;

	@Autowired
	private Rn_Menu_Register_Service rn_menu_register_service;

	@Autowired
	private Rn_Function_Register_Service rn_function_register_service;

	@Autowired
	private TokenProvider tokenProvider;
	
	@Autowired(required=false)
	private AppUserService userService;

	// GET ALL
	@ApiOperation(value = "List of Menu Group", response = Rn_Forms_SetupResponse.class)
	@GetMapping("/menu-group")
	public Rn_Menu_Group_Response getMenuGroups(
			@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
			@RequestParam(value = "size", defaultValue = "20", required = false) Integer size) {
		// sorted data
		Pageable paging = PageRequest.of(page, size, Sort.by("createdAt").descending());
		Page<Rn_Menu_Group_Header> result = rn_menu_group_service.getAll(paging);

		Rn_Menu_Group_Response resp = new Rn_Menu_Group_Response();
		resp.setPageStats(result, true);
		resp.setItems(result.getContent());
		return resp;
	}

	// GET BY ID
	@ApiOperation(value = "Get a Menu Group", response = Rn_Menu_Group_Header.class)
	@GetMapping("/menu-group/{id}")
	public ResponseEntity<Rn_Menu_Group_Header> getMenuGroupById(@PathVariable(value = "id") Long id) {
		Rn_Menu_Group_Header rn_menu_group = rn_menu_group_service.getById(id);
		return ResponseEntity.ok().body(rn_menu_group);
	}

	// SAVE
	@ApiOperation(value = "Save A New Menu Group", response = Rn_Menu_Group_Header.class)
	@PostMapping("/menu-group")
	public ResponseEntity<Rn_Menu_Group_Header> createMenuGroup(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
			@Valid @RequestBody Rn_Menu_Group_Header rn_menu_group_header) {
//		String userId = tokenUtil.getUserId(authToken);
//		rn_menu_group_header.setCreatedBy(userId);
//		rn_menu_group_header.setAccountId(userId);
		Rn_Menu_Group_Header savedMenu_group = rn_menu_group_service.save(rn_menu_group_header);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedMenu_group);
	}

	// UPDATE
	@ApiOperation(value = "Update A Menu Group", response = Rn_Menu_Group_Header.class)
	@PutMapping("/menu-group/{id}")
	public ResponseEntity<Rn_Menu_Group_Header> updateMenuGroup(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
			@PathVariable(value = "id") Long id, @Valid @RequestBody Rn_Menu_Group_Header rn_menu_group_header) {
//		String userId = tokenUtil.getUserId(authToken);
//		rn_menu_group_header.setUpdatedBy(userId);
		Rn_Menu_Group_Header updatedMenu_Group = rn_menu_group_service.updateById(id, rn_menu_group_header);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedMenu_Group);
	}

	// DELETE
	@DeleteMapping("/menu-group/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteMenuGroup(@PathVariable(value = "id") Long id) {
		boolean deleted = rn_menu_group_service.deleteById(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		// response.put("deleted", deleted);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	// ---- working code ----
	@ApiOperation(value = "Menus For Individual Group", response = Rn_Forms_SetupResponse.class)
	@GetMapping("/load-menus")
	public ResponseEntity<?> getMenus(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken) {
		//String userId = tokenProvider.getEmailFromToken(authToken);
		AppUser user = userService.getLoggedInUser();
		System.out.println("getMenus controller " + user);
		Long menu_group_id = user.getUsrGrp().getUsrGrp();
		System.out.println("getMenus() MENU GROUP ID = " + menu_group_id);
		
		// from user table, get menu_group_id
		//int menu_group_id = 1;

		// rn_menu_group_service.loadmenuGroupByUser(menu_group_id);
		// or

		// Rn_Main_Menu rn_main_menu = new Rn_Main_Menu();
		// Rn_Sub_Menu rn_sub_menu = new Rn_Sub_Menu();

		List<Rn_Main_Menu> mainMenuList = new ArrayList<Rn_Main_Menu>();
		// List<Rn_Sub_Menu> subMenuList = new ArrayList<Rn_Sub_Menu>();

		Rn_Menu_Group_Header menu_group_header = rn_menu_group_service.getById(menu_group_id);
		List<Rn_Menu_Group_Line> menu_group_lines = menu_group_header.getMenu_group_lines();

		// menu_group loop start
		for (Rn_Menu_Group_Line menu_group_line : menu_group_lines) {
			// menu id will be diff
			int menu_id = menu_group_line.getMenu_id();

			// HEADER
			Rn_Menu_Register rn_menu_register = rn_menu_register_service.getById(menu_id);
			String main_menu_name = rn_menu_register.getMain_menu_name();
			String main_menu_action_name = rn_menu_register.getMain_menu_action_name();
			String main_menu_icon = rn_menu_register.getMain_menu_icon();
			// boolean main_menu_flag = rn_menu_register.getEnable_flag();

			Rn_Main_Menu rn_main_menu = new Rn_Main_Menu();
			rn_main_menu.setMenu_name(main_menu_name);
			rn_main_menu.setMenu_action_link(main_menu_action_name);
			rn_main_menu.setMenu_icon(main_menu_icon);

			// LINE
			// List<Rn_Sub_Menu> subMenuList = new ArrayList<Rn_Sub_Menu>();
            System.out.println("Before Fun Register");
			List<Rn_Function_Register> functions = rn_function_register_service.getByMenuId(menu_id);
			System.out.println("After Fun Register");
			if (!functions.isEmpty()) {
				List<Rn_Sub_Menu> subMenuList = new ArrayList<Rn_Sub_Menu>();
				for (Rn_Function_Register function : functions) {
					String function_name = function.getFunction_name();
					String function_action_name = function.getFunction_action_name();
					String function_icon = function.getFunction_icon();
					// LocalDate function_end_date = function.getEnd_date();
					// String function_flag = function.getEnable_flag();

					Rn_Sub_Menu rn_sub_menu = new Rn_Sub_Menu();
					rn_sub_menu.setSub_menu_name(function_name);
					rn_sub_menu.setSub_menu_action_link(function_action_name);
					rn_sub_menu.setSub_menu_icon(function_icon);

					subMenuList.add(rn_sub_menu);
				} // func loop end
				rn_main_menu.setSub_menus(subMenuList);
			}
			// rn_main_menu.setSub_menus(sub_menus);
			mainMenuList.add(rn_main_menu);
		} // menu_group loop end
		

		return ResponseEntity.ok().body(mainMenuList);
	}

}
