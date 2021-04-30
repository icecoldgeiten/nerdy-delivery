package com.dao;

import com.entity.Administrator;
import com.entity.Customer;
import com.entity.Driver;
import com.entity.Route;
import com.helpers.AES256;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

public class DriverDao {
    EntityManager em;
    Driver driver;

    public DriverDao(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ice-unit");
        em = emf.createEntityManager();
    }


    public List<Customer> listDrivers() {

        try {
            em.getTransaction().begin();
            List<Customer> customers = em.createQuery("FROM Customer", Customer.class).getResultList();

            return customers;
        }catch (Exception e){
            System.out.println("Hier komt de exception:"+ e);
        }
        return null;
    }



    public void addDriver(String name, String ins, String sn, int phone, LocalDate bd, String un, String pw){
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
            driver.setPassword(pw);

            em.persist(driver);
            em.getTransaction().commit();

            System.out.println("Driver added");
        }catch (Exception e){
            System.out.println();
        }
    }

    public boolean validate(String username, String password) {
        try {
            em.getTransaction().begin();
            Driver d = em.createQuery("from Driver D where D.username = :username", Driver.class).setParameter("username", username).getSingleResult();
            if (d != null && d.getPassword().equals(AES256.encrypt(password))) {
                System.out.println("Validate succes");
                return true;

            }
            em.getTransaction().commit();
            em.close();

        } catch (Exception e) {
//            if (em.getTransaction() != null) {
////                em.getTransaction().rollback();
//            }
            System.out.println("Validate error: "+ e);
            e.printStackTrace();
        }
        return false;
    }

    public String randomPassword(int length){
        String passwordSet = "ABCDEFGHIJKLMOPQRSRUVWXYZ0123456789!@#$%";
        char[] password = new char[length];
        for(int i= 0 ; i<length ;i++){
            int rand = (int) (Math.random() * passwordSet.length());
            password[i] = passwordSet.charAt(rand);
        }
        return new String(password);
    }

}
