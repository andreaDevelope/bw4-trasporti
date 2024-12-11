package it.epicode.DAO;

import it.epicode.entity.table_extensions.Rivenditore;

import java.util.List;

interface RivenditoreDAO {
    Rivenditore findById(Long id);
    List<Rivenditore> findAll();
    void save(Rivenditore rivenditore);
    void update(Rivenditore rivenditore);
    void delete(Long id);
}