package matteomoscardini.ultimoprogettosettimanale.services;


import matteomoscardini.ultimoprogettosettimanale.entities.Event;
import matteomoscardini.ultimoprogettosettimanale.exceptions.BadRequestException;
import matteomoscardini.ultimoprogettosettimanale.exceptions.NotFoundException;
import matteomoscardini.ultimoprogettosettimanale.payloads.NewEventPayload;
import matteomoscardini.ultimoprogettosettimanale.repositories.EventDAO;
import matteomoscardini.ultimoprogettosettimanale.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {
    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private UserDAO userDAO;


    public Page<Event> getAllEvents(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.eventDAO.findAll(pageable);
    }

    public Event save(NewEventPayload body) throws BadRequestException {
        this.eventDAO.findByName(body.name()).ifPresent(
                event -> {
                    throw new BadRequestException("This Event " + body.name() + " is already created");

                }
        );
        Event newEvent = new Event(body.name(), body.description(), body.date(), body.location(), body.nPartecipants());
        return eventDAO.save(newEvent);

    }

    public Event findEventById(UUID id){
        return eventDAO.findById(id).orElseThrow(() -> new NotFoundException(id));

    }

    public Event findEventByIdAndUpdate(UUID id, Event updatedEvent){
        Optional<Event> optionalEvent = eventDAO.findById(id);
        if (optionalEvent.isPresent()){
            Event found = optionalEvent.get();
            found.setName(updatedEvent.getName());
            found.setDescription(updatedEvent.getDescription());
            found.setDate(updatedEvent.getDate());
            found.setLocation(updatedEvent.getLocation());
            found.setNPartecipants(updatedEvent.getNPartecipants());

            return this.eventDAO.save(found);
        }else {
            throw new NotFoundException(id);
        }
    }

    public void findEventByIdAndDelete(UUID id){
        Optional<Event> optionalEvent = eventDAO.findById(id);
        if (optionalEvent.isPresent()){
            Event found = optionalEvent.get();
            this.eventDAO.delete(found);
        }else{
            throw new NotFoundException(id);
        }
    }

}
