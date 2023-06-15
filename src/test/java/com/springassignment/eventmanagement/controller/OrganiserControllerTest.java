package com.springassignment.eventmanagement.controller;

import com.springassignment.eventmanagement.dto.OrganizersDTO;
import com.springassignment.eventmanagement.entity.Organizers;
import com.springassignment.eventmanagement.services.OrganizerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrganiserControllerTest {

    @Mock
    private OrganizerService organizerService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private OrganiserController organiserController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void getOrganizerById_WithValidId_ReturnsOrganizerDTO() {
        int organizerId = 1;
        Organizers organizer = new Organizers(organizerId, "Organizer 1");
        OrganizersDTO organizerDTO = new OrganizersDTO(organizerId, "Organizer 1");
        when(organizerService.findById(organizerId)).thenReturn(organizer);
        when(modelMapper.map(organizer, OrganizersDTO.class)).thenReturn(organizerDTO);

        ResponseEntity<OrganizersDTO> response = organiserController.getOrganizerById(organizerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(organizerDTO, response.getBody());
    }

    @Test
    void getOrganizerById_WithInvalidId_ReturnsNotFound() {
        int organizerId = 1;
        when(organizerService.findById(organizerId)).thenThrow(NoSuchElementException.class);

        ResponseEntity<OrganizersDTO> response = organiserController.getOrganizerById(organizerId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createOrganizer_ReturnsCreatedOrganizerDTO() {
        OrganizersDTO organizerDTO = new OrganizersDTO(1, "Organizer 1");
        Organizers organizer = new Organizers(1, "Organizer 1");
        when(modelMapper.map(organizerDTO, Organizers.class)).thenReturn(organizer);
        when(organizerService.save(organizer)).thenReturn(organizer);
        when(modelMapper.map(organizer, OrganizersDTO.class)).thenReturn(organizerDTO);

        ResponseEntity<OrganizersDTO> response = organiserController.createOrganizer(organizerDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(organizerDTO, response.getBody());
    }


    @Test
    void deleteOrganizer_WithExistingOrganizer_ReturnsNoContent() {
        int organizerId = 1;

        ResponseEntity<Void> response = organiserController.deleteOrganizer(organizerId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(organizerService, times(1)).deleteById(organizerId);
    }

    @Test
    void deleteOrganizer_WithNonexistentOrganizer_ReturnsNotFound() {

        int organizerId = 1;
        doThrow(NoSuchElementException.class).when(organizerService).deleteById(organizerId);

        ResponseEntity<Void> response = organiserController.deleteOrganizer(organizerId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
