package com.entity;

import javax.persistence.*;
import java.sql.Time;
import java.util.HashSet;
import java.util.Objects;
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

    @Override
    public String toString() {
        return name + " " + startTime + " - " + endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timeslot timeslot = (Timeslot) o;
        return Objects.equals(id, timeslot.id) &&  Objects.equals(timeslotCode, timeslot.timeslotCode);
    }

    //Setters
    public Time getStartTime() {
        return startTime;
    }
    public String getName() {
        return name;
    }
    public Time getEndTime() {
        return endTime;
    }
}
