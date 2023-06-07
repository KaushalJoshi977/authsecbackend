package com.realnet.bugTracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realnet.bugTracker.Entity.BugTracker;

@Repository
public interface BugTrackerRepository extends JpaRepository<BugTracker, Long> {
	
}
