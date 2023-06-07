package com.realnet.entitybuilder.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realnet.entitybuilder.entity.frontendtable;
@Repository
public interface FrontendRepo extends JpaRepository<frontendtable, Long> {

}
