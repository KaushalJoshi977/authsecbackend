package com.realnet.codeextractor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realnet.codeextractor.entity.Rule_library_keyword;


@Repository
public interface Rule_library_keywordRepository extends JpaRepository<Rule_library_keyword, Integer> {
}
