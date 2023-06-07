package com.realnet.session.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import com.realnet.config.EmailService;
import com.realnet.config.TokenProvider;
import com.realnet.fnd.response.OperationResponse;
import com.realnet.fnd.response.OperationResponse.ResponseStatusEnum;
import com.realnet.logging1.entity.AppUserLog;
import com.realnet.logging1.service.LoggingService;
import com.realnet.session.entity.AboutWork;
import com.realnet.session.entity.SessionItem;
import com.realnet.session.response.SessionResponse;
import com.realnet.users.entity.CompanyDto;
import com.realnet.users.entity.LoginUser;
import com.realnet.users.entity.Role;
import com.realnet.users.entity.Sys_Accounts;

import com.realnet.users.entity1.AppUser;
import com.realnet.users.entity1.AppUserSessions;
import com.realnet.users.repository.CompanyRepo;
import com.realnet.users.repository1.AppUserSessionsRepository;
import com.realnet.users.service.AboutWorkService;
import com.realnet.users.service.CompanyService;
import com.realnet.users.service1.AppUserService;
import com.realnet.users.service1.AppUserSessionsServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/*
This is a dummy rest controller, for the purpose of documentation (/session) path is map to a filter
 - This will only be invoked if security is disabled
 - If Security is enabled then SessionFilter.java is invoked
 - Enabling and Disabling Security is done at config/applicaton.properties 'security.ignored=/**'
*/

