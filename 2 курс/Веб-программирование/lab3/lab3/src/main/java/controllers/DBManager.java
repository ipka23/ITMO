package controllers;

import jakarta.persistence.*;
import entities.Point;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private EntityManagerFactory emf;

    public DBManager() {
        emf = Persistence.createEntityManagerFactory("persistenceUnit");
    }

    public ArrayList<Point> getPoints() {
        EntityManager em = emf.createEntityManager();
        try {
            return new ArrayList<>(em.createQuery("SELECT p FROM Point p", Point.class).getResultList());
        } finally {
            em.close();
        }
    }

    public void addPoint(Point point) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(point);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}