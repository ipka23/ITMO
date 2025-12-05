package controllers;

import jakarta.persistence.*;
import entities.Point;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit");


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public ArrayList<Point> getPoints() {
        EntityManager em = emf.createEntityManager();
        ArrayList<Point> points = new ArrayList<>();

        try {
            TypedQuery<Point> query = em.createQuery("SELECT p FROM Point p", Point.class);
            List<Point> resultList = query.getResultList();
            points = new ArrayList<>(resultList);

        } catch (Exception e) {
        } finally {
            if (em.isOpen()) em.close();
        }
        return points;
    }

    public void addPoint(Point point) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(point);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
        } finally {
            em.close();
        }
    }
}
