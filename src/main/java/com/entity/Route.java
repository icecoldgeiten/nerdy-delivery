package com.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RouteID", unique = true)
    private Long id;

    @Column(name = "Duration")
    private int duration;

    @ManyToOne(targetEntity = RouteStatus.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "status")
    private RouteStatus routeStatus;

    @ManyToOne
    @JoinColumn(name = "DriverID")
    private Driver driver;

    @OneToMany(targetEntity = Order.class, mappedBy = "route", cascade = {CascadeType.MERGE}, orphanRemoval = true)
    private Set<Order> orders = new HashSet<>();

    public Route() {}

    public Route(Driver driver, Set<Order> orders) {
        this.driver = driver;
        this.orders = orders;
    }

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
    public RouteStatus getRouteStatus() {
        return routeStatus;
    }

    //Setters
    public void setId(Long id) {
        this.id = id;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public void setDriver(Driver driver) {
        this.driver = driver;
    }
    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
    public void setRouteStatus(RouteStatus routeStatus) {
        this.routeStatus = routeStatus;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", driver=" + driver.getName() +
                '}';
    }
}
