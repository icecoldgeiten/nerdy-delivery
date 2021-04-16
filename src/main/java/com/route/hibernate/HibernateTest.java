package com.route.hibernate;

import java.util.List;

import com.route.domain.Klant;
import com.route.domain.Bestelling;
import com.route.util.*;

import org.hibernate.*;

public class HibernateTest {

    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        Klant klant1 = new Klant("Admiraal Helfrich 124","0612345618");
        session.save(klant1);

        session.save(new Bestelling(20,1,klant1));
        session.save(new Bestelling(15,2,klant1));

        session.getTransaction().commit();

        Query q = session.createQuery("From Bestelling ");

        List<Klant> resultList = q.list();
        System.out.println("num of klanten:" + resultList.size());
        for (Klant next : resultList) {
            System.out.println("next volgende klant: " + next);
        }

    }

}