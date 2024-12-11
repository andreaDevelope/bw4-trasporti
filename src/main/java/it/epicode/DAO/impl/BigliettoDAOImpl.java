package it.epicode.DAO.impl;

import it.epicode.DAO.BigliettoDAO;
import it.epicode.entity.table_extensions.Biglietto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class BigliettoDAOImpl implements BigliettoDAO {
    @PersistenceContext
    public EntityManager entityManager;

    @Override
    public Biglietto findById(Long id) {
        return entityManager.find(Biglietto.class, id);
    }

    @Override
    public List<Biglietto> findAll() {
        return entityManager.createQuery("SELECT b FROM Biglietto b", Biglietto.class).getResultList();
    }

    @Override
    public void save(Biglietto biglietto) {
        entityManager.persist(biglietto);
    }

    @Override
    public void update(Biglietto biglietto) {
        entityManager.merge(biglietto);
    }

    @Override
    public void delete(Long id) {
        Biglietto biglietto = findById(id);
        if (biglietto != null) {
            entityManager.remove(biglietto);
        }
    }
}