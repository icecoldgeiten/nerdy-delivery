package com.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderID", updatable = false, nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name ="CustomerID", insertable = false, updatable = false)
    private Customer customer;

    @ManyToOne(targetEntity = Route.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "RouteID")
    private Route route;

    @Column(name = "DeliveryInstructions")
    private String deliveryInstructions;

    @Column(name = "Comments")
    private String comments;

    @Column(name = "Delivered")
    private int delivered;

    @Column(name = "Nothome")
    private int notHome;

    @Column(name = "Status")
    private String status;

    @OneToMany
    @JoinColumn(name = "OrderID")
    private Set<Orderline> orderlines;

    //Getters
    public int getId() {
        return id;
    }
    public Customer getCustomer() {
        return customer;
    }
    public Route getRoute() {
        return route;
    }
    public String getStatus() {
        return status;
    }
    public Set<Orderline> getOrderlines() {
        return orderlines;
    }

    //Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setRoute(Route route) {
        this.route = route;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
