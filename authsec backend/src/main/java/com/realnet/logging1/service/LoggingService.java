package com.realnet.logging1.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.realnet.logging1.entity.AppUserLog;
import com.realnet.logging1.repository.AppUserLogginRepository;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserServiceImpl;

@Service
public class LoggingService {
//	private String	generate_log="N";
//	private String user_log_file_nam="sysadmin2_5463783.log";
//	private String log_level="debug";

	private AppUserLogginRepository appUserLogginRepository;
	private AppUserServiceImpl appUserServiceImpl;

	@Autowired
	public LoggingService(AppUserLogginRepository appUserLogginRepository, AppUserServiceImpl appUserServiceImpl) {
		super();
		this.appUserLogginRepository = appUserLogginRepository;
		this.appUserServiceImpl = appUserServiceImpl;
	}

	public AppUserLog generate(AppUser appUser) throws IOException {
		long id = 1;
//		AppUserLogCompositeKey key = new AppUserLogCompositeKey(appUser.getUserId(),id);
//		AppUserLog log = appUserLogginRepository.findById(key).orElse(null);
//		AppUserLog log = appUserLogginRepository.findByUserId(appUser.getUserId()).orElse(null);
		List<AppUserLog> l = appUserLogginRepository.findByUserName(appUser.getUsername());
		if (l.size() > 0) {
			AppUserLog log = l.get(0);
			if (log != null) {
				// For local machine testing
				Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
				Path filePath = Paths.get(root.toString(), "logs", log.getLogFileName());
				File f = new File(filePath.toString());
				// For prod server
				// File f = new File("/home/jboss/EAP-7.1.0/logs/"+this.user_log_file_nam);
				if (!f.exists()) {
					f.createNewFile();
				}

				return log;

			}
		}

		return null;
	}

	public AppUserLog add(AppUserLog appUserLog) {
		// return appUserLogginRepository.save(appUserLog);
//		return appUserLogginRepository.insertManual(appUserLog.getUserId().getUserId(),appUserLog.getLogId(), 
//				appUserLog.getGenrateLog(), appUserLog.getLogFileName(),appUserLog.getLogLevel());
		return appUserLogginRepository.save(appUserLog);
	}

	public List<AppUserLog> getAll() {
		List<AppUserLog> a = appUserLogginRepository.findAll();
		DateFormat simple = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
		a.forEach(o -> {
			try {
				byte[] fileContent = null;
				Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
				Path filePath = Paths.get(root.toString(), "logs", o.getLogFileName());
				File f = new File(filePath.toString());
				fileContent = FileUtils.readFileToByteArray(f);
				long size_kb = (Files.size(filePath)) / 1024;
				long size_mb = size_kb / 1024;
				o.setFilesizeCurrent(size_mb);
				Blob b = new SerialBlob(fileContent);
				o.setCreatedOnFormated(simple.format(o.getCreatedOn()));
				o.setFile(b);
			} catch (Exception e) {
				o.setFile(null);
				o.setFilesizeCurrent(null);
				o.setCreatedOnFormated(null);
			}
		});
		return a;
	}

	public AppUserLog getOne(Long id) {
		AppUser user = appUserServiceImpl.getById(id).orElse(null);
		if (user != null) {
//			long id1=1;
//			AppUserLogCompositeKey key = new AppUserLogCompositeKey(user.getUserId(),id1);
			List<AppUserLog> l = appUserLogginRepository.findByUserName(user.getUsername());
			if (l.size() > 0) {
				return l.get(0);
			}
		}
		return null;
	}

	public AppUserLog update(AppUserLog appUserLog) {
		return appUserLogginRepository.save(appUserLog);
	}

	public Resource loadFileAsResource(String fileName) {
		try {
			Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
			Path filePath = Paths.get(root.toString(), "logs", fileName);
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				return null;
			}
		} catch (MalformedURLException ex) {
			return null;
		}
	}

}
