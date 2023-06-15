package com.springassignment.eventmanagement.controller;

import com.springassignment.eventmanagement.dto.OrganizersDTO;
import com.springassignment.eventmanagement.entity.Organizers;
import com.springassignment.eventmanagement.services.OrganizerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/organizers")
public class OrganiserController {

    private OrganizerService organizerService;

    private ModelMapper modelMapper;

    @Autowired
    public OrganiserController(OrganizerService theorganizerService, ModelMapper modelMapper) {
        organizerService = theorganizerService;
        this.modelMapper = modelMapper;
    }
    @GetMapping
    public List<OrganizersDTO> getAllOrganizers() {
        List<Organizers> organizersList = organizerService.findAll();
        return organizersList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizersDTO> getOrganizerById(@PathVariable("id") Integer id) {
        try {
            Organizers organizers = organizerService.findById(id);
            OrganizersDTO organizersDTO = convertToDto(organizers);
            return ResponseEntity.ok(organizersDTO);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<OrganizersDTO> createOrganizer(@RequestBody OrganizersDTO organizersDTO) {
        Organizers organizers = convertToEntity(organizersDTO);
        Organizers createdOrganizer = organizerService.save(organizers);
        OrganizersDTO createdOrganizerDTO = convertToDto(createdOrganizer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrganizerDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrganizersDTO> updateOrganizer(@PathVariable("id") int id, @RequestBody OrganizersDTO organizersDTO) {
        try {
            Organizers organizers = convertToEntity(organizersDTO);
            Organizers updatedOrganizer = organizerService.update(id, organizers);
            OrganizersDTO updatedOrganizerDTO = convertToDto(updatedOrganizer);
            return ResponseEntity.ok(updatedOrganizerDTO);
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

    private OrganizersDTO convertToDto(Organizers organizers) {
        return modelMapper.map(organizers, OrganizersDTO.class);
    }

    private Organizers convertToEntity(OrganizersDTO organizersDTO) {
        return modelMapper.map(organizersDTO, Organizers.class);
    }
}
