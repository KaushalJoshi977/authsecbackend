package com.realnet.users.service;

import com.realnet.session.entity.AboutWork;
import com.realnet.users.entity.Email;

public interface AboutWorkService {
	public AboutWork adddata(AboutWork data);

	public AboutWork updateById(Long id, AboutWork about);
	public AboutWork updateById2(Long id, AboutWork about);
	public AboutWork updateById3(Long id, AboutWork about);

	public AboutWork adddata(Email email);

//	public AboutWork adddata(Email email);

//	public AboutWork adddata(Email email);

//	public AboutWork updateById(Long id, @Valid AboutWork aboutWork);

//	User adddata(User data);

}
