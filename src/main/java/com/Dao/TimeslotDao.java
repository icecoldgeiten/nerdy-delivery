package com.dao;

import com.entity.Timeslot;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

public class TimeslotDao {
    private final EntityManagerFactory emf;

    public TimeslotDao() {
        emf = Persistence.createEntityManagerFactory("ice-unit");
    }

    public List<Timeslot> getAllTimeslots() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            List<Timeslot> timeslots = em.createQuery("from Timeslot", Timeslot.class).getResultList();
            em.close();
            return timeslots;
        } catch (Exception e) {
            e.printStackTrace();
        }
        em.close();
        return null;
    }
}
