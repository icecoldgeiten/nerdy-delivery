package com.dao;

import com.entity.Driver;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

public class DriverDao {
    EntityManagerFactory emf;
    Driver driver;

    public DriverDao() {
         emf = Persistence.createEntityManagerFactory("ice-unit");
    }

    public List<Driver> getAllDrivers() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            List<Driver> drivers = em.createQuery("from Driver", Driver.class).getResultList();
            em.close();
            return drivers;
        } catch (Exception e) {
            e.printStackTrace();
        }
        em.close();
        return null;
    }

    public void addDriver(String name, String ins, String sn, int phone, LocalDate bd, String un){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
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

            System.out.println("Driver added");
        }catch (Exception e){
            System.out.println();
        }
        em.close();
    }


    public void changeDriver(int clickedOn, String name, String ins, String sn, int phone, LocalDate bd){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            //Driver opslaan in driver door te zoeken op naam waar opgeklikt is.
            driver = em.find(Driver.class, clickedOn);
            em.merge(driver);
            driver.setName(name);
            driver.setInserts(ins);
            driver.setSirname(sn);
            driver.setPhonenumber(phone);
            driver.setBirthdate(bd);

            em.getTransaction().commit();

        } catch (Exception e){
            System.out.println(e);
        }
    }

    public Driver searchDriver(int clickedOn){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try{
            return driver = em.find(Driver.class, clickedOn);
        } catch (Exception e){
            System.out.println(e);
            return driver = null;
        }
    }

    public void rowDelete(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            driver = em.find(Driver.class,id);
            em.remove(driver);
            System.out.println("Bezorger is verwijderd");
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Niet gelukt");
        }
    }
}





