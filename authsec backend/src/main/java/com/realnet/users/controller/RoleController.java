package com.realnet.users.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.users.entity.Role;
import com.realnet.users.repository.RoleRepo;
import com.realnet.users.service.RoleService;
import com.realnet.users.service1.AppUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api") // , produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { "Role Assignment" })
public class RoleController {

	@Autowired(required=false)
	private AppUserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private RoleRepo roleRepo;

	// GET profile details
	@ApiOperation(value = "Gets All Roles", response = Role.class)
	@GetMapping("/roles")
	public ResponseEntity<?> getAllRoles(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
			@RequestParam(value = "size", defaultValue = "20", required = false) Integer size) {
		//Pageable paging = PageRequest.of(page, size, Sort.by("created_at").descending());
		//Page<Role> result = roleService.getAll(paging);
		List<Role> roles = roleService.getRoles();
		return ResponseEntity.ok().body(roles);
	}

	// GET USER BY ID ADDED BY ADMIN
	@ApiOperation(value = "Get Roles Id", response = Role.class)
	@GetMapping("/roles/{id}")
	public ResponseEntity<?> getRolesById(@PathVariable(value = "id") Long id) {
		Role role = roleService.getRoleById(id);
		return ResponseEntity.ok().body(role);
	}

	/* ========WORKING CODE ======= */
	// add or remove a Role on a user
	@GetMapping("org-users/{id}/add-roles/{roleId}")
	public ResponseEntity<?> addRole(@PathVariable("id") Long id, @PathVariable("roleId") Long roleId) {
		roleService.addRole(id, roleId);
		Map<String, String> res = new HashMap<String, String>();
		res.put("message", "Role Added Succcessfully");
		return ResponseEntity.ok(res);
	}

	@GetMapping("org-users/{id}/remove-roles/{roleId}")
	public ResponseEntity<?> removeRole(@PathVariable("id") Long id, @PathVariable("roleId") Long roleId) {
		roleService.removeRole(id, roleId);
		Map<String, String> res = new HashMap<String, String>();
		res.put("message", "Role removed Succcessfully");
		return ResponseEntity.ok(res);
	}
	
//	ADD NEW ROLE
	@PostMapping("/roles")
	public ResponseEntity<?> add(@RequestBody Role role) {
		Role save = roleRepo.save(role);
		
		return ResponseEntity.ok(save);
	}

	// ===================================

//    @ApiOperation(value = "Assign roles to an user", response = ServiceResponse.class)
//    @ApiResponses(value = {
//        @ApiResponse(code = 200, message = "Roles successfully assigned to user!"), //response = UserResponse.class),
//        @ApiResponse(code = 401, message = Constants.UNAUTHORIZED_MESSAGE), //response = BadRequestResponse.class),
//        @ApiResponse(code = 403, message = Constants.FORBIDDEN_MESSAGE), //response = BadRequestResponse.class),
//        @ApiResponse(code = 422, message = Constants.INVALID_DATA_MESSAGE) //, response = InvalidDataResponse.class),
//    })
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PutMapping("/assign")
//    public ResponseEntity<?> assignRoles(@Valid @RequestBody RoleUpdateDto roleUpdateDto) {
//    	User user = userService.getById(roleUpdateDto.getUserId());
//
//        Arrays.stream(roleUpdateDto.getRoles()).forEach(role -> {
//            Role roleObject = roleRepo.findByName(role);
//            //if (roleObject != null && !user.hasRole(role)) {
//                user.addRole(roleObject);
//            }
//        });
//
//        userService.update(user);
//
//        return ResponseEntity.ok().body(user);
//    }
//
//    @ApiOperation(value = "Assign roles to an user", response = ServiceResponse.class)
//    @ApiResponses(value = {
//        @ApiResponse(code = 200, message = "Roles successfully assigned to user!"), // response = UserResponse.class),
//        @ApiResponse(code = 401, message = Constants.UNAUTHORIZED_MESSAGE), // response = BadRequestResponse.class),
//        @ApiResponse(code = 403, message = Constants.FORBIDDEN_MESSAGE), //response = BadRequestResponse.class),
//        @ApiResponse(code = 422, message = Constants.INVALID_DATA_MESSAGE)//, response = InvalidDataResponse.class),
//    })
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PutMapping("/revoke")
//    public ResponseEntity revokeRoles(@Valid @RequestBody RoleUpdateDto roleUpdateDto) {
//        User user = userService.findById(roleUpdateDto.getUserId());
//
//        Arrays.stream(roleUpdateDto.getRoles()).forEach(role -> {
//            Role roleObject = roleService.findByName(role);
//
//            if (roleObject != null && user.hasRole(role)) {
//                user.removeRole(roleObject);
//            }
//        });
//
//        userService.update(user);
//
//        return ResponseEntity.ok().body(user);
//    }
//}

}
