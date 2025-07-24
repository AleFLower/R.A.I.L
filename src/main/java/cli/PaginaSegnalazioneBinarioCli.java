package cli;


import utility.Printer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PaginaSegnalazioneBinarioCli {
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public String chiediLocalizzazione() throws IOException {
        Printer.print("Inserisci localizzazione (esc per uscire):");
        return reader.readLine();
    }

    public String chiediNumeroBinario() throws IOException {
        Printer.print("Inserisci numero binario:");
        return reader.readLine();
    }

    public String chiediProblematica() throws IOException {
        Printer.print("Inserisci problematica:");
        return reader.readLine();
    }

    public boolean confermaSalvataggio() throws IOException {
        Printer.print("Salvare la segnalazione? (y/n)");
        String risposta = reader.readLine();
        if (risposta == null) {
            return false; // oppure puoi decidere un comportamento diverso
        }

        risposta = risposta.trim().toLowerCase();
        return risposta.equals("y");
    }

    public void mostraErrore(String msg) {
        Printer.error(msg);
    }

    public void mostraSuccesso() throws IOException {
        Printer.print("Segnalazione avvenuta con successo. Premi INVIO per tornare alla home.");
        String ignorato = reader.readLine(); // valore ignorato volontariamente
    }
}
