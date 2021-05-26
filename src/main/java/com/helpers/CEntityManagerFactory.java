package com.helpers;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CEntityManagerFactory {
    private static EntityManagerFactory emf=null;

    private static void initEntityManagerFactory()
    {
        emf = Persistence.createEntityManagerFactory("ice-unit");
    }

    public static EntityManagerFactory getEntityManagerFactory()
    {
        if(emf == null){
            initEntityManagerFactory();
        }
        return emf;
    }
}
