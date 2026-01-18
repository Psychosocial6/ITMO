package utils;

import entities.ResultEntity;
import jakarta.enterprise.inject.Vetoed;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Vetoed
public class DatabaseManager {
    private EntityManagerFactory emf;

    public  DatabaseManager() {

    }

    public DatabaseManager(String persistenceUnitName) {
        this.emf = Persistence.createEntityManagerFactory(persistenceUnitName);
    }

    public void saveResult(ResultEntity resultEntity) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(resultEntity);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Saving error");
        }
        finally {
            em.close();
        }
    }

    public List<ResultEntity> getResults() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<ResultEntity> query = em.createQuery("SELECT r FROM ResultEntity r", ResultEntity.class);
            return query.getResultList();
        }
        catch (Exception e) {
            throw new RuntimeException("Results getting error");
        }
        finally {
            em.close();
        }
    }

    public void clearResults() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM ResultEntity").executeUpdate();
            em.getTransaction().commit();
        }
        catch (Exception e) {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Clearing error");
        }
        finally {
            em.close();
        }
    }

    public void close() {
        emf.close();
    }
}
