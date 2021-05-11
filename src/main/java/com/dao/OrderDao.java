package com.dao;

import com.entity.Order;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class OrderDao {
    EntityManager em;

    public OrderDao() {
        EntityManagerFactory session = Persistence.createEntityManagerFactory("ice-unit");
        em = session.createEntityManager();
    }

    public List<Order> getOrders() {
        try {
            em.getTransaction().begin();
            List<Order> orders = em.createQuery("from Order O where O.delivered = 0 and O.route = NULL", Order.class).getResultList();
            em.close();
            return orders;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
