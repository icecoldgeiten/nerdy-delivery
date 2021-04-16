package com.route;

import java.time.LocalDate;

public abstract class Employee extends Person {
    //This either will be an encrypted string
    //Or te vars will be unnecessary
    private String username;
    private String password;

    public Employee(String name, String inserts, String sirname, LocalDate birthdate, int phonenumber) {
        super(name, inserts, sirname, birthdate, phonenumber);
    }
}
