package com.entity;

import javax.persistence.*;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "routetimeslot")
public class Timeslot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TimeSlotID", unique = true)
    private Long id;

    @OneToMany(targetEntity = Route.class, mappedBy = "timeslot", cascade = {CascadeType.MERGE}, orphanRemoval = true)
    private Set<Route> routes = new HashSet<>();

    @Column(name = "timeslotCode")
    private String timeslotCode;

    @Column(name = "name")
    private String name;

    @Column(name = "startTime")
    private Time startTime;

    @Column(name = "endTime")
    private Time endTime;
}
