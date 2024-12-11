package it.epicode.DAO.impl;

import it.epicode.DAO.MezzoDAO;
import it.epicode.entity.Mezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class MezzoDAOImpl implements MezzoDAO {
    @PersistenceContext
    public EntityManager entityManager;

    @Override
    public Mezzo findById(Long id) {
        return entityManager.find(Mezzo.class, id);
    }

    @Override
    public List<Mezzo> findAll() {
        return entityManager.createQuery("SELECT m FROM Mezzo m", Mezzo.class).getResultList();
    }

    @Override
    public void save(Mezzo mezzo) {
        entityManager.persist(mezzo);
    }

    @Override
    public void update(Mezzo mezzo) {
        entityManager.merge(mezzo);
    }

    @Override
    public void delete(Long id) {
        Mezzo mezzo = findById(id);
        if (mezzo != null) {
            entityManager.remove(mezzo);
        }
    }
}