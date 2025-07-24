package cli;

import eccezioni.SceltaNonValidaException;
import utility.Printer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PaginaSegnalazionePassaggioALivelloCli {
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public String chiediCodicePL() throws IOException {
        Clear.clear();
        Printer.print("""
                ------------------Pagina Segnalazione Passaggio a Livello------------------
                Inserisci numero del passaggio a livello da segnalare (digitare 'esc' per uscire):""");
        return reader.readLine();
    }

    public String chiediLocalizzazione() throws IOException {
        Printer.print("Inserisci la posizione (digitare 'esc' per uscire): ");
        return reader.readLine();
    }

    public String chiediProblematica() throws IOException {
        Printer.print("Inserisci la problematica del passaggio a livello:");
        return reader.readLine();
    }

    public boolean confermaSalvataggio() throws IOException, SceltaNonValidaException {
        Printer.print("Salvare la segnalazione? (y/n)");

        String scelta = reader.readLine();

        if (scelta == null) {
            throw new SceltaNonValidaException("Input interrotto o nullo.");
        }

        scelta = scelta.trim().toLowerCase();

        if (!scelta.equals("y") && !scelta.equals("n")) {
            throw new SceltaNonValidaException("Scelta non valida. Devi digitare solo 'y' oppure 'n'.");
        }

        return scelta.equals("y");
    }

    public void mostraMessaggioSuccesso() throws IOException {
        Printer.print("Segnalazione avvenuta con successo! Premi INVIO per tornare alla home.");
        String ignore = reader.readLine();
    }

    public void mostraErrore(String messaggio) {
        Printer.error(messaggio);
    }
}
