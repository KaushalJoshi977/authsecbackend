package com.realnet.users.service1;

import java.util.List;
import java.util.Optional;


import com.realnet.users.entity1.AppUser;

public interface AppUserService {
	public boolean insertOrSaveUser(AppUser appUser);

	public List<AppUser> getAllUsers();

	// company registration
	List<AppUser> getAll();

	void delete(long id);

	// Optional<User> getByUserNameAndPassword(String username, String password);
	AppUser getByUserNameAndPassword(String username, String userPassw);

	Optional<AppUser> getByUserName(String username);

	AppUser getByEmail(String email);

	Optional<AppUser> getById(Long id);

	boolean existsByEmail(String email);

	// update by username
//	User updateByEmail(String email, UserProfileDTO userProfile);

	// get logged in user details
	String getLoggedInUserEmail();

	Long getLoggedInUserId();
	
	Long getLoggedInUserAccountId();

	AppUser getLoggedInUser();

	AppUser getUserInfoByUserId(Long userId);

	// creating new user (sign up user as ADMIN)
	AppUser userResister(AppUser appUser);

	// --- USERS ADDED BY ADMIN ---
	AppUser createUserByAdmin(AppUser appUser);
//	List<User> getUsersByAccountId(Long id);
	//List<User> getUsersByCompanyId(Long id); // need mod
//	public User updateById(Long id, User UserRequest);
	boolean deleteById(Long id);
	
//	boolean changePassword(String oldPassword, String newPassword);
	
	public void sendEmail(String email,Long id,Long checkNo);

	//public User adddata(AboutWork about);

//	public User updateById(Long id, @Valid AboutWork aboutWork);
//	public User updateById2(Long id, @Valid AboutWork aboutWork);

	public AppUser userResister(AppUser user, Long id);

	
//	User updateByIdWorkingId(Long id, @Valid AboutWork aboutWork);

//	User updateByMangingWork(Long id, @Valid AboutWork aboutWork);

//	public User userResisteremail(User user);
//
//	public void sendEmail2(String email1, Long userId, Long checknumber);
//	
//	public void sendEmail3(String email2, Long userId, Long checknumber);
//    
//	public void sendEmail4(String email3, Long userId, Long checknumber);
//	
//	public void sendEmail5(String email4, Long userId, Long checknumber);
//	
	//public boolean exitbychecknumber(Long userId, Long checknumber);
	//public User exitbychecknumber(Long userId, Long checknumber);

//	public User save(User user);
	
}
