package it.epicode.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Percorrenza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mezzo_id")
    private Mezzo mezzo;

    @ManyToOne
    @JoinColumn(name = "tratta_id")
    private Tratta tratta;

    private LocalDateTime dataPercorrenza;
    private int tempoEffettivo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Mezzo getMezzo() { return mezzo; }
    public void setMezzo(Mezzo mezzo) { this.mezzo = mezzo; }

    public Tratta getTratta() { return tratta; }
    public void setTratta(Tratta tratta) { this.tratta = tratta; }

    public LocalDateTime getDataPercorrenza() { return dataPercorrenza; }
    public void setDataPercorrenza(LocalDateTime dataPercorrenza) { this.dataPercorrenza = dataPercorrenza; }

    public int getTempoEffettivo() { return tempoEffettivo; }
    public void setTempoEffettivo(int tempoEffettivo) { this.tempoEffettivo = tempoEffettivo; }
}
