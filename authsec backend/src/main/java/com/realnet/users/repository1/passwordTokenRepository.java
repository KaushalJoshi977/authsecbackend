package com.realnet.users.repository1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.realnet.users.entity.PasswordResetToken;

public interface passwordTokenRepository extends JpaRepository<PasswordResetToken, Long> {

//	@Query(value = "SELECT * FROM password_reset_token a where a.token =?1", nativeQuery = true)
//
//	Optional findByToken(String token);
	
//	@Query(value = "SELECT * FROM password_reset_token a where a.token = ?1", nativeQuery = true)
//
//	PasswordResetToken findByToken(String token);
	
	@Query(value="SELECT * FROM password_reset_token a where a.token=:token", nativeQuery = true)
	PasswordResetToken findByToken(@Param("token") String token);


}
