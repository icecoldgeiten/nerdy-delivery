package com.dao;

import com.entity.Administrator;
import com.helpers.AES256;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AdminDao {
    private final EntityManager em;

    public AdminDao() {
        EntityManagerFactory session = Persistence.createEntityManagerFactory("ice-unit");
        em = session.createEntityManager();
    }

    public boolean validate(String username, String password) {
        try {
            em.getTransaction().begin();
            Administrator admin = em.createQuery("from Administrator D where D.username = :username", Administrator.class).setParameter("username", username).getSingleResult();
            if (admin != null && admin.getPassword().equals(AES256.encrypt(password))) {
                return true;
            }
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
//            if (em.getTransaction() != null) {
////                em.getTransaction().rollback();
//            }
            e.printStackTrace();
        }
        return false;
    }
}

