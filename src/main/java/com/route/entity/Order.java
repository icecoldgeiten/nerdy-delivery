package com.route.entity;

import javax.persistence.*;

import java.util.ArrayList;
//@Entity
//@Table(name = "orders", schema = "nerdygadgets")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "OrderID", updatable = false, nullable = false)
    private int id;

    @Column(name = "RouteID")
    private int routeID;

    @Column(name = "DeliveryInstructions")
    private String deliveryInstructions;

    @Column(name = "Comments")
    private String comments;

    @Column(name = "Delivered")
    private int delivered;

    @Column(name = "Nothome")
    private int notHome;

    //Dit moet mooi geregeld worden
    private Customer customer;
    private ArrayList<Orderline> orderlines;

    //Getters
    public int getId() {
        return id;
    }

    public int getRouteID() {
        return routeID;
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
}
