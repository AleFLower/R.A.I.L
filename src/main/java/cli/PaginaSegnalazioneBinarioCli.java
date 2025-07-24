package cli;

import controllergraficicommandlineinterface.ControllerGraficoPaginaSegnalazionePassaggioLivelloCli;
import controllergraficicommandlineinterface.ControllerGraficoPagineSegnalazioneBinarioCli;
import eccezioni.SceltaNonValidaException;
import factory.TypeOfPersistence;
import utility.Printer;
import utility.UtilityAccesso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PaginaSegnalazioneBinarioCli {
    private String localizzazione;
    private String numeroBinario;
    private String problematica;
    public void inserisciInput() throws IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            Printer.print("-----------------Pagina Segnalazione binario stradale-----------------\n" +
                    "inserisci localizzazione(digitare esc per uscire): ");
            this.localizzazione = bufferedReader.readLine();
            Printer.print("inserisci il numero del binario: ");
            this.numeroBinario = bufferedReader.readLine();
            Printer.print("inserisci la problematica da segnalare: ");
            this.problematica = bufferedReader.readLine();

            if (verificaInputUscita(localizzazione, numeroBinario, problematica)) {
                //l'utente vuole tornare alla home
                tornaAllaHomePage();
            }

            Printer.print("salvare la segnalazione? (y/n)");
            try {
                String scelta = bufferedReader.readLine().trim().toLowerCase();

                if (!scelta.equals("y") && !scelta.equals("n")) {
                    throw new SceltaNonValidaException("Scelta non valida. Devi digitare solo 'y' oppure 'n'.");
                }
                if (scelta.equals("n")) return;

                ControllerGraficoPagineSegnalazioneBinarioCli controllerGraficoPagineSegnalazioneBinarioCli = new ControllerGraficoPagineSegnalazioneBinarioCli(localizzazione, problematica, numeroBinario, UtilityAccesso.getPersistence());
                controllerGraficoPagineSegnalazioneBinarioCli.inviaDatiAlBean();
            } catch (SceltaNonValidaException e) {
                Printer.error(e.getMessage());
                inserisciInput(); // Retry
            }
        }catch (IOException e) {
                Printer.error("Errore durante la lettura dell'input.");
        }

    }

    private boolean verificaInputUscita(String localizzazione, String numeroBinario, String problematica){
        return (localizzazione.equalsIgnoreCase("esc") || numeroBinario.equalsIgnoreCase("esc") || problematica.equalsIgnoreCase("esc"));

    }
    private void tornaAllaHomePage() throws IOException {
        PaginaHome paginaHome=new PaginaHome();
        paginaHome.displayHomepage();
    }
}
