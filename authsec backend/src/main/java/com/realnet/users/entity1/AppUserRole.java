package com.realnet.users.entity1;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="SEC_USER_GROUP")
@EnableJpaAuditing
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppUserRole implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="usr_grp")
	@GeneratedValue(generator = "SecUserGrp_gen")
	@SequenceGenerator(name="SecUserGrp_gen", sequenceName="sec_user_group_id",initialValue = 40, allocationSize = 1)
	private Long usrGrp; //auto generate
//	@Transient
//	@ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)  
//	private AppUser appUser;
	private String groupName; //will come
	private String groupDesc; //will come
	private String createby;
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdate;
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedate;
	private String updateby;
	private String status; //will come but need to change
	private Long groupLevel; //will come
	@Transient
	private String createDateFormated;
	@Transient 
	private String updateDateFormated;

}
