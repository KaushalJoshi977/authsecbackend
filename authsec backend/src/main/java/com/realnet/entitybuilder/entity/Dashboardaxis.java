package com.realnet.entitybuilder.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
public class Dashboardaxis {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String jan;
	private String feb;
	private String march;
	private String april;
	
}