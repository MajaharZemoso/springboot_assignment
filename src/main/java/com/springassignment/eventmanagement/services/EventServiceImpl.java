package com.springassignment.eventmanagement.services;

import com.springassignment.eventmanagement.dao.EventRepository;
import com.springassignment.eventmanagement.dao.OrganizerRepository;
import com.springassignment.eventmanagement.dao.UserRepository;
import com.springassignment.eventmanagement.entity.Events;
import com.springassignment.eventmanagement.entity.Organizers;
import com.springassignment.eventmanagement.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
public class EventServiceImpl implements EventService{

    private EventRepository eventRepository;
    private UserRepository userRepository;

    private OrganizerRepository organizerRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository, OrganizerRepository organizerRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.organizerRepository = organizerRepository;
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
        }
        return updatedEvent;
    }
    @Override
    public Events assignUserToEvent(int eventId, int userid) {
        Set<Users> usersSet = null;
        Events events = eventRepository.findById(eventId).get();
        Users users = userRepository.findById(userid).get();
        usersSet =  events.getUsers();
        usersSet.add(users);
        events.setUsers(usersSet);
        return eventRepository.save(events);
    }

    @Override
    public Events assignOrganizersToEvent(int eventId, int organizerid) {
        Set<Organizers> organizerSet = null;
        Events events = eventRepository.findById(eventId).get();
        Organizers organizers = organizerRepository.findById(organizerid).get();
        organizerSet =  events.getOrganizers();
        organizerSet.add(organizers);
        events.setOrganizers(organizerSet);
        return eventRepository.save(events);
    }
}
