package com.dao;

import com.entity.Order;
import com.entity.OrderStatus;
import com.helpers.CEntityManagerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class OrderDao {
    private final EntityManagerFactory emf;

    public OrderDao() {
        emf = CEntityManagerFactory.getEntityManagerFactory();
    }

    public List<Order> getOrders() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            List<Order> orders = em.createQuery("from Order where route = NULL AND (orderStatus = :open OR orderStatus = :notHome)", Order.class).setParameter("open", StatusDao.getOrderStatus("OPENFORDELIVERY")).setParameter("notHome", StatusDao.getOrderStatus("NOTHOME")).getResultList();
            em.getTransaction().commit();
            em.close();
            return orders;
        } catch (Exception e) {
            e.printStackTrace();
        }
        em.close();
        return null;
    }

    public void updateStatus(String code, Order order) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            OrderStatus status = em.createQuery("from OrderStatus where statusCode = :status", OrderStatus.class).setParameter("status", code).getSingleResult();
            order.setOrderStatus(status);
            em.merge(order);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().commit();
        }
        em.close();
    }
}
