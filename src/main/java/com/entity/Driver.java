package com.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "driver")
public class Driver extends Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DriverID", updatable = false, nullable = false)
    private int id;
    @OneToMany
    @JoinColumn(name = "DriverID")
    private Set<Route> routes;
    @Column(name = "Vehicle")
    private int vehicle;
    @Column(name = "LicenseNr")
    private int lincenseNr;

    //Getters
    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return id == driver.id;
    }
}
