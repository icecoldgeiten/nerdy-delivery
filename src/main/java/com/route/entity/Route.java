package com.route.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RouteID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "Duration")
    private int duration;

    @ManyToOne
    @JoinColumn(name = "DriverID")
    private Driver driver;

    @OneToMany
    @JoinColumn(name = "RouteID")
    private Set<Order> orders;

    //Getters
    public Long getId() {
        return id;
    }

    public int getDuration() {
        return duration;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public Driver getDriver() {
        return driver;
    }
}
