package com.realnet.entitybuilder.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realnet.entitybuilder.entity.Dashboardaxis;

@Repository
public interface DashboardAxisrepo extends JpaRepository<Dashboardaxis, Long> {
}