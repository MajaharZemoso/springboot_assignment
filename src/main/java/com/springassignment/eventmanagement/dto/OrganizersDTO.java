package com.springassignment.eventmanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class OrganizersDTO {
    private int id;
    private String name;
    private String location;
    @JsonIgnore
    private Set<EventsDTO> events;

    public OrganizersDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
