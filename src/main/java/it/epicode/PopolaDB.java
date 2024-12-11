package it.epicode;

import com.github.javafaker.Faker;
import it.epicode.entity.*;
import it.epicode.entity.table_extensions.*;
import it.epicode.enums.TipoAbbonamento;
import it.epicode.enums.TipoMezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PopolaDB {
    static Faker faker = new Faker(new Locale("it"));
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");
    EntityManager em = emf.createEntityManager();

    public static void main(String[] args) {
        PopolaDB popolaDB = new PopolaDB();
        popolaDB.popola();
    }

    public void popola() {
        em.getTransaction().begin();

        // Creazione dei distributori e rivenditori (necessari per le relazioni)
        List<DistributoreAutomatico> distributori = new ArrayList<>();
        List<Rivenditore> rivenditori = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DistributoreAutomatico distributore = new DistributoreAutomatico();
            distributore.setStato(faker.random().nextBoolean());
            distributori.add(distributore);
            em.persist(distributore);

            Rivenditore rivenditore = new Rivenditore();
            rivenditore.setNome(faker.company().name());
            rivenditore.setIndirizzo(faker.address().streetAddress());
            rivenditori.add(rivenditore);
            em.persist(rivenditore);
        }

        // Creazione dei mezzi
        List<Mezzo> mezzi = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Mezzo mezzo = new Mezzo();
            mezzo.setTipo(i % 2 == 0 ? TipoMezzo.AUTOBUS : TipoMezzo.TRAM);
            mezzo.setCapienza(faker.number().numberBetween(20, 50));
            mezzo.setInServizio(faker.random().nextBoolean());
            mezzo.setPeriodoServizio(LocalDate.now());
            mezzo.setPeriodoManutenzione(LocalDate.now().plusMonths(3));
            mezzo.setBigliettiVidimati(new ArrayList<>());
            mezzo.setTratte(new ArrayList<>());
            mezzi.add(mezzo);
            em.persist(mezzo);
        }

        // Creazione degli utenti e delle tessere
        List<Utente> utenti = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Utente utente = new Utente();
            utente.setNome(faker.name().firstName());
            utente.setCognome(faker.name().lastName());

            // Creazione della tessera
            Tessera tessera = new Tessera();
            tessera.setCodiceUnivoco(faker.code().ean8());
            tessera.setDataEmissione(LocalDate.now());
            tessera.setDataScadenza(LocalDate.now().plusYears(1));
            em.persist(tessera); // Persist before associating
            utente.setTessera(tessera);

            utenti.add(utente);
            em.persist(utente);
        }

        // Creazione delle tratte
        List<Tratta> tratte = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Tratta tratta = new Tratta();
            tratta.setZonaPartenza(faker.address().city());
            tratta.setCapolinea(faker.address().city());
            tratta.setTempoPrevisto(faker.number().numberBetween(30, 60));
            tratta.setMezzi(new ArrayList<>());
            tratta.setPercorrenze(new ArrayList<>());
            tratte.add(tratta);
            em.persist(tratta);
        }

        // Creazione dei biglietti e abbonamenti
        List<Biglietto> biglietti = new ArrayList<>();
        List<Abbonamento> abbonamenti = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            // Creazione del biglietto
            Biglietto biglietto = new Biglietto();
            biglietto.setDataEmissione(LocalDate.now());
            biglietto.setValidita(faker.random().nextBoolean());
            biglietto.setCodiceUnivoco(faker.code().ean8());
            biglietto.setMezzo(mezzi.get(i % mezzi.size()));  // Associa il mezzo
            biglietto.setDistributore(distributori.get(i % distributori.size())); // Associa il distributore
            biglietto.setUtente(utenti.get(i % utenti.size())); // Associa l'utente
            biglietto.setRivenditore(rivenditori.get(i % rivenditori.size())); // Associa il rivenditore
            biglietti.add(biglietto);
            em.persist(biglietto);

            // Creazione dell'abbonamento
            Tessera tessera = new Tessera(); // Create a new Tessera
            em.persist(tessera); // Persist it first
            Abbonamento abbonamento = new Abbonamento();
            abbonamento.setCodiceUnivoco(faker.code().ean8());
            abbonamento.setTipo(TipoAbbonamento.MENSILE);
            abbonamento.setDataInizio(LocalDate.now());
            abbonamento.setDataFine(LocalDate.now().plusMonths(1));
            abbonamento.setUtente(utenti.get(i % utenti.size()));  // Associa l'utente
            abbonamento.setCard(tessera);  // Associa la tessera
            abbonamento.setRivenditore(rivenditori.get(i % rivenditori.size()));  // Associa il rivenditore
            abbonamento.setDistributore(distributori.get(i % distributori.size()));  // Associa il distributore
            abbonamenti.add(abbonamento);
            em.persist(abbonamento);
        }

        for (int i = 0; i < 5; i++) {
            // Creazione del biglietto
            Biglietto biglietto = new Biglietto();
            biglietto.setDataEmissione(LocalDate.now());
            biglietto.setValidita(faker.random().nextBoolean());
            biglietto.setCodiceUnivoco(faker.code().ean8());
            biglietto.setMezzo(mezzi.get(i % mezzi.size()));  // Associa il mezzo
            biglietto.setDistributore(distributori.get(i % distributori.size())); // Associa il distributore
            biglietto.setUtente(utenti.get(i % utenti.size())); // Associa l'utente
            biglietto.setRivenditore(rivenditori.get(i % rivenditori.size())); // Associa il rivenditore
            biglietti.add(biglietto);
            em.persist(biglietto);

            // Creazione dell'abbonamento
            Tessera tessera = new Tessera(); // Create a new Tessera
            em.persist(tessera); // Persist it first
            Abbonamento abbonamento = new Abbonamento();
            abbonamento.setCodiceUnivoco(faker.code().ean8());
            abbonamento.setTipo(TipoAbbonamento.SETTIMANALE);
            abbonamento.setDataInizio(LocalDate.now());
            abbonamento.setDataFine(LocalDate.now().plusMonths(1));
            abbonamento.setUtente(utenti.get(i % utenti.size()));  // Associa l'utente
            abbonamento.setCard(tessera);  // Associa la tessera
            abbonamento.setRivenditore(rivenditori.get(i % rivenditori.size()));  // Associa il rivenditore
            abbonamento.setDistributore(distributori.get(i % distributori.size()));  // Associa il distributore
            abbonamenti.add(abbonamento);
            em.persist(abbonamento);
        }

        // Creazione delle percorrenze
        List<Percorrenza> percorrenze = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Percorrenza percorrenza = new Percorrenza();
            percorrenza.setMezzo(mezzi.get(i % mezzi.size()));  // Associa il mezzo
            percorrenza.setTratta(tratte.get(i % tratte.size()));  // Associa la tratta
            percorrenza.setDataPercorrenza(LocalDateTime.now());
            percorrenza.setTempoEffettivo(faker.number().numberBetween(20, 90));
            percorrenze.add(percorrenza);
            em.persist(percorrenza);
        }

        em.getTransaction().commit();
    }
}
