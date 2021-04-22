package com.entity;

import javax.persistence.*;

@Entity
@Table(name = "administrator")
public class Administrator extends Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AdminID", updatable = false, nullable = false)
    private int id;
}