@Api(tags = { "Authentication" })
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping(value = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
public class SessionController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private LoggingService loggingService;
	@Autowired
	private TokenProvider jwtTokenUtil;
	
	@Autowired
	private AppUserSessionsRepository appUserSessionsRepository;
	
	
	
	@Autowired(required=false)
	private AppUserService userService;

//	@Autowired
//	private CompanyService companyService;

	@Autowired
	private EmailService emailService;
	
	 @Autowired
	    private JavaMailSender mailSender;
	 
	 @Autowired
	 private AboutWorkService aboutworkservice;  
	 
	 @Autowired
	 private CompanyRepo sysrepo;
	 
	 @Autowired
	 private BCryptPasswordEncoder bcryptEncoder;
	
	 @Autowired
	 private AppUserSessionsServiceImpl appUserSessionsServiceImpl;
	
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Will return a security token, which must be passed in every request", response = SessionResponse.class) })
	@RequestMapping(value = "/session", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public SessionResponse newSession(@RequestBody LoginUser loginRequest,HttpServletRequest request,HttpSession session1) {



		try {

			final Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			final String token = jwtTokenUtil.generateToken(authentication);

			System.out.println("authentication.getName() =>" + authentication.getName()); // email



			AppUser loggedInUser = userService.getLoggedInUser();
			//System.out.println("/session logged in user -> " + loggedInUser);

//			List<String> loggedInUserRoles = new ArrayList<String>();
			StringBuilder roleString = new StringBuilder();
			roleString.append(loggedInUser.getUsrGrp().getGroupName());
//			.forEach(role -> {
////				loggedInUserRoles.add(role.getName());
//				roleString.append(role.getName() + ", ");
//			});
			//String role = roleString.toString().substring(0, roleString.toString().lastIndexOf(","));
			//List<String> roleList = Arrays.asList(role.split("\\s*,\\s*"));


			SessionResponse resp = new SessionResponse();
			SessionItem sessionItem = new SessionItem();
			sessionItem.setToken(token);
			sessionItem.setUserId(loggedInUser.getUserId());
			sessionItem.setFullname(loggedInUser.getFullName());
			sessionItem.setFirstName(loggedInUser.getFullName());
			//sessionItem.setUsername(loggedInUser.getUsername());
			sessionItem.setEmail(loggedInUser.getEmail());
			// sessionItem.setRoles(roleList);
			Set<Role> roles = loggedInUser.getRoles();
			List<String> roleList= new ArrayList<>();
			for(Role ro:roles) {
				roleList.add(ro.getDescription());
			}
			sessionItem.setRoles(roleList);
			//sessionItem.setRoles(loggedInUser.getUsrGrp().getGroupName());
			resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
			resp.setOperationMessage("Login Success");
			resp.setItem(sessionItem);
			
			InetAddress ip;
			StringBuilder sb = new StringBuilder();
		    try {
		      ip = InetAddress.getLocalHost();
		      System.out.println("Current IP address : " + ip.getHostAddress());
		      NetworkInterface network = NetworkInterface.getByInetAddress(ip);
		      byte[] mac = network.getHardwareAddress();
		      System.out.print("Current MAC address : ");
		      
		      for (int i = 0; i < mac.length; i++) {
		        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? ":" : ""));
		      }
		      System.out.println(sb.toString());
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		    
			AppUserSessions session = new AppUserSessions();
			
			session.setUserId(loggedInUser);
			session.setLastAccessDate(new Date());
			session.setLogintime(new Date());
//			session.setLogouttime(new Date());
			session.setSessionId(RequestContextHolder.currentRequestAttributes().getSessionId());
			//String ip =  request.getHeader("X-Forward-For");
			//String ip = getClientIp();
			//String ip = getClientIp(request);
			String ip1 =request.getRemoteAddr();
			session.setClientIp(ip1);
			session.setMacid(sb.toString());
			appUserSessionsServiceImpl.add(session);
			AppUserLog s= loggingService.generate(loggedInUser);
			//AppUserLog s = null;
			if(s!=null) {
				session1.setAttribute("LogginLevel",s.getLogLevel());
				session1.setAttribute("generate_log",s.getGenerateLog());
				session1.setAttribute("LogFileName",s.getLogFileName());
			}else {
				session1.setAttribute("generate_log","N");
			}
			return resp;
		} catch (Exception e) {
			LOGGER.error("Login Failed "+e.getMessage());
			System.out.print(e.getMessage());
			SessionResponse resp = new SessionResponse();
			resp.setOperationStatus(ResponseStatusEnum.ERROR);
			resp.setOperationMessage("Login Failed");
			return resp;
		}

	}
	//logout
		@GetMapping("/logout")
		public ResponseEntity<?> logoutUser(HttpSession session2) throws IOException{
			
//			if(session1.getAttribute("generate_log").equals("Y")) {
//		    Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
//		    Path filePath = Paths.get(root.toString(),"logs",session1.getAttribute("LogFileName").toString());
//				File f=filePath.toFile();
//				FileWriter fw = new FileWriter(f,true);
//				fw.write("Logout\n");
//				fw.close();			
//			}
			
			String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
			AppUserSessions session = appUserSessionsRepository.findBySessionId(session2.toString());
			session.setLogouttime(new Date());			
			appUserSessionsServiceImpl.add(session);
			return new ResponseEntity<>("Logged out succesfully",HttpStatus.OK);
		}
	//logout
	
	public String getClientIp(HttpServletRequest request) {
		 final String LOCALHOST_IPV4 = "127.0.0.1";
		 final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";
		String ipAddress = request.getHeader("X-Forwarded-For");
		if(StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		
		if(StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		
		if(StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if(LOCALHOST_IPV4.equals(ipAddress) || LOCALHOST_IPV6.equals(ipAddress)) {
				try {
					InetAddress inetAddress = InetAddress.getLocalHost();
					ipAddress = inetAddress.getHostAddress();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
		}
		
		if(!StringUtils.isEmpty(ipAddress) 
				&& ipAddress.length() > 15
				&& ipAddress.indexOf(",") > 0) {
			ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
		}
		
		return ipAddress;
	}
//	public String getClientIp() {
//		final String[] IP_HEADER_CANDIDATES = {
//		        "X-Forwarded-For",
//		        "Proxy-Client-IP",
//		        "WL-Proxy-Client-IP",
//		        "HTTP_X_FORWARDED_FOR",
//		        "HTTP_X_FORWARDED",
//		        "HTTP_X_CLUSTER_CLIENT_IP",
//		        "HTTP_CLIENT_IP",
//		        "HTTP_FORWARDED_FOR",
//		        "HTTP_FORWARDED",
//		        "HTTP_VIA",
//		        "REMOTE_ADDR"
//		    };
//
//		 if (RequestContextHolder.getRequestAttributes() == null) {
//	            return "0.0.0.0";
//	        }
//
//	        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//	        for (String header: IP_HEADER_CANDIDATES) {
//	            String ipList = request.getHeader(header);
//	            if (ipList != null && ipList.length() != 0 && !"unknown".equalsIgnoreCase(ipList)) {
//	                String ip = ipList.split(",")[0];
//	                return ip;
//	            }
//	        }
//
//	        return request.getRemoteAddr();
//	}
	//@ApiResponses(value = { @ApiResponse(code = 200, message = "email varification") })
///	@RequestMapping(value = "/email-exists", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<?> emailExistCheck(@RequestBody EmailRequest emailReq) {
//		boolean exists = userService.existsByEmail(emailReq.getEmail());
//		
//		System.out.println(emailReq.getEmail() + " ::: EMAIL exists? " + exists);
//		Map<String, String> res = new HashMap<String, String>();
//		if (exists) {
//			String message = emailReq.getEmail() + " is Already Exists";
//			res.put("message", message);
//			// return new ResponseEntity<Error>(HttpStatus.BAD_REQUEST);
//			return new ResponseEntity<>(res, HttpStatus.CONFLICT);
//		} else {
//			AboutWork aboutWork=new AboutWork();
//			AboutWork about = aboutworkservice.adddata(aboutWork);
//			Sys_Accounts sys = new Sys_Accounts();
//			sys.setId(about.getId());
//			sysrepo.save(sys);
//			
//			
//			AppUser user=new User();
//			Random random = new Random();
//			long no=random.nextLong();
//			user.setChecknumber(no);
//			user.setEmail(emailReq.getEmail());
//			user.setPassword(bcryptEncoder.encode("demopass"));
//			user.setRole("ADMIN");
//			user.setSys_account(sys);
//			User u=userService.save(user);
//			userService.sendEmail(emailReq.getEmail(),u.getUserId(),u.getChecknumber());
//			
//			
//			String message = "Congratulations " + emailReq.getEmail();
//			res.put("message", message);
//			// return new ResponseEntity<SUCCESSFUL>(HttpStatus.OK);
//			return ResponseEntity.ok(res);
//		}
//		
//	}

	// admin
//	@ApiOperation(value = "Add new user (admin)", response = OperationResponse.class)
//	@RequestMapping(value = "/user-registration", method = RequestMethod.POST, produces = { "application/json" })
//	public UserResponse addNewUser(@RequestBody UserDto user, HttpServletRequest req) {
//		System.out.println("----------This is my comment---------");
//		User userAddSuccess = userService.userResister(user);
//		System.out.println("----------This is my comment---------");
//		UserResponse resp = new UserResponse();
//		UserItem userItem = new UserItem();
//		if (userAddSuccess != null) {
//			userItem.setUserId(userAddSuccess.getUserId());
//			userItem.setFirstName(userAddSuccess.getFirstName());
//			userItem.setFullname(userAddSuccess.getFullName());
//			userItem.setEmail(userAddSuccess.getEmail());
//			resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
//			resp.setOperationMessage("User Added");
//			resp.setItem(userItem);
//			return resp;
//		} else {
//			resp.setOperationStatus(ResponseStatusEnum.ERROR);
//			resp.setOperationMessage("Unable to add user");
//			return resp;
//		}
//	}

	
	
	
//	@ApiOperation(value = "Add new Company", response = OperationResponse.class)
//	@RequestMapping(value = "/company-registration", method = RequestMethod.POST, produces = { "application/json" })
//	public ResponseEntity<?> addNewCompany(@RequestBody CompanyDto company) {
//		Sys_Accounts companyAddSuccess = companyService.companyResister(company);
//
//		Map<String, String> res = new HashMap<String, String>();
//		if (companyAddSuccess != null) {
//			String message = "Company Added";
//			res.put("message", message);
//			return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
//		} else {
//			String message = "Unable to add Company";
//			res.put("message", message);
//			// return ResponseEntity.ok(res);
//			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
//		}
		
//}
	
	
		@ApiOperation(value = "Add new cluodnsure", response = OperationResponse.class)
		@PostMapping("/aboutwork")
		public AppUser addNewCustomer(@RequestBody AboutWork aboutWork) {
		
			System.out.println("about work controller started");
		
			// save acccount info
			AboutWork about = aboutworkservice.adddata(aboutWork);
			Sys_Accounts sys = new Sys_Accounts();
			sys.setAccount_id(about.getId());
			sysrepo.save(sys);
		
			// save user with accout id
			AppUser user = new AppUser();
			user.setChangePassw(aboutWork.getPassword());
			user.setEmail(aboutWork.getEmail());
			user.setMob_no(aboutWork.getMobile());
			AppUser userResister = userService.userResister(user, about.getId());
			return userResister;
		}
	 
	 
	
	 
//		@ApiOperation(value = "Update about ", response = User.class)
//		@PutMapping("/aboutwork/{id}")
//		public User updateUser(@PathVariable(value = "id") Long id, @Valid @RequestBody AboutWork aboutWork) {
//			User updateabout = userService.updateById(id, aboutWork);
//			System.out.println("account id:"+updateabout.getSys_account().getId()+"Pancard NO:"+aboutWork.getPancard()+"Passwored::"+aboutWork.getPassword());
//			AboutWork aw = aboutworkservice.updateById(updateabout.getSys_account().getId(), aboutWork);
//			return updateabout;
//		}
		
//		@ApiOperation(value = "Update about ", response = User.class)
//		@PutMapping("/aboutwork2/{id}")
//		public User updateUser2(@PathVariable(value = "id") Long id, @Valid @RequestBody AboutWork aboutWork) {
//			//User user=userService.getById(id);
//			//System.out.println("Passwaord::"+user.getPassword());
//			//User newUser=new User();
//			//newUser.setPassword(user.getPassword());
//			User updateabout = userService.updateById2(id, aboutWork);
//			System.out.println("account id:"+updateabout.getSys_account().getId()+"Pancard NO:"+aboutWork.getPancard()+"Passwored::"+aboutWork.getPassword());
//			//AboutWork ab=new AboutWork();
//			//ab.setPassword(user.getPassword());
//			AboutWork aw = aboutworkservice.updateById2(updateabout.getSys_account().getId(), aboutWork);
//			return updateabout;
//		}
	 
	 
	 
	 
	 
	 
//	 @ApiOperation(value = "Update about ", response = User.class)
//		@PutMapping("/aboutwork_working/{id}")
//		public ResponseEntity<?> updateByIdWorkingId(@PathVariable(value = "id") Long id, @Valid @RequestBody AboutWork aboutWork) {
//		 System.out.println("about work controller started");
//
//		User updateabout = userService.updateByIdWorkingId(id, aboutWork);
//
//		 
//		 System.out.println( updateabout.getSys_account().getId());
//		 
////		 AboutWork aw=aboutworkservice.updateById(updateabout.getSys_account().getId(),aboutWork);
//		 
////		 AboutWork about = aboutworkservice.updateById(id,aboutWork);
////		 User updateaccount = aboutworkservice.updateById(id, aboutWork);
//		 
//		 return ResponseEntity.status(HttpStatus.ACCEPTED).body(updateabout);
//	}
	 
//	 @ApiOperation(value = "Update about ", response = User.class)
//		@PutMapping("/aboutwork_managing/{id}")
//		public ResponseEntity<?> updateByMangingWork(@PathVariable(value = "id") Long id, @Valid @RequestBody AboutWork aboutWork) {
//		 System.out.println("about work controller started");
//
//		 User updateabout = userService.updateByMangingWork(id, aboutWork);
//		 AboutWork aw=aboutworkservice.updateById3(updateabout.getSys_account().getId(),aboutWork);
//		 return null;
//	}
//	 
	 
//	    @ApiOperation(value = "Add new cluodnsure", response = OperationResponse.class)
//	    @PostMapping("/addemails/{id}")
//	    public   User addNewEmails(@PathVariable(value = "id") Long id,@RequestBody Email email) {
//	     
//		 System.out.println(id + "about work controller started");
//		 
//		 User u = userService.getUserInfoByUserId(id);
////		 System.out.println(u.getSys_account().getId());
//		
//		Sys_Accounts acc_id=u.getSys_account();
//		long account=acc_id.getId();
//		System.out.println(account);
//
//
//		Sys_Accounts sys= new Sys_Accounts();
//		sys.setId(account);
//	
//		
//		User user=new User();
//
//	if(email.getEmail1()!=null)
//		{
//		
//		
//		Random random = new Random();
////		long random1 = (long)user.getChecknumber();
//		System.out.println("Random Long: " + random.nextLong());
//		long ra=random.nextLong();
////		System.out.println("Ra: " + ra);
//		
//			user.setEmail(email.getEmail1());
//			user.setPassword("pfp");
//			user.setSys_account(sys);
//			user.setChecknumber(ra);
//			user.setRole("USER");
////			user.setUserId(id);
//			
//			
//			
//
//		User userid=userService.userResisteremail(user);
//			
//			userService.sendEmail2(email.getEmail1(),userid.getUserId(),ra);
//		
////		userService.sendEmail(email.getEmail1());
//			
//			}
//		
//	User user2=new User();
//	if(email.getEmail2()!=null)
//		{		
//		
//		
//		
//		Random random = new Random();
////		long random1 = (long)user.getChecknumber();
//		System.out.println("Random Long: " + random.nextLong());
//		long ra=random.nextLong();
////		System.out.println("Ra: " + ra);
//
//		
//			
//			user2.setEmail(email.getEmail2());
//			user2.setPassword("kkl");
//			user2.setSys_account(sys);
//			user2.setChecknumber(ra);
//			user2.setRole("USER");
////			user2.setUserId(id);
//
//			
//		User userid2=	userService.userResisteremail(user2);
////			userService.sendEmail(email.getEmail2());
//		
//			userService.sendEmail2(email.getEmail2(),userid2.getUserId(),ra);
//		}
//		
//		
//	User user3=new User();
//		if(email.getEmail3()!=null)
//		{
//		
//			
//			Random random = new Random();
////			long random1 = (long)user.getChecknumber();
//			System.out.println("Random Long: " + random.nextLong());
//			long ra=random.nextLong();
////			System.out.println("Ra: " + ra);
//			
//			user3.setEmail(email.getEmail3());
//			user3.setPassword("skl");
//			user3.setSys_account(sys);
//			user3.setChecknumber(ra);
//			user3.setRole("USER");
////			user3.setUserId(id);
//
//
//			
//		
//		User user4=	userService.userResisteremail(user3);
////			userService.sendEmail(email.getEmail3() );
//		
//			userService.sendEmail2(email.getEmail3(),user4.getUserId(),ra);
//		}
//		
//		
//		User user4=new User();
//		if(email.getEmail4()!=null)
//		{
//			
//			
//			Random random = new Random();
////			long random1 = (long)user.getChecknumber();
//			System.out.println("Random Long: " + random.nextLong());
//			long ra=random.nextLong();
////			System.out.println("Ra: " + ra);
//			
//			user4.setEmail(email.getEmail4());
//			user4.setPassword("ppl");
//			user4.setSys_account(sys);
//			user4.setChecknumber(ra);
//			user4.setRole("USER");
////			user4.setUserId(id);
//
//		
//		User user5=	userService.userResisteremail(user4);
////			userService.sendEmail(email.getEmail4());
//		
//			userService.sendEmail2(email.getEmail4(),user5.getUserId(),ra);
//			
//		}
//	
//		
//		
//		return null;
//
//	
//	    }
	    
	    
//	    @GetMapping("userid/{id}/{checknumber}")
//		public ResponseEntity<?> addRole(@PathVariable("id") Long id, @PathVariable("checknumber") Long checknumber) {
//			
//	    	Map<String, String> res = new HashMap<String, String>();
//			res.put("message", "Role Added Succcessfully");
//	    	
//
//			Boolean user=userService.exitbychecknumber(id, checknumber);
//	    
//	    
//			
//	    	return ResponseEntity.ok(user);
//		}
	    
	    //latest
//	    @GetMapping("userid/{id}/{checknumber}")
//		public User addRole(@PathVariable("id") Long id, @PathVariable("checknumber") Long checknumber) {
//			User user=userService.exitbychecknumber(id, checknumber);
//	        return user;
//		}

	 
	 
	 
	 
//	 
//	//save data
//			@ApiOperation(value = "Add A New emails", response = User.class)
//			@PostMapping("/addemail")
//			public ResponseEntity<List<AboutWork>> createEmails(
//					@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
//					 @RequestBody List<User> about) 
//					{
//				
//				List<AboutWork> savedRn_Forms_Setup =userService .save(about);
//				return ResponseEntity.status(HttpStatus.CREATED).body(savedRn_Forms_Setup);
//			}
	 
	 
//	 @ApiOperation(value = "Add new cluodnsure", response = OperationResponse.class)
//	    @PostMapping("/aboutwork")
//	    public User  addNewEmails(@RequestBody AboutWork aboutWork) {
//	     
//		 System.out.println("about work controller started");
//		AboutWork about=  aboutworkservice.adddata(aboutWork);
//		User user=new User();
//		user.setPassword(aboutWork.getPassword());
//		user.setEmail(aboutWork.getEmail());
//		user.setPhone(aboutWork.getMobile());
//		
//		Sys_Accounts sys = new Sys_Accounts();
//		sys.setId(about.getId());
//		
//		System.out.println(about.getId());
//		sysrepo.save(sys);
////		Sys_Accounts id=sysrepo.save(sys);
////		Long id1=id.getId();
//		
////		Long id=user.getUserId();
////		aboutWork.setId(id);
////		user.setUserId(id);
//		User userResister = userService.userResister(user,about.getId());
//		System.out.println(userResister.getUserId());
////		User userResister = userService.userResister(user,id1);
//			return userResister;
//	    }
	 
	 
	 
//	 @ApiOperation(value = "Update about ", response = User.class)
//		@PutMapping("/aboutwork/{acc_id}")
//		public ResponseEntity<?> updateUser(@PathVariable(value = "acc_id") Long acc_id, @Valid @RequestBody AboutWork aboutWork) {
//		 System.out.println("about work controller started");
//
//		 User updateabout = userService.updateById(acc_id, aboutWork);
//		
//		return null;
//			
//		}
//	 
	

// ===============================================================
//	  @PostMapping(value = "/forgot-password")
//	    public ResponseEntity<ServiceResponse> forgotPassword(@Valid @RequestBody ForgotPasswordDto forgotPasswordDto) {
//	        User user = userService.findByEmail(forgotPasswordDto.getEmail());
//	        HashMap<String, String> result = new HashMap<>();
//
//	if(user==null)
//	{
//		result.put("message", "No user found with this email!");
//
//		return ResponseEntity.badRequest().body(new ServiceResponse(HttpStatus.BAD_REQUEST.value(), result));
//	}
//
//	eventPublisher.publishEvent(new OnResetPasswordEvent(user));
//
//	result.put("message","A password reset link has been sent to your email box!");
//
//	return ResponseEntity.ok(new ServiceResponse(HttpStatus.OK.value(),result));
//}
//
//@ApiOperation(value = "Change the user password through a reset token", response = ServiceResponse.class)
//@ApiResponses(value = {
//        @ApiResponse(code = 200, message = "The action completed successfully!"), // response = SuccessResponse.class),
//        @ApiResponse(code = 400, message = "The token is invalid or has expired"),//, response = BadRequestResponse.class),
//        @ApiResponse(code = 422, message = Constants.INVALID_DATA_MESSAGE)//, response = InvalidDataResponse.class),
//})
//@PostMapping(value = "/reset-password")
//public ResponseEntity<ServiceResponse> resetPassword(@Valid @RequestBody ResetPasswordDto passwordResetDto) {
//    ResetPassword resetPassword = resetPasswordService.findByToken(passwordResetDto.getToken());
//    HashMap<String, String> result = new HashMap<>();
//
//    if (resetPassword == null) {
//        result.put("message", "The token is invalid!");
//
//        return ResponseEntity.badRequest().body(new ServiceResponse(HttpStatus.BAD_REQUEST.value(), result));
//    }
//
//    if (resetPassword.getExpireAt() < new Date().getTime()) {
//        result.put("message", "You token has been expired!");
//
//        resetPasswordService.delete(resetPassword.getId());
//
//        return ResponseEntity.badRequest().body(new ServiceResponse(HttpStatus.BAD_REQUEST.value(), result));
//    }
//
//    userService.updatePassword(resetPassword.getUser().getId(), passwordResetDto.getPassword());
//
//    result.put("message", "Your password has been resetted successfully!");
//
//    // Avoid the user or malicious person to reuse the link to change the password
//    resetPasswordService.delete(resetPassword.getId());
//
//    return ResponseEntity.badRequest().body(new ServiceResponse(HttpStatus.OK.value(), result));
//}
//}
}