package it.epicode.DAO.impl;

import it.epicode.DAO.TesseraDAO;
import it.epicode.entity.Tessera;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class TesseraDAOImpl implements TesseraDAO {
    @PersistenceContext
    public EntityManager entityManager;

    @Override
    public Tessera findById(Long id) {
        return entityManager.find(Tessera.class, id);
    }

    @Override
    public Tessera findByCodiceUnivoco(String codice) {
        return entityManager.createQuery("SELECT t FROM Tessera t WHERE t.codiceUnivoco = :codice", Tessera.class)
                .setParameter("codice", codice)
                .getSingleResult();
    }
    @Override
    public void save(Tessera tessera) {
        entityManager.persist(tessera);
    }

    @Override
    public void update(Tessera tessera) {
        entityManager.merge(tessera);
    }

    @Override
    public void delete(Long id) {
        Tessera tessera = findById(id);
        if (tessera != null) {
            entityManager.remove(tessera);
        }
    }
}