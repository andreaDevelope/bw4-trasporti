package it.epicode.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Tratta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String zonaPartenza;
    private String capolinea;
    private int tempoPrevisto;

    @ManyToMany(mappedBy = "tratte")
    private List<Mezzo> mezzi;

    @OneToMany(mappedBy = "tratta", cascade = CascadeType.ALL)
    private List<Percorrenza> percorrenze;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getZonaPartenza() { return zonaPartenza; }
    public void setZonaPartenza(String zonaPartenza) { this.zonaPartenza = zonaPartenza; }

    public String getCapolinea() { return capolinea; }
    public void setCapolinea(String capolinea) { this.capolinea = capolinea; }

    public int getTempoPrevisto() { return tempoPrevisto; }
    public void setTempoPrevisto(int tempoPrevisto) { this.tempoPrevisto = tempoPrevisto; }

    public List<Mezzo> getMezzi() { return mezzi; }
    public void setMezzi(List<Mezzo> mezzi) { this.mezzi = mezzi; }

    public List<Percorrenza> getPercorrenze() { return percorrenze; }
    public void setPercorrenze(List<Percorrenza> percorrenze) { this.percorrenze = percorrenze; }
}
