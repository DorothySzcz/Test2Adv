package pl.kurs.equationsolverapp.dao;

import org.springframework.stereotype.Repository;
import pl.kurs.equationsolverapp.model.EquationEvent;

import javax.persistence.*;

@Repository
public class EquationEventDao implements IEquationEventDao {

    @PersistenceUnit
    private EntityManagerFactory factory;

    @Override
    public void save(EquationEvent event) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(event);
        tx.commit();
        entityManager.close();
    }

    @Override
    public EquationEvent get(long id) {
        EntityManager entityManager = factory.createEntityManager();
        return entityManager.find(EquationEvent.class, id);
    }

}
