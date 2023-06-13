package com.springassignment.eventmanagement.services;

import com.springassignment.eventmanagement.entity.Organizers;

import java.util.List;

public interface OrganizerService {
    public List<Organizers> findAll();

    public Organizers findById(int theId);

    public Organizers save(Organizers theOrganizer);

    public void deleteById(int theId);

    Organizers update(int theId, Organizers organizers);
}
