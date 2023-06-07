package com.realnet.users.entity1;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="DEPARTMENT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppUserDepartment {
	@Id
	@Column(name="department_code")
	private String departmentCode;
	private String active;
	private String description;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on",updatable = false,insertable = false)
	private Date updatedOn;
	@Column(name="updated_by")
	private String updatedBy;
	private Long id;
	
}
