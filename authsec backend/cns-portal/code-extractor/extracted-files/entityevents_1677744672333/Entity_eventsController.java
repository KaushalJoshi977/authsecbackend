package com.realnet.entityevents.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.realnet.entityevents.Entity.Entity_events_t;
import com.realnet.entityevents.Service.Entity_eventsService;


@RestController
@RequestMapping("/entityevents")
public class Entity_eventsController {

    @Autowired
    private Entity_eventsService entity_eventsService;

    // Get all entity events
    @GetMapping("/entityevents")
    public ResponseEntity<List<Entity_events_t>> getAllEntityEvents() {
        List<Entity_events_t> entityEvents = entity_eventsService.getAllEntityEvents();
        return new ResponseEntity<List<Entity_events_t>>(entityEvents, HttpStatus.OK);
    }

    // Get entity event by ID
    @GetMapping("/entityevents/{id}")
    public ResponseEntity<Entity_events_t> getEntityEventById(@PathVariable Long id) {
        Entity_events_t entityEvent = entity_eventsService.getEntityEventById(id);
        if (entityEvent != null) {
            return new ResponseEntity<Entity_events_t>(entityEvent, HttpStatus.OK);
        } else {
            return new ResponseEntity<Entity_events_t>(HttpStatus.NOT_FOUND);
        }
    }

    // Create entity event
    @PostMapping("/entityevents")
    public ResponseEntity<Entity_events_t> createEntityEvent(@RequestBody Entity_events_t entityEvent) {
        Entity_events_t createdEntityEvent = entity_eventsService.createEntityEvent(entityEvent);
        return new ResponseEntity<Entity_events_t>(createdEntityEvent, HttpStatus.CREATED);
    }

    // Update entity event
    @PutMapping("/entityevents/{id}")
    public ResponseEntity<Entity_events_t> updateEntityEvent(@PathVariable Long id, @RequestBody Entity_events_t entityEvent) {
        Entity_events_t updatedEntityEvent = entity_eventsService.updateEntityEvent(id, entityEvent);
        if (updatedEntityEvent != null) {
            return new ResponseEntity<Entity_events_t>(updatedEntityEvent, HttpStatus.OK);
        } else {
            return new ResponseEntity<Entity_events_t>(HttpStatus.NOT_FOUND);
        }
    }

    
    // Delete entity event
    @DeleteMapping("/entityevents/{id}")
	public ResponseEntity<String> deleteEntityEvent(@PathVariable Long id) {
    	entity_eventsService.deleteEntityEvent(id);
		String message = "ID " + id + " has been deleted";
		return ResponseEntity.ok(message);
	}
    

    

//    @GetMapping("/entityevents/name-type")
//    public ResponseEntity<Boolean> getEntityEventByName(@RequestParam("entity_name") String entity_name, @RequestParam("event_type") String event_type) {
//        boolean exists = entity_eventsService.existsByEntityNameAndEventType(entity_name, event_type);
//        return new ResponseEntity<Boolean>(exists, HttpStatus.OK);
//
//    }
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    
    
}