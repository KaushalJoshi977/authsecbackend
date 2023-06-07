package com.realnet.fnd.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realnet.fnd.entity.Rn_Menu_Group_Header;
import com.realnet.fnd.entity.Rn_Menu_Group_Line;
import com.realnet.fnd.repository.RnGroupLineRepository;

@Service
public class RnGroupMenuLineServiecImpl implements RnGroupMenuLineServiec {
	
	@Autowired
	private RnGroupLineRepository r;

	@Override
	public Rn_Menu_Group_Line addToDb(Rn_Menu_Group_Line rn_Menu_Group_Line) {
		Rn_Menu_Group_Line addToDb = this.r.save(rn_Menu_Group_Line);
		return addToDb;
	}

	@Override
	public Rn_Menu_Group_Line getoneById(int id) {
		Optional<Rn_Menu_Group_Line> findById = this.r.findById(id);
		return findById.get();
	}

	@Override
	public List<Rn_Menu_Group_Line> getAll() {
		List<Rn_Menu_Group_Line> all = this.r.findAll();
		return all;
	}

	@Override
	public Rn_Menu_Group_Line updateToDb(Rn_Menu_Group_Line rn_Menu_Group_Line) {
		Rn_Menu_Group_Line addToDb = this.r.save(rn_Menu_Group_Line);
		return addToDb;
	}

//	@Override
//	public List<Rn_Menu_Group_Line> getLinesByHeader(Rn_Menu_Group_Header rn_Menu_Group_Header) {
//		List<Rn_Menu_Group_Line> lineByHeader = this.r.findLineByHeader(rn_Menu_Group_Header);
//		return lineByHeader;
//	}

}
