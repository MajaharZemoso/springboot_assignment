package com.springassignment.eventmanagement.services;

import com.springassignment.eventmanagement.dao.OrganizerRepository;
import com.springassignment.eventmanagement.entity.Organizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizerServiceImpl implements OrganizerService{

    private OrganizerRepository organizerRepository;

    @Autowired
    public OrganizerServiceImpl(OrganizerRepository organizerRepository) {
        this.organizerRepository = organizerRepository;
    }

    @Override
    public List<Organizers> findAll() {
        return organizerRepository.findAll();
    }

    @Override
    public Organizers findById(int theId) {
        Optional<Organizers> result =  organizerRepository.findById(theId);

        Organizers theOrganizer = null;

        if(result.isPresent()){
            theOrganizer = result.get();
        }
        else{
            throw new RuntimeException("Did not find Event id : "+theId);
        }
        return theOrganizer;
    }

    @Override
    public Organizers save(Organizers theOrganizer) {
        organizerRepository.save(theOrganizer);
        return theOrganizer;
    }

    @Override
    public void deleteById(int theId) {
        organizerRepository.deleteById(theId);
    }

    @Override
    public Organizers update(int theId, Organizers updatedEvent) {
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
