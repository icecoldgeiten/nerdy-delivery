package com.route.domain;

import javax.persistence.*;

@Entity
@Table
public class Bestelling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bestellingID;
    private double prijs;
    private int hoeveelheid;

    @ManyToOne
    private Klant klant;

    //CONSTRUCTOR
    public Bestelling(double pr, int hoev, Klant klant){
        this.prijs = pr;
        this.hoeveelheid = hoev;
    }

    //GETTER
    public int getBestellingID() {return bestellingID;}
    public double getPrijs() {return prijs;}
    public int getHoeveelheid() {return hoeveelheid;}

    //SETTER
    public void setBestellingID(int bestellingID) {this.bestellingID = bestellingID;}
    public void setPrijs(double prijs) { this.prijs = prijs;}
    public void setHoeveelheid(int hoeveelheid) {this.hoeveelheid = hoeveelheid;}


}
