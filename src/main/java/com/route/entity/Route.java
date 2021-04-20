package com.route.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Set;

@Entity
@Table(name = "route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RouteID", updatable = false, nullable = false)
    private int id;

    @Column(name = "Duration")
    private int duration;

    @ManyToOne
    @JoinColumn(name = "DriverID")
    private Driver driver;

    @OneToMany(mappedBy = "route")
    private Set<Order> orders;
}
