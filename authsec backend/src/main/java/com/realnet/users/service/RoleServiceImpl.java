package com.realnet.users.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.realnet.exceptions.InvalidUserDataException;
import com.realnet.exceptions.ResourceNotFoundException;
import com.realnet.users.entity.Role;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.repository.RoleRepo;
import com.realnet.users.repository1.AppUserRepository;
import com.realnet.users.service1.AppUserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService { // UserDetailsService,

	@Autowired
	private AppUserRepository userRepository;
	@Autowired(required=false)
	private AppUserService userService;
	
	

	@Autowired
	private RoleRepo roleRepository;

	@Override
	public List<Role> getRoles() {
		return roleRepository.findAll();
	}
	
	@Override
	public Page<Role> getAll(Pageable page) {
		return roleRepository.findAll(page);
	}

	@Override
	public Role getRoleById(Long id) {
		Role role = roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Role not found :: " + id));
		return role;
	}

	@Override
	public ResponseEntity<Object> addRole(Role role) {
		Role newRole = new Role();
		newRole.setName(role.getName());
		newRole.setDescription(role.getDescription());
		Set<Role> roleList = new HashSet<>();
		roleList.add(newRole);
		// for(int i=0; i< role.getUsers().size(); i++){
		for (AppUser user : role.getAppusers()) {
			// if(!userRepository.findByEmail(role.getUsers().get(i).getEmail()).isPresent())
			// {
			AppUser usr = userRepository.findByEmail(user.getEmail());
			if (usr != null) {
				AppUser newUser = user;
				newUser.setRoles(roleList);
				AppUser savedUser = userRepository.save(newUser);
				if (!userRepository.findById(savedUser.getUserId()).isPresent())
					return ResponseEntity.unprocessableEntity().body("Role Creation Failed");
			} else
				return ResponseEntity.unprocessableEntity().body("User with email Id is already Present");
		}
		return ResponseEntity.ok("Successfully created Role");

	}

	@Override
	public ResponseEntity<Object> deleteRole(Long id) {
		if (roleRepository.findById(id).isPresent()) {
			if (roleRepository.getOne(id).getAppusers().size() == 0) {
				roleRepository.deleteById(id);
				if (roleRepository.findById(id).isPresent()) {
					return ResponseEntity.unprocessableEntity().body("Failed to delete the specified record");
				} else
					return ResponseEntity.ok().body("Successfully deleted specified record");
			} else
				return ResponseEntity.unprocessableEntity()
						.body("Failed to delete,  Please delete the users associated with this role");
		} else
			return ResponseEntity.unprocessableEntity().body("No Records Found");
	}

	@Override
	public ResponseEntity<Object> updateRole(Long id, Role role) {
		if (roleRepository.findById(id).isPresent()) {
			Role newRole = roleRepository.findById(id).get();
			newRole.setName(role.getName());
			newRole.setDescription(role.getDescription());
			Role savedRole = roleRepository.save(newRole);
			if (roleRepository.findById(savedRole.getId()).isPresent())
				return ResponseEntity.accepted().body("Role saved successfully");
			else
				return ResponseEntity.badRequest().body("Failed to update Role");
		} else
			return ResponseEntity.unprocessableEntity().body("Specified Role not found");
	}


	public void addUserRole(AppUser user, long roleId) {
		Optional<Role> roleOpt = roleRepository.findById(roleId);
		if (!roleOpt.isPresent()) {
			throw new ResourceNotFoundException("Role cannot be null");
		}
		user.getRoles().add(roleOpt.get());
	}

	/* ==== WORKING CODE ==== */
	@Override
	public AppUser addRole(Long id, Long roleId) {
		// check user
		Optional<AppUser> userOpt = userRepository.findById(id);
		if (!userOpt.isPresent()) {
			throw new ResourceNotFoundException(String.format("User not found with Id = %s", id));
		}
		AppUser user = userOpt.get();
		// check role
		Optional<Role> roleOpt = roleRepository.findById(roleId);
		if (!roleOpt.isPresent()) {
			throw new ResourceNotFoundException(String.format("Role not found with Id = %s", roleId));
		}
		Role role = roleOpt.get();
		
		// ==== MOD ====
		// check if user already have that role...
		if(user.getRoles().contains(role)) {
			throw new InvalidUserDataException(String.format("Role %s already exists with the User = %s", roleId, id));
		}
		user.getRoles().add(role);
//		user.setUpdateby(userService.getLoggedInUserId());
		userRepository.save(user);
		log.info(String.format("Added role %s on user id = %s", role.getName(), user.getUserId()));
		return user;
	}
	@Override
	public AppUser removeRole(Long id, Long roleId) {
		// check user
		Optional<AppUser> userOpt = userRepository.findById(id);
		if (!userOpt.isPresent()) {
			throw new ResourceNotFoundException(String.format("User not found with Id = %s", id));
		}
		AppUser user = userOpt.get();
		// check role
		Optional<Role> roleOpt = roleRepository.findById(roleId);
		if (!roleOpt.isPresent()) {
			throw new ResourceNotFoundException(String.format("Role not found with Id = %s", roleId));
		}
		Role role = roleOpt.get();
		user.getRoles().remove(role);
		
		// === MOD NEEDED
		if(user.getRoles().isEmpty()) {
			throw new InvalidUserDataException(String.format("User %s Must Have a Single Role", id));
			//user.setRoles(roleRepository.findByName("USER"));
		}
//		user.setUpdatedBy(userService.getLoggedInUserId());
		userRepository.save(user);
		log.info(String.format("Removed role %s on user id = %s", role.getName(), user.getUserId()));
		return user;
	}
}
