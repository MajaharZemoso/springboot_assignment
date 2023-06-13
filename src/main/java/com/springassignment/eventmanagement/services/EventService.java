package com.springassignment.eventmanagement.services;

import com.springassignment.eventmanagement.entity.Events;

import java.util.List;

public interface EventService {
    public List<Events> findAll();

    public Events findById(int theId);

    public Events save(Events theEvents);

    public void deleteById(int theId);

    public Events update(int theId, Events theEvents);
}
