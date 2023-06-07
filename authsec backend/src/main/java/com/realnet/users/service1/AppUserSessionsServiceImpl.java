package com.realnet.users.service1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realnet.users.entity1.AppUserSessions;
import com.realnet.users.repository1.AppUserSessionsRepository;

@Service
public class AppUserSessionsServiceImpl {
	private AppUserSessionsRepository appUserSessionsRepository;
	@Autowired
	public AppUserSessionsServiceImpl(AppUserSessionsRepository appUserSessionsRepository) {
		super();
		this.appUserSessionsRepository = appUserSessionsRepository;
	}
	public AppUserSessions newSession(AppUserSessions appUserSessions) {
		return appUserSessionsRepository.save(appUserSessions);
	}
	public AppUserSessions add(AppUserSessions session) {
		return appUserSessionsRepository.save(session);
	}
}
