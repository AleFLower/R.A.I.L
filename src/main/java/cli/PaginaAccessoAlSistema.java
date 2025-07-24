package cli;

import controllergraficicommandlineinterface.ControllerGraficoInviaDatiAccessoAlSistemaCli;
import utility.Printer;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;

public class PaginaAccessoAlSistema {
    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public String chiediEmail() throws IOException {
        Printer.print("""
                --------------------------Pagina log in----------------------------
                Digita la tua email (digita 'esc' per tornare indietro):""");
        return bufferedReader.readLine();
    }

    public String chiediPassword() throws IOException {
        Printer.print("Inserisci la tua password:");
        return bufferedReader.readLine();
    }

    public void mostraMessaggioErrore(String messaggio) {
        Printer.error(messaggio);
    }

    public void mostraMessaggio(String messaggio) {
        Printer.print(messaggio);
    }

    public boolean confermaRegistrazione() throws IOException {
        Printer.print("Vuoi registrarti con queste credenziali? (s/n): ");
        String risposta = bufferedReader.readLine();
        return risposta.equalsIgnoreCase("s");
    }

    public String chiediUsername() throws IOException {
        Printer.print("Inserisci username: ");
        return bufferedReader.readLine();
    }

    public void attendiTastoPerContinuare(String messaggio) throws IOException {
        Printer.print(messaggio);
        bufferedReader.readLine();
    }
}

