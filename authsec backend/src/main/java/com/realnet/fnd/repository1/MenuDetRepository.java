package com.realnet.fnd.repository1;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realnet.fnd.entity1.GrpMenuAccess;
import com.realnet.fnd.entity1.MIXMENUN;
import com.realnet.fnd.entity1.MenuDet;
import com.realnet.fnd.entity1.MixMenuNew;

@Repository
public interface MenuDetRepository extends JpaRepository<MenuDet,Long>{
	@Query(value="select a.menu_item_id,a.menu_item_desc, \n"
			+ "    a.menu_id,b.m_create,b.m_visible,\n"
			+ "    b.m_edit,b.m_query,b.m_delete\n"
			+ "from sec_menu_det a,sec_grp_menu_access b\n"
			+ "where b.menu_item_id=a.menu_item_id\n"
			+ "order by a.item_seq",
			countQuery="select count(*) "
					+ "from sec_menu_det a,sec_grp_menu_access b\n"
					+ "where b.menu_item_id=a.menu_item_id\n"
					+ "order by a.item_seq",nativeQuery=true)
		Page<Object> getAll(Pageable page);
	
	@Query(value="select a.menu_item_id,a.menu_item_desc, \n"
			+ "    a.menu_id,b.m_create,b.m_visible,\n"
			+ "    b.m_edit,b.m_query,b.m_delete\n"
			+ "from sec_menu_det a,sec_grp_menu_access b\n"
			+ "where b.menu_item_id=a.menu_item_id\n"
			+ "and b.usr_grp = ?1 "
			+ "and a.menu_id = ?2 "
			+ "order by a.item_seq",countQuery="select count(*) "
					+ "from sec_menu_det a,sec_grp_menu_access b\n"
					+ "where b.menu_item_id=a.menu_item_id\n"
					+ "and b.usr_grp = ?1 "
					+ "and a.menu_id = ?2 "
					+ "order by a.item_seq;"
			,nativeQuery = true)
	List<Object> getById(Long usr_grp,Long menu_id);
	
//	@Query(value="select a.menu_item_id,a.menu_item_desc,\n"
//			+ "    a.menu_id,b.m_create,b.m_visible,\n"
//			+ "    b.m_edit,b.m_query,b.m_delete,\n"
//			+ " a.main_menu_action_name,a.main_menu_icon_name "
//			+ "from sec_menu_det a,sec_grp_menu_access b\n"
//			+ "where b.menu_item_id=a.menu_item_id\n"
//			+ "and b.usr_grp = ?1\n"
//			+ "and a.menu_id= ?2 "
//			+ "order by a.item_seq",
//			countQuery="select count(*) "
//					+ "from sec_menu_det a,sec_grp_menu_access b\n"
//					+ "where b.menu_item_id=a.menu_item_id\n"
//					+ "and b.usr_grp = ?1"
//					+ "and a.menu_id= ?2 "
//					+ "order by a.item_seq",
//					nativeQuery=true)
//	List<Object> getByUserId(Long usr_grp,Long menu_id);
	
	
	@Query(value="select a.menu_item_id menu_item_id,a.menu_item_desc menu_item_desc,\n"
			+ "    a.menu_id menu_id,b.m_create m_create,b.m_visible m_visible,\n"
			+ "    b.m_edit m_edit,b.m_query m_query,b.m_delete m_delete,\n"
			+ " a.main_menu_action_name main_menu_action_name,a.main_menu_icon_name main_menu_icon_name "
			+ "from sec_menu_det a,sec_grp_menu_access b\n"
			+ "where b.menu_item_id=a.menu_item_id\n"
			+ "and b.usr_grp =:usr_grp\n"
			+ "and a.menu_id=:menu_id "
			+ "order by a.item_seq",
//			countQuery="select count(*) "
//					+ "from sec_menu_det a,sec_grp_menu_access b\n"
//					+ "where b.menu_item_id=a.menu_item_id\n"
//					+ "and b.usr_grp = ?1"
//					+ "and a.menu_id= ?2 "
//					+ "order by a.item_seq",
					nativeQuery=true)
	List<Object> getByUserId(Long usr_grp,Long menu_id);

