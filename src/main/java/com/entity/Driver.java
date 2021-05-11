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

    //GETTERS
    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getInserts() {return inserts;}
    public String getSirname() {return sirname;}
    public LocalDate getBirthdate() {return birthdate;}
    public int getPhone() {return phone;}
    public int getVehicle() {return vehicle;}
    public int getLincenseNr() {return lincenseNr; }

    //SETTERS
    public void setName(String name) {this.name = name;}
    public void setInserts(String inserts) {this.inserts = inserts;}
    public void setSirname(String sirname) {this.sirname = sirname;}
    public void setBirthdate(LocalDate birthdate) {this.birthdate = birthdate;}
    public void setPhone(int phone) {this.phone = phone;}
    public void setVehicle(int vehicle) {this.vehicle = vehicle;}
    public void setLincenseNr(int lincenseNr) {this.lincenseNr = lincenseNr;}
}
