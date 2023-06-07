package com.realnet.fnd.entity1;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MIXMENUN {
	private long menuItemId;
	private String menuItemDesc;
	private long menuId;
	
	private String mainMenuActionName;
	private String mainMenuIconName;
	private String mCreate;
	private String mEdit;
	private String mQuery;
	private String mDelete;
	private String mVisible;
	private List<MIXMENUN> subMenus;
}
