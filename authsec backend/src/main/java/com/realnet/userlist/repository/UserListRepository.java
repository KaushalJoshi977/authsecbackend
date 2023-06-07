package com.realnet.userlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.realnet.userlist.entity.UserList;

public interface UserListRepository extends JpaRepository<UserList, Long> {

}
