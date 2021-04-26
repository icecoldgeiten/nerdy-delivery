package com.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "driver")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "DriverID", updatable = false, nullable = false)
    private int id;

    @OneToMany
    @JoinColumn(name = "DriverID")
    private Set<Route> routes;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Inserts")
    private String inserts;

    @Column(name = "Sirname", nullable = false)
    private String sirname;

    @Column(name = "Birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(name = "Phonenumber", nullable = false)
    private int phone;

    @Column(name = "Username", nullable = false)
    private String userName;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "Vehicle")
    private int vehicle;

    @Column(name = "LicenseNr")
    private int lincenseNr;

    //Getters
    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getUserName() {
        return userName;
    }
    public String getInserts() {return inserts;}
    public String getSirname() {return sirname;}
    public LocalDate getBirthdate() {return birthdate;}
    public int getPhone() {return phone;}
    public int getVehicle() {return vehicle;}
    public int getLincenseNr() {return lincenseNr; }
}
