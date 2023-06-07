package com.realnet.template.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.realnet.template.entity.TemplateFileUpload;

public interface TemplatedataRepo extends JpaRepository<TemplateFileUpload, Long>{

}
