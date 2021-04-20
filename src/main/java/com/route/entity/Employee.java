package com.route.entity;

import javax.persistence.*;

@Entity
public abstract class Employee extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmployeeID", updatable = false, nullable = false)
    private int id;

    @Column(name = "Username")
    private String username;

    @Column(name = "Password")
    private String password;
}
