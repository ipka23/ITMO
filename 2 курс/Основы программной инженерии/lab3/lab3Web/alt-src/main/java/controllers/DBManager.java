package controllers;

import jakarta.ejb.Singleton;
import jakarta.persistence.*;
import entities.Point;

import java.io.Serializable;
import java.util.ArrayList;
@Singleton
public class AltDBManager implements Serializable {
    @PersistenceContext(unitName = "persistenceUnit")
    private EntityManager em;

    public ArrayList<Point> getPoints() {
        return new ArrayList<>(em.createQuery("SELECT p FROM Point p", Point.class).getResultList());
    }

    public void addPoint(Point point) {
        em.persist(point);
    }
}