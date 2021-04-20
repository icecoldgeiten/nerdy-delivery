package com.route.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderID", updatable = false, nullable = false)
    private int id;

    @Column(name ="CustomerID")
    private int custumerID;

    @ManyToOne
    private int routeID;

    @Column(name = "DeliveryInstructions")
    private String deliveryInstructions;

    @Column(name = "Comments")
    private String comments;

    @Column(name = "Delivered")
    private int delivered;

    @Column(name = "Nothome")
    private int notHome;

    @ManyToOne
    @JoinColumn(name = "CustomerID", referencedColumnName = "CustomerID")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "RouteID", nullable = false)
    private Set<Orderline> orderlines;

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

    public int getCustumerID() {
        return custumerID;
    }
}
