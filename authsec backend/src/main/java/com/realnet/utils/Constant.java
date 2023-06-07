package com.realnet.utils;

public final class Constant {
	
	
	public final static String LOCAL_HOST = "localhost";
 	public final static String BACKEND_PORT_9191 = "9191";
 	public final static String FRONTEND_PORT_9191 = "4200";
 	public final static String SUREOPS_PORT_9090 = "9090";
 	public final static String JOBPRO_PORT_8087 = "8087";
 	public final static String SCRIPT_RUNNER_8901 = "8901";
 	public final static String APP_BUILD_19001 = "19001";
 	public final static String SURE_JOB_8089 = "8089";
 	public final static String SURE_COMMUNICATION_19002 = "19002";
	public final static String REGISTRATION_BACKEND = "9190";
	public final static String REGISTRATION_FRONTEND = "4200";
	public final static String SUREOPS_PORT_9090_AWS = "9090";
	public final static String FARM_IP_ADDRESS = "localhost";
	
 	
 	
//	public final static String LOCAL_HOST = "43.205.154.152";
//	public final static String BACKEND_PORT_9191 = "30166";
// 	public final static String FRONTEND_PORT_9191 = "30165";
//	public final static String SUREOPS_PORT_9090 = "31123";
//	public final static String JOBPRO_PORT_8087 = "30167";
//	public final static String SCRIPT_RUNNER_8901 = "30168";
// 	public final static String APP_BUILD_19001 = "30171";
// 	public final static String SURE_JOB_8089 = "30188";
// 	public final static String SURE_COMMUNICATION_19002 = "30187";
//	public final static String SCRIPT_RUNNER_8901_AWS = "8901";
//	public final static String REGISTRATION_BACKEND = "30190";
//	public final static String REGISTRATION_FRONTEND = "30191";
//	public final static String SUREOPS_PORT_9090_AWS = "9090";
//	public final static String FARM_IP_ADDRESS = "13.233.70.96";
 	
	
	public final static String GITEA_IP_ADDRESS = "try.gitea";
 	public final static String GITEA_PORT = "io";


	// SORT BY
	public final static String SORT_BY_CREATION_DATE = "createdAt";
	public final static String SORT_BY_CREATION_DATE_NATIVE_QUERY = "created_at";

	// --------------------------------------Error Type
	public final static String ERROR_TYPE_HIBERNATE = "Hibernate Exception";
	public final static String ERROR_TYPE_DB = "Database Exception";
	public final static String ERROR_TYPE_EXCEPTION = "Other Exception";
	public final static String ERROR_TYPE_CONTROLLER = "Controller Exception";
	public final static String ERROR_TYPE_HTTP = "HTTP Error";

	public final static String NOT_FOUND_EXCEPTION = "Data Not Found ";
	public final static String NOT_EXIST_EXCEPTION = "Data Not Exist ";

	// --------------------------------------Error Status
	public final static String ERROR_LEVEL_FAILURE = "Failure";
	public final static String ERROR_LEVEL_SUCCESS = "Success";

	// --------------------------------------DB Error Code
	public final static int ERROR_CODE_SERVER = 404;
	public final static int ERROR_CODE_SERVER_AUTH = 401;
	public final static int ERROR_CODE_DB = 500;
	public final static int ERROR_CODE_DATA = 201;
	public final static int SUCCESS_CODE = 200;
	public final static int ERROR_CODE_VALIDATION = 301;
	public final static int ERROR_CODE_VERYFICATION = 403;

	// --------------------------------------REAL IT API RESPONSE MESSEGES
	public final static String EXTRACTOR_API_TITLE = "Extractor";
	public final static String EXTRACTOR_CREATED_SUCCESSFULLY = "Extractor added Successfully !";
	public final static String EXTRACTOR_UPDATED_SUCCESSFULLY = "Extractor updated Successfully !";
	public final static String EXTRACTOR_DELETED_SUCCESSFULLY = "Extractor deleted Successfully !";
	public final static String EXTRACTOR_NOT_DELETED = "Extractor not deleted";
	public final static String EXTRACTOR_NOT_CREATED = "Extractor not Added !";
	public final static String EXTRACTOR_NOT_UPDATED = "Extractor not updated !";
	

