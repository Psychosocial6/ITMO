package services;

import entities.ResultEntity;
import entities.UserEntity;
import exceptions.UserAlreadyExistsException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import utils.PasswordManager;

import java.util.List;

@Stateless
public class DatabaseManager {

    @PersistenceContext(unitName = "persistence-unit")
    private EntityManager em;

    public void saveResult(ResultEntity resultEntity, String userLogin) {
        resultEntity.setUserLogin(userLogin);
        em.persist(resultEntity);
    }

    public void saveResult(ResultEntity resultEntity) {
        em.persist(resultEntity);
    }

    public List<ResultEntity> getResultsByUser(String userLogin) {
        return em.createQuery(
                        "SELECT r FROM ResultEntity r WHERE r.userLogin = :userLogin",
                        ResultEntity.class
                )
                .setParameter("userLogin", userLogin)
                .getResultList();
    }

    public List<ResultEntity> getResults() {
        return em.createQuery("SELECT r FROM ResultEntity r", ResultEntity.class)
                .getResultList();
    }

    public void clearUserResults(String userLogin) {
        em.createQuery("DELETE FROM ResultEntity r WHERE r.userLogin = :userLogin")
                .setParameter("userLogin", userLogin)
                .executeUpdate();
    }

    public void clearResults() {
        em.createQuery("DELETE FROM ResultEntity").executeUpdate();
    }

    public boolean checkUser(String login, String password) {
        UserEntity user = findUser(login);
        return user != null && PasswordManager.checkPassword(password, user.getPassword());
    }

    public void saveUser(UserEntity userEntity) throws UserAlreadyExistsException {
        UserEntity user = findUser(userEntity.getLogin());

        if (user != null) {
            throw new UserAlreadyExistsException("User already exists");
        }

        userEntity.setPassword(PasswordManager.hashPassword(userEntity.getPassword()));
        em.persist(userEntity);
    }

    public UserEntity findUser(String login) {
        try {
            TypedQuery<UserEntity> query = em.createQuery(
                    "SELECT u FROM UserEntity u WHERE u.login = :login",
                    UserEntity.class
            );
            query.setParameter("login", login);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}