package com.realnet.userlist.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user_list")
public class UserList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String user_id;
	private String name;
	private String gender;
	private String dob;
	private String email;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String security_provider_id;
	private String default_customer_id;
	private String company;
	private String phone;
	private String address1;
	private String address2;
	private String country;
	private String postal;
	private String role;
	private String other_roles;
	private boolean is_active;
	private boolean is_blocked;
	private String secret_question;
	private String secret_answer;
	private boolean enable_beta_testing;
	private boolean enable_renewal;
	private String created_at;
	private String created_by;
	private String updated_at;
	private String updated_by;
	private String about;
	private String department;
	private String fullname;
	private String photos;
	private String status;
	private String account_id;
	private String checknumber;
	private String managing_work;
	private String pancard;
	private String working;
	private int menu_group;
	
//	@OneToMany(mappedBy = "userlist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JsonIgnore
//	private List<UserImage> img = new ArrayList<>();
	
	public UserList() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public int getMenu_group() {
		return menu_group;
	}

	public void setMenu_group(int menu_group) {
		this.menu_group = menu_group;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getSecurity_provider_id() {
		return security_provider_id;
	}

	public void setSecurity_provider_id(String security_provider_id) {
		this.security_provider_id = security_provider_id;
	}

	public String getDefault_customer_id() {
		return default_customer_id;
	}

	public void setDefault_customer_id(String default_customer_id) {
		this.default_customer_id = default_customer_id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getOther_roles() {
		return other_roles;
	}

	public void setOther_roles(String other_roles) {
		this.other_roles = other_roles;
	}

	public boolean isIs_active() {
		return is_active;
	}

	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}

	public boolean isIs_blocked() {
		return is_blocked;
	}

	public void setIs_blocked(boolean is_blocked) {
		this.is_blocked = is_blocked;
	}

	public String getSecret_question() {
		return secret_question;
	}

	public void setSecret_question(String secret_question) {
		this.secret_question = secret_question;
	}

	public String getSecret_answer() {
		return secret_answer;
	}

	public void setSecret_answer(String secret_answer) {
		this.secret_answer = secret_answer;
	}

	public boolean isEnable_beta_testing() {
		return enable_beta_testing;
	}

	public void setEnable_beta_testing(boolean enable_beta_testing) {
		this.enable_beta_testing = enable_beta_testing;
	}

	public boolean isEnable_renewal() {
		return enable_renewal;
	}

	public void setEnable_renewal(boolean enable_renewal) {
		this.enable_renewal = enable_renewal;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

	public String getChecknumber() {
		return checknumber;
	}

	public void setChecknumber(String checknumber) {
		this.checknumber = checknumber;
	}

	public String getManaging_work() {
		return managing_work;
	}

	public void setManaging_work(String managing_work) {
		this.managing_work = managing_work;
	}

	public String getPancard() {
		return pancard;
	}

	public void setPancard(String pancard) {
		this.pancard = pancard;
	}

	public String getWorking() {
		return working;
	}

	public void setWorking(String working) {
		this.working = working;
	}

//	public List<UserImage> getImg() {
//		return img;
//	}
//
//	public void setImg(List<UserImage> img) {
//		this.img = img;
//	}

	

}
