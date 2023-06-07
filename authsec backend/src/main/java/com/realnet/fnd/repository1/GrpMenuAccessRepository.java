package com.realnet.fnd.repository1;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realnet.fnd.entity1.GrpMenuAccess;
import com.realnet.fnd.entity1.GrpMenuAccesscompositeKey;

@Repository
public interface GrpMenuAccessRepository extends JpaRepository<GrpMenuAccess,GrpMenuAccesscompositeKey>{
	
	@Query(value="SELECT * FROM sec_grp_menu_access a where a.usr_grp= ?1", nativeQuery = true)
	GrpMenuAccess findByUsrGrp(Long usr_grp);
	
	@Query(value="SELECT * FROM sec_grp_menu_access a where a.usr_grp= ?1 ORDER BY item_seq", nativeQuery = true)
	List< GrpMenuAccess>  findAllByUsrGrp(Long usr_grp);
	
	@Query(value="SELECT * FROM sec_grp_menu_access a where a.menu_item_id=?1",nativeQuery = true)
	 GrpMenuAccess findbymenuitemid(Long menu_item_id);
	
	@Query(value="SELECT * FROM sec_grp_menu_access where  child = 0 ", nativeQuery = true)
    List<GrpMenuAccess> findAllRoots();
	
	@Query(value="SELECT * FROM sec_grp_menu_access a where a.child=:parent", nativeQuery = true)
    List<GrpMenuAccess> findAllSubmenu(@Param("parent") Long parent);
//	@Query(value="SELECT * FROM sec_grp_menu_access a where a.parent=:parent", nativeQuery = true)
//    GrpMenuAccess findById(@Param("parent") Long parent);
	
//	@Query(value="SELECT * FROM sec_grp_menu_access where  child = 0 and created_for =:created_for ", nativeQuery = true)
//    List<GrpMenuAccess> findAllRootsbyusrGrp(@Param("created_for") Long created_for);

	@Query(value="SELECT * FROM sec_grp_menu_access where  child = 0 and usr_grp =:usr_grp ", nativeQuery = true)
    List<GrpMenuAccess> findAllRootsbyusrGrp(@Param("usr_grp") Long usr_grp);
	
	@Query(value="SELECT * FROM sec_grp_menu_access where  menu_id = 0 and status= 'Enable' and usr_grp =:usr_grp ORDER BY item_seq;", nativeQuery = true)
    List<GrpMenuAccess> findAllRootsByMenuId(@Param("usr_grp") Long usr_grp);
	
	@Query(value="SELECT * FROM sec_grp_menu_access a where a.menu_id=:menu_item_id and a.usr_grp =:usr_grp ORDER BY item_seq", nativeQuery = true)
    List<GrpMenuAccess> findAllSubmenuByMenuId(@Param("menu_item_id") Long menu_item_id,@Param("usr_grp") Long usr_grp);
	
	@Query(value="SELECT * FROM sec_grp_menu_access a where a.status= 'Enable' and a.menu_id=:menu_item_id and a.usr_grp =:usr_grp ORDER BY item_seq", nativeQuery = true)
    List<GrpMenuAccess> findAllSubmenuByMenuIdWithStatus(@Param("menu_item_id") Long menu_item_id,@Param("usr_grp") Long usr_grp);

	
	@Query(value="SELECT * FROM sec_grp_menu_access a where a.menu_item_id=:menu_item_id", nativeQuery = true)
	GrpMenuAccess findById1(@Param("menu_item_id") Long menu_item_id);
	
	@Query(value="SELECT * FROM sec_grp_menu_access a where a.menu_item_id=:menu_item_id", nativeQuery = true)
	List<GrpMenuAccess> findlist(@Param("menu_item_id") Long menu_item_id);
	
	@Query(value="SELECT * FROM sec_grp_menu_access a where a.menu_item_id=:menu_item_id  && a.usr_grp =:usr_grp", nativeQuery = true)
	GrpMenuAccess findById2(@Param("menu_item_id") Long menu_item_id, @Param("usr_grp") Long usr_grp);
	
	@Query(value="SELECT * FROM sec_grp_menu_access a where a.menu_item_id=:menu_item_id && a.usr_grp =:usr_grp", nativeQuery = true)
	Optional<GrpMenuAccess>  findbygrpandmenuid(@Param("menu_item_id") Long menu_item_id,@Param("usr_grp") Long usr_grp);
	
	@Query(value="SELECT * FROM sec_grp_menu_access a where a.usr_grp =:usr_grp and a.menu_item_id=:menu_item_id", nativeQuery = true)
	List<GrpMenuAccess> findById(@Param("usr_grp") Long usr_grp,@Param("menu_item_id") Long menu_item_id);
	
	@Query(value="SELECT * FROM sec_grp_menu_access a where a.usr_grp =:usr_grp and a.menu_item_id=:menu_item_id", nativeQuery = true)
	GrpMenuAccess findSingle(@Param("usr_grp") Long usr_grp,@Param("menu_item_id") Long menu_item_id);

	
	@Query(value="SELECT * FROM sec_grp_menu_access a where a.menu_item_id=:menu_item_id && a.usr_grp =:usr_grp", nativeQuery = true)
	GrpMenuAccess  findByUsrgrpAndMenuitemid(@Param("menu_item_id") Long menu_item_id,@Param("usr_grp") Long usr_grp);
	
	@Query(value="SELECT * FROM sec_grp_menu_access a where  a.menu_item_id=:menu_item_id", nativeQuery = true)
	List<GrpMenuAccess>  findByGrpAndMenuid1(@Param("menu_item_id") Long menu_item_id);
	
	@Query(value="SELECT * FROM sec_grp_menu_access a where  a.menu_id = 0 && a.usr_grp =:usr_grp && a.menu_item_id=:menu_item_id ORDER BY item_seq;", nativeQuery = true)
    GrpMenuAccess findAllRootsByMenuIdAndGrp(@Param("menu_item_id") Long menu_item_id,@Param("usr_grp") Long usr_grp);
	
	@Query(value="SELECT * FROM sec_grp_menu_access a where a.menu_id=:menu_item_id", nativeQuery = true)
    List<GrpMenuAccess> findAllSubmenuByMenuitemid(@Param("menu_item_id") Long menu_item_id);
	
	@Query(value="SELECT * FROM sec_grp_menu_access a where a.menu_id=:menu_item_id && a.usr_grp =:usr_grp ORDER BY item_seq", nativeQuery = true)
    List<GrpMenuAccess> findAllSubmenuByMenuitemidAndUsrGrp(@Param("menu_item_id") Long menu_item_id,@Param("usr_grp") Long usr_grp);

	@Query(value="SELECT * FROM sec_grp_menu_access a where a.menu_item_id=:menu_item_id", nativeQuery = true)
	List<GrpMenuAccess> findAllById(@Param("menu_item_id") Long menu_item_id);
	
}
