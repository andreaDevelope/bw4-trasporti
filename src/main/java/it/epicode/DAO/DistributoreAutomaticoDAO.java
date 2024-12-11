package it.epicode.DAO;

import it.epicode.entity.table_extensions.DistributoreAutomatico;

import java.util.List;

interface DistributoreAutomaticoDAO {
    DistributoreAutomatico findById(Long id);
    List<DistributoreAutomatico> findAll();
    void save(DistributoreAutomatico distributore);
    void update(DistributoreAutomatico distributore);
    void delete(Long id);
}

