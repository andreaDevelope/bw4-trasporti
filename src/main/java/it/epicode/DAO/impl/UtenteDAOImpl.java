package it.epicode.DAO.impl;

import it.epicode.DAO.UtenteDAO;
import it.epicode.entity.Tessera;
import it.epicode.entity.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class UtenteDAOImpl implements UtenteDAO {
    @PersistenceContext
    public EntityManager entityManager;

    @Override
    public Utente findById(Long id) {
        return entityManager.find(Utente.class, id);
    }

    @Override
    public List<Utente> findAll() {
        return entityManager.createQuery("SELECT u FROM Utente u", Utente.class).getResultList();
    }

    @Override
    public void save(Utente utente) {
        entityManager.persist(utente);
    }

    @Override
    public void update(Utente utente) {
        entityManager.merge(utente);
    }
    public void updateTessera(Utente utente, Tessera tessera) {
        if (utente != null && tessera != null) {
            utente.setTessera(tessera);
            entityManager.merge(utente);
        }
    }

    @Override
    public void delete(Long id) {
        Utente utente = findById(id);
        if (utente != null) {
            entityManager.remove(utente);
        }
    }
}
