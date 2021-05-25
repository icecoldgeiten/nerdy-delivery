package com.entity;

import javax.persistence.*;

@Entity
@Table(name = "orderlines")
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderlineId", updatable = false, nullable = false)
    private int id;

    @JoinColumn(name = "StockItemID")
    private int stockitemid;

    @Column(name = "Description")
    private String description;

    @Column(name = "Quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "OrderID")
    private Order order;

    public int getStockitemid() {
        return stockitemid;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }
}
