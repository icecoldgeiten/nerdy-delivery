package com.dao;

import com.entity.Driver;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class DriverDao {
    EntityManager em;

    public void addDriver(String name, String ins, String sn, int phone, LocalDate bd, String un){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ice-unit");
        em = emf.createEntityManager();
        try {

            em.getTransaction().begin();
            Driver driver = new Driver();

            driver.setName(name);
            driver.setInserts(sn);
            driver.setInserts(ins);
            driver.setSirname(sn);
            driver.setPhonenumber(phone);
            driver.setBirthdate(bd);
            driver.setUsername(un);
            driver.setPassword("NOTNULL");

            em.persist(driver);
            em.getTransaction().commit();

            System.out.println("Driver added");
        }catch (Exception e){
            System.out.println();
        }
    }
}
