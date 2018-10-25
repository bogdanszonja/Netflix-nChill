package com.codecool.netflixandchill.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class TransactionManager {

    public void addToTable(EntityManager em, Object object) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(object);
        transaction.commit();
    }

}
