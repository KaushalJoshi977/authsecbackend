package com.realnet.fnd.service;

import java.util.List;

import com.realnet.fnd.entity.Rn_Menu_Group_Header;
import com.realnet.fnd.entity.Rn_Menu_Group_Line;

public interface RnGroupMenuLineServiec {
	
	public Rn_Menu_Group_Line addToDb(Rn_Menu_Group_Line rn_Menu_Group_Line);
	
	public Rn_Menu_Group_Line getoneById(int id);
	
	public List<Rn_Menu_Group_Line> getAll();
	
	public Rn_Menu_Group_Line updateToDb(Rn_Menu_Group_Line rn_Menu_Group_Line);
	
//	public List<Rn_Menu_Group_Line> getLinesByHeader(Rn_Menu_Group_Header rn_Menu_Group_Header);
}
