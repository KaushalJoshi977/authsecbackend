package com.realnet.users.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.realnet.users.entity1.AppUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Entity
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String name;

	@Column
	private String description;
	
//	@JsonBackReference
//	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles", cascade = { CascadeType.PERSIST, CascadeType.DETACH,
//			CascadeType.MERGE, CascadeType.REFRESH })
//	private Set<User> users;
	
	@JsonBackReference
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles", cascade = { CascadeType.PERSIST, CascadeType.DETACH,
			CascadeType.MERGE, CascadeType.REFRESH })
	private Set<AppUser> appusers;


//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getDescription() {
//		return description;
//	}
//
//	public void setDescription(String description) {
//		this.description = description;
//	}
//
//	public Set<User> getUsers() {
//		return users;
//	}
//
//	public void setUsers(Set<User> users) {
//		this.users = users;
//	}
//
//	public Role(Long id, String name, String description, Set<User> users) {
//		super();
//		this.id = id;
//		this.name = name;
//		this.description = description;
//		this.users = users;
//	}

	public Role() {
		
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
