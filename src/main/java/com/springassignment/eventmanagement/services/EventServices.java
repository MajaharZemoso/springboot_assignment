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
import java.util.Optional;
import java.util.Set;

@Service
public class EventServices {

    private EventRepository eventRepository;
    private UserRepository userRepository;

    private OrganizerRepository organizerRepository;


    @Autowired
    public EventServices(EventRepository eventRepository, UserRepository userRepository, OrganizerRepository organizerRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.organizerRepository = organizerRepository;
    }

    public List<Events> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Events> getAllEventsById(int theId) {
        return eventRepository.findById(theId);
    }

    public Events saveEvent(Events theEvents) {
             return eventRepository.save(theEvents);
    }

    public void deleteEventById(int theId) {
            eventRepository.deleteById(theId);
    }

    public Events assignUserToEvent(int eventId, int userId) {
        Set<Users> userSet = null;
        Events events = eventRepository.findById(eventId).get();
        Users users = userRepository.findById(userId).get();
        userSet =  events.getUsers();
        userSet.add(users);
        events.setUsers(userSet);
        return eventRepository.save(events);
    }

    public Events assignOrganizersToEvent(int eventId, int organizerId) {
        Set<Organizers> organizerset = null;
        Events events = eventRepository.findById(eventId).get();
        Organizers organizers = organizerRepository.findById(organizerId).get();
        organizerset =  events.getOrganizers();
        organizerset.add(organizers);
        events.setOrganizers(organizerset);
        return eventRepository.save(events);
    }
    public Events updateEvent(int theId, Events updatedEvent) {
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