	public final static String STATIC_EXTRACTION_SUCCESS = "Static Extraction Completed !";
	public final static String DYNAMIC_EXTRACTION_SUCCESS = "Dynamic Extraction Completed !";
	public final static String STATIC_EXTRACTION_FAILED = "Static Extraction Failed !";
	public final static String DYNAMIC_EXTRACTION_FAILED = "Dynamic Extraction Failed !";

	// RULE
	public final static String RULE_LIBRARY_API_TITLE = "Rule Library";
	public final static String RULE_CREATED_SUCCESSFULLY = "Rule added Successfully !";
	public final static String RULE_UPDATED_SUCCESSFULLY = "Ruler updated Successfully !";
	public final static String RULE_DELETED_SUCCESSFULLY = "Rule deleted Successfully !";
	public final static String RULE_NOT_DELETED = "Rule not deleted";
	public final static String RULE_NOT_CREATED = "Rule not Added !";
	public final static String RULE_NOT_UPDATED = "Rule not updated !";

	// EXCEPTION RULE
	public final static String EXCEPTION_RULE_LIBRARY_API_TITLE = "Exception Rule Library";
	public final static String EXCEPTION_RULE_CREATED_SUCCESSFULLY = "Exception Rule added Successfully !";
	public final static String EXCEPTION_RULE_UPDATED_SUCCESSFULLY = "Exception Rule updated Successfully !";
	public final static String EXCEPTION_RULE_DELETED_SUCCESSFULLY = "Exception Rule deleted Successfully !";

	public final static String EXCEPTION_RULE_NOT_DELETED = "Exception Rule not deleted";
	public final static String EXCEPTION_RULE_NOT_CREATED = "Exception Rule not Added !";
	public final static String EXCEPTION_RULE_NOT_UPDATED = "Exception Rule not updated !";

	// TECHNOLOGY STACK
	public final static String TECHNOLOGY_STACK_API_TITLE = "Technology Stack";
	public final static String TECHNOLOGY_STACK_CREATED_SUCCESSFULLY = "Technology Stack saved Successfully !";
	public final static String TECHNOLOGY_STACK_UPDATED_SUCCESSFULLY = "Technology Stack updated Successfully !";
	public final static String TECHNOLOGY_STACK_DELETED_SUCCESSFULLY = "Technology Stack deleted Successfully !";
	public final static String TECHNOLOGY_STACK_NOT_DELETED = "Technology Stack not deleted";
	public final static String TECHNOLOGY_STACK_NOT_CREATED = "Technology Stack not Added !";
	public final static String TECHNOLOGY_STACK_NOT_UPDATED = "Technology Stack not updated !";
	public final static String UNZIP_EXCEPTION = "There is a problem while Unziping";

	// PROJECT SETUP
	public final static String PROJECT_SETUP_API_TITLE = "Project Setup";
	public final static String PROJECT_CREATED_SUCCESSFULLY = "Project saved Successfully !";
	public final static String PROJECT__UPDATED_SUCCESSFULLY = "Project updated Successfully !";
	public final static String PROJECT_DELETED_SUCCESSFULLY = "Project deleted Successfully !";
	public final static String PROJECT_NOT_DELETED = "Project not deleted";
	public final static String PROJECT_NOT_CREATED = "Project not Added !";
	public final static String PROJECT_NOT_UPDATED = "Project not updated !";
	public final static String PROJECT_COPY_SUCCESS = "Project Copy Success !";
	public final static String PROJECT_COPY_FAILURE = "Project Copy Failed !";

	// MODULE SETUP
	public final static String MODULE_SETUP_API_TITLE = "Module Setup";
	public final static String MODULE_CREATED_SUCCESSFULLY = "Module saved Successfully !";
	public final static String MODULE_UPDATED_SUCCESSFULLY = "Module updated Successfully !";
	public final static String MODULE_DELETED_SUCCESSFULLY = "Module deleted Successfully !";
	public final static String MODULE_NOT_DELETED = "Module not deleted";
	public final static String MODULE_NOT_CREATED = "Module not Added !";
	public final static String MODULE_NOT_UPDATED = "Module not updated !";
	public final static String MODULE_COPY_SUCCESS = "Module Copy Success !";
	public final static String MODULE_COPY_FAILURE = "Module Copy Failed !";
	
