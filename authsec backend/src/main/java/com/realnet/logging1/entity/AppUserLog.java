package com.realnet.logging1.entity;

import java.sql.Blob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="app_user_log")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class AppUserLog {
	@Id
	@GeneratedValue(generator = "appUserLog_gen")
	@SequenceGenerator(name="appUserLog_gen", sequenceName="app_user_log_sequence",initialValue = 1, allocationSize = 1)
	private Long logId;
	@Column(unique = true)
	private String userName;	
	private String generateLog="N";
	private String logFileName;
	private String logLevel="info";
	private Date createdOn;
	@Transient
	private Blob file;
	@Transient
	private Long filesizeCurrent;
	@Transient
	private String createdOnFormated;
}
