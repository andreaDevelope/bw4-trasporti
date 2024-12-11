package it.epicode.entity.table_extensions;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class DistributoreAutomatico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean stato;

    @OneToMany(mappedBy = "distributore", cascade = CascadeType.ALL)
    private List<Biglietto> bigliettiEmessi = new ArrayList<>();
    @OneToMany(mappedBy = "distributore", cascade = CascadeType.ALL)
    private List<Abbonamento> abbonamentiEmessi = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isStato() {
        return stato;
    }

    public void setStato(boolean stato) {
        this.stato = stato;
    }

    public List<Biglietto> getBigliettiEmessi() {
        return bigliettiEmessi;
    }

    public void setBigliettiEmessi(List<Biglietto> bigliettiEmessi) {
        this.bigliettiEmessi = bigliettiEmessi;
    }

    public List<Abbonamento> getAbbonamentiEmessi() {
        return abbonamentiEmessi;
    }

    public void setAbbonamentiEmessi(List<Abbonamento> abbonamentiEmessi) {
        this.abbonamentiEmessi = abbonamentiEmessi;
    }
}
