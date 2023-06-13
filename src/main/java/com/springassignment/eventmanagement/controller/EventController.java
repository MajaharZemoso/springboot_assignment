package com.springassignment.eventmanagement.controller;

import com.springassignment.eventmanagement.entity.Events;
import com.springassignment.eventmanagement.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/events")
public class EventController {

    private EventService eventService;

    @Autowired
    public EventController(EventService theEventSerice) {
        eventService = theEventSerice;
    }

    @GetMapping
    public List<Events> getAllEvents() {
        return eventService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Events> getEventById(@PathVariable("id") Integer id) {
        try {
            Events events = eventService.findById(id);
            return ResponseEntity.ok(events);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Events> updateEvent(@PathVariable("id") int id, @RequestBody Events events) {
        try {
            Events theEvents = eventService.update(id,events);
            return ResponseEntity.ok(theEvents);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Events> createEvent(@RequestBody Events events) {
        Events events1 = eventService.save(events);
        return ResponseEntity.status(HttpStatus.CREATED).body(events1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable("id") Integer id) {
        try {
            eventService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

