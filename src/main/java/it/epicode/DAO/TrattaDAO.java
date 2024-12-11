package it.epicode.DAO;

import it.epicode.entity.Tratta;

import java.util.List;

interface TrattaDAO {
    Tratta findById(Long id);
    List<Tratta> findAll();
    void save(Tratta tratta);
    void update(Tratta tratta);
    void delete(Long id);
}