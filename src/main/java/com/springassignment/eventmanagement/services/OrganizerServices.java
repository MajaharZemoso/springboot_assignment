package com.springassignment.eventmanagement.services;

import com.springassignment.eventmanagement.dao.OrganizerRepository;
import com.springassignment.eventmanagement.entity.Organizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizerServices {

    private OrganizerRepository organizerRepository;

    @Autowired
    public OrganizerServices(OrganizerRepository organizerRepository) {
        this.organizerRepository = organizerRepository;
    }

    public List<Organizers> getAllOrganisers() {
        return organizerRepository.findAll();
    }

    public Optional<Organizers> getAllOrganizerById(int theId) {
        return organizerRepository.findById(theId);
    }

    public Organizers saveOrganizer(Organizers theOrganizer) {
        organizerRepository.save(theOrganizer);
        return theOrganizer;
    }

    public void deleteOrganizerById(int theId) {
        organizerRepository.deleteById(theId);
    }

    public Organizers updateOrganizer(int theId, Organizers updatedEvent) {
        Optional<Organizers> result = organizerRepository.findById(theId);

        if (result.isPresent()) {
            Organizers theEvent = result.get();
            theEvent.setName(updatedEvent.getName());
            theEvent.setLocation(updatedEvent.getLocation());
            organizerRepository.save(theEvent);
            return theEvent;
        } else {
            throw new RuntimeException("Did not find Event id : " + theId);
        }
    }
}