	// WIREFRAME APIS
	public final static String WIREFRAME_API_TITLE = "Wireframe Setup";
	public final static String WIREFRAME_CREATED_SUCCESSFULLY = "Wireframe saved Successfully !";
	public final static String WIREFRAME_UPDATED_SUCCESSFULLY = "Wireframe updated Successfully !";
	public final static String WIREFRAME_DELETED_SUCCESSFULLY = "Wireframe deleted Successfully !";
	public final static String WIREFRAME_NOT_DELETED = "Wireframe not deleted";
	public final static String WIREFRAME_NOT_CREATED = "Wireframe not Added !";
	public final static String WIREFRAME_NOT_UPDATED = "Wireframe not updated !";
	
	//REPORT APIS
	public final static String REPORT_API_TITLE = "report Setup";
	public final static String REPORT_CREATED_SUCCESSFULLY = "Report saved Successfully !";
	public final static String REPORT_UPDATED_SUCCESSFULLY = "Report updated Successfully !";
	public final static String REPORT_DELETED_SUCCESSFULLY = "Report deleted Successfully !";
	public final static String REPORT_NOT_DELETED = "Report not deleted";
	public final static String REPORT_NOT_CREATED = "Report not Added !";
	public final static String REPORT_NOT_UPDATED = "Report not updated !";
	
	
	
	public final static String WIREFRAME_BUTTON_ADDED = "Wireframe Button Added !";
	public final static String WIREFRAME_SECTION_ADDED = "Wireframe Section Added !";
	public final static String WIREFRAME_FIELD_ADDED_IN_SECTION = "Wireframe Field Added In Section !";
	public final static String WIREFRAME_FIELD_NOT_ADDED_IN_SECTION = "Wireframe Field Not Added";
	public final static String WIREFRAME_FIELD_NAME_CHANGE_SUCCESS = "Wireframe Field Name Changed";
	public final static String WIREFRAME_FIELD_NAME_CHANGE_FAILURE = "Wireframe Field Name Not Changed";
	public final static String WIREFRAME_COPY_SUCCESS = "Wireframe Copy Success !";
	public final static String WIREFRAME_COPY_FAILURE = "Wireframe Copy Failed !";
	
	public final static String WIREFRAME_SECTION_DELETE_SUCCESS = "Section Deleted Successfully !";
	public final static String WIREFRAME_SECTION_DELETE_FAILURE = "Section Deletion Failure !";
	public final static String WIREFRAME_FIELD_DELETE_SUCCESS = "Section Deleted Successfully !";
	public final static String WIREFRAME_FIELD_DELETE_FAILURE = "Section Deletion Failure !";
	
	// LOOKUP
	public final static String LOOKUP_API_TITLE = "Lookup";
	public final static String LOOKUP_CREATED_SUCCESSFULLY = "Lookup saved Successfully !";
	public final static String LOOKUP_UPDATED_SUCCESSFULLY = "Lookup updated Successfully !";
	public final static String LOOKUP_DELETED_SUCCESSFULLY = "Lookup deleted Successfully !";
	public final static String LOOKUP_NOT_DELETED = "Lookup not deleted";
	public final static String LOOKUP_NOT_CREATED = "Lookup not Added !";
	public final static String LOOKUP_NOT_UPDATED = "Lookup not updated !";
	
	// ACTION BUILDER
	public final static String ACTION_BUILDER_API_TITLE = "Action Builder";
	public final static String ACTION_CREATED_SUCCESSFULLY = "Action saved Successfully !";
	public final static String ACTION_UPDATED_SUCCESSFULLY = "Action updated Successfully !";
	public final static String ACTION_DELETED_SUCCESSFULLY = "Action deleted Successfully !";
	public final static String ACTION_NOT_DELETED = "Action not deleted";
	public final static String ACTION_NOT_CREATED = "Action not Added !";
	public final static String ACTION_NOT_UPDATED = "Action not updated !";
	public final static String ACTION_CFF_LAYOUT_SUCCESS = "Layout Created Successfully!";
	public final static String ACTION_CFF_LAYOUT_FAILURE = "Layout Not Created!";
	public final static String ACTION_CFF_DATA_SUCCESS = "Data Added Successfully!";
	public final static String ACTION_CFF_DATA_FAILURE = "Data Not Added!";
	
