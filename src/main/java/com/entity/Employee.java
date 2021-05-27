package com.entity;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
public abstract class Employee {
    @Column(name = "Name", nullable = false)
    private String name;
    @Column(name = "Inserts")
    private String inserts;
    @Column(name = "Sirname", nullable = false)
    private String sirname;
    @Column(name = "Birthdate", nullable = false)
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
    public String getInserts() {
        return inserts;
    }

    public String getSirname() {
        return sirname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public Integer getPhonenumber() {
        return phonenumber;
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
    public void setPhonenumber(Integer phonenumber) {
        this.phonenumber = phonenumber;
    }



    @Override
    public String toString() {
        return name + " " + sirname;
    }
}
