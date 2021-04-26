package com.Dao;

import com.entity.Driver;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class DriverDao {
    EntityManager em;


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
}
