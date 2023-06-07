package com.realnet.userlist.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realnet.userlist.entity.UserList;
import com.realnet.userlist.repository.UserListRepository;
import com.realnet.userlist.service.UserListService;

@Service
public class UserListServiceImpl implements UserListService {
	
	@Autowired
	private UserListRepository userListRepository;

	@Override
	public UserList addToDb(UserList userList) {
		UserList save = this.userListRepository.save(userList);
		return save;
	}

	@Override
	public UserList updateToDb(UserList userList) {
		UserList save = this.userListRepository.save(userList);
		return save;
	}

	@Override
	public UserList getOneById(Long id) {
		Optional<UserList> findById = this.userListRepository.findById(id);
		return findById.get();
	}

	@Override
	public List<UserList> getAllFromDb() {
		List<UserList> findAll = this.userListRepository.findAll();
		return findAll;
	}

	@Override
	public void deleteFromDbById(Long id) {
		this.userListRepository.deleteById(id);
		System.out.println("Deletion success...");
	}

}