	// MASTER BUILDER CONTROLLER
	public final static String MASTER_BUILDER_API_TITLE = "Master Builder";
	public final static String MASTER_BUILDER_SUCCESS = "Master Builder created Successfully!";
	public final static String MASTER_BUILDER_FAILURE = "Master Builder Not Created!";
	
	// FORM BUILDER
	public final static String FORM_BUILDER_API_TITLE = "Form Builder";
	public final static String FORM_BUILD_SUCCESS = "Form created Successfully!";
	public final static String FORM_BUILD_FAILURE = "Form Not Created!";
	public final static String FORM_DELETE_SUCCESS = "Form Deleted!";
	public final static String FORM_DELETE_FAILURE = "Form Not Delete!";
	
	// QUERY BUILDER
	public final static String QUERY_API_TITLE = "Create Query";
	public final static String QUERY_CREATE_SUCCESS = "Table created Successfully!";
	public final static String QUERY_CREATE_FAILURE = "Table Not Created!";
	
	// FILE OPERATION
	public final static String FILE_OPERATION_API_TITLE = "File Operation";
	public final static String FILE_CODE_SAVE_SUCCESSFULLY = "Code Save Successfully!";
	public final static String FILE_NOT_FOUND_EXCEPTION = "File Not Found!";
	public final static String FILE_CODE_SAVE_FAILURE = "Code Not Saved!";
	public final static String FILE_LIST_API_TITLE = "File List";
	public final static String FILE_LIST_IS_EMPTY = "File List Is Empty";
	
	// FLF
	public final static String FLF_API_TITLE = "FLF";
	public final static String FLF_CREATED_SUCCESSFULLY = "FLF saved Successfully !";
	public final static String FLF_UPDATED_SUCCESSFULLY = "FLF updated Successfully !";
	public final static String FLF_DELETED_SUCCESSFULLY = "FLF deleted Successfully !";
	public final static String FLF_NOT_DELETED = "FLF not deleted";
	public final static String FLF_NOT_CREATED = "FLF not Added !";
	public final static String FLF_NOT_UPDATED = "FLF not updated !";
	
	
	
	
	// --------------------------------------Success another
	public final static String USER_ADDED_SUCCESSFULLY = "User added Successfully !";
	public final static String USER_INFO_UPDATED_SUCCESSFULLY = "User info update successfully !";
	public final static String ERROR_MESSAGE_SAME_OLD_NEW_PWD = "Old and New Password are same";
	public final static String USER_DOES_NOT_EXIST = "UserName does not exist.";
	public final static String USER_DELETE_TITLE = "User Deleted";
	public final static String USER_DELETED_SUCCESSFULLY = "User deleted Successfully !";
	public final static String ERROR_MESSAGE_AUTH_FAILED = "Authentication failed";
	public final static String ERROR_MESSAGE_USER_NAME_AND_PASS = "Please check username and password";
	public final static String USER_LOFOUT_TITLE = "Auth Session";
	public final static String USER_LOG_OUT_SUCCESSFULLY = "User Loged Out successfully !";
	public final static String ERROR_LOG_OUT_MESSAGE = "Session does not exist.";
	public final static String ERROR_MOBILE_NO_VERIFICATION = "Mobile number is not register.";
	public final static String VERYFICATION_CODE_ACCEPTED = "Code accepted. Phone now marked verified";
	public final static String ERROR_VERYFICATION_MESSAGE = "Code not accepted or Invalide OTP";
	public final static String PROFILE_PIC_ADDED_SUCCESSFULLY = "Pic added Successfully !";
	public final static String ERROR_PROFILE_PIC_FAILED_TO_UPLOAD = "Pic failed to add.";
	public final static String FAILED_TO_DELETED_USER = "User info does not exist.";
	public final static String PICTURE_TITLE = "Picture";

//	public final static String PROFILE_PIC_DELETED_SUCCESSFULLY = "Pic delted Successfully !";
//	public final static String PROFILE_API_TITLE = "Profile Pic";
//	public final static String ERROR_PROFILE_PIC_FAILED_TO_DELETE = "Profile Picture does not exist";
//
//	public final static String CONTACT_API_TITLE = "Contacts Uploaded";
//	public final static String CONTACTS_UPLOADED_SUCCESS_MESSAGE = "Contacts uploaded successfully !";
//	public final static String FAILED_TO_UPLOADED_CONTACTS = "Failed to uploaded contacts";
//
//	public final static String GROUP_WITH_USERS_CREATED_SUCCESSFULLY = "Group with users created Successfully !";
//	public final static String FAILED_TO_CREATE_GROUP_WITH_USERS = "Failed to create group with users.";
//
//	public final static String GROUP_API_TITLE = "Group";
//	public final static String GROUP_NOT_FOUND_MSG = "There is no any group.";
//	public final static String GROUP_CREATED_SUCCESSFULLY_MESSAGE = "Group added Successfully !";
//	public final static String FAILED_TO_CREATE_GROUP_MESSAGE = "Failed to create group.";
//	public final static String GROUP_NAME_UPDATED_SUCCESSFULLY = "Group name updated Successfully !";
//	public final static String FAILED_TO_UPDATE_GROUP_NAME_MESSAGE = "Failed to upload group name.";
//	public final static String USER_DELETED_FROM_GROUP_SUCCESSFULLY = "User deleted from group Successfully !";
//	public final static String FAILED_TO_DELETE_USER_FROM_GROUP = "Failed to delete user from group.";
//	public final static String GROUP_DELETED_SUCCESSFULLY = "User deleted from group Successfully !";
//	public final static String FAILED_TO_DELETE_GROUP = "Failed to delete group.";
//
//	public final static String CHAT_MESSAGE_TITLE = "Message";
//	public final static String MESSAGE_SEND_SUCCESSFULLY = "Message sent Successfully !";
//	public final static String FAILED_TO_SEND_MESSAGE = "Failed to send message.";
//	public final static String FAILED_TO_DELETE_MESSAGE = "Failed to Delete";
//
//	public final static String NOTIFICATIONS_MESSAGE_TITLE = "Notification";
//	public final static String NOTIFICATIONS_MESSAGE_SET_SUCCESSFULLY = "Notifications preferences set successfully !";
//	public final static String NOTIFICATIONS_FAILED_TO_SET_MESSAGE = "Failed to set preferences.";
//
//	public final static String SOCIAL_MESSAGE_TITLE = "Social Network";
//	public final static String SOCIAL_FAILED_TO_SET_MESSAGE = "Failed to link social network site.";
//
//	public final static String LIVE_LOCATION_MESSAGE_TITLE = "Live Location";
//	public final static String LIVE_LOCATION_FAILED_TO_SET_MESSAGE = "Failed to set current location.";
//	public final static String LIVE_LOCATION_FAILED_TO_GET_MESSAGE = "There is no active sharing sessions.";
//	public final static String LIVE_LOCATION_DELETED = "Current live location sharing session deleted.";

