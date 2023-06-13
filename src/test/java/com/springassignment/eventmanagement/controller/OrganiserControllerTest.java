package com.springassignment.eventmanagement.controller;

import com.springassignment.eventmanagement.entity.Organizers;
import com.springassignment.eventmanagement.services.OrganizerService;
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

class OrganiserControllerTest {

    @Mock
    private OrganizerService organizerService;

    @InjectMocks
    private OrganiserController organiserController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllOrganizer_ReturnsListOfOrganizers() {

        List<Organizers> organizers = Arrays.asList(new Organizers(1, "Organizer 1"), new Organizers(2, "Organizer 2"));
        when(organizerService.findAll()).thenReturn(organizers);


        List<Organizers> result = organiserController.getAllOrganizer();

        assertEquals(organizers, result);
    }

    @Test
    void getOrganizerById_WithValidId_ReturnsOrganizer() {

        int organizerId = 1;
        Organizers organizer = new Organizers(organizerId, "Organizer 1");
        when(organizerService.findById(organizerId)).thenReturn(organizer);


        ResponseEntity<Organizers> response = organiserController.getOrganizerById(organizerId);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(organizer, response.getBody());
    }

    @Test
    void getOrganizerById_WithInvalidId_ReturnsNotFound() {

        int organizerId = 1;
        when(organizerService.findById(organizerId)).thenThrow(NoSuchElementException.class);


        ResponseEntity<Organizers> response = organiserController.getOrganizerById(organizerId);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createOrganizer_ReturnsCreatedOrganizer() {

        Organizers organizer = new Organizers(1, "Organizer 1");
        when(organizerService.save(organizer)).thenReturn(organizer);


        ResponseEntity<Organizers> response = organiserController.createOrganizer(organizer);


        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(organizer, response.getBody());
    }

    @Test
    void updateOrganizer_WithExistingOrganizer_ReturnsUpdatedOrganizer() {

        int organizerId = 1;
        Organizers existingOrganizer = new Organizers(organizerId, "Organizer 1");
        Organizers updatedOrganizer = new Organizers(organizerId, "Updated Organizer 1");
        when(organizerService.update(organizerId, updatedOrganizer)).thenReturn(updatedOrganizer);


        ResponseEntity<Organizers> response = organiserController.updateOrganizer(organizerId, updatedOrganizer);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedOrganizer, response.getBody());
    }

}

