package com.realnet.bugTracker.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.realnet.incident.entity.WhooColomns;

import lombok.Data;

@Entity
@Data

public class BugTracker extends WhooColomns {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String key_no;
    private String summary;
    private String assignee;
    private String reporter;
    
    private String select_project;
    private String title;
    private String select_status;
    private String select_priority;
    private String steps;
    private String description;
    
    
    private String fileName;
    
    private String fileType;
 
    @Lob
    private byte[] data;
    
    
}
