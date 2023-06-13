package com.springassignment.eventmanagement.dao;

import com.springassignment.eventmanagement.entity.Events;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Events,Integer> {
}
