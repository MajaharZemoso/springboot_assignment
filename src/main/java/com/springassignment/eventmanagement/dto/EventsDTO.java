package com.springassignment.eventmanagement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class EventsDTO {
    private int id;
    private String name;
    private String venue;
    private Set<UsersDTO> users;
    private Set<OrganizersDTO> organizers;

    public EventsDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