	@Query(value="select a.menu_item_id menu_item_id,a.menu_item_desc menu_item_desc,\r\n"
			+ "			    a.menu_id menu_id,b.m_create m_create,b.m_visible m_visible,\r\n"
			+ "			    b.m_edit m_edit,b.m_query m_query,b.m_delete m_delete,\r\n"
			+ "			 a.main_menu_action_name main_menu_action_name,a.main_menu_icon_name main_menu_icon_name \r\n"
			+ "			from sec_menu_det a,sec_grp_menu_access b \r\n"
			,
					nativeQuery=true)
	List<MixMenuNew> getallmenu();
	
	@Query(value="select a.menu_item_id menu_item_id,a.menu_item_desc menu_item_desc,\r\n"
			+ "			    a.menu_id menu_id,b.m_create m_create,b.m_visible m_visible,\r\n"
			+ "			    b.m_edit m_edit,b.m_query m_query,b.m_delete m_delete\r\n"
//			+ "			 a.main_menu_action_name main_menu_action_name,a.main_menu_icon_name main_menu_icon_name \r\n"
			+ "			from sec_menu_det a,sec_grp_menu_access b \r\n"
			,
					nativeQuery=true)
	List<Object> allmenu();
	

	
	@Query(value="select a.menu_item_id,a.menu_item_desc,\r\n"
			+ "			  a.menu_id,b.m_create,b.m_visible,\r\n"
			+ "			  b.m_edit,b.m_query,b.m_delete,\r\n"
			+ "			 a.main_menu_action_name,a.main_menu_icon_name \r\n"
			+ "		from sec_menu_det a,sec_grp_menu_access b\r\n"
			+ "			where a.menu_id=100",
			nativeQuery = true)
	List<Object> findEqual100();

	@Query(value="select a.menu_item_id,a.menu_item_desc,\r\n"
			+ "			  a.menu_id,b.m_create,b.m_visible,\r\n"
			+ "			  b.m_edit,b.m_query,b.m_delete,\r\n"
			+ "			 a.main_menu_action_name,a.main_menu_icon_name \r\n"
			+ "		from sec_menu_det a,sec_grp_menu_access b \r\n"
			+ "			where a.menu_id=ANY(SELECT menu_id from sec_menu_det where menu_id >100 && menu_id< 200);",
			nativeQuery = true)
	List<Object> findGreater100();
	
	@Query(value="select a.menu_item_id,a.menu_item_desc,\r\n"
			+ "			  a.menu_id,b.m_create,b.m_visible,\r\n"
			+ "			  b.m_edit,b.m_query,b.m_delete,\r\n"
			+ "			 a.main_menu_action_name,a.main_menu_icon_name \r\n"
			+ "		from sec_menu_det a,sec_grp_menu_access b\r\n"
			+ "			where a.menu_id=200",
			nativeQuery = true)
	List<Object> findEqual200();

	@Query(value="select a.menu_item_id,a.menu_item_desc,\r\n"
			+ "			  a.menu_id,b.m_create,b.m_visible,\r\n"
			+ "			  b.m_edit,b.m_query,b.m_delete,\r\n"
			+ "			 a.main_menu_action_name,a.main_menu_icon_name \r\n"
			+ "		from sec_menu_det a,sec_grp_menu_access b \r\n"
			+ "			where a.menu_id=ANY(SELECT menu_id from sec_menu_det where menu_id >200 && menu_id< 300);",
			nativeQuery = true)
	List<Object> findGreater200();
	
	
	@Query(value="select a.menu_item_id,a.menu_item_desc,\r\n"
			+ "			  a.menu_id,b.m_create,b.m_visible,\r\n"
			+ "			  b.m_edit,b.m_query,b.m_delete,\r\n"
			+ "			 a.main_menu_action_name,a.main_menu_icon_name \r\n"
			+ "		from sec_menu_det a,sec_grp_menu_access b\r\n"
			+ "			where a.menu_id=300",
			nativeQuery = true)
	List<Object> findEqual300();

