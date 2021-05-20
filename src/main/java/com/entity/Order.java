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

    @ManyToOne(targetEntity = OrderStatus.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderStatusID")
    private OrderStatus orderStatus;

    @Column(name = "DeliveryInstructions")
    private String deliveryInstructions;

    @Column(name = "Comments")
    private String comments;

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
    public Set<Orderline> getOrderlines() {
        return orderlines;
    }
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    //Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setRoute(Route route) {
        this.route = route;
    }
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
