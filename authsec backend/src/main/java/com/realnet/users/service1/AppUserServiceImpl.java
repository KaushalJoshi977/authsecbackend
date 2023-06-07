package com.realnet.users.service1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.jar.Attributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.realnet.exceptions.ResourceNotFoundException;
import com.realnet.userDTO.User;
import com.realnet.users.entity.ERole;
import com.realnet.users.entity.PasswordResetRequest;
import com.realnet.users.entity.PasswordResetToken;
import com.realnet.users.entity.Role;
import com.realnet.users.entity.Sys_Accounts;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.entity1.AppUserDepartment;
import com.realnet.users.entity1.AppUserDto;
import com.realnet.users.entity1.AppUserPosition;
import com.realnet.users.entity1.AppUserPrinciple;
import com.realnet.users.entity1.AppUserRole;
import com.realnet.users.entity1.Registration;
import com.realnet.users.entity1.SignUp;
import com.realnet.users.repository.RoleRepo;
import com.realnet.users.repository.SysAccountRepo;
import com.realnet.users.repository1.AppUserDepartmentRepository;
import com.realnet.users.repository1.AppUserPositionRepository;
import com.realnet.users.repository1.AppUserRepository;
import com.realnet.users.repository1.AppUserRoleRepository;
import com.realnet.users.repository1.passwordTokenRepository;
import com.realnet.users.response.MessageResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AppUserServiceImpl implements UserDetailsService, AppUserService {

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private SysAccountRepo sysAccountRepo;
	
	

	private passwordTokenRepository passwordTokenRepository;
	private AppUserRepository appUserRepository;
	private AppUserRoleRepository appUserRoleRepository;
	private AppUserPositionRepository appUserPositionRepository;
	private AppUserDepartmentRepository appUserDepartmentRepository;
	private BCryptPasswordEncoder bcryptEncoder;

	@Autowired
	public AppUserServiceImpl(AppUserRepository appUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
			AppUserRoleRepository appUserRoleRepository, AppUserPositionRepository appUserPositionRepository,
			AppUserDepartmentRepository appUserDepartmentRepository, passwordTokenRepository passwordTokenRepository) {
		super();
		this.appUserRepository = appUserRepository;
		this.bcryptEncoder = bCryptPasswordEncoder;
		this.appUserRoleRepository = appUserRoleRepository;
		this.appUserPositionRepository = appUserPositionRepository;
		this.appUserDepartmentRepository = appUserDepartmentRepository;
		this.passwordTokenRepository = passwordTokenRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<AppUser> user = appUserRepository.findByUsername(username);
		if (user.get() == null) {
			throw new UsernameNotFoundException("Invalid Email or password.");
		}
		AppUserPrinciple appUserPrinciple = new AppUserPrinciple(user.get());

		return appUserPrinciple;
	}

	public List<AppUser> getAllAppUsers(Pageable page) {
		Page<AppUser> p = appUserRepository.getallusers(page);
		List<AppUser> l = p.getContent();
		for (AppUser o : l) {
			if (o.getCustomerId() != null) {
				o.setCustomerNumer(appUserRepository.getCustomerNumber(BigDecimal.valueOf(o.getCustomerId())));
			}
			if (o.getUsrGrp() != null) {
				o.setUsrGrpId(o.getUsrGrp().getUsrGrp());
				o.setUsrGrpName(o.getUsrGrp().getGroupName());
				o.setUsrGrp(null);
			}
			if (o.getPositionCode() != null) {
				o.setPositionCodeString(o.getPositionCode().getPositionCode());
				o.setPositionCode(null);
			}
			if (o.getDepartmentCode() != null) {
				o.setDepartmentCodeString(o.getDepartmentCode().getDepartmentCode());
				o.setDepartmentCode(null);
			}
//			if(o.getStatus()!=null) {
//				String s = getStatus("USRST",o.getStatus());
//				if(s!=null) {
//					o.setStatus(s);
//				}
//			}
		}
		return l;
	}

	public AppUser addOneUser(Registration reg) {
		AppUser appUser = new AppUser();
		if (reg.getNew_password().equals(reg.getConfirm_passwordS())) {

			appUser.setEmail(reg.getEmail());
			appUser.setUsername(reg.getEmail());

//		if(appUserRepository.findByUserName(appUser.getUsername())!=null) {
//			return null;
//		}
//		AppUserPosition p = appUserPositionRepository.findById(appUser.getPositionCodeString()).orElse(null);
//		AppUserDepartment d = appUserDepartmentRepository.findById(appUser.getDepartmentCodeString()).orElse(null);	
			AppUserRole r = null;
			if (reg.getUsrGrpId() != null) {
				r = appUserRoleRepository.findById(reg.getUsrGrpId()).orElse(null);
			}
//		if(p!=null){
//			appUser.setPositionCode(p);
//		}
//		if(d!=null) {
//			appUser.setDepartmentCode(d);
//		}
			if (r != null) {
				appUser.setUsrGrp(r);
			}

//		appUser.setCreateby(getLoggedInUser().getFullName());
//		AppUserRole r1=new AppUserRole();
			

			Sys_Accounts account = sysAccountRepo.findById(reg.getAccount_id()).orElseThrow(null);
			appUser.setAccount(account);
			appUser.setFullName(reg.getFirst_name() +" "+ reg.getLast_name());
			appUser.setMob_no(reg.getMob_no());
			appUser.setChangePassw(reg.getNew_password());
			appUser.setUserPassw(bcryptEncoder.encode(reg.getNew_password()));
			appUser.setIsComplete(true);
			appUser.setActive(true);

			appUser.setAccesstype(reg.getAccesstype());
			Set<Role> strRoles = appUser.getRoles();
			Set<Role> roles = new HashSet<>();

			if (strRoles == null) {
				String role1 = "ROLE_Developer";
				Role userRole = roleRepo.findByName(role1);
				roles.add(userRole);
				appUser.setRoles(roles);
			}
		}

		AppUser save = appUserRepository.save(appUser);

		return save;
	}

	public AppUser addOneUserCustom(User ppUser) {
		AppUser appUser = new AppUser();
		appUser.setUsername(ppUser.getUsername());
		appUser.setFullName(ppUser.getFullName());
		appUser.setEmail(ppUser.getEmail());
		appUser.setUserPassw(ppUser.getPassword());
		appUser.setShortName(ppUser.getFirstName());
		appUser.setUsrGrpName(ppUser.getRole());
		appUser.setAbout(ppUser.getAbout());
		appUser.setProvider(ppUser.getProvider().name());
		appUser.setCountry(ppUser.getCountry());
		appUser.setBlocked(false);
		appUser.setIsComplete(true);
		appUser.setActive(true);

		appUser.setChecknumber(ppUser.getChecknumber());
		Sys_Accounts account = sysAccountRepo.findByEmail(ppUser.getEmail());
		if(account != null) {
			appUser.setAccount(account);
		}
		Set<Role> roles = new HashSet<>();
			String role1 = "ROLE_ADMIN";
			Role userRole = roleRepo.findByName(role1);
			roles.add(userRole);
			appUser.setRoles(roles);
		if (appUserRepository.findByEmail(ppUser.getEmail()) != null) {
			return null;
		}
		return appUserRepository.save(appUser);
	}

	public Optional<AppUser> getOneUser(Long id) {
		Optional<AppUser> o = appUserRepository.findById(id);
		if (o.get() != null) {
			// o.get().setCustomerNumer(appUserRepository.getCustomerNumber(o.get().getCustomerId()));
			if (o.get().getCustomerId() != null) {
				o.get().setCustomerNumer(
						appUserRepository.getCustomerNumber(BigDecimal.valueOf(o.get().getCustomerId())));
			}
			if (o.get().getUsrGrp() != null) {
				o.get().setUsrGrpId(o.get().getUsrGrp().getUsrGrp());
				o.get().setUsrGrpName(o.get().getUsrGrp().getGroupName());
				o.get().setUsrGrp(null);
			}
			if (o.get().getPositionCode() != null) {
				o.get().setPositionCodeString(o.get().getPositionCode().getPositionCode());
				o.get().setPositionCode(null);
			}
			if (o.get().getDepartmentCode() != null) {
				o.get().setDepartmentCodeString(o.get().getDepartmentCode().getDepartmentCode());
				o.get().setDepartmentCode(null);
			}
		}
		return o;
	}

	@Override
	public boolean insertOrSaveUser(AppUser appUser) {
		// TODO Auto-generated method stub
		appUserRepository.save(appUser);
		return true;
	}

	@Override
	public List<AppUser> getAllUsers() {
		// TODO Auto-generated method stub
		List<AppUser> users = appUserRepository.findAll();
		// users.forEach(o->o.setCustomerNumer(appUserRepository.getCustomerNumber(o.getCustomerId())));
		return users;
	}

	@Override
	public List<AppUser> getAll() {
		// TODO Auto-generated method stub
		List<AppUser> list = new ArrayList<>();
		appUserRepository.findAll().iterator().forEachRemaining(list::add);
		// list.forEach(o->o.setCustomerNumer(appUserRepository.getCustomerNumber(o.getCustomerId())));
		return list;
	}

	@Override
	public void delete(long id) {
		appUserRepository.deleteById(id);
	}

	@Override
	public AppUser getByUserNameAndPassword(String username, String password) {
		// TODO Auto-generated method stub
		String userPassw = bcryptEncoder.encode(password);
		AppUser user = appUserRepository.findByUsernameAndUserPassw(username, userPassw).orElse(null);
		return user;
	}

	@Override
	public Optional<AppUser> getByUserName(String username) {
		// TODO Auto-generated method stub
		return appUserRepository.findByUsername(username);
	}

	@Override
	public AppUser getByEmail(String email) {
		// TODO Auto-generated method stub
		AppUser user = appUserRepository.findByEmail(email);
		return user;
	}

	@Override
	public Optional<AppUser> getById(Long id) {
		// TODO Auto-generated method stub
		return appUserRepository.findById(id);
	}

	@Override
	public boolean existsByEmail(String email) {
		// TODO Auto-generated method stub
		return appUserRepository.existsByEmail(email);
	}

	@Override
	public String getLoggedInUserEmail() {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			return "noSession";
		}
		return auth.getName();
	}

	@Override
	public Long getLoggedInUserId() {
		// TODO Auto-generated method stub
		String loggedInUserEmail = this.getLoggedInUserEmail();
		AppUser appUser = appUserRepository.findByEmail(loggedInUserEmail);
		Long id = appUser.getUserId();
		return id;
	}

	@Override
	public Long getLoggedInUserAccountId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AppUser getLoggedInUser() {
		String loggedInUserName = this.getLoggedInUserEmail();
		Optional<AppUser> user = appUserRepository.findByUsername(loggedInUserName);
		log.info("getLoggedInUser() : {} ", user.get());
		return user.get();
	}

	@Override
	public AppUser getUserInfoByUserId(Long userId) {
		// TODO Auto-generated method stub
		AppUser user = appUserRepository.findById(userId).orElse(null);
		return user;
	}

	@Override
	public AppUser userResister(AppUser appUser) {
		// TODO Auto-generated method stub
		appUser.setUserPassw(bcryptEncoder.encode(appUser.getUserPassw()));
		return appUserRepository.save(appUser);
	}

	@Override
	public AppUser createUserByAdmin(AppUser appUser) {
		appUser.setUserPassw(bcryptEncoder.encode(appUser.getUserPassw()));
		return appUserRepository.save(appUser);
	}

	@Override
	public boolean deleteById(Long id) {
		// TODO Auto-generated method stub
		if (!appUserRepository.existsById(id)) {
			throw new ResourceNotFoundException("User not exists");
		}
		appUserRepository.deleteById(id);
		return true;
	}

	@Override
	public void sendEmail(String email, Long id, Long checkNo) {
		// TODO Auto-generated method stub

	}

	public User userResister(User user, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public AppUser updateOneUser(AppUser appUser) {
		return appUserRepository.save(appUser);
	}

	public AppUser updateAppUserDto(Long userId,AppUserDto appUserDto) {
		AppUser a = appUserRepository.findById(userId).orElse(null);
		if (a != null) {
			String encodedPass1 = bcryptEncoder.encode(appUserDto.getUserPassw());
			if (a.getUserPassw() != encodedPass1) {
				a.setUserPassw(encodedPass1);
				a.setPwdChangedCnt((long) 0);
				a.setPwdChangedCnt(a.getPwdChangedCnt() + 1);
				a.setLastPwdChangedDate(new Date());
			}
			a.setUsername(appUserDto.getUsername() != null ? appUserDto.getUsername() : a.getUsername());
			a.setTitle(appUserDto.getTitle() != null ? appUserDto.getTitle() : a.getTitle());
			a.setShortName(appUserDto.getShortName() != null ? appUserDto.getShortName() : a.getShortName());
			a.setFullName(appUserDto.getFullName() != null ? appUserDto.getFullName() : a.getFullName());
			a.setStatus(appUserDto.getStatus() != null ? appUserDto.getStatus() : a.getStatus());
			a.setCustomerId(appUserDto.getCustomerId() != null ? appUserDto.getCustomerId() : a.getCustomerId());
			a.setEmail(appUserDto.getEmail() != null ? appUserDto.getEmail() : a.getEmail());
			a.setNotification(
					appUserDto.getNotification() != null ? appUserDto.getNotification() : a.getNotification());

			a.setMob_no(appUserDto.getMob_no());
			a.setActive(appUserDto.isActive());

			if (appUserDto.getUsrGrpId() != null) {
				if (a.getUsrGrp().getUsrGrp() != appUserDto.getUsrGrpId()) {
					AppUserRole role = appUserRoleRepository.findById(appUserDto.getUsrGrpId()).orElse(null);
					if (role != null) {
						a.setUsrGrp(role);
					}
				}
			}
			if (appUserDto.getPositionCodeId() != null) {
				if (!a.getPositionCode().getPositionCode().equals(appUserDto.getPositionCodeId())) {
					AppUserPosition position = appUserPositionRepository.findById(appUserDto.getPositionCodeId())
							.orElse(null);
					if (position != null) {
						a.setPositionCode(position);
					}
				}
			}
			if (appUserDto.getDepartmentCodeId() != null) {
				if (!a.getDepartmentCode().getDepartmentCode().equals(appUserDto.getDepartmentCodeId())) {
					AppUserDepartment department = appUserDepartmentRepository
							.findById(appUserDto.getDepartmentCodeId()).orElse(null);
					if (department != null) {
						a.setDepartmentCode(department);
					}
				}
			}
			a.setUpdateby(getLoggedInUser().getFullName());
			appUserRepository.save(a);
			return a;
		}
		return null;
	}

	public AppUser resetPassword(Long userId, String hash, String newPassword) {
		AppUser a = appUserRepository.findById(userId).orElse(null);
		if (a != null) {
			if (a.getPassword4().equals(hash)) {
				a.setUserPassw(bcryptEncoder.encode(newPassword));
				a.setPwdChangedCnt(a.getPwdChangedCnt() != null ? (long) 1 + a.getPwdChangedCnt() : (long) 1);
				a.setLastPwdChangedDate(new Date());
//				a.setPassword4(null);
				appUserRepository.save(a);
				return a;
			}
			return null;
		}
		return null;
	}

//	By Gaurav
//	RESET PASSWORD WITH CONFIRM PASSWORD

	public AppUser resetPasswordnew(PasswordResetRequest p) {
		AppUser a = appUserRepository.findById(p.getUserId()).orElse(null);

		if (a != null) {
			if (bcryptEncoder.matches(p.getOldPassword(), a.getUserPassw())) {

				if (p.getNewPassword().equals(p.getConfirmPassword())) {

					a.setUserPassw(bcryptEncoder.encode(p.getNewPassword()));
					a.setPwdChangedCnt(a.getPwdChangedCnt() != null ? (long) 1 + a.getPwdChangedCnt() : (long) 1);
					a.setLastPwdChangedDate(new Date());
					a.setChangePassw(p.getNewPassword());

					appUserRepository.save(a);
					return a;
				}
				return null;
			}
			return null;
		}
		return null;
	}

//	By Gaurav
//	FORGOT PASSWORD

	public AppUser forgotpassword(PasswordResetRequest p) {
		AppUser a = appUserRepository.findById(p.getUserId()).orElse(null);

		if (a != null) {

			if (p.getNewPassword().equals(p.getConfirmPassword())) {

				a.setUserPassw(bcryptEncoder.encode(p.getNewPassword()));
				a.setPwdChangedCnt(a.getPwdChangedCnt() != null ? (long) 1 + a.getPwdChangedCnt() : (long) 1);
				a.setLastPwdChangedDate(new Date());
				a.setChangePassw(p.getNewPassword());

				appUserRepository.save(a);
				return a;
			}
			return null;
		}
		return null;

	}

	public String generateRandomHash(int len) {
		String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk" + "lmnopqrstuvwxyz!@#$%&";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		return sb.toString();
	}

	public String getStatus(String tableName, String code) {
		String s = appUserRepository.getStatus(tableName, code);
		if (StringUtils.hasText(s)) {
			return s;
		}
		return null;
	}

	@Override
	public AppUser userResister(AppUser user, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	// method for forgot password by email

	public AppUser findUserByEmail(String email) {
		AppUser user = appUserRepository.findByEmail(email);

		return user;
	}

//	public void createPasswordResetTokenForUser(AppUser user, String token) {
//	    PasswordResetToken myToken = new PasswordResetToken(user,token);
//	    passwordTokenRepository.save(myToken);
//	}

	public void createPasswordResetTokenForUser(AppUser user, String token) {
		AppUser user1 = appUserRepository.findByEmail(user.getEmail());

		PasswordResetToken myToken = new PasswordResetToken(token);
		myToken.setUser(user1);
		passwordTokenRepository.save(myToken);
	}

	// ADD USER VIA ADMIN
	public void adduserviaadmin(AppUser user, String token, String email, Long account_id) {
		Sys_Accounts accounts = sysAccountRepo.findById(account_id).orElseThrow(null);

		user.setRandom_no(token);
		user.setUsername(email);
		user.setEmail(email);
		user.setAccount(accounts);
		
		appUserRepository.save(user);

	}
	// ADD GUEST VIA ADMIN
	public void addguestviaadmin(AppUser user, String token, String email, Long account_id,Long access_duration) {
		Sys_Accounts accounts = sysAccountRepo.findById(account_id).orElseThrow(null);

		user.setRandom_no(token);
		user.setUsername(email);
		user.setEmail(email);
		user.setAccount(accounts);
		user.setAccess_duration(access_duration);
		appUserRepository.save(user);

	}

	public String validatePasswordResetToken(String token) {
		final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);

		return !isTokenFound(passToken) ? "invalidToken" : isTokenExpired(passToken) ? "expired" : null;
	}

	private boolean isTokenFound(PasswordResetToken passToken) {
		return passToken != null;
	}

	private boolean isTokenExpired(PasswordResetToken passToken) {
		final Calendar cal = Calendar.getInstance();
		return passToken.getExpiryDate().before(cal.getTime());
	}

	public void changeUserPassword(AppUser user, String newPassword) {
		user.setUserPassw(bcryptEncoder.encode(newPassword));
		appUserRepository.save(user);
	}

	public AppUser getUserByPasswordResetToken(String token) {
		AppUser token2 = appUserRepository.findByToken(token);
		return token2;
	}

	// end method for forgot password
}
