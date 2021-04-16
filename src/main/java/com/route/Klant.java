package com.route;

import javax.persistence.*;

@Entity
@Table
public class Klant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String DeliveryAdress;

    private String CustomerEmailAdress;

    private String CustomerPhoneNumber;
}
