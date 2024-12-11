package it.epicode.entity;

import it.epicode.entity.Tratta;
import it.epicode.entity.table_extensions.Biglietto;
import it.epicode.enums.TipoMezzo;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Mezzo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoMezzo tipo;

    private int capienza;

    private boolean inServizio;

    private LocalDate periodoServizio;
    private LocalDate periodoManutenzione;

    @OneToMany(mappedBy = "mezzo", cascade = CascadeType.ALL)
    private List<Biglietto> bigliettiVidimati;

    @ManyToMany
    private List<Tratta> tratte;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoMezzo getTipo() {
        return tipo;
    }

    public void setTipo(TipoMezzo tipo) {
        this.tipo = tipo;
    }

    public int getCapienza() {
        return capienza;
    }

    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    public boolean isInServizio() {
        return inServizio;
    }

    public void setInServizio(boolean inServizio) {
        this.inServizio = inServizio;
    }

    public LocalDate getPeriodoServizio() {
        return periodoServizio;
    }

    public void setPeriodoServizio(LocalDate periodoServizio) {
        this.periodoServizio = periodoServizio;
    }

    public LocalDate getPeriodoManutenzione() {
        return periodoManutenzione;
    }

    public void setPeriodoManutenzione(LocalDate periodoManutenzione) {
        this.periodoManutenzione = periodoManutenzione;
    }

    public List<Biglietto> getBigliettiVidimati() {
        return bigliettiVidimati;
    }

    public void setBigliettiVidimati(List<Biglietto> bigliettiVidimati) {
        this.bigliettiVidimati = bigliettiVidimati;
    }

    public List<Tratta> getTratte() {
        return tratte;
    }

    public void setTratte(List<Tratta> tratte) {
        this.tratte = tratte;
    }
}
