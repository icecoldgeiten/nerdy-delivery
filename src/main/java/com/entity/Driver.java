package com.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "driver")
public class Driver extends Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "DriverID", updatable = false, nullable = false)
    private int id;

    @OneToMany
    @JoinColumn(name = "DriverID")
    private Set<Route> routes;

    @Column(name = "Vehicle")
    private int vehicle;

    @Column(name = "LicenseNr")
    private int lincenseNr;
}
