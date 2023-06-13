package com.springassignment.eventmanagement.controller;

import com.springassignment.eventmanagement.entity.Organizers;
import com.springassignment.eventmanagement.services.OrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/organizers")
public class OrganiserController {

    private OrganizerService organizerService;

    @Autowired
    public OrganiserController(OrganizerService theorganizerSercie) {
        organizerService = theorganizerSercie;
    }

    @GetMapping
    public List<Organizers> getAllOrganizer() {
        return organizerService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Organizers> getOrganizerById(@PathVariable("id") Integer id) {
        try {
            Organizers organizers = organizerService.findById(id);
            return ResponseEntity.ok(organizers);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Organizers> createOrganizer(@RequestBody Organizers organizers) {
        Organizers organizer1 = organizerService.save(organizers);
        return ResponseEntity.status(HttpStatus.CREATED).body(organizer1);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Organizers> updateOrganizer(@PathVariable("id") int id, @RequestBody Organizers organizers) {
        try {
            Organizers theOrganizers = organizerService.update(id,organizers);
            return ResponseEntity.ok(theOrganizers);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganizer(@PathVariable("id") Integer id) {
        try {
            organizerService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
