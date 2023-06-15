package com.springassignment.eventmanagement.controller;

import com.springassignment.eventmanagement.dto.EventsDTO;
import com.springassignment.eventmanagement.entity.Events;
import com.springassignment.eventmanagement.services.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/events")
public class EventController {

    private EventService eventService;

    private ModelMapper modelMapper;

    @Autowired
    public EventController(EventService theEventService, ModelMapper modelMapper) {
        eventService = theEventService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<EventsDTO> getAllEvents() {
        List<Events> eventsList = eventService.findAll();
        return eventsList.stream()
                .map(this::entitytoDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventsDTO> getEventById(@PathVariable("id") Integer id) {
        try {
            Events events = eventService.findById(id);
            EventsDTO eventsDTO = entitytoDTO(events);
            return ResponseEntity.ok(eventsDTO);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventsDTO> updateEvent(@PathVariable("id") int id, @RequestBody EventsDTO eventsDTO) {
        try {
            Events events = dtoToEntity(eventsDTO);
            Events updatedEvent = eventService.update(id, events);
            EventsDTO updatedEventDTO = entitytoDTO(updatedEvent);
            return ResponseEntity.ok(updatedEventDTO);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EventsDTO> createEvent(@RequestBody EventsDTO eventsDTO) {
        Events events = dtoToEntity(eventsDTO);
        Events createdEvent = eventService.save(events);
        EventsDTO createdEventDTO = entitytoDTO(createdEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEventDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("id") Integer id) {
        try {
            eventService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{eventId}/users/{userId}")
    public Events assignUserToEvent(
            @PathVariable int eventId,
            @PathVariable int userId
    ) {
        return eventService.assignUserToEvent(eventId,userId);
    }

    @PutMapping("/{eventId}/organizers/{organizerId}")
    public Events assigOrganizerToEvent(
            @PathVariable int eventId,
            @PathVariable int organizerId
    ) {
        return eventService.assignOrganizersToEvent(eventId,organizerId);
    }

    public Events dtoToEntity(EventsDTO eventsDTO){
        return this.modelMapper.map(eventsDTO, Events.class);
    }

    public EventsDTO entitytoDTO(Events events){
        return this.modelMapper.map(events,EventsDTO.class);
    }
}

