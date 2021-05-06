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

    //GETTERS
    public String getName() {
        return this.name;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    //SETTERS
    public void setName(String name) {
        this.name = name;
    }
    public void setInserts(String inserts) {
        this.inserts = inserts;
    }
    public void setSirname(String sirname) {
        this.sirname = sirname;
    }
    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }
    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return name + " " + sirname;
    }
}
