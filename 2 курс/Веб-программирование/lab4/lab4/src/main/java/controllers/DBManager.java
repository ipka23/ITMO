package controllers;

import entities.Point;
import entities.User;
import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.ArrayList;

@Singleton
public class DBManager implements Serializable {
    @PersistenceContext(unitName = "persistenceUnit")
    private EntityManager em;

    public ArrayList<Point> getPoints(User u) {
        return new ArrayList<>(em.createQuery("select p from Point p where p.userId like :userId", Point.class).setParameter("userId", u.getId()).getResultList());
    }

    public void addPoint(Point point) {
        em.persist(point);
    }

    public boolean userExist(User u) throws NonUniqueResultException {
        String login = u.getLogin();
        User user = em.createQuery("select u from User u where u.login like :login", User.class).setParameter("login", login).getSingleResultOrNull();
        return user != null;
    }

    public void addUser(User user) {
        if (!userExist(user)) {
            //
        } else {
            em.persist(user);
        }
    }

}