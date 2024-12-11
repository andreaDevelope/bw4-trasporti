package it.epicode.DAO;

import it.epicode.entity.Utente;
import it.epicode.entity.table_extensions.Abbonamento;

import java.util.List;

interface AbbonamentoDAO {
    Abbonamento findById(Long id);
    List<Abbonamento> findByUtente(Utente utente);
    void save(Abbonamento abbonamento);
    void update(Abbonamento abbonamento);
    void delete(Long id);
}