	@Query(value="select a.menu_item_id,a.menu_item_desc,\r\n"
			+ "			  a.menu_id,b.m_create,b.m_visible,\r\n"
			+ "			  b.m_edit,b.m_query,b.m_delete,\r\n"
			+ "			 a.main_menu_action_name,a.main_menu_icon_name \r\n"
			+ "		from sec_menu_det a,sec_grp_menu_access b \r\n"
			+ "			where a.menu_id=ANY(SELECT menu_id from sec_menu_det where menu_id >300 && menu_id< 400);",
			nativeQuery = true)
	List<Object> findGreater300();
	
	
	@Query(value="select a.menu_item_id,a.menu_item_desc,\r\n"
			+ "			  a.menu_id,b.m_create,b.m_visible,\r\n"
			+ "			  b.m_edit,b.m_query,b.m_delete,\r\n"
			+ "			 a.main_menu_action_name,a.main_menu_icon_name \r\n"
			+ "		from sec_menu_det a,sec_grp_menu_access b\r\n"
			+ "			where a.menu_id=400",
			nativeQuery = true)
	List<Object> findEqual400();

	@Query(value="select a.menu_item_id,a.menu_item_desc,\r\n"
			+ "			  a.menu_id,b.m_create,b.m_visible,\r\n"
			+ "			  b.m_edit,b.m_query,b.m_delete,\r\n"
			+ "			 a.main_menu_action_name,a.main_menu_icon_name \r\n"
			+ "		from sec_menu_det a,sec_grp_menu_access b \r\n"
			+ "			where a.menu_id=ANY(SELECT menu_id from sec_menu_det where menu_id >400 && menu_id< 500);",
			nativeQuery = true)
	List<Object> findGreater400();
	
	@Query(value="select a.menu_item_id,a.menu_item_desc,\r\n"
			+ "			  a.menu_id,b.m_create,b.m_visible,\r\n"
			+ "			  b.m_edit,b.m_query,b.m_delete,\r\n"
			+ "			 a.main_menu_action_name,a.main_menu_icon_name \r\n"
			+ "		from sec_menu_det a,sec_grp_menu_access b\r\n"
			+ "			where a.menu_id=500",
			nativeQuery = true)
	List<Object> findEqual500();

	@Query(value="select a.menu_item_id,a.menu_item_desc,\r\n"
			+ "			  a.menu_id,b.m_create,b.m_visible,\r\n"
			+ "			  b.m_edit,b.m_query,b.m_delete,\r\n"
			+ "			 a.main_menu_action_name,a.main_menu_icon_name \r\n"
			+ "		from sec_menu_det a,sec_grp_menu_access b \r\n"
			+ "			where a.menu_id=ANY(SELECT menu_id from sec_menu_det where menu_id >500 && menu_id< 600);",
			nativeQuery = true)
	List<Object> findGreater500();
	
	@Query(value="select a.menu_item_id,a.menu_item_desc,\r\n"
			+ "			  a.menu_id,b.m_create,b.m_visible,\r\n"
			+ "			  b.m_edit,b.m_query,b.m_delete,\r\n"
			+ "			 a.main_menu_action_name,a.main_menu_icon_name \r\n"
			+ "		from sec_menu_det a,sec_grp_menu_access b\r\n"
			+ "			where a.menu_id=600",
			nativeQuery = true)
	List<Object> findEqual600();

	@Query(value="select a.menu_item_id,a.menu_item_desc,\r\n"
			+ "			  a.menu_id,b.m_create,b.m_visible,\r\n"
			+ "			  b.m_edit,b.m_query,b.m_delete,\r\n"
			+ "			 a.main_menu_action_name,a.main_menu_icon_name \r\n"
			+ "		from sec_menu_det a,sec_grp_menu_access b \r\n"
			+ "			where a.menu_id=ANY(SELECT menu_id from sec_menu_det where menu_id >600 && menu_id< 700);",
			nativeQuery = true)
	List<Object> findGreater600();
	
	@Query(value="SELECT * FROM sec_menu_det where menu_id=100", nativeQuery = true)
	MenuDet findequalto100();
	
