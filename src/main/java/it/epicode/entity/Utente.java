package it.epicode.entity;

import it.epicode.entity.table_extensions.Abbonamento;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cognome;

    @OneToOne(cascade = CascadeType.ALL)
    private Tessera tessera;

    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL)
    private List<Abbonamento> abbonamenti;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
    }

    public void setAbbonamenti(List<Abbonamento> abbonamenti) {
        this.abbonamenti = abbonamenti;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public Tessera getTessera() {
        return tessera;
    }

    public List<Abbonamento> getAbbonamenti() {
        return abbonamenti;
    }
}