package persistence.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import persistence.entities.PointEntity;
import persistence.entities.UserEntity;
import utility.exceptions.UserNotFoundException;

import java.util.ArrayList;

@Stateless
public class PointDAO {

    @PersistenceContext(unitName = "persistenceUnit")
    private EntityManager em;

    public ArrayList<PointEntity> getPoints(Long userId) throws UserNotFoundException {
        checkUser(userId);
        return new ArrayList<>(em.createQuery("select p from PointEntity p where userId = :userId", PointEntity.class).setParameter("userId", userId).getResultList());
    }

    private void checkUser(Long userId) throws UserNotFoundException {
        UserEntity user = em.find(UserEntity.class, userId);
        if (user == null) {
            throw new UserNotFoundException(userId.toString());
        }
    }


    public void addPoint(PointEntity point, long startTime, Long userId) throws UserNotFoundException {
        checkUser(userId);
        point.setUserId(userId);
        String executionTime = (System.nanoTime() - startTime) / 1_000_000L + "ms";
        point.setExecutionTime(executionTime);
        em.persist(point);
    }

}
