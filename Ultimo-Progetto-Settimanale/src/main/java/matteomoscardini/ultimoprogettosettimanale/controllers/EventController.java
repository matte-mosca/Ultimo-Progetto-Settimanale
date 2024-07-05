package matteomoscardini.ultimoprogettosettimanale.controllers;

import matteomoscardini.ultimoprogettosettimanale.entities.Event;
import matteomoscardini.ultimoprogettosettimanale.payloads.NewEventPayload;
import matteomoscardini.ultimoprogettosettimanale.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    EventService eventService;

    @GetMapping
    public Page<Event> getAllEvents(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "id") String sortBy){
        return this.eventService.getAllEvents(page, size, sortBy);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('EVENT_PLANNER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Event saveEvent(@RequestBody NewEventPayload body){
        return this.eventService.save(body);
    }

    @GetMapping("/{eventId}")
    public Event findSingleEvent(@PathVariable UUID EventId){
        return this.eventService.findEventById(EventId);
    }

    @PutMapping("/{eventId}")
    @PreAuthorize("hasAuthority('EVENT_PLANNER')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Event findSingleEventAndUpdate(@PathVariable UUID EventId, @RequestBody Event body){
        return this.eventService.findEventByIdAndUpdate(EventId, body);
    }

    @DeleteMapping("/{eventId}")
    @PreAuthorize("hasAuthority('EVENT_PLANNER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void findSingleEventAndDelete(@PathVariable UUID eventId){
        this.eventService.findEventByIdAndDelete(eventId);
    }
}
