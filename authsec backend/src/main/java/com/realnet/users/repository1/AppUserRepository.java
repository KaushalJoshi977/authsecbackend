package com.realnet.users.repository1;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.realnet.users.entity1.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Long>{
	Optional<AppUser> findByUsername(String username);

//	AppUser findByEmail(String email);

	Optional<AppUser> findByUsernameAndUserPassw(String username, String userPassw);

	Boolean existsByEmail(String email);
	@Query(value="SELECT customer_number FROM  be_cust_master  WHERE customer_id = ?1",
			countQuery = "SELECT count(*) FROM  be_cust_master  WHERE customer_id = ?1",
			nativeQuery=true)
	String getCustomerNumber(BigDecimal customerId);
	
	@Query(value="select refcodedesc from refcodedet where reftype = ?1 and refcode = ?2",
			countQuery="select refcodedesc from refcodedet where reftype = ?1 and refcode = ?2",
			nativeQuery=true)
	String getStatus(String tableName,String code);
	
	@Query(value = "SELECT * FROM sec_users a where a.email =?1", nativeQuery = true)
	AppUser findByEmail(String email);
	
	@Query(value = "SELECT * FROM sec_users a where a.user_name =?1", nativeQuery = true)
	AppUser findByUserName(String email);
	
	
	@Query(value = "SELECT * FROM sec_users a where a.random_no = ?1", nativeQuery = true)
	AppUser findByToken(String random_no);

//	@Query(value="SELECT * FROM sec_grp_menu_access a where a.usr_grp= ?1", nativeQuery = true)
//	GrpMenuAccess findByUsrGrp(Long usr_grp);
	
	@Query(value = "SELECT * FROM sec_users a where a.account_id =?1", nativeQuery = true)
	List<AppUser> getall(Long account_id);
	
	@Query(value = "SELECT * FROM sec_users a where  a.usr_grp=42 and a.account_id =?1", nativeQuery = true)
	List<AppUser> getalluser(Long account_id);
	
	@Query(value = "SELECT * FROM sec_users a where  a.usr_grp=45 and a.account_id =?1", nativeQuery = true)
	List<AppUser> getallguest(Long account_id);

	@Query(value = "SELECT * FROM sec_users a where a.user_id =?1", nativeQuery = true)
    AppUser findByUserId(Long id);
	
	@Query(value = "SELECT * FROM sec_users  where user_name LIKE %:keyword% ", nativeQuery = true)
    List<AppUser> SearchByKeyword(@Param("keyword") String keyword);
	
	@Query(value = "SELECT * FROM sec_users a where a.active =true", nativeQuery = true)
	Page<AppUser> getallusers(Pageable page);
	
//	@Query(value = "SELECT * FROM realnet_CNSBE.secuser_roles  a.secuser_id =?1", nativeQuery = true)
//	List<secuser_roles> getseuserroles(Long secuser_id);
	
	
	
}
