package com.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerID", updatable = false, nullable = false)
    private int id;

    @Column(name = "CustomerName")
    private String customername;

    @Column(name = "PhoneNumber")
    private String phonenumber;

    @Column(name = "DeliveryAddressLine1")
    private String addres;

    @Column(name = "DeliveryPostalCode")
    private String postal;

    @OneToMany
    @JoinColumn(name = "OrderID")
    private Set<Order> order;

    @Override
    public String toString() {
        return customername;
    }

    //Getters
    public String getCustomername() {
        return customername;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getAddres() {
        return addres;
    }

    public String getPostal() {
        return postal;
    }
}
