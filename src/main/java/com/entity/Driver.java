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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return id == driver.id;
    }

    //Getters
    public int getId() {
        return id;
    }
    public Set<Route> getRoutes() {
        return routes;
    }
    public int getVehicle() {
        return vehicle;
    }
    public int getLincenseNr() {
        return lincenseNr;
    }
}
