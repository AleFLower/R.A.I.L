package cli;


import utility.Printer;
import utility.UtilityAccesso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class PaginaHome {

    public int mostraMenuHome() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Clear.clear();
        String statoAccesso = (UtilityAccesso.getCodiceUtente() == null) ? "Login" : "Logout";

        Printer.print("""
                ------------------------------------------HOME------------------------------------------
                digita:
                1 per segnalare un problema
                2 per lasciare una recensione
                3 per suggerire nuove funzioni
                4 per %s
                5 per visualizzare le segnalazioni attive
                6 per visualizzare le segnalazioni risolte
                7 per uscire dall'applicazione
                """.formatted(statoAccesso));

        try {
            return Integer.parseInt(bufferedReader.readLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void mostraFunzioneNonDisponibile(String funzione) {
        Printer.print("La funzione per %s non è ancora disponibile.".formatted(funzione));
    }

    public void mostraErroreInput() {
        Printer.error("Input non valido. Riprova.");
    }

    public void mostraMessaggioUscita() {
        Printer.print("Grazie per aver usato l'applicazione. Arrivederci!");
    }

    public void mostraMessaggio(String s) {
    Printer.print(s);
    }
}

