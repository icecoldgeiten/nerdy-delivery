package com.route.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@MappedSuperclass
public abstract class Person {
    @Column(name = "Name")
    private String name;

    @Column(name = "Inserts")
    private String inserts;

    @Column(name = "Sirname")
    private String sirname;

    @Column(name = "Birthdate")
    private LocalDate birthdate;

    @Column(name = "Phonenumber")
    private int phonenumber;
}
