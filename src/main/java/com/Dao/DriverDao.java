package com.Dao;

import com.entity.Driver;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.List;

public class DriverDao {
    EntityManager em;
    Driver driver;


    public List<Driver> listviewDrivers() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ice-unit");
        em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            List<Driver> drivers = em.createQuery("FROM Driver", Driver.class).getResultList();

            return drivers;
        }catch (Exception e){
            System.out.println("Hier komt de exception:"+ e);
        }
        return null;
    }

    public void changeDriver(int clickedOn, String name, String ins, String sn, int phone, LocalDate bd){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ice-unit");
        em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            //Driver opslaan in driver door te zoeken op naam waar opgeklikt is.
            driver = em.find(Driver.class, clickedOn);
            em.merge(driver);
            driver.setName(name);
            driver.setInserts(ins);
            driver.setSirname(sn);
            driver.setPhone(phone);
            driver.setBirthdate(bd);

            em.getTransaction().commit();

        } catch (Exception e){
            System.out.println(e);
        }
    }

    public Driver searchDriver(int clickedOn){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ice-unit");
        em = emf.createEntityManager();
        em.getTransaction().begin();
        try{
            //Driver opslaan in driver door te zoeken op naam waar opgeklikt is.

            return driver = em.find(Driver.class, clickedOn);
        } catch (Exception e){
            System.out.println(e);
            return driver = null;
        }
    }
}
