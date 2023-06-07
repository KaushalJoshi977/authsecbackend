package com.realnet.entityevents.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

import com.realnet.fnd.entity.Rn_Who_AccId_Column;

@Entity
@Data
public class Entity_events_t extends Rn_Who_AccId_Column{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String entity_name;

	private String event_type;

	private boolean is_active = true;
}
