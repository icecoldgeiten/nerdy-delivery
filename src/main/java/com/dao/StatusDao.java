package com.dao;

import com.entity.OrderStatus;
import com.entity.RouteStatus;
import com.helpers.CEntityManagerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class StatusDao {
    public static OrderStatus getOrderStatus(String code) {
        EntityManager em = CEntityManagerFactory.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            OrderStatus status = em.createQuery("from OrderStatus where statusCode = :status", OrderStatus.class).setParameter("status", code).getSingleResult();
            em.getTransaction().commit();
            em.close();
            return status;
        } catch (Exception e) {
            e.printStackTrace();
        }
        em.close();
        return null;
    }

    public static RouteStatus getRouteStatus(String code) {
        EntityManager em = CEntityManagerFactory.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            RouteStatus status = em.createQuery("from RouteStatus where statusCode = :statusCode", RouteStatus.class).setParameter("statusCode", code).getSingleResult();
            em.getTransaction().commit();
            em.close();
            return status;
        } catch (Exception e) {
            e.printStackTrace();
        }
        em.close();
        return null;
    }
}
