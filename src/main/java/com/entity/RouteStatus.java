package com.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "routestatus")
public class RouteStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RouteStatusID", unique = true)
    private Long id;

    @OneToMany(targetEntity = RouteStatus.class)
    private Set<Route> routes = new HashSet<>();

    @Column(name = "status")
    private String status;

    @Column(name = "statusCode")
    private String statusCode;

    //Getters
    public String getStatusCode() {
        return statusCode;
    }

    @Override
    public String toString() {
        return status;
    }
}
