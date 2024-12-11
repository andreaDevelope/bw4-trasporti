package it.epicode.DAO;

import it.epicode.entity.Mezzo;

import java.util.List;

public interface MezzoDAO {
    Mezzo findById(Long id);
    List<Mezzo> findAll();
    void save(Mezzo mezzo);
    void update(Mezzo mezzo);
    void delete(Long id);
}