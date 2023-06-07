"package com.realnet.entityevents.Service;" + "\r\n" + 
"" + "\r\n" + 
"" + "\r\n" + 
"import java.util.HashSet;" + "\r\n" + 
"import java.util.List;" + "\r\n" + 
"" + "\r\n" + 
"import java.util.Set;" + "\r\n" + 
"" + "\r\n" + 
"import org.springframework.beans.factory.annotation.Autowired;" + "\r\n" + 
"import org.springframework.stereotype.Service;" + "\r\n" + 
"" + "\r\n" + 
"import com.realnet.entityevents.Entity.Entity_events_t;" + "\r\n" + 
"import com.realnet.entityevents.Repository.Entity_eventsRepository;" + "\r\n" + 
"" + "\r\n" + 
"" + "\r\n" + 
"@Service" + "\r\n" + 
"public class Entity_eventsService {" + "\r\n" + 
"" + "\r\n" + 
"    @Autowired" + "\r\n" + 
"    private Entity_eventsRepository entity_eventsRepository;" + "\r\n" + 
"" + "\r\n" + 
"    public List<Entity_events_t> getAllEntityEvents() {" + "\r\n" + 
"        return entity_eventsRepository.findAll();" + "\r\n" + 
"    }" + "\r\n" + 
"" + "\r\n" + 
"    public Entity_events_t getEntityEventById(Long id) {" + "\r\n" + 
"        return entity_eventsRepository.findById(id).orElse(null);" + "\r\n" + 
"    }" + "\r\n" + 
"" + "\r\n" + 
"    public Entity_events_t createEntityEvent(Entity_events_t entityEvent) {" + "\r\n" + 
"        return entity_eventsRepository.save(entityEvent);" + "\r\n" + 
"    }" + "\r\n" + 
"" + "\r\n" + 
"    public Entity_events_t updateEntityEvent(Long id, Entity_events_t entityEvent) {" + "\r\n" + 
"        Entity_events_t existingEntityEvent = entity_eventsRepository.findById(id).orElse(null);" + "\r\n" + 
"        if (existingEntityEvent != null) {" + "\r\n" + 
"            existingEntityEvent.setEntity_name(entityEvent.getEntity_name());" + "\r\n" + 
"            existingEntityEvent.setEvent_type(entityEvent.getEvent_type());" + "\r\n" + 
"            existingEntityEvent.set_active(entityEvent.is_active());" + "\r\n" + 
"            return entity_eventsRepository.save(existingEntityEvent);" + "\r\n" + 
"        }" + "\r\n" + 
"        return null;" + "\r\n" + 
"    }" + "\r\n" + 
"" + "\r\n" + 
"    " + "\r\n" + 
"    public void deleteEntityEvent(Long id) {" + "\r\n" + 
"    	entity_eventsRepository.deleteById(id);" + "\r\n" + 
"    }" + "\r\n" + 
"" + "\r\n" + 
"    " + "\r\n" + 
"" + "\r\n" + 
"    " + "\r\n" + 
"    public boolean isEntityAllowed(String entity_name) {" + "\r\n" + 
"" + "\r\n" + 
"        System.out.println(\"isEntityAllowed called \" + entity_name);" + "\r\n" + 
"" + "\r\n" + 
"        if (entity_name == null || entity_name.isEmpty()) {" + "\r\n" + 
"            return true; // Allow unspecified entities" + "\r\n" + 
"        }" + "\r\n" + 
"" + "\r\n" + 
"        List<Entity_events_t> entityEventsList = entity_eventsRepository.findByEntityName(entity_name);" + "\r\n" + 
"        if (entityEventsList == null || entityEventsList.isEmpty()) {" + "\r\n" + 
"            return false; // Disallow entities not found in entity events" + "\r\n" + 
"        }" + "\r\n" + 
"" + "\r\n" + 
"        return true;" + "\r\n" + 
"    }" + "\r\n" + 
"" + "\r\n" + 
"    " + "\r\n" + 
"    " + "\r\n" + 
"    " + "\r\n" + 
" " + "\r\n" + 
"    " + "\r\n" + 
"    " + "\r\n" + 
"    " + "\r\n" + 
"    " + "\r\n" + 
"    " + "\r\n" + 
serviceloop1
"}" 