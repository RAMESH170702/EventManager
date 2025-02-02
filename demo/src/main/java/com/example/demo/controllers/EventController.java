package com.example.demo.controllers;

import com.example.demo.models.Event;
import com.example.demo.services.WebSocketService;
import com.example.demo.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private WebSocketService webSocketService;

    @PostMapping("/create")
    public ResponseEntity<Event> createEvent(@RequestBody Event event, Authentication authentication) {
        event.setCreatedBy(authentication.getName()); // Set event owner
        return ResponseEntity.ok(eventService.createEvent(event));
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable String id) {
        Optional<Event> event = eventService.getEventById(id);
        return event.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/my-events")
    public ResponseEntity<List<Event>> getUserEvents(Authentication authentication) {
        return ResponseEntity.ok(eventService.getEventsByUser(authentication.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable String id, @RequestBody Event event) {
        return ResponseEntity.ok(eventService.updateEvent(id, event));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable String id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok("Event deleted successfully");
    }

    @PutMapping("/{id}/join")
    public ResponseEntity<String> joinEvent(@PathVariable String id, Authentication authentication) {
        webSocketService.updateAttendees(id, authentication.getName());
        return ResponseEntity.ok("Joined event successfully!");
    }

}

