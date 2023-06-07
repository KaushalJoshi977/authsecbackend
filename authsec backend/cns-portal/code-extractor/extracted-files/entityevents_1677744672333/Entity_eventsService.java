package com.realnet.entityevents.Service;


import java.util.HashSet;
import java.util.List;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realnet.entityevents.Entity.Entity_events_t;
import com.realnet.entityevents.Repository.Entity_eventsRepository;


@Service
public class Entity_eventsService {

    @Autowired
    private Entity_eventsRepository entity_eventsRepository;

    public List<Entity_events_t> getAllEntityEvents() {
        return entity_eventsRepository.findAll();
    }

    public Entity_events_t getEntityEventById(Long id) {
        return entity_eventsRepository.findById(id).orElse(null);
    }

    public Entity_events_t createEntityEvent(Entity_events_t entityEvent) {
        return entity_eventsRepository.save(entityEvent);
    }

    public Entity_events_t updateEntityEvent(Long id, Entity_events_t entityEvent) {
        Entity_events_t existingEntityEvent = entity_eventsRepository.findById(id).orElse(null);
        if (existingEntityEvent != null) {
            existingEntityEvent.setEntity_name(entityEvent.getEntity_name());
            existingEntityEvent.setEvent_type(entityEvent.getEvent_type());
            existingEntityEvent.set_active(entityEvent.is_active());
            return entity_eventsRepository.save(existingEntityEvent);
        }
        return null;
    }

    
    public void deleteEntityEvent(Long id) {
    	entity_eventsRepository.deleteById(id);
    }

    

    
    public boolean isEntityAllowed(String entity_name) {

        System.out.println("isEntityAllowed called " + entity_name);

        if (entity_name == null || entity_name.isEmpty()) {
            return true; // Allow unspecified entities
        }

        List<Entity_events_t> entityEventsList = entity_eventsRepository.findByEntityName(entity_name);
        if (entityEventsList == null || entityEventsList.isEmpty()) {
            return false; // Disallow entities not found in entity events
        }

        return true;
    }

    
    
    
 
    
    
    
    
    
//  public boolean existsByEntityNameAndEventType(String entity_name, String event_type) {
//      return entity_eventsRepository.existsByEntityNameAndEventType(entity_name, event_type);
//  }
  

}