package cli;

import utility.Printer;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;

public class PaginaSegnalazioneProblemaCli {
    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public int mostraProblemiChePossonoEssereSegnalati() throws IOException {
        Clear.clear();
        Printer.print("""
        ------------------------------------------Pagina Segnalazione Problema------------------------------------------
        digita:
        1 per segnalare un passaggio a livello
        2 per segnalare un binario
        qualsiasi altro input per tornare alla home
        """);

        String scelta = bufferedReader.readLine();
        try {
            return Integer.parseInt(scelta);
        } catch (NumberFormatException e) {
            return -1;  // valore di fallback per tornare alla home
        }
    }
}

