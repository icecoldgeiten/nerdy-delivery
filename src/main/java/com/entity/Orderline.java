package com.entity;

import javax.persistence.*;

@Entity
@Table(name = "orderlines")
public class Orderline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderlineId", updatable = false, nullable = false)
    private int id;

    @Column(name = "Description")
    private String description;

    @Column(name = "Quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "OrderID")
    private Order order;
}
