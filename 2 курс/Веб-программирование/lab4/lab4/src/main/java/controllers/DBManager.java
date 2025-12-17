package controllers;

import entities.Point;
import entities.User;
import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.ArrayList;

@Singleton
public class DBManager implements Serializable {
    @PersistenceContext(unitName = "persistenceUnit")
    private EntityManager em;

    public ArrayList<Point> getPoints() {
        return new ArrayList<>(em.createQuery("SELECT p FROM Point p", Point.class).getResultList());
    }

    public void addPoint(Point point) {
        em.persist(point);
    }

    public boolean userExist(User user){
        return true;
    }

    public void addUser(User user) {
        if (!userExist()) {

        }


    }

}