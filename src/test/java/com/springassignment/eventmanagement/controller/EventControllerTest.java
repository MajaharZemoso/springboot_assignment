package com.springassignment.eventmanagement.controller;

import com.springassignment.eventmanagement.dto.EventsDTO;
import com.springassignment.eventmanagement.entity.Events;
import com.springassignment.eventmanagement.services.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EventControllerTest {

    @Mock
    private EventService eventService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EventController eventController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getEventById_WithValidId_ReturnsEventDTO() {
        int eventId = 1;
        Events event = new Events(eventId, "Event 1");
        EventsDTO eventDTO = new EventsDTO(eventId, "Event 1");
        when(eventService.findById(eventId)).thenReturn(event);
        when(modelMapper.map(event, EventsDTO.class)).thenReturn(eventDTO);

        ResponseEntity<EventsDTO> response = eventController.getEventById(eventId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(eventDTO, response.getBody());
    }

    @Test
    void getEventById_WithInvalidId_ReturnsNotFound() {
        int eventId = 1;
        when(eventService.findById(eventId)).thenThrow(NoSuchElementException.class);

        ResponseEntity<EventsDTO> response = eventController.getEventById(eventId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateEvent_WithExistingEvent_ReturnsUpdatedEventDTO() {
        int eventId = 1;
        EventsDTO updatedEventDTO = new EventsDTO(eventId, "Updated Event 1");
        Events updatedEvent = new Events(eventId, "Updated Event 1");
        when(modelMapper.map(updatedEventDTO, Events.class)).thenReturn(updatedEvent);
        when(eventService.update(eventId, updatedEvent)).thenReturn(updatedEvent);
        when(modelMapper.map(updatedEvent, EventsDTO.class)).thenReturn(updatedEventDTO);

        ResponseEntity<EventsDTO> response = eventController.updateEvent(eventId, updatedEventDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedEventDTO, response.getBody());
    }

    @Test
    void createEvent_ReturnsCreatedEventDTO() {
        EventsDTO eventDTO = new EventsDTO(1, "Event 1");
        Events event = new Events(1, "Event 1");
        when(modelMapper.map(eventDTO, Events.class)).thenReturn(event);
        when(eventService.save(event)).thenReturn(event);
        when(modelMapper.map(event, EventsDTO.class)).thenReturn(eventDTO);

        ResponseEntity<EventsDTO> response = eventController.createEvent(eventDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(eventDTO, response.getBody());
    }

    @Test
    void deleteEvent_WithExistingEvent_ReturnsNoContent() {
        int eventId = 1;

        ResponseEntity<Void> response = eventController.deleteEvent(eventId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(eventService, times(1)).deleteById(eventId);
    }

    @Test
    void deleteEvent_WithNonexistentEvent_ReturnsNotFound() {
        int eventId = 1;
        doThrow(NoSuchElementException.class).when(eventService).deleteById(eventId);

        ResponseEntity<Void> response = eventController.deleteEvent(eventId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
