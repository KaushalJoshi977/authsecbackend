package com.realnet.users.controller1;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.config.EmailService;
import com.realnet.userDTO.User;
import com.realnet.users.entity.PasswordResetRequest;
import com.realnet.users.entity.PasswordResetToken;
import com.realnet.users.entity.Role;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.entity1.AppUserDto;
import com.realnet.users.entity1.AppUserRole;
import com.realnet.users.entity1.Registration;
import com.realnet.users.entity1.SignUp;
import com.realnet.users.repository.RoleRepo;
import com.realnet.users.repository1.AppUserRepository;
import com.realnet.users.repository1.AppUserRoleRepository;
import com.realnet.users.response.MessageResponse;
import com.realnet.users.service1.AppUserServiceImpl;
import com.realnet.utils.Port_Constant;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api")
public class AppUserController {
	@Autowired
	private AppUserServiceImpl userService;
	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;
	@Autowired
	private AppUserRepository appUserRepository;

	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private AppUserRoleRepository appUserRoleRepository;

	private AppUserServiceImpl appUserServiceImpl;
	private EmailService emailService;

	@Autowired
	public AppUserController(AppUserServiceImpl appUserServiceImpl, EmailService emailService) {
		super();
		this.appUserServiceImpl = appUserServiceImpl;
		this.emailService = emailService;
	}

	@GetMapping("/getAllAppUser")
	public ResponseEntity<?> getAllUsers(
			@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
			@RequestParam(value = "size", defaultValue = "1000", required = false) Integer size) {
		Pageable p = PageRequest.of(page, size);
		List<AppUser> u = appUserServiceImpl.getAllAppUsers(p);
		return new ResponseEntity<>(u, HttpStatus.OK);
	}

