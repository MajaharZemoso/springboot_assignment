package com.springassignment.eventmanagement.services;

import com.springassignment.eventmanagement.dao.OrganizerRepository;
import com.springassignment.eventmanagement.entity.Organizers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrganizerServiceImplTest {
    @Mock
    private OrganizerRepository organizerRepository;

    @InjectMocks
    private OrganizerServiceImpl organizerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindAll() {

        List<Organizers> organizersList = new ArrayList<>();
        organizersList.add(new Organizers("Organizer 1", "Location 1"));
        organizersList.add(new Organizers("Organizer 2", "Location 2"));

        when(organizerRepository.findAll()).thenReturn(organizersList);


        List<Organizers> result = organizerService.findAll();


        assertEquals(organizersList, result);
        verify(organizerRepository, times(1)).findAll();
    }

    @Test
    void testFindById_existingId_shouldReturnOrganizer() {

        Organizers organizer = new Organizers("Organizer 1", "Location 1");

        when(organizerRepository.findById(anyInt())).thenReturn(Optional.of(organizer));


        Organizers result = organizerService.findById(1);


        assertEquals(organizer, result);
        verify(organizerRepository, times(1)).findById(1);
    }

    @Test
    void testSave() {

        Organizers organizer = new Organizers("Organizer 1", "Location 1");

        when(organizerRepository.save(any(Organizers.class))).thenReturn(organizer);


        Organizers result = organizerService.save(organizer);


        assertEquals(organizer, result);
        verify(organizerRepository, times(1)).save(organizer);
    }

    @Test
    void testDeleteById() {

        organizerService.deleteById(1);

        verify(organizerRepository, times(1)).deleteById(1);
    }
}
