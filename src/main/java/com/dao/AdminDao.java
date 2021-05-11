package com.dao;

import com.entity.Administrator;
import com.helpers.AES256;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AdminDao {
    private final EntityManager em;
    private static Administrator admin;

    public AdminDao() {
        EntityManagerFactory session = Persistence.createEntityManagerFactory("ice-unit");
        em = session.createEntityManager();
    }

    public boolean validate(String username, String password) {
        try {
            em.getTransaction().begin();
            Administrator admin = em.createQuery("from Administrator D where D.username = :username", Administrator.class).setParameter("username", username).getSingleResult();
            if (admin != null && admin.getPassword().equals(AES256.encrypt(password))) {
                setAdmin(admin);
                return true;
            }
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
        return false;
    }

    //Getters
    public static String getAdmin() {
        return admin.getName();
    }

    //Setters
    private static void setAdmin(Administrator admin) {
        AdminDao.admin = admin;
    }
}

