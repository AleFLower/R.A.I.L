package cli;

import controllergraficicommandlineinterface.ControllerGraficoPagineSegnalazioneBinarioCli;
import eccezioni.SceltaNonValidaException;
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
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            Printer.print("""
                -----------------Pagina Segnalazione binario stradale-----------------
                inserisci localizzazione (digitare esc per uscire): 
                """);
            this.localizzazione = bufferedReader.readLine();

            Printer.print("inserisci il numero del binario: ");
            this.numeroBinario = bufferedReader.readLine();

            Printer.print("inserisci la problematica da segnalare: ");
            this.problematica = bufferedReader.readLine();

            if (verificaInputUscita(localizzazione, numeroBinario, problematica)) {
                tornaAllaHomePage();
                return;
            }

            gestisciConfermaSalvataggio(bufferedReader);  //for sonar , lo suggerisce lui di non usare try annidati
            //magari cambia nome al metodo

        } catch (IOException e) {
            Printer.error("Errore durante la lettura dell'input.");
        }
    }

    private void gestisciConfermaSalvataggio(BufferedReader reader) throws IOException {
        Printer.print("salvare la segnalazione? (y/n)");
        try {
            String scelta = reader.readLine().trim().toLowerCase();
            if (!scelta.equals("y") && !scelta.equals("n")) {
                throw new SceltaNonValidaException("Scelta non valida. Devi digitare solo 'y' oppure 'n'.");
            }
            if (scelta.equals("n")) {
                return;
            }

            ControllerGraficoPagineSegnalazioneBinarioCli controllerGrafico =
                    new ControllerGraficoPagineSegnalazioneBinarioCli(localizzazione, problematica, numeroBinario, UtilityAccesso.getPersistence());
            controllerGrafico.inviaDatiAlBean();

        } catch (SceltaNonValidaException e) {
            Printer.error(e.getMessage());
            gestisciConfermaSalvataggio(reader); // retry
        }
    }

    private boolean verificaInputUscita(String localizzazione, String numeroBinario, String problematica) {
        return localizzazione.equalsIgnoreCase("esc")
                || numeroBinario.equalsIgnoreCase("esc")
                || problematica.equalsIgnoreCase("esc");
    }

    private void tornaAllaHomePage() throws IOException {
        PaginaHome paginaHome = new PaginaHome();
        paginaHome.displayHomepage();
    }
}
