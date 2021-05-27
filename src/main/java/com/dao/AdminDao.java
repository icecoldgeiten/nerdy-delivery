package com.dao;

import com.entity.Administrator;
import com.helpers.AES256;
import com.helpers.CEntityManagerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

public class AdminDao {
    private final EntityManagerFactory emf;
    private static Administrator admin;

    public AdminDao() {
        emf = CEntityManagerFactory.getEntityManagerFactory();
    }

    public boolean validate(String username, String password) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            Administrator admin = em.createQuery("from Administrator D where D.username = :username", Administrator.class).setParameter("username", username).getSingleResult();
            if (admin != null && admin.getPassword().equals(AES256.encrypt(password))) {
                setAdmin(admin);
                return true;
            }
        } catch (NoResultException e) {
            System.out.println("No result");
        }
        em.getTransaction().commit();
        em.close();
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

