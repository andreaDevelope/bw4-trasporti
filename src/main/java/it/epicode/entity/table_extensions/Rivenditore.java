package it.epicode.entity.table_extensions;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Rivenditore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String indirizzo;

    @OneToMany(mappedBy = "rivenditore", cascade = CascadeType.ALL)
    private List<Biglietto> bigliettiEmessi = new ArrayList<>();
    @OneToMany(mappedBy = "rivenditore", cascade = CascadeType.ALL)
    private List<Abbonamento> abbonamentiEmessi = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public List<Biglietto> getBigliettiEmessi() {
        return bigliettiEmessi;
    }

    public List<Abbonamento> getAbbonamentiEmessi() {
        return abbonamentiEmessi;
    }

    public void setAbbonamentiEmessi(List<Abbonamento> abbonamentiEmessi) {
        this.abbonamentiEmessi = abbonamentiEmessi;
    }

    public void setBigliettiEmessi(List<Biglietto> bigliettiEmessi) {
        this.bigliettiEmessi = bigliettiEmessi;
    }
}