	public final static String PIC_UPLOADED_TITLE = "Profile Picture";
	public final static String PIC_UPLOAD_FAILED = "You failed to upload ";

	public final static String PICTURE_CATEGORY = "full";
	public final static String IMAGE_STORAGE_PATH = "D:\\image\\cyooTempFiles\\";

	public final static String USER_TITLE = "User";
	public final static String USER_NAME_EMAIL_IN_USE = "Username/Email already taken.";
	public final static String USER_NAME_EMAIL_NOT_IN_USE = "Username/Email not taken.";
	public final static String EMAIL_ID_IN_USE = "Email already taken.";
	public final static String USER_NOT_IN_USE = "Username not taken.";
	public final static String EMAIL_NOT_IN_USE = "Email not taken.";

	// ========================================================================================

	/*
	 * Group group = new Group(); group.setGroupId(2);
	 * group.setGroupName("friends"); group.setCreatedBy("Munta");
	 * group.setCreatedDateTime("2014-11-22 12:45:34"); group.setIsDelete(0);
	 * 
	 * Set<GroupUser> grList = new HashSet<GroupUser>();
	 * 
	 * GroupUser groupUser = new GroupUser(); groupUser.setId(1);
	 * groupUser.setGroupId(4); groupUser.setUserName("Jake4");
	 * groupUser.setAddedDateTime("2014-11-22 12:45:34"); grList.add(groupUser);
	 * 
	 * groupUser = new GroupUser(); groupUser.setId(5); groupUser.setGroupId(2);
	 * groupUser.setUserName("Jake5");
	 * groupUser.setAddedDateTime("2014-11-22 12:45:34"); grList.add(groupUser);
	 * 
	 * group.setGroupUserList(grList);
	 */

}
