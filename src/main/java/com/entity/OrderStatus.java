package com.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "orderstatus")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderStatusID", unique = true)
    private Long id;

    @OneToMany
    private Set<Order> orders = new HashSet<>();

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String statusCode;

    //Getters
    public String getStatusCode() {
        return statusCode;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderStatus that = (OrderStatus) o;
        return Objects.equals(id, that.id) && Objects.equals(orders, that.orders) && Objects.equals(name, that.name) && Objects.equals(statusCode, that.statusCode);
    }
}
