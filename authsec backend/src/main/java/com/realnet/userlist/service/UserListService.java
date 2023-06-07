package com.realnet.userlist.service;

import java.util.List;

import com.realnet.userlist.entity.UserList;


public interface UserListService {
	
	public UserList addToDb(UserList userList);
	
	public UserList updateToDb(UserList userList);
	
	public UserList getOneById(Long id);
	
	public List<UserList> getAllFromDb();
	
	public void deleteFromDbById(Long id);

}
