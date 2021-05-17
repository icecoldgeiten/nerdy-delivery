package com.dao;

import com.entity.Driver;
import com.entity.Order;
import com.helpers.AES256;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

public class DriverDao {
    private static Driver LogedinDriver;

    private final EntityManagerFactory emf;
    private Driver driver;

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

    public void addDriver(String name, String ins, String sn, int phone, LocalDate bd, String un) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        String rp  = randomPassword(10);
        System.out.println(rp);
        try {
            driver = new Driver();

            driver.setName(name);
            driver.setInserts(sn);
            driver.setInserts(ins);
            driver.setSirname(sn);
            driver.setPhonenumber(phone);
            driver.setBirthdate(bd);
            driver.setUsername(un);
            driver.setPassword(AES256.encrypt(rp));

            em.persist(driver);
            em.getTransaction().commit();

            System.out.println("Driver added");
        } catch (Exception e) {
            System.out.println();
        }
        em.close();
    }

    public void changeDriver(int clickedOn, String name, String ins, String sn, int phone, LocalDate bd) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            driver = em.find(Driver.class, clickedOn);
            em.merge(driver);
            driver.setName(name);
            driver.setInserts(ins);
            driver.setSirname(sn);
            driver.setPhonenumber(phone);
            driver.setBirthdate(bd);

            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Driver searchDriver(int clickedOn) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            return driver = em.find(Driver.class, clickedOn);
        } catch (Exception e) {
            System.out.println(e);
            return driver = null;
        }
    }

    public void rowDelete(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            driver = em.find(Driver.class, id);
            em.remove(driver);
            System.out.println("Bezorger is verwijderd");
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Niet gelukt");
        }
    }

    public boolean validate(String username, String password) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            Driver driver = em.createQuery("from Driver D where D.username = :username", Driver.class).setParameter("username", username).getSingleResult();
            System.out.println(driver);
            if (driver != null && driver.getPassword().equals(AES256.encrypt(password))) {
                LogedinDriver = driver;
                return true;
            }
            em.getTransaction().commit();
        } catch (NoResultException e) {
            e.printStackTrace();
        }
        em.close();
        return false;
    }

    public String randomPassword(int length) {
        String passwordSet = "ABCDEFGHIJKLMOPQRSRUVWXYZ0123456789!@#$%";
        char[] password = new char[length];
        for (int i = 0; i < length; ++i) {
            int rand = (int) (Math.random() * (double) passwordSet.length());
            password[i] = passwordSet.charAt(rand);
        }
        return new String(password);
    }

    public void updateOrderStatus(String status, Order order) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        order.setStatus(status);
        em.merge(order);
        em.getTransaction().commit();

    }

    //GETTER
    public static Driver getLogedinDriver() {
        return LogedinDriver;
    }

}