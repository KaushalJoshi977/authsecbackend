package com.realnet.users.entity1;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="POSITION")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppUserPosition {
	@Id
	private String positionCode;
	private String active;
	private String description;
	private Date createdOn;
	private String createdBy;
	private Date updatedOn;
	private String updatedBy;
}
