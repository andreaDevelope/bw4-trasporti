package it.epicode.DAO;

import it.epicode.entity.Tessera;

public interface TesseraDAO {
    Tessera findById(Long id);
    Tessera findByCodiceUnivoco(String codice);
    void save(Tessera tessera);
    void update(Tessera tessera);
    void delete(Long id);
}