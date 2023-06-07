package com.realnet.Abc_1665647440047_back.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
 

import com.realnet.Abc_1665647440047_back.Entity.Abc;

@Repository
public interface  AbcRepository  extends  JpaRepository<Abc, Long>  { 
}