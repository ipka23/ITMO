package persistence.dao;

import jakarta.ejb.Stateless;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import persistence.entities.UserEntity;

import java.util.ArrayList;
import java.util.List;


@Stateless
public class UserDAO {

    @PersistenceContext(unitName = "persistenceUnit")
    private EntityManager em;


    public boolean userExist(UserEntity u) throws NonUniqueResultException {
        String login = u.getLogin();
        List<UserEntity> users = em.createQuery("select u from UserEntity u where u.login like :login", UserEntity.class).setParameter("login", login).getResultList();
        if (users.size() == 1) return true;
        else return false;
    }

    public boolean passwordIsCorrect(UserEntity u) {
        String password = u.getPassword();
        UserEntity user = em.createQuery("select u from UserEntity u where u.password like :password", UserEntity.class).setParameter("password", password).getSingleResultOrNull();
        return user != null;
    }

    public void addUser(UserEntity user) {
        em.persist(user);
    }
}
