package com.dao;

import com.entity.*;
import com.helpers.AES256;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DriverDao {
    private static Driver LogedinDriver;

    private final EntityManagerFactory emf;
    private Driver driver;

    public DriverDao() {
        emf = Persistence.createEntityManagerFactory("ice-unit");
    }

    public List<Driver> getAllActiveDrivers() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            List<Driver> drivers = em.createQuery("from Driver D where D.active = 1", Driver.class).getResultList();
            em.close();
            return drivers;
        } catch (Exception e) {
            e.printStackTrace();
        }
        em.close();
        return null;
    }

    public List<Driver> getAvailableDrivers(LocalDate date, Timeslot timeslot) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            List<Driver> drivers = em.createQuery("from Driver", Driver.class).getResultList();
            em.close();
            return filterDrivers(drivers, date, timeslot);
        } catch (Exception e) {
            e.printStackTrace();
        }
        em.close();
        return null;
    }

    public List<Driver> filterDrivers(List<Driver> drivers, LocalDate date, Timeslot timeslot) {
        List<Driver> toRemove = new ArrayList<>();
        for (Driver driver : drivers) {
            for (Route route : driver.getRoutes()) {
                if (route.getTimeslot().equals(timeslot) && route.getDate().equals(date)) {
                    toRemove.add(route.getDriver());
                }
            }
        }

        drivers.removeAll(toRemove);
        return drivers;
    }

    public void addDriver(String name, String ins, String sn, int phone, LocalDate bd, String un) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        String rp = randomPassword(10);
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

        } catch (Exception e) {
            System.out.println();
        }
        em.close();
    }

    public void changeDriver(int clickedOn, String name, String ins, String sn, int phone, LocalDate bd, boolean vehicle, boolean license) {
        EntityManager em = emf.createEntityManager();

        int veh = vehicle ? 1 :0;
        int lic = license ? 1 :0;

        try {
            em.getTransaction().begin();
            driver = em.find(Driver.class, clickedOn);
            em.merge(driver);
            driver.setName(name);
            driver.setInserts(ins);
            driver.setSirname(sn);
            driver.setPhonenumber(phone);
            driver.setBirthdate(bd);
            driver.setVehicle(veh);
            driver.setLincenseNr(lic);

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
            driver.setActive(false);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean validate(String username, String password) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            Driver driver = em.createQuery("from Driver D where D.username = :username", Driver.class).setParameter("username", username).getSingleResult();
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

    //GETTER
    public static Driver getLogedinDriver() {
        return LogedinDriver;
    }
    public static void setLogedinDriver(Driver logedinDriver) {
        LogedinDriver = logedinDriver;
    }

    public void changePassword(String pw, int clickedOn) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            driver = em.find(Driver.class, clickedOn);
            em.merge(driver);
            driver.setPassword(AES256.encrypt(pw));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

//    public void changeLicense(boolean license, int clickedOn){
//        EntityManager em = emf.createEntityManager();
//        driver = em.find(Driver.class, clickedOn);
//        em.merge(driver);
//        em.getTransaction().begin();
//
//        driver.setLincenseNr(lic);
//        em.getTransaction().commit();
//    }
//
//    public void changeVehicle(boolean vehicle, int clickedOn){
//        EntityManager em = emf.createEntityManager();
//        driver = em.find(Driver.class, clickedOn);
//        em.merge(driver);
//        em.getTransaction().begin();
//
//        driver.setVehicle(veh);
//        em.getTransaction().commit();
//    }


}