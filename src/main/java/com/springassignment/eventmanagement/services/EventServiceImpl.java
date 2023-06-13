package com.springassignment.eventmanagement.services;

import com.springassignment.eventmanagement.dao.EventRepository;
import com.springassignment.eventmanagement.entity.Events;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService{

    private EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Events> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public Events findById(int theId) {
        return eventRepository.findById(theId).orElseThrow(() -> new NoSuchElementException("Id not found"));
    }

    @Override
    public Events save(Events theEvents) {
             return eventRepository.save(theEvents);
    }

    @Override
    public void deleteById(int theId) {
            eventRepository.deleteById(theId);
    }

    @Override
    public Events update(int theId, Events updatedEvent) {
        Optional<Events> result = eventRepository.findById(theId);

        if (result.isPresent()) {
            Events theEvent = result.get();
            theEvent.setName(updatedEvent.getName());
            theEvent.setVenue(updatedEvent.getVenue());
            eventRepository.save(theEvent);
            return theEvent;
        } else {
            throw new RuntimeException("Did not find Event id : " + theId);
        }
    }
}
