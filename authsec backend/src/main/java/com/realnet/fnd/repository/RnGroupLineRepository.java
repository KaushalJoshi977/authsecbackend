package com.realnet.fnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.realnet.fnd.entity.Rn_Menu_Group_Header;
import com.realnet.fnd.entity.Rn_Menu_Group_Line;

public interface RnGroupLineRepository extends JpaRepository<Rn_Menu_Group_Line, Integer> {
	
//	public List<Rn_Menu_Group_Line> findByHeader(Rn_Menu_Group_Header menu_group_header);

}
