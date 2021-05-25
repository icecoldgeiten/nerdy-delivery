package com.dao;

import com.entity.Administrator;
import com.helpers.AES256;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AdminDao {
    private final EntityManagerFactory emf;
    private static Administrator admin;

    public AdminDao() {
        emf = Persistence.createEntityManagerFactory("ice-unit");
    }

    public boolean validate(String username, String password) {
        EntityManager em = emf.createEntityManager();
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
            em.close();
            e.printStackTrace();
        }
        return false;
    }

    //Getters
    public static String getAdmin() {
        return admin.getName();
    }

    //Setters
    public static void setAdmin(Administrator admin) {
        AdminDao.admin = admin;
    }
}

