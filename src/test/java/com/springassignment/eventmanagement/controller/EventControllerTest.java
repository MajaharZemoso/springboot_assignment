package com.springassignment.eventmanagement.controller;

import com.springassignment.eventmanagement.entity.Events;
import com.springassignment.eventmanagement.services.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EventControllerTest {

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllEvents_ReturnsListOfEvents() {
        // Arrange
        List<Events> events = Arrays.asList(new Events(1, "Event 1"), new Events(2, "Event 2"));
        when(eventService.findAll()).thenReturn(events);

        // Act
        List<Events> result = eventController.getAllEvents();

        // Assert
        assertEquals(events, result);
    }

    @Test
    void getEventById_WithValidId_ReturnsEvent() {
        // Arrange
        int eventId = 1;
        Events event = new Events(eventId, "Event 1");
        when(eventService.findById(eventId)).thenReturn(event);

        // Act
        ResponseEntity<Events> response = eventController.getEventById(eventId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(event, response.getBody());
    }

    @Test
    void getEventById_WithInvalidId_ReturnsNotFound() {
        // Arrange
        int eventId = 1;
        when(eventService.findById(eventId)).thenThrow(NoSuchElementException.class);

        // Act
        ResponseEntity<Events> response = eventController.getEventById(eventId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateEvent_WithExistingEvent_ReturnsUpdatedEvent() {
        // Arrange
        int eventId = 1;
        Events existingEvent = new Events(eventId, "Event 1");
        Events updatedEvent = new Events(eventId, "Updated Event 1");
        when(eventService.update(eventId, updatedEvent)).thenReturn(updatedEvent);

        // Act
        ResponseEntity<Events> response = eventController.updateEvent(eventId, updatedEvent);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedEvent, response.getBody());
    }

    @Test
    void updateEvent_WithNonexistentEvent_ReturnsNotFound() {
        // Arrange
        int eventId = 1;
        Events updatedEvent = new Events(eventId, "Updated Event 1");
        when(eventService.update(eventId, updatedEvent)).thenThrow(NoSuchElementException.class);

        // Act
        ResponseEntity<Events> response = eventController.updateEvent(eventId, updatedEvent);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createEvent_ReturnsCreatedEvent() {
        // Arrange
        Events event = new Events(1, "Event 1");
        when(eventService.save(event)).thenReturn(event);

        // Act
        ResponseEntity<Events> response = eventController.createEvent(event);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(event, response.getBody());
    }

}

