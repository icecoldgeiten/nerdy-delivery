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

    @ManyToOne
    @JoinColumn(name = "RouteID",insertable = false, updatable = false)
    private Route route;

    @Column(name = "DeliveryInstructions")
    private String deliveryInstructions;

    @Column(name = "Comments")
    private String comments;

    @Column(name = "Delivered")
    private int delivered;

    @Column(name = "Nothome")
    private int notHome;

    @OneToMany
    @JoinColumn(name = "OrderID")
    private Set<Orderline> orderlines;

    //Getters
    public int getId() {
        return id;
    }

    public Route getRouteID() {
        return route;
    }

    public String getComments() {
        return comments;
    }

    public int getDelivered() {
        return delivered;
    }

    public int getNotHome() {
        return notHome;
    }

    public Customer getCustomer() {
        return customer;
    }
}
