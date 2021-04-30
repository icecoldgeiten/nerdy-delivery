
package com.dao;

import com.entity.Customer;
import com.entity.Driver;
import com.entity.Order;
import com.helpers.AES256;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DriverDao {
    EntityManager em;
    Driver driver;
    Order order;
    private static Driver ingelogdeDriver;

    //Constructor
    public DriverDao() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ice-unit");
        this.em = emf.createEntityManager();
    }

    //Returns a list of drivers
    public List<Driver> listDrivers() {
        try {
            this.em.getTransaction().begin();
            List<Driver> drivers = this.em.createQuery("FROM Driver", Driver.class).getResultList();
            this.em.getTransaction().commit();
            return drivers;
        } catch (Exception var2) {
            System.out.println("Hier komt de exception:" + var2);
            return null;
        }
    }
    //Returns a list of customers.
    public List<Customer> listCustomers() {
        try {
            this.em.getTransaction().begin();
            List<Customer> customers = this.em.createQuery("FROM Customer", Customer.class).getResultList();
            this.em.getTransaction().commit();
            return customers;
        } catch (Exception var2) {
            System.out.println("Hier komt de exception:" + var2);
            return null;
        }
    }

    //Adds a new driver
    public void addDriver(String name, String ins, String sn, int phone, LocalDate bd, String un, String pw) {
        try {
            this.em.getTransaction().begin();
            this.driver = new Driver();
            this.driver.setName(name);
            this.driver.setInserts(sn);
            this.driver.setInserts(ins);
            this.driver.setSirname(sn);
            this.driver.setPhonenumber(phone);
            this.driver.setBirthdate(bd);
            this.driver.setUsername(un);
            this.driver.setPassword(pw);
            this.em.persist(this.driver);
            this.em.getTransaction().commit();
            System.out.println("Driver added");
        } catch (Exception var9) {
            System.out.println();
        }
    }

    //Validates password is username and password are the same in database.
    public boolean validate(String username, String password) {
        try {
            em.getTransaction().begin();
            Driver d = em.createQuery("from Driver D where D.username = :username", Driver.class).setParameter("username", username).getSingleResult();
            if (d != null && d.getPassword().equals(AES256.encrypt(password))) {
                ingelogdeDriver = d;
                return true;
            }
            em.getTransaction().commit();
            em.close();
        } catch (Exception var4) {
            System.out.println("Validate error: " + var4);
            var4.printStackTrace();
        }

        return false;
    }
    //Generates random password
    public String randomPassword(int length) {
        String passwordSet = "ABCDEFGHIJKLMOPQRSRUVWXYZ0123456789!@#$%";
        char[] password = new char[length];
        for(int i = 0; i < length; ++i) {
            int rand = (int)(Math.random() * (double)passwordSet.length());
            password[i] = passwordSet.charAt(rand);
        }
        return new String(password);
    }

    //Change notHome
    public void changeNothome(int orderid, int i){
        em.getTransaction().begin();
        order = em.find(Order.class, orderid);
        order.setNotHome(i);
        em.merge(order);
        em.getTransaction().commit();
    }
    //Change delivered
    public void changeDelivered(int orderid, int i){
        em.getTransaction().begin();
        order = em.find(Order.class, orderid);
        order.setDelivered(i);
        em.merge(order);
        em.getTransaction().commit();
    }


    //GETTER
    public static Driver getIngelogdeDriver() {
        return ingelogdeDriver;
    }
}
