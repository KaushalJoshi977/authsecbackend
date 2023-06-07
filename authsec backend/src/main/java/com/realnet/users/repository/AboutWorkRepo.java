package com.realnet.users.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realnet.session.entity.AboutWork;
import com.realnet.users.entity.Email;

@Repository
public interface AboutWorkRepo  extends JpaRepository<AboutWork,Long>{


//	Optional<AboutWork> findById(Long id);
	
	

}