	@GetMapping("/getOneAppUser/{id}")
	public ResponseEntity<?> getOneAppUser(@PathVariable("id") Long id) {
		Optional<AppUser> a = appUserServiceImpl.getOneUser(id);
		if (a.get() != null) {
			return new ResponseEntity<>(a.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>("User not found", HttpStatus.OK);
	}

	@PostMapping("/addOneAppUser")
	public ResponseEntity<?> addOneUser(@RequestBody Registration reg) {
		if (appUserRepository.findByEmail(reg.getEmail()) != null) {
			return ResponseEntity.badRequest().body(new MessageResponse("email already exist"));
		}
		AppUser a = appUserServiceImpl.addOneUser(reg);
		return new ResponseEntity<>(a, HttpStatus.OK);
	}

	@GetMapping("/test")
	public void test() {
		System.out.println("Aaa");
	}

	@PostMapping("/addOneAppUserCustom")
	public ResponseEntity<?> addOneUserCustom(@RequestBody User appUser) {
		System.out.println("aaaaa");
		System.out.println(appUser);
		AppUser a = appUserServiceImpl.addOneUserCustom(appUser);
		System.out.println(a);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/updateAppUser")
	public ResponseEntity<?> updateOneUser(@RequestBody AppUser appUser) {
		AppUser a = appUserServiceImpl.updateOneUser(appUser);
		return new ResponseEntity<>(a, HttpStatus.OK);
	}
//   update app user
	@PutMapping("/updateAppUserDto/{userId}")
	public ResponseEntity<?> updateAppUser(@PathVariable Long userId,@RequestBody AppUserDto appUserDto) {
		AppUser a = appUserServiceImpl.updateAppUserDto(userId,appUserDto);
		if (a != null) {
			return new ResponseEntity<>(a, HttpStatus.OK);
		}
		return new ResponseEntity<>("No user found", HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/resetPasswordEmail/{userId}")
	public ResponseEntity<?> sendEmailForResetPassword(@PathVariable("userId") Long userId) {
		AppUser a = appUserServiceImpl.getUserInfoByUserId(userId);
		// random string
		String hash = appUserServiceImpl.generateRandomHash(8);
		if (a != null) {
			String email = a.getEmail();
			String subject = "Pass reset";
			String link = "http://" + hash + "/" + String.valueOf(a.getUserId());
			emailService.sendSimpleMessage(null, email, subject, link);
			return new ResponseEntity<>("Email sent success", HttpStatus.OK);
		}
		return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/resetPassword/{userId}/{hash}")
	public ResponseEntity<?> resetPassword(@PathVariable("userId") Long userId, @PathVariable("hash") String hash,
			@RequestBody PasswordResetRequest passwordResetRequest) {

		AppUser a = appUserServiceImpl.resetPassword(userId, hash, passwordResetRequest.getNewPassword());
		if (a != null) {
			return new ResponseEntity<>("Password Reset Successfull", HttpStatus.OK);
		}
		return new ResponseEntity<>("Password Not changed", HttpStatus.BAD_REQUEST);
	}

	// By Gk
	// ADD USER VIA ADMIN
	@ApiOperation(value = "Add user Via Admin")
	@PostMapping("/userviaadmin")
	public ResponseEntity<?> userviaadmin(HttpServletRequest request, @RequestParam("email") String email) {
		AppUser loggedInUser = userService.getLoggedInUser();
		Long account_id = loggedInUser.getAccount().getAccount_id();

		AppUser user = userService.findUserByEmail(email);
		if (user != null) {
			return ResponseEntity.badRequest().body(new MessageResponse(email + " already exist"));
		} else {
			String token = UUID.randomUUID().toString();
			AppUser appUser = new AppUser();
			userService.adduserviaadmin(appUser, token, email, account_id);

			String subject = "add user";
			// String url = "http://localhost:4200/#/adduser/" +token;
			// String url = "http://surecns.ml:30165/#/adduser/" +token;
			String url = "http://" + Port_Constant.LOCAL_HOST + ":" + Port_Constant.BACKEND_PORT_9191 + "/api"
					+ "/admin/adduser/" + token;
			emailService.adduserviaadmin(email, subject, url);
			return new ResponseEntity<>("Email sent success", HttpStatus.OK);
		}

	}

	// get email make user VIA ADMIN
	@PostMapping("/admin/adduser/{token}")
	public ResponseEntity<?> saveuser(@PathVariable String token, @Valid @RequestBody SignUp signUp) {

		AppUser a = appUserRepository.findByToken(token);

		if (a != null) {

			if (signUp.getPassword().equals(signUp.getConfirm_passwordS())) {

				a.setFullName(signUp.getFirst_name() + " " + signUp.getLast_name());
				a.setMob_no(signUp.getMob_no());
				a.setUserPassw(bcryptEncoder.encode(signUp.getPassword()));
				a.setPwdChangedCnt(a.getPwdChangedCnt() != null ? (long) 1 + a.getPwdChangedCnt() : (long) 1);
				a.setLastPwdChangedDate(new Date());
				a.setChangePassw(signUp.getPassword());
				a.setIsComplete(true);
				a.setActive(true);

				long r = 42;
				AppUserRole grp = appUserRoleRepository.findById(r).orElse(null);
				a.setUsrGrp(grp);
//				Set<Role> strRoles = a.getRoles();
				Set<Role> roles = new HashSet<>();

//				if (strRoles == null) {
				String role1 = "ROLE_Developer";
				Role userRole = roleRepo.findByName(role1);
				roles.add(userRole);
				a.setRoles(roles);
//				}
				a.setRandom_no(null);
				AppUser save = appUserRepository.save(a);
				return new ResponseEntity<>(save, HttpStatus.CREATED);

			}
			return ResponseEntity.badRequest().body(new MessageResponse("password and confirm password not match"));
		}

		return ResponseEntity.ok().body(new MessageResponse("registration already done"));
	}

	// By Gk
	// ADD GUEST VIA ADMIN
	@ApiOperation(value = "Add user Via Admin")
	@PostMapping("/guest_via_admin")
	public ResponseEntity<?> guestviaadmin(HttpServletRequest request, @RequestParam("email") String email,
			@RequestParam("access_duration") Long access_duration) {

		AppUser loggedInUser = userService.getLoggedInUser();
		Long account_id = loggedInUser.getAccount().getAccount_id();

		AppUser user = userService.findUserByEmail(email);
		if (user != null) {
			return ResponseEntity.badRequest().body(new MessageResponse(email + " already exist"));
		} else {
			String token = UUID.randomUUID().toString();
			AppUser appUser = new AppUser();
			userService.addguestviaadmin(appUser, token, email, account_id, access_duration);

			String subject = "add guest";
			// String url = "http://localhost:4200/#/addguest/" +token;
			// String url = "http://surecns.ml:30165/#/addguest/" +token;
			String url = "http://" + Port_Constant.LOCAL_HOST + ":" + Port_Constant.BACKEND_PORT_9191 + "/api"
					+ "/admin/addguest/" + token;
			emailService.adduserviaadmin(email, subject, url);
			return new ResponseEntity<>("Email sent success", HttpStatus.OK);
		}

	}

	// get email ADD GUEST VIA ADMIN
	@PostMapping("/admin/addguest/{token}")
	public ResponseEntity<?> saveguest(@PathVariable String token, @Valid @RequestBody SignUp signUp) {

		AppUser a = appUserRepository.findByToken(token);

		if (a != null) {

			if (signUp.getPassword().equals(signUp.getConfirm_passwordS())) {

				a.setFullName(signUp.getFirst_name() + " " + signUp.getLast_name());
				a.setMob_no(signUp.getMob_no());
				a.setUserPassw(bcryptEncoder.encode(signUp.getPassword()));
				a.setPwdChangedCnt(a.getPwdChangedCnt() != null ? (long) 1 + a.getPwdChangedCnt() : (long) 1);
				a.setLastPwdChangedDate(new Date());
				a.setChangePassw(signUp.getPassword());
				a.setIsComplete(true);
				a.setActive(true);
				long r = 45;
				AppUserRole grp = appUserRoleRepository.findById(r).orElse(null);
				a.setUsrGrp(grp);
//					Set<Role> strRoles = a.getRoles();
				Set<Role> roles = new HashSet<>();

//					if (strRoles == null) {
				String role1 = "ROLE_GUEST";
				Role userRole = roleRepo.findByName(role1);
				roles.add(userRole);
				a.setRoles(roles);
//					}
				a.setRandom_no(null);
				AppUser save = appUserRepository.save(a);
				return new ResponseEntity<>(save, HttpStatus.CREATED);

			}
			return ResponseEntity.badRequest().body(new MessageResponse("password and confirm password not match"));
		}

		return ResponseEntity.ok().body(new MessageResponse("registration already done"));
	}

	// GET ALL USER FOR CHAT APP
	@GetMapping("/token/getalluser_chat")
	public ResponseEntity<?> getAllUserSforchat() {

		List<AppUser> u = appUserRepository.findAll();
		return new ResponseEntity<>(u, HttpStatus.OK);
	}

	// GET ALL USER FOR CHAT APP
	@GetMapping("/token/getuser_chat/{userName}")
	public ResponseEntity<?> getUserSforchat(@PathVariable String userName) {

		AppUser u = appUserRepository.findByUserName(userName);
		return new ResponseEntity<>(u, HttpStatus.OK);
	}

	@GetMapping("/token/getchatuser_byid/{id}")
	public ResponseEntity<?> getChatUserById(@PathVariable Long id) {
		AppUser u = appUserRepository.findByUserId(id);
		return new ResponseEntity<>(u, HttpStatus.OK);
	}

	@DeleteMapping("/delete_usr/{user_id}")
	public ResponseEntity<?> deleteusr(@PathVariable Long user_id) {
		Optional<AppUser> r = appUserRepository.findById(user_id);
		r.get().setActive(false);
		AppUser save = appUserRepository.save(r.get());
		
			return new ResponseEntity<>(save, HttpStatus.OK);

		

	}
}
