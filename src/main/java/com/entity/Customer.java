package com.entity;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerID", updatable = false, nullable = false)
    private int id;

    @Column(name = "DeliveryAddressLine1")
    private String addres;

    @Column(name = "DeliveryPostalCode")
    private String postal;

    @Column(name = "DeliveryStatus")
    private String deliverystatus;

    @OneToMany
    @JoinColumn(name = "OrderID")
    private Set<Order> order;


    //GETTER
    public int getId() { return id;    }
    public String getAddres() { return addres; }
    public String getPostal() { return postal;}
    public Set<Order> getOrder() { return order; }
    public String getDeliverystatus() { return deliverystatus; }

    //SETTER
    public void setDeliverystatus(String deliverystatus) {this.deliverystatus = deliverystatus; }

    //METHODS
    @Override
    public String toString() {
        return addres + ", " + postal;
    }
}
