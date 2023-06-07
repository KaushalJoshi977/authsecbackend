package com.realnet.fnd.entity1;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.realnet.users.entity1.AppUserRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="SEC_GRP_MENU_ACCESS")
@IdClass(GrpMenuAccesscompositeKey.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GrpMenuAccess {
	@Id
	@ManyToOne
	@JoinColumn(name = "usr_grp")
	private AppUserRole usrGrp;
	@Id
	@ManyToOne
	@JoinColumn(name = "menu_item_id")
	private MenuDet menuItemId;
	private String mCreate;
	private String mEdit;
	private String mQuery;
	private String mDelete;
	private String mexport;

	
	private Long menuId;
	private String mVisible;
	private String createby;
	private Date createdAt;
	private String updateby;
	private Date updatedAt;
	
	private String isdisable;
	
	
	@Column(name = "itemSeq")
	private Long itemSeq;
	
	@Column(name = "MenuItemDesc")
	private String menuItemDesc;
	private String status;
	
	
	@Column(name = "moduleName")
	private String moduleName;
	
	
	
	@Column(name = "MainMenuActionName")
	private String main_menu_action_name;
	
	@Column(name = "MainMenuIconName")
	private String main_menu_icon_name;
	
	@Transient
	private List<GrpMenuAccess> subMenus=new ArrayList<GrpMenuAccess>();
	
	
	@Transient
	private Long grpid;
	@Transient
	private Long gmenuid;


}
