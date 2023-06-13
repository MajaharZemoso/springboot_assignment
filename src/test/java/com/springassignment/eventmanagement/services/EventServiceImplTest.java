package com.springassignment.eventmanagement.services;

import com.springassignment.eventmanagement.dao.EventRepository;
import com.springassignment.eventmanagement.entity.Events;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EventServiceImplTest {
    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindAll() {

        List<Events> eventsList = new ArrayList<>();
        eventsList.add(new Events("Event 1", "Venue 1"));
        eventsList.add(new Events("Event 2", "Venue 2"));

        when(eventRepository.findAll()).thenReturn(eventsList);

        List<Events> result = eventService.findAll();

        assertEquals(eventsList, result);
        verify(eventRepository, times(1)).findAll();
    }

    @Test
    void testFindById_existingId_shouldReturnEvent() {

        Events event = new Events("Event 1", "Venue 1");

        when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event));

        Events result = eventService.findById(1);

        assertEquals(event, result);
        verify(eventRepository, times(1)).findById(1);
    }

    @Test
    void testFindById_nonExistingId_shouldThrowNoSuchElementException() {
        when(eventRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> eventService.findById(1));
        verify(eventRepository, times(1)).findById(1);
    }

    @Test
    void testSave() {
        Events event = new Events("Event 1", "Venue 1");

        when(eventRepository.save(any(Events.class))).thenReturn(event);

        Events result = eventService.save(event);

        assertEquals(event, result);
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void testDeleteById() {
        eventService.deleteById(1);

        verify(eventRepository, times(1)).deleteById(1);
    }
}

