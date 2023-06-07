package com.realnet.users.repository1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realnet.users.entity1.AppUserDepartment;

@Repository
public interface AppUserDepartmentRepository extends JpaRepository<AppUserDepartment,String>{

}
