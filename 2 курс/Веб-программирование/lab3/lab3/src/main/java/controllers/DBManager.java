package controllers;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import entities.Point;

import java.util.List;
@Stateless
public class DBManager {
    @PersistenceContext(unitName = "persistenceUnit")
    private EntityManager em;

    public List<Point> getPoints() {
        return em.createQuery("SELECT p FROM Point p", Point.class).getResultList();
    }

    public void addPoint(Point point) {
        em.persist(point);
    }
}
