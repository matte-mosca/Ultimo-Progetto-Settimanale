package matteomoscardini.ultimoprogettosettimanale.controllers;

import matteomoscardini.ultimoprogettosettimanale.entities.Event;
import matteomoscardini.ultimoprogettosettimanale.payloads.NewEventPayload;
import matteomoscardini.ultimoprogettosettimanale.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    EventService eventService;

    @GetMapping
    public Page<Event> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "id") String sortBy){
        return this.eventService.getAllEvents(page, size, sortBy);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Event saveEvent(@RequestBody NewEventPayload body){
        return this.eventService.save(body);
    }

    @GetMapping("/{eventId}")
    public Event findSingleEvent(@PathVariable UUID id){
        return this.eventService.findEventById(id);
    }

    @PutMapping("/{eventId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Event findSingleEventAndUpdate(@PathVariable UUID id, @RequestBody Event body){
        return this.eventService.findEventByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{eventId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void findSingleEventAndDelete(@PathVariable UUID id){
        this.eventService.findEventByIdAndDelete(id);
    }
}
