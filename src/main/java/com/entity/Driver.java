package com.entity;

import org.hibernate.hql.internal.ast.tree.IsNullLogicOperatorNode;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Set;

@Entity
@Table(name = "driver")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "DriverID", updatable = false, nullable = false)
    private int id;

    @OneToMany
    @JoinColumn(name = "DriverID")
    private Set<Route> routes;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Inserts")
    private String inserts;

    @Column(name = "Sirname", nullable = false)
    private String sirname;

    @Column(name = "Birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(name = "Phonenumber", nullable = false)
    private int phone;

    @Column(name = "Username", nullable = false)
    private String userName;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "Vehicle")
    private int vehicle;

    @Column(name = "LicenseNr")
    private int lincenseNr;

    private EntityManager em;

    //constructor
    public Driver(String name, String ins, String sn, int phone, LocalDate bd){
        this(name,sn,phone,bd);
        this.inserts = ins;
    }
    public Driver(String name, String sn, int phone, LocalDate bd){



        this.name = name;
        this.inserts = "";
        this.sirname = sn;
        this.phone = phone;
        this.birthdate = bd;
    }


    //Getters
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getUserName() {
        return userName;
    }

    //METHOD
    public void newDriver(String naam, String inserts, String sirname, int phone, String birthdate){
        LocalDate gb;
        try {
            gb = LocalDate.parse(birthdate);
        } catch (DateTimeParseException ex){
            gb = LocalDate.now();
        }

        EntityManagerFactory session = Persistence.createEntityManagerFactory("ice-unit");
        em = session.createEntityManager();
        Driver driver = new Driver(naam,inserts,sirname,phone,gb);
        em.persist(driver);

    }
}
