package com.route.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table
public class Klant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int klantID;
    private String DeliveryAdress;
    private String CustomerPhoneNumber;

    @OneToMany(mappedBy = "klant", cascade = CascadeType.PERSIST)
    private List<Bestelling> bestellingen = new ArrayList<Bestelling>();

    //CONSTRUCTOR
    public Klant(String da, String cpn){
        this.DeliveryAdress = da;
        this.CustomerPhoneNumber = cpn;
    }

    //GETTER
    public int getKlantID() {return klantID;}
    public String getDeliveryAdress() {return DeliveryAdress;}
    public String getCustomerPhoneNumber() {
        return CustomerPhoneNumber;
    }
    public List<Bestelling> getBestellingen() {return bestellingen;}

    //SETTER
    public void setKlantID(int klantID) {this.klantID = klantID; }
    public void setDeliveryAdress(String deliveryAdress) {DeliveryAdress = deliveryAdress; }
    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        CustomerPhoneNumber = customerPhoneNumber;
    }
    public void setBestellingen(ArrayList<Bestelling> bestellingen) {this.bestellingen = bestellingen;}


}
