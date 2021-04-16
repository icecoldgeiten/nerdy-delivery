package com.route;

import java.time.LocalDate;
import java.util.ArrayList;

public class Driver extends Employee {
    private int vechile;
    private int licensenr;
    private ArrayList<Route> routes;

    public Driver(String name, String inserts, String sirname, LocalDate birthdate, int phonenumber, int vechile, int licensenr, ArrayList<Route> routes) {
        super(name, inserts, sirname, birthdate, phonenumber);
        this.vechile = vechile;
        this.licensenr = licensenr;
        this.routes = routes;
    }
}
