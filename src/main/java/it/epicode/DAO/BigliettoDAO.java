package it.epicode.DAO;

import it.epicode.entity.table_extensions.Biglietto;

import java.util.List;

public interface BigliettoDAO {
    Biglietto findById(Long id);
    List<Biglietto> findAll();
    void save(Biglietto biglietto);
    void update(Biglietto biglietto);
    void delete(Long id);
}
