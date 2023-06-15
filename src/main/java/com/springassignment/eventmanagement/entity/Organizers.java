package com.springassignment.eventmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "organizers")
public class Organizers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organizer_id")
    private int id;

    @Column(name = "organizer_name")
    private String name;

    @Column(name = "organizer_location")
    private String location;

    @JsonIgnore
    @ManyToMany(mappedBy = "organizers",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Events> events = new HashSet<>();

    public Organizers(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public Organizers(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
