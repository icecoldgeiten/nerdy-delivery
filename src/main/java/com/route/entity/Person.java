package com.route.entity;

import java.time.LocalDate;

public abstract class Person {
    private String name;
    private String inserts;
    private String sirname;
    private LocalDate birthdate;
    private int phonenumber;

    public Person(String name, String inserts, String sirname, LocalDate birthdate, int phonenumber) {
        this.name = name;
        this.inserts = inserts;
        this.sirname = sirname;
        this.birthdate = birthdate;
        this.phonenumber = phonenumber;
    }
}
