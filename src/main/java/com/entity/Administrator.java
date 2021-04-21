package com.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Administrator extends Person{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AdministratorId", updatable = false, nullable = false)
    private int id;

    @Column(name = "Username")
    private String username;

    @Column(name = "Password")
    private String password;
}
