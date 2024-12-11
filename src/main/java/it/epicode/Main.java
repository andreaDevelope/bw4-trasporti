package it.epicode;
import it.epicode.entity.*;
import it.epicode.entity.table_extensions.Abbonamento;
import it.epicode.entity.table_extensions.Biglietto;
import it.epicode.entity.table_extensions.DistributoreAutomatico;
import it.epicode.entity.table_extensions.Rivenditore;
import it.epicode.enums.TipoAbbonamento;
import it.epicode.enums.TipoMezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import com.github.javafaker.Faker;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");
        EntityManager em = emf.createEntityManager();
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.println("\nSeleziona Modalità:\n1. Amministratore\n2. Utente\n0. Esci");
            int roleChoice = scanner.nextInt();
            scanner.nextLine();

            switch (roleChoice) {
                case 1 -> amministratoreMenu(scanner, em);
                case 2 -> utenteMenu(scanner, em);
                case 0 -> {
                    running = false;
                    System.out.println("Uscita dal programma.");
                }
                default -> System.out.println("Scelta non valida.");
            }
        }
        em.close();
        emf.close();
    }

    private static void amministratoreMenu(Scanner scanner, EntityManager em) {
        System.out.println("\nMenu Amministratore:\n" +
                "1. Visualizza periodi di servizio e manutenzione\n" +
                "2. Vidima un biglietto\n" +
                "3. Visualizza numero di biglietti vidimati su un mezzo\n" +
                "4. Visualizza numero di biglietti vidimati in un periodo\n" +
                "5. Gestisci stato mezzo (in servizio/manutenzione)\n" +
                "6. Assegna tratta a un mezzo\n" +
                "7. Registra percorrenza effettiva\n" +
                "8. Calcola tempo medio effettivo di percorrenza di una tratta");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> {
                System.out.println("Inserisci ID del mezzo:");
                Long mezzoId = scanner.nextLong();
                scanner.nextLine();
                Mezzo mezzo = em.find(Mezzo.class, mezzoId);

                if (mezzo != null) {
                    System.out.println("Periodo di servizio: " + mezzo.getPeriodoServizio());
                    System.out.println("Periodo di manutenzione: " + mezzo.getPeriodoManutenzione());
                } else {
                    System.out.println("Mezzo non trovato.");
                }
            }
            case 2 -> {
                System.out.println("Inserisci ID del biglietto da vidimare:");
                Long bigliettoId = scanner.nextLong();
                System.out.println("Inserisci ID del mezzo:");
                Long mezzoId = scanner.nextLong();
                scanner.nextLine();

                em.getTransaction().begin();
                Biglietto biglietto = em.find(Biglietto.class, bigliettoId);
                Mezzo mezzo = em.find(Mezzo.class, mezzoId);

                if (biglietto == null) {
                    System.out.println("Biglietto non trovato.");
                } else if (!biglietto.isValidita()) {
                    System.out.println("Il biglietto è già stato vidimato.");
                } else if (mezzo == null) {
                    System.out.println("Mezzo non trovato.");
                } else if (!mezzo.isInServizio()) {
                    System.out.println("Il mezzo non è in servizio. Impossibile vidimare il biglietto.");
                } else {
                    biglietto.setValidita(false);
                    biglietto.setMezzo(mezzo);
                    em.merge(biglietto);
                    System.out.println("Biglietto vidimato con successo sul mezzo ID: " + mezzoId);
                }
                em.getTransaction().commit();
            }
            case 3 -> {
                System.out.println("Inserisci ID del mezzo:");
                Long mezzoId = scanner.nextLong();
                scanner.nextLine();

                Long count = em.createQuery(
                                "SELECT COUNT(b) FROM Biglietto b WHERE b.mezzo.id = :mezzoId AND b.validita = false", Long.class)
                        .setParameter("mezzoId", mezzoId)
                        .getSingleResult();

                System.out.println("Numero di biglietti vidimati sul mezzo ID " + mezzoId + ": " + count);
            }
            case 4 -> {
                System.out.println("Inserisci data inizio (YYYY-MM-DD):");
                LocalDate start = LocalDate.parse(scanner.nextLine());
                System.out.println("Inserisci data fine (YYYY-MM-DD):");
                LocalDate end = LocalDate.parse(scanner.nextLine());

                Long count = em.createQuery(
                                "SELECT COUNT(b) FROM Biglietto b WHERE b.dataEmissione BETWEEN :start AND :end AND b.validita = false", Long.class)
                        .setParameter("start", start)
                        .setParameter("end", end)
                        .getSingleResult();

                System.out.println("Numero di biglietti vidimati tra " + start + " e " + end + ": " + count);
            }
            case 5 -> {
                System.out.println("Inserisci ID del mezzo:");
                Long mezzoId = scanner.nextLong();
                scanner.nextLine();
                System.out.println("Imposta stato del mezzo (SERVIZIO/MANUTENZIONE):");
                String stato = scanner.nextLine().toUpperCase();

                em.getTransaction().begin();
                Mezzo mezzo = em.find(Mezzo.class, mezzoId);
                if (mezzo != null) {
                    if (stato.equals("SERVIZIO")) {
                        mezzo.setInServizio(true);
                        mezzo.setPeriodoServizio(LocalDate.now());
                        System.out.println("Mezzo impostato in SERVIZIO.");
                    } else if (stato.equals("MANUTENZIONE")) {
                        mezzo.setInServizio(false);
                        mezzo.setPeriodoManutenzione(LocalDate.now());
                        System.out.println("Mezzo impostato in MANUTENZIONE.");
                    } else {
                        System.out.println("Stato non valido.");
                    }
                    em.merge(mezzo);
                } else {
                    System.out.println("Mezzo non trovato.");
                }
                em.getTransaction().commit();
            }
            case 6 -> {
                System.out.println("Inserisci ID mezzo:");
                Long mezzoId = scanner.nextLong();
                System.out.println("Inserisci ID tratta:");
                Long trattaId = scanner.nextLong();
                scanner.nextLine();

                em.getTransaction().begin();
                Mezzo mezzo = em.find(Mezzo.class, mezzoId);
                Tratta tratta = em.find(Tratta.class, trattaId);

                if (mezzo != null && tratta != null) {
                    mezzo.getTratte().add(tratta);
                    em.merge(mezzo);
                    System.out.println("Tratta assegnata con successo.");
                } else {
                    System.out.println("Mezzo o tratta non trovati.");
                }
                em.getTransaction().commit();
            }
            case 7 -> {
                System.out.println("Inserisci ID mezzo:");
                Long mezzoId = scanner.nextLong();
                System.out.println("Inserisci ID tratta:");
                Long trattaId = scanner.nextLong();
                System.out.println("Tempo effettivo di percorrenza (in minuti):");
                int tempoEffettivo = scanner.nextInt();
                scanner.nextLine();

                em.getTransaction().begin();
                Mezzo mezzo = em.find(Mezzo.class, mezzoId);
                Tratta tratta = em.find(Tratta.class, trattaId);

                if (mezzo != null && tratta != null) {
                    Percorrenza percorrenza = new Percorrenza();
                    percorrenza.setMezzo(mezzo);
                    percorrenza.setTratta(tratta);
                    percorrenza.setDataPercorrenza(LocalDateTime.now());
                    percorrenza.setTempoEffettivo(tempoEffettivo);

                    em.persist(percorrenza);
                    System.out.println("Percorrenza registrata con successo.");
                } else {
                    System.out.println("Mezzo o tratta non trovati.");
                }
                em.getTransaction().commit();
            }
            case 8 -> {
                System.out.println("Inserisci ID tratta:");
                Long trattaId = scanner.nextLong();
                scanner.nextLine();

                List<Integer> tempi = em.createQuery(
                                "SELECT p.tempoEffettivo FROM Percorrenza p WHERE p.tratta.id = :trattaId", Integer.class)
                        .setParameter("trattaId", trattaId)
                        .getResultList();

                if (!tempi.isEmpty()) {
                    double tempoMedio = tempi.stream().mapToInt(Integer::intValue).average().orElse(0);
                    System.out.println("Tempo medio effettivo di percorrenza: " + tempoMedio + " minuti.");
                } else {
                    System.out.println("Nessuna percorrenza registrata per questa tratta.");
                }
            }
            default -> System.out.println("Scelta non valida.");
        }
    }


    private static void utenteMenu(Scanner scanner, EntityManager em) {
        System.out.println("\nMenu Utente:\n1. Acquista biglietto\n2. Crea utente\n3. Crea tessera\n4. Acquista abbonamento\n5. Verifica validità abbonamento");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> {
                System.out.println("Inserisci ID utente:");
                Long utenteId = scanner.nextLong();
                scanner.nextLine();

                System.out.println("Codice univoco del biglietto:");
                String codice = scanner.nextLine();

                System.out.println("Acquisto da:");
                System.out.println("1. Rivenditore\n2. Distributore Automatico");
                int tipoVendita = scanner.nextInt();
                scanner.nextLine();

                em.getTransaction().begin();
                Utente utente = em.find(Utente.class, utenteId);

                if (utente != null) {
                    Biglietto biglietto = new Biglietto();
                    biglietto.setCodiceUnivoco(codice);
                    biglietto.setDataEmissione(LocalDate.now());
                    biglietto.setValidita(true);
                    biglietto.setUtente(utente);

                    boolean acquistoValido = false;

                    if (tipoVendita == 1) {
                        System.out.println("Inserisci ID rivenditore:");
                        Long rivenditoreId = scanner.nextLong();
                        scanner.nextLine();
                        Rivenditore rivenditore = em.find(Rivenditore.class, rivenditoreId);

                        if (rivenditore != null) {
                            biglietto.setRivenditore(rivenditore);
                            rivenditore.getBigliettiEmessi().add(biglietto);
                            em.merge(rivenditore);
                            acquistoValido = true;
                        } else {
                            System.out.println("Rivenditore non trovato.");
                        }

                    } else if (tipoVendita == 2) {
                        System.out.println("Inserisci ID distributore automatico:");
                        Long distributoreId = scanner.nextLong();
                        scanner.nextLine();
                        DistributoreAutomatico distributore = em.find(DistributoreAutomatico.class, distributoreId);

                        if (distributore != null && distributore.isStato()) {
                            biglietto.setDistributore(distributore);
                            distributore.getBigliettiEmessi().add(biglietto);
                            em.merge(distributore);
                            acquistoValido = true;
                        } else {
                            System.out.println("Distributore automatico non trovato oppure non in servizio.");
                        }

                    } else {
                        System.out.println("Tipo di acquisto non valido.");
                    }

                    if (acquistoValido) {
                        em.persist(biglietto);
                        System.out.println("Biglietto acquistato con successo.");
                    } else {
                        System.out.println("Acquisto non completato. Biglietto non creato.");
                    }

                } else {
                    System.out.println("Utente non trovato.");
                }

                em.getTransaction().commit();
            }

            case 2 -> {
                System.out.println("Inserisci nome:");
                String nome = scanner.nextLine();
                System.out.println("Inserisci cognome:");
                String cognome = scanner.nextLine();

                em.getTransaction().begin();
                Utente utente = new Utente();
                utente.setNome(nome);
                utente.setCognome(cognome);
                em.persist(utente);
                em.getTransaction().commit();
                System.out.println("Utente creato con successo: " + nome + " " + cognome);
            }
            case 3 -> {
                System.out.println("Inserisci ID utente:");
                Long utenteId = scanner.nextLong();
                scanner.nextLine();

                em.getTransaction().begin();
                Utente utente = em.find(Utente.class, utenteId);
                if (utente != null) {
                    if (utente.getTessera() != null) {
                        System.out.println("L'utente ha già una tessera associata. Codice: " + utente.getTessera().getCodiceUnivoco());
                    } else {
                        Tessera tessera = new Tessera();
                        tessera.setCodiceUnivoco("CARD" + System.currentTimeMillis());
                        tessera.setDataEmissione(LocalDate.now());
                        tessera.setDataScadenza(LocalDate.now().plusYears(1));
                        tessera.setUtente(utente);
                        utente.setTessera(tessera);
                        em.persist(tessera);
                        em.merge(utente);
                        System.out.println("Tessera creata con successo. Codice: " + tessera.getCodiceUnivoco());
                    }
                } else {
                    System.out.println("Utente non trovato.");
                }
                em.getTransaction().commit();
            }
            case 4 -> {
                System.out.println("Inserisci ID utente:");
                Long utenteId = scanner.nextLong();
                scanner.nextLine();
                System.out.println("Scegli tipo di abbonamento (MENSILE/SETTIMANALE):");
                String tipoAbbonamento = scanner.nextLine().toUpperCase();

                em.getTransaction().begin();
                Utente utente = em.find(Utente.class, utenteId);

                if (utente != null && utente.getTessera() != null) {
                    List<Abbonamento> abbonamenti = em.createQuery(
                                    "SELECT a FROM Abbonamento a WHERE a.card.id = :tesseraId", Abbonamento.class)
                            .setParameter("tesseraId", utente.getTessera().getId())
                            .getResultList();

                    boolean abbonamentoAttivo = abbonamenti.stream().anyMatch(a -> LocalDate.now().isBefore(a.getDataFine()));

                    if (abbonamentoAttivo) {
                        System.out.println("Esiste già un abbonamento attivo per questa tessera.");
                    } else {
                        System.out.println("Acquisto da:\n1. Rivenditore\n2. Distributore Automatico");
                        int tipoVendita = scanner.nextInt();
                        scanner.nextLine();

                        Abbonamento abbonamento = new Abbonamento();
                        abbonamento.setCard(utente.getTessera());
                        abbonamento.setTipo(TipoAbbonamento.valueOf(tipoAbbonamento));
                        abbonamento.setDataInizio(LocalDate.now());
                        abbonamento.setDataFine(LocalDate.now()
                                .plusMonths(tipoAbbonamento.equals("MENSILE") ? 1 : 0)
                                .plusWeeks(tipoAbbonamento.equals("SETTIMANALE") ? 1 : 0));
                        abbonamento.setUtente(utente);

                        boolean acquistoValido = false;

                        if (tipoVendita == 1) {
                            System.out.println("Inserisci ID rivenditore:");
                            Long rivenditoreId = scanner.nextLong();
                            scanner.nextLine();
                            Rivenditore rivenditore = em.find(Rivenditore.class, rivenditoreId);

                            if (rivenditore != null) {
                                abbonamento.setRivenditore(rivenditore);
                                rivenditore.getAbbonamentiEmessi().add(abbonamento);
                                em.merge(rivenditore);
                                acquistoValido = true;
                            } else {
                                System.out.println("Rivenditore non trovato.");
                            }

                        } else if (tipoVendita == 2) {
                            System.out.println("Inserisci ID distributore automatico:");
                            Long distributoreId = scanner.nextLong();
                            scanner.nextLine();
                            DistributoreAutomatico distributore = em.find(DistributoreAutomatico.class, distributoreId);

                            if (distributore != null && distributore.isStato()) {
                                abbonamento.setDistributore(distributore);
                                distributore.getAbbonamentiEmessi().add(abbonamento);
                                em.merge(distributore);
                                acquistoValido = true;
                            } else {
                                System.out.println("Distributore automatico non trovato oppure non in servizio.");
                            }

                        } else {
                            System.out.println("Tipo di acquisto non valido.");
                        }

                        if (acquistoValido) {
                            em.persist(abbonamento);
                            System.out.println("Abbonamento creato con successo. Scadenza: " + abbonamento.getDataFine());
                        } else {
                            System.out.println("Acquisto non completato. Abbonamento non creato.");
                        }
                    }
                } else {
                    System.out.println("Utente non trovato o tessera non associata.");
                }

                em.getTransaction().commit();
            }
            case 5 -> {
                System.out.println("Inserisci ID utente:");
                Long utenteId = scanner.nextLong();
                scanner.nextLine();

                em.getTransaction().begin();
                Utente utente = em.find(Utente.class, utenteId);
                if (utente != null && utente.getTessera() != null) {
                    List<Abbonamento> abbonamenti = em.createQuery(
                                    "SELECT a FROM Abbonamento a WHERE a.card.id = :tesseraId", Abbonamento.class)
                            .setParameter("tesseraId", utente.getTessera().getId())
                            .getResultList();

                    if (abbonamenti.isEmpty()) {
                        System.out.println("Nessun abbonamento trovato per questa tessera.");
                    } else {
                        Abbonamento abbonamento = abbonamenti.get(abbonamenti.size() - 1);
                        if (LocalDate.now().isBefore(abbonamento.getDataFine())) {
                            System.out.println("Abbonamento valido fino al: " + abbonamento.getDataFine());
                        } else {
                            System.out.println("Abbonamento scaduto il: " + abbonamento.getDataFine());
                        }
                    }
                } else {
                    System.out.println("Utente non trovato o tessera non associata.");
                }
                em.getTransaction().commit();
            }

            default -> System.out.println("Scelta non valida.");
        }
    }

}