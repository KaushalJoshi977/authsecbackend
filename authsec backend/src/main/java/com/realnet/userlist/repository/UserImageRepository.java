package com.realnet.userlist.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.realnet.userlist.entity.UserImage;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {
	
	Optional<UserImage> findByid(String user_id);
	
	Optional<UserImage> findByFilename(String filename);
	
	Optional<UserImage> findByUser(String user);

}
