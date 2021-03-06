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

    @ManyToOne(targetEntity = Route.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "RouteID")
    private Route route;

    @ManyToOne(targetEntity = OrderStatus.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "OrderStatusID")
    private OrderStatus orderStatus;

    @Column(name = "DeliveryInstructions")
    private String deliveryInstructions;

    @Column(name = "Comments")
    private String comments;

    @Column(name = "ExpectedDeliveryDate")
    private String expectedDeliveryDate;

    @OneToMany(targetEntity = OrderLine.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "OrderID")
    private Set<OrderLine> orderLines;

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
    public Set<OrderLine> getOrderlines() {
        return orderLines;
    }
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
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
