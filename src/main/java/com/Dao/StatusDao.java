package com.dao;

import com.entity.OrderStatus;
import com.entity.RouteStatus;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class StatusDao {
    public static OrderStatus getOrderStatus(String code) {
        try {
            EntityManager em = Persistence.createEntityManagerFactory("ice-unit").createEntityManager();
            em.getTransaction().begin();
            OrderStatus status = em.createQuery("from OrderStatus where statusCode = :status", OrderStatus.class).setParameter("status", code).getSingleResult();
            em.close();
            return status;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static RouteStatus getRouteStatus(String code) {
        try {
            EntityManager em = Persistence.createEntityManagerFactory("ice-unit").createEntityManager();
            em.getTransaction().begin();
            RouteStatus status = em.createQuery("from RouteStatus where status = :status", RouteStatus.class).setParameter("status", code).getSingleResult();
            em.close();
            return status;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