	@Query(value="SELECT * FROM sec_menu_det a where a.menu_id=ANY(SELECT menu_id from sec_menu_det where menu_id >100 && menu_id< 200)", nativeQuery = true)
	List<MenuDet> findgreaterthan100();

	@Query(value="SELECT * FROM sec_menu_det where menu_id=200", nativeQuery = true)
	MenuDet findequalto200();
	
	@Query(value="SELECT * FROM sec_menu_det a where a.menu_id=ANY(SELECT menu_id from sec_menu_det where menu_id >200 && menu_id< 300)", nativeQuery = true)
	List<MenuDet> findgreaterthan200();
	
	@Query(value="SELECT * FROM sec_menu_det where menu_id=300", nativeQuery = true)
	MenuDet findequalto300();
	
	@Query(value="SELECT * FROM sec_menu_det a where a.menu_id=ANY(SELECT menu_id from sec_menu_det where menu_id >300 && menu_id< 400)", nativeQuery = true)
	List<MenuDet> findgreaterthan300();
	
	@Query(value="SELECT * FROM sec_menu_det where menu_id=400", nativeQuery = true)
	MenuDet findequalto400();
	
	@Query(value="SELECT * FROM sec_menu_det a where a.menu_id=ANY(SELECT menu_id from sec_menu_det where menu_id >400 && menu_id< 500)", nativeQuery = true)
	List<MenuDet> findgreaterthan400();
	
	@Query(value="SELECT * FROM sec_menu_det where menu_id=500", nativeQuery = true)
	MenuDet findequalto500();
	
	@Query(value="SELECT * FROM sec_menu_det a where a.menu_id=ANY(SELECT menu_id from sec_menu_det where menu_id >500 && menu_id< 600)", nativeQuery = true)
	List<MenuDet> findgreaterthan500();
	
	@Query(value="SELECT * FROM sec_menu_det where menu_id=600", nativeQuery = true)
	MenuDet findequalto600();
	
	@Query(value="SELECT * FROM sec_menu_det a where a.menu_id=ANY(SELECT menu_id from sec_menu_det where menu_id >600 && menu_id< 700)", nativeQuery = true)
	List<MenuDet> findgreaterthan600();

	@Query(value="SELECT * FROM sec_menu_det a where a.menu_id=?1", nativeQuery = true)
	List<MenuDet> findAllById(Long menu_id);
	
	@Query(value="SELECT * FROM sec_menu_det a where a.root_menudet_id is null", nativeQuery = true)
     List<MenuDet> findAllRoots();

	@Query(value="SELECT * FROM sec_menu_det a where a.root_menudet_id=:menu_item_id", nativeQuery = true)
     List<MenuDet> findAllSubmenu(@Param("menu_item_id") Long menu_item_id);

	@Query(value="SELECT * FROM sec_menu_det a where a.root_menudet_id=:menu_item_id", nativeQuery = true)
	List<MenuDet> findAllSubCategoriesInRoot(@Param("menu_item_id") List<Long> menu_item_id);

	@Query(value="SELECT * FROM sec_menu_det a where a.menu_id = 0 ORDER BY item_seq ASC", nativeQuery = true)
    List<MenuDet> findAllRootsByMenuId();
	
	@Query(value="SELECT * FROM sec_menu_det a where a.menu_id=:menu_item_id ORDER BY item_seq ASC", nativeQuery = true)
    List<MenuDet> findAllSubmenuByMenuId(@Param("menu_item_id") Long menu_item_id);
	
	@Query(value="select * from sec_menu_det where menu_item_id = :menu_item_id UNION select * from sec_menu_det where menu_id = :menu_item_id", nativeQuery = true)
    List<MenuDet> findAllSubmenuforusrgrp(@Param("menu_item_id") Long menu_item_id);
	
	@Query(value="SELECT `COLUMN_NAME` FROM `INFORMATION_SCHEMA`.`COLUMNS` WHERE `TABLE_SCHEMA`='realnet_CNSBE' AND `TABLE_NAME`=:TABLE_NAME", nativeQuery = true)
    List<Object> findcolumnbytablename(@Param("TABLE_NAME") Object TABLE_NAME);
	
}
