package com.springassignment.eventmanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UsersDTO {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    @JsonIgnore
    private Set<EventsDTO> events;


    public UsersDTO(int id, String firstName) {
        this.id = id;
        this.firstName = firstName;
    }
}
