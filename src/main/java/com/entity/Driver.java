package com.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "driver")
public class Driver extends Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DriverID", updatable = false, nullable = false)
    private int id;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "DriverID")
    private Set<Route> routes;

    @Column(name = "Vehicle")
    private Integer vehicle;

    @Column(name = "LicenseNr")
    private Integer lincenseNr;

    @Column(name = "Active")
    private Boolean active;

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
    public Integer getVehicle() {
        return vehicle;
    }
    public Integer getLincenseNr() {
        return lincenseNr;
    }


    //Setters
    public void setActive(boolean active){
        this.active = active;
    }
    public void setVehicle(Integer vehicle) {
        this.vehicle = vehicle;
    }
    public void setLincenseNr(Integer lincenseNr) {
        this.lincenseNr = lincenseNr;
    }

}
