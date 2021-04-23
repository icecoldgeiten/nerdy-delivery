package com.entity;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
public abstract class Employee {
    @Column(name = "Name")
    private String name;

    @Column(name = "Inserts")
    private String inserts;

    @Column(name = "Sirname")
    private String sirname;

    @Column(name = "Birthdate")
    private LocalDate birthdate;

    @Column(name = "Phonenumber")
    private Integer phonenumber;

    @Column(name = "Username", nullable = false)
    private String username;

    @Column(name = "Password", nullable = false)
    private String password;

    //Getters
    public String getName() {
        return this.name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
