package com.entity;

import javax.persistence.*;
import java.time.LocalDate;
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

    @ManyToOne
    @JoinColumn(name = "timeslot")
    private Timeslot timeslot;

    @OneToMany(targetEntity = Order.class, mappedBy = "route", fetch = FetchType.EAGER ,cascade = {CascadeType.MERGE}, orphanRemoval = true)
    private Set<Order> orders = new HashSet<>();

    @Column(name = "Date")
    private LocalDate date;

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
    public Timeslot getTimeslot() {
        return timeslot;
    }
    public LocalDate getDate() {
        return date;
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
    public void setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", driver=" + driver.getName() +
                '}';
    }


}
