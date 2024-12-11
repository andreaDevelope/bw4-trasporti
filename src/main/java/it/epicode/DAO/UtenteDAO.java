package it.epicode.DAO;

import it.epicode.entity.Utente;

import java.util.List;

public interface UtenteDAO {
    Utente findById(Long id);
    List<Utente> findAll();
    void save(Utente utente);
    void update(Utente utente);
    void delete(Long id);
}