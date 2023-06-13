package com.springassignment.eventmanagement.dao;

import com.springassignment.eventmanagement.entity.Organizers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizerRepository extends JpaRepository<Organizers,Integer> {

}
