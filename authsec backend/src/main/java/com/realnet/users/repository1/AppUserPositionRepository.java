package com.realnet.users.repository1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realnet.users.entity1.AppUserPosition;

@Repository
public interface AppUserPositionRepository extends JpaRepository<AppUserPosition,String>{

}
