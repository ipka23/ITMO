package controllers;

import entities.User;
import jakarta.persistence.*;
import entities.Point;

import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

public class DBManager {

    @PersistenceContext(unitName = "persistenceUnit")
    private EntityManager em;

    public ArrayList<Point> getPoints() {
        return new ArrayList<>(em.createQuery("SELECT p FROM Point p", Point.class).getResultList());
    }


    public void addPoint(Point point) {
        em.persist(point);
    }
    public boolean userExists(User user) {
        return em.contains(user);
    }
    public void addUser(User user){
        if (userExists(user)) {
            em.persist(user);
        }
    }
}