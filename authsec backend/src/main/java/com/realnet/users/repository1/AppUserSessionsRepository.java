package com.realnet.users.repository1;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.users.entity1.AppUser;
import com.realnet.users.entity1.AppUserSessions;
import com.realnet.users.entity1.AppUserSessionsCompositeKey;

@Repository
public interface AppUserSessionsRepository extends JpaRepository<AppUserSessions,AppUserSessionsCompositeKey>{
	
	List<AppUserSessions> findByUserId(AppUser userId);
	
	@Query(value = "SELECT * FROM realnet_CNSBE.sec_user_sessions where session_id = ?1",nativeQuery = true)
	AppUserSessions findBySessionId(String sessionId);
}
