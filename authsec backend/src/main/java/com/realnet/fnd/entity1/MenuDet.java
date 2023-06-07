package com.realnet.fnd.entity1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "SEC_MENU_DET")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MenuDet extends menudet_who{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long menuItemId;
	
	@Column(name = "itemSeq")
	private Long itemSeq;
	
	@Column(name = "MenuItemDesc")
	private String menuItemDesc;
	private String status;
	
	private Long menuId;
	
	@Column(name = "moduleName")
	private String moduleName;
	
	


	
	@Column(name = "MainMenuActionName")
	private String main_menu_action_name;
	
	@Column(name = "MainMenuIconName")
	private String main_menu_icon_name;

	@Transient
	private List<MenuDet> subMenus=new ArrayList<MenuDet>();;
	


	    
	    

}
