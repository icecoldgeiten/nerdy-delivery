package com.dao;
import com.entity.Timeslot;
import com.helpers.CEntityManagerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

public class TimeslotDao {
    private final EntityManagerFactory emf;

    public TimeslotDao() {
        emf = CEntityManagerFactory.getEntityManagerFactory();
    }

    public List<Timeslot> getAllTimeslots() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            List<Timeslot> timeslots = em.createQuery("from Timeslot", Timeslot.class).getResultList();
            em.getTransaction().commit();
            em.close();
            return timeslots;
        } catch (Exception e) {
            e.printStackTrace();
        }
        em.close();
        return null;
    }
}
