package com.dao;

import com.entity.Driver;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

public class DriverDao {
    EntityManager em;
    Driver driver;

    public DriverDao() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ice-unit");
        em = emf.createEntityManager();
    }

    public void addDriver(String name, String ins, String sn, int phone, LocalDate bd, String un){
        try {
            em.getTransaction().begin();
            driver = new Driver();

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
            em.close();

            System.out.println("Driver added");
        }catch (Exception e){
            System.out.println();
        }
    }

    public List<Driver> getAllDrivers() {
        try {
            em.getTransaction().begin();
            List<Driver> drivers = em.createQuery("from Driver", Driver.class).getResultList();
            em.close();
            return drivers;